package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.utils.Distance;
import lapr.project.utils.Physics;
import lapr.project.utils.graph.AdjacencyMatrixGraph;
import lapr.project.utils.graph.GraphAlgorithms;
import lapr.project.utils.graphbase.Graph;
import lapr.project.utils.graphbase.GraphAlgorithmsB;
import oracle.ucp.util.Pair;

import java.sql.SQLException;
import java.util.*;

public class OrderController {

    private final ClientOrderHandler clientOrderHandler;
    private final CourierDataHandler courierDataHandler;
    private final AddressDataHandler addressDataHandler;
    private final ClientDataHandler clientDataHandler;
    private final PharmacyDataHandler pharmacyDataHandler;
    private final DeliveryHandler deliveryHandler;
    private final VehicleHandler vehicleHandler;

    private Graph<Address,Double> citygraph;

    public OrderController(ClientOrderHandler clh, CourierDataHandler cdh, AddressDataHandler addressDataHandler,
                           ClientDataHandler clientDataHandler, PharmacyDataHandler pharmacyDataHandler,
                           DeliveryHandler deliveryHandler, VehicleHandler vehicleHandler) {
        this.clientOrderHandler = clh;
        this.courierDataHandler = cdh;
        this.addressDataHandler = addressDataHandler;
        this.clientDataHandler = clientDataHandler;
        this.pharmacyDataHandler = pharmacyDataHandler;
        this.deliveryHandler = deliveryHandler;
        this.vehicleHandler = vehicleHandler;
        citygraph = new Graph<>(true);
    }
    
    public Courier getCourierByEmail(String email) {
        return courierDataHandler.getCourierByEmail(email);
    }

    public Courier getCourierByNIF(double nif) {
        return courierDataHandler.getCourier(nif);
    }

    public Map<Integer, ClientOrder> getUndoneOrders(int pharID) {
        return clientOrderHandler.getUndoneOrders(pharID);
    }

    public Graph<Address, Double> buildGraph(List<Address> addresses) {

        for (Address add : addresses) {
            citygraph.insertVertex(add);
        }

        for (int i = 0; i < addresses.size(); i++) {
            for(int p=0; p<addresses.size();p++){
                if(!addresses.get(i).equals(addresses.get(p))){
                    Address add1 = addresses.get(i);
                    Address add2 = addresses.get(p);
                    double weight = Distance.distanceBetweenTwoAddresses(add1.getLatitude(), add1.getLongitude(), add2.getLatitude(), add2.getLongitude());
                    citygraph.insertEdge(add1, add2, 1.0, weight);
                }
            }
        }

        return citygraph;
    }

    /**
     * Method used to create an adjacency matrix of points of interest
     *
     * @param graph Graph that contains the points of interest that we be used
     * to create the adjacency matrix
     * @return adjacency matrix of points of interest
     */
    public AdjacencyMatrixGraph generateAdjacencyMatrixGraph(Graph<Address, Double> graph) {

        AdjacencyMatrixGraph matrizAdjacencias = new AdjacencyMatrixGraph(graph.numVertices());

        for (Address p1 : graph.vertices()) {
            matrizAdjacencias.insertVertex(p1);
        }

        for (Address p1 : graph.vertices()) {
            for (Address p2 : graph.vertices()) {
                if (graph.getEdge(p1, p2) != null && !p1.equals(p2)) {
                    matrizAdjacencias.insertEdge(p1, p2, graph.getEdge(p1, p2).getWeight());
                }
            }
        }
        matrizAdjacencias = GraphAlgorithms.transitiveClosure(matrizAdjacencias, null);
        return matrizAdjacencias;
    }

    public Pharmacy getPharmByID(int pharmacyID) {
        return pharmacyDataHandler.getPharmacyByID(pharmacyID);
    }

    public List<Pair<LinkedList<Address>, Double>> processDelivery(List<ClientOrder> ordersInThisDelivery, Pharmacy pharmacy) throws SQLException {
        List<Address> addresses = addressDataHandler.getAllAddresses();
        ArrayList<Address> addressesToMakeDelivery = new ArrayList<>();
        generateAdjacencyMatrixGraph(buildGraph(addresses));


        Address startPoint = null;

        for (ClientOrder co : ordersInThisDelivery) {
            Client client = clientDataHandler.getClientByID(co.getClientId());
            for (Address add : addresses) {
                if (add.getLatitude() == client.getLatitude() && add.getLongitude() == client.getLongitude()) {
                    addressesToMakeDelivery.add(add);
                }
                if (add.getLatitude() == pharmacy.getLatitude() && add.getLongitude() == pharmacy.getLongitude()) {
                    startPoint = add;
                }
            }
        }

        List<Pair<Address, Double>> distanceToAddresses = new ArrayList<>();
        for (Address a : addressesToMakeDelivery) {
            //double distance = GraphAlgorithmsB.shortestPath(citygraph, startPoint, a, new LinkedList<>());
            double distance = Distance.distanceBetweenTwoAddresses(startPoint.getLatitude(), startPoint.getLongitude(), a.getLatitude(), a.getLongitude());
            distanceToAddresses.add(new Pair<>(a,distance));
        }

        Collections.sort(distanceToAddresses, (o1, o2) -> Double.compare(o2.get2nd(), o1.get2nd()));

        Address closer = distanceToAddresses.get(0).get1st();

        List<Address> deliveryPath = new ArrayList<>();

        deliveryPath.add(startPoint);
        deliveryPath.add(closer);
        double distance = distanceToAddresses.get(0).get2nd();

        for(int i=1; i<distanceToAddresses.size();i++){
            List<Address> goingPath = new ArrayList<>();
            distance += GraphAlgorithmsB.shortestPath(citygraph, closer, distanceToAddresses.get(i).get1st(), goingPath);
            goingPath.remove(0);
            deliveryPath.addAll(goingPath);
            closer = distanceToAddresses.get(i).get1st();
        }



       /*
        LinkedList<Address> goingPath = new LinkedList<>();
        LinkedList<Address> returnPath = new LinkedList<>();
        LinkedList<Address> finalPath = new LinkedList<>();
        double distance = GraphAlgorithmsB.shortestPath(citygraph, startPoint, moreDistant, goingPath);
        distance += GraphAlgorithmsB.shortestPath(citygraph, moreDistant, startPoint, returnPath);
        returnPath.remove(0);

        finalPath.addAll(goingPath);
        finalPath.addAll(returnPath);

        List<Pair<LinkedList<Address>, Double>> returnList = new ArrayList<>();
        returnList.add(new Pair(finalPath, distance));


        return returnList;*/


        return null;
    }

    public boolean createDroneDelivery(List<ClientOrder> ordersInThisDelivery, Pharmacy pharmacy, double weight) throws SQLException {
        if(ordersInThisDelivery.isEmpty()){
                return false;
        }
        double distance = processDelivery(ordersInThisDelivery, pharmacy).get(0).get2nd();
        double necessaryEnergy = getTotalEnergy(distance, weight);
        List<Vehicle> dronesAvailable = getDronesAvailable(pharmacy.getId(), necessaryEnergy);
        int idDroneDelivery = 0;
        if(dronesAvailable.isEmpty()){
            return false;
        }

        idDroneDelivery = dronesAvailable.get(0).getId();

        Delivery d = new Delivery(necessaryEnergy, distance, weight, 0, idDroneDelivery);
        deliveryHandler.addDelivery(d);
        return true;
    }

    public boolean createDelivery(List<ClientOrder> ordersInThisDelivery, Pharmacy pharmacy, double weight) throws SQLException {
        double distance = processDelivery(ordersInThisDelivery, pharmacy).get(0).get2nd();
        List<Courier> couriersAvailable = getAvailableCouriers(pharmacy.getId());

        if(couriersAvailable.isEmpty()){
           return false;
        }
        Courier deliveryCourier = couriersAvailable.get(0);

        double weightCourierAndOrders = deliveryCourier.getWeight() + getOrdersWeight(ordersInThisDelivery);
        double necessaryEnergy = getTotalEnergy(distance, weightCourierAndOrders);

        Delivery d = new Delivery(necessaryEnergy, distance, weight, deliveryCourier.getIdCourier(), 0);
        deliveryHandler.addDelivery(d);


        return true;
    }

    public String getCourierEmail() {
        return UserSession.getInstance().getUser().getEmail();
    }

    public double getTotalEnergy(double distance, double totalWeight) {
        Physics p = new Physics();
        return p.getNecessaryEnergy(distance, totalWeight);
    }

    public double getOrdersWeight(List<ClientOrder> ordersInThisDelivery) {
        double weightSum = 0;

        for (ClientOrder c : ordersInThisDelivery) {
            weightSum += c.getFinalWeight();
        }

        return weightSum;
    }

    public List<Courier> getAvailableCouriers(int idPhar){
        return courierDataHandler.getAvailableCouriers(idPhar);

    }

    public List<Delivery> getDeliverysByCourierId(int idCourier) {
        return deliveryHandler.getDeliverysByCourierId(idCourier);
    }

    public List<Pharmacy> getAllPharmacies() {

        return pharmacyDataHandler.getAllPharmacies();

    }

    public List<Vehicle> getDronesAvailable(int idPhar, double necessaryEnergy) {
        return vehicleHandler.getDronesAvailable(idPhar, necessaryEnergy);
    }
}



