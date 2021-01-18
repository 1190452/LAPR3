package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.utils.Physics;
import lapr.project.utils.graph.AdjacencyMatrixGraph;
import lapr.project.utils.graph.EdgeAsDoubleGraphAlgorithms;
import lapr.project.utils.graph.GraphAlgorithms;
import lapr.project.utils.graphbase.Graph;
import lapr.project.utils.graphbase.GraphAlgorithmsB;
import oracle.ucp.util.Pair;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderController {

    private final ClientOrderHandler clientOrderHandler;
    private final CourierDataHandler courierDataHandler;
    private final AddressDataHandler addressDataHandler;
    private final ClientDataHandler clientDataHandler;
    private final PharmacyDataHandler pharmacyDataHandler;
    private final DeliveryHandler deliveryHandler;
    private final VehicleHandler vehicleHandler;
    private final RefillStockDataHandler refillStockDataHandler;
    private final Graph<Address, Double> citygraph;


    public OrderController(ClientOrderHandler clh, CourierDataHandler cdh, AddressDataHandler addressDataHandler,
                           ClientDataHandler clientDataHandler, PharmacyDataHandler pharmacyDataHandler,
                           DeliveryHandler deliveryHandler, VehicleHandler vehicleHandler, RefillStockDataHandler refillStockDataHandler) {
        this.clientOrderHandler = clh;
        this.courierDataHandler = cdh;
        this.addressDataHandler = addressDataHandler;
        this.clientDataHandler = clientDataHandler;
        this.pharmacyDataHandler = pharmacyDataHandler;
        this.deliveryHandler = deliveryHandler;
        this.vehicleHandler = vehicleHandler;
        this.refillStockDataHandler = refillStockDataHandler;
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

    public Pharmacy getPharmByID(int pharmacyID) {
        return pharmacyDataHandler.getPharmacyByID(pharmacyID);
    }

    public Vehicle createDroneDelivery(LinkedList<ClientOrder> ordersInThisDelivery, Pharmacy pharmacy, double weight) throws SQLException {
        if (ordersInThisDelivery.isEmpty()) {
            return null;
        }
        double distance = processDelivery(ordersInThisDelivery, pharmacy).get2nd();
        double necessaryEnergy = 0; //getTotalEnergy(distance, weight, 2, frontalArea,elevationInitial,finalElevation, latitude1, latitude2, longitude1, longitude2);
        List<Vehicle> dronesAvailable = getDronesAvailable(pharmacy.getId(), necessaryEnergy);
        Vehicle droneDelivery;
        if (dronesAvailable.isEmpty()) {
            return null;
        }

        droneDelivery = dronesAvailable.get(0);

        Delivery d = new Delivery(necessaryEnergy, distance, weight, 0, droneDelivery.getId());
        int id = deliveryHandler.addDelivery(d);

        for (ClientOrder c: ordersInThisDelivery){
            updateStatusOrder(id, c.getOrderId());
        }

        sendMailToAllClients(deliveryHandler.getDeliveryByDroneId(droneDelivery.getId()).getId());

        return droneDelivery;
    }

    public boolean createDeliveryByScooter(LinkedList<ClientOrder> ordersInThisDelivery, Pharmacy pharmacy, double weight) throws SQLException {
        double distance = processDelivery(ordersInThisDelivery, pharmacy).get2nd();
        List<Courier> couriersAvailable = getAvailableCouriers(pharmacy.getId());

        if (couriersAvailable.isEmpty()) {
            return false;
        }
        Courier deliveryCourier = couriersAvailable.get(0);

        double weightCourierAndOrders = deliveryCourier.getWeight() + getOrdersWeight(ordersInThisDelivery);
        double necessaryEnergy = 0; //getTotalEnergy(distance, weight, 2, frontalArea,elevationInitial,finalElevation, latitude1, latitude2, longitude1, longitude2);

        Delivery d = new Delivery(necessaryEnergy, distance, weightCourierAndOrders, deliveryCourier.getIdCourier(), 0);

        int idDelivery=deliveryHandler.addDelivery(d);

        for (ClientOrder c: ordersInThisDelivery){
            updateStatusOrder(idDelivery, c.getOrderId());
        }

        return true;
    }

    public boolean updateStatusOrder(int idDelivery, int orderId) {
        return clientOrderHandler.updateStatusOrder(idDelivery, orderId);

    }

    public Pair<LinkedList<Address>, Double> processDelivery(LinkedList<ClientOrder> ordersInThisDelivery, Pharmacy pharmacy) throws SQLException {
        List<Address> addresses = addressDataHandler.getAllAddresses();
        ArrayList<Address> addressesToMakeDelivery = new ArrayList<>();
        Graph<Address, Double> graph = buildGraph(addresses);
        AdjacencyMatrixGraph<Address, Double> matrix = generateAdjacencyMatrixGraph(graph);

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
        addressesToMakeDelivery.add(startPoint);
        return shortestPathForDelivery(addressesToMakeDelivery, matrix, startPoint, graph);
    }

    public Graph<Address, Double> buildGraph(List<Address> addresses) {

        for (Address add : addresses) {
            citygraph.insertVertex(add);
        }

        for (int i = 0; i < addresses.size(); i++) {
            for (int p = 0; p < addresses.size(); p++) {
                if (!addresses.get(i).equals(addresses.get(p))) {
                    Address add1 = addresses.get(i);
                    Address add2 = addresses.get(p);
                    double weight = Physics.calculateDistanceWithElevation(add1.getLatitude(), add2.getLatitude(), add1.getLongitude(), add2.getLongitude(), add1.getAltitude(), add2.getAltitude());
                    citygraph.insertEdge(add1, add2, 1.0, weight);
                }
            }
        }

        return citygraph;
    }

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

    public Pair<LinkedList<Address>, Double> shortestPathForDelivery(List<Address> addressList, AdjacencyMatrixGraph<Address, Double> matrix, Address startingPoint, Graph<Address, Double> graph) {

        List<Pair<LinkedList<Address>, Double>> permutations = getPermutations(addressList, matrix);
    if (!permutations.isEmpty()) {
            double sum = 0;

            List<Pair<LinkedList<Address>, Double>> permutationsToRemove = new ArrayList<>();
            LinkedList<Address> path = new LinkedList<>();
            LinkedList<Address> auxpath = new LinkedList<>();
            Address next = startingPoint;

            while (!addressList.isEmpty()) {

                double smallestDistance = Double.MAX_VALUE;

                for (Pair<LinkedList<Address>, Double> p : permutations) {
                    if (p.get1st().getFirst().equals(next) && p.get2nd().compareTo(smallestDistance) < 0) {
                        auxpath.clear();
                        auxpath.addAll(p.get1st());
                        smallestDistance = p.get2nd();
                    }
                }

                for (Pair<LinkedList<Address>, Double> p : permutations) {
                    if (p.get1st().getFirst().equals(next) || p.get1st().getLast().equals(next)) {
                        permutationsToRemove.add(p);
                    }
                }

                sum += smallestDistance;

                next = auxpath.getLast();

                addressList.removeAll(auxpath);
                path.addAll(auxpath);
                auxpath.clear();
                permutations.removeAll(permutationsToRemove);
                permutationsToRemove.clear();
            }

            auxpath = new LinkedList<>();
            sum += GraphAlgorithmsB.shortestPath(graph, path.getLast(), startingPoint, auxpath);
            auxpath.remove(0);
            path.addAll(auxpath);

            return new Pair<>(path, sum);
        }
        return null;
    }

    public List<Pair<LinkedList<Address>, Double>> getPermutations(List<Address> addressList, AdjacencyMatrixGraph<Address, Double> matrix) {
        List<Pair<LinkedList<Address>, Double>> permutations = new ArrayList<>();
        for (Address a1 : addressList) {
            for (Address a2 : addressList) {
                if (!a1.equals(a2)) {
                    LinkedList<Address> path = new LinkedList<>();
                    double distance = EdgeAsDoubleGraphAlgorithms.shortestPath(matrix, a1, a2, path);
                    permutations.add(new Pair<>(path, distance));
                }
            }
        }
        return permutations;
    }

    public String getCourierEmail() {
        return UserSession.getInstance().getUser().getEmail();
    }

    public double getTotalEnergy(double distance, double totalWeight, int typeVehicle,double frontalArea,double elevationInitial, double elevationFinal, double latitude1, double latitude2, double longitude1, double longitude2) {
        Physics p = new Physics();
        return p.getNecessaryEnergy(Physics.calculateDistanceWithElevation(latitude1, latitude2, longitude1, longitude2, elevationInitial, elevationFinal), totalWeight,typeVehicle,frontalArea, (elevationFinal - elevationInitial));
    }

    public double getOrdersWeight(List<ClientOrder> ordersInThisDelivery) {
        double weightSum = 0;

        for (ClientOrder c : ordersInThisDelivery) {
            weightSum += c.getFinalWeight();
        }

        return weightSum;
    }

    public List<Courier> getAvailableCouriers(int idPhar) {
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

    public void updateStatusDelivery(int delId) {
        deliveryHandler.updateStatusDelivery(delId);
    }

    public void sendMailToAllClients(int id) {
        List<String> mails = clientOrderHandler.getClientEmailByDelivery(id);
        for (String mail : mails) {
            EmailAPI.sendDeliveryEmailToClient(mail);
        }
    }

    public List<RestockOrder> getRestockList(int pharmID) {
        return new RestockDataHandler().getRestockList(pharmID);
    }

    public Product getProduct(String productName) {
        return new ProductDataHandler().getProduct(productName);
    }

    public Pair<Integer, String> createRestockRequestByEletricScooter(List<RestockOrder> restocklistToMakeDelivery, double weightSum, List<Pharmacy> points) {

        Pharmacy phar = getPharmByID(restocklistToMakeDelivery.get(0).getPharmReceiverID());
        Pair<LinkedList<Address>, Double> path = getPath(restocklistToMakeDelivery);
        List<Courier> couriersAvailable = getAvailableCouriers(phar.getId());

        if (couriersAvailable.isEmpty()) {
            return null;
        }

        Courier deliveryCourier = couriersAvailable.get(0);
        double distance = path.get2nd();
        double necessaryEnergy = 0; //Physics.getNecessaryEnergy(distance, weightSum);

        List<Vehicle> vehicleList = vehicleHandler.getAllScooterAvaiables(phar.getId());
        for (Vehicle vehicle : vehicleList) {
            double actualBattery = vehicle.getActualBattery();
            if (necessaryEnergy < actualBattery) {
                String licensePlate = vehicle.getLicensePlate();
                int idVehicle = vehicle.getId();
                Park park = vehicleHandler.getParkByPharmacyId(phar.getId(), 1);
                int parkId = park.getId();
                vehicleHandler.updateStatusToParked(licensePlate);
                int isCharging = vehicle.getIsCharging();
                if (isCharging == 1) {
                    new ParkHandler().updateActualChargingPlacesA(parkId);
                    vehicleHandler.updateIsChargingN(licensePlate);
                } else {
                    new ParkHandler().updateActualCapacityA(parkId);
                }

                vehicleHandler.updateStatusToBusy(licensePlate);

                RefillStock r = new RefillStock(necessaryEnergy, distance, weightSum, deliveryCourier.getIdCourier(), idVehicle);
                int idRS = refillStockDataHandler.addRefillStock(r);
                callTimer("Delivery RestockOrder Created...");
                for(Pharmacy p : points){
                    EmailAPI.sendMail(phar.getEmail(), "RestockOrder", "The products you required are already on their away!");
                }

                callTimer("Delivery Restock Created...");
                for (RestockOrder co : restocklistToMakeDelivery) {
                    new RestockDataHandler().updateStatusRestock(co.getId());
                    Client c = clientDataHandler.getClientByClientOrderID(co.getClientOrderID());
                    EmailAPI.sendMail(c.getEmail(), "RestockOrder", "The product(s) that you are waiting for is/are already available. Your products will be delivered soon");
                }

                refillStockDataHandler.updateStatusToDone(idRS);
                int id = phar.getId();
                return new Pair<>(id, licensePlate);
            }
        }
            return null;
    }

    public Pair<Integer, String> createRestockRequestByDrone(List<RestockOrder> restocklistToMakeDelivery, double weightSum, List<Pharmacy> points) {
        Pharmacy phar = getPharmByID(restocklistToMakeDelivery.get(0).getPharmReceiverID());
        Pair<LinkedList<Address>, Double> path = getPath(restocklistToMakeDelivery);
        double distance = path.get2nd();
        double necessaryEnergy = 0; //Physics.getNecessaryEnergy(distance, weightSum);
        List<Vehicle> dronesAvailable = vehicleHandler.getDronesAvailable(phar.getId(), necessaryEnergy);

        if (dronesAvailable.isEmpty()) {
            return null;
        } else {

            Vehicle vehicle = dronesAvailable.get(0);

            String licensePlate = vehicle.getLicensePlate();
            int idVehicle = vehicle.getId();
            Park park = vehicleHandler.getParkByPharmacyId(phar.getId(), 2);
            int parkId = park.getId();
            vehicleHandler.updateStatusToParked(licensePlate);
            int isCharging = vehicle.getIsCharging();
            if (isCharging == 1) {
                new ParkHandler().updateActualChargingPlacesA(parkId);
                vehicleHandler.updateIsChargingN(licensePlate);
            } else {
                new ParkHandler().updateActualCapacityA(parkId);
            }
            vehicleHandler.updateStatusToBusy(licensePlate);

            RefillStock r = new RefillStock(necessaryEnergy, distance, weightSum,0, idVehicle);
            int idRS = refillStockDataHandler.addRefillStock(r);
            callTimer("Delivery RestockOrder Created...");
            for(Pharmacy p : points){
                EmailAPI.sendMail(phar.getEmail(), "RestockOrder", "The products you required are already on their away!");
            }

            callTimer("Delivery Restock Created...");
            for (RestockOrder co : restocklistToMakeDelivery) {
                new RestockDataHandler().updateStatusRestock(co.getId());
                Client c = clientDataHandler.getClientByClientOrderID(co.getClientOrderID());
                EmailAPI.sendMail(c.getEmail(), "RestockOrder", "The product(s) that you are waiting for is/are already available. Your products will be delivered soon");
            }
            refillStockDataHandler.updateStatusToDone(idRS);
            int id = phar.getId();

            return new Pair<>(id, licensePlate);
        }
    }

    public Pair<LinkedList<Address>, Double> getPath(List<RestockOrder> restocklistToMakeDelivery){
        List<Address> addresses = addressDataHandler.getAllAddresses();
        ArrayList<Address> addressesToMakeDelivery = new ArrayList<>();
        Pharmacy phar = getPharmByID(restocklistToMakeDelivery.get(0).getPharmReceiverID());
        Address startPoint = null;
        for (RestockOrder co : restocklistToMakeDelivery) {
            Client client = clientDataHandler.getClientByID(co.getClientOrderID());
            for (Address add : addresses) {
                if (add.getLatitude() == client.getLatitude() && add.getLongitude() == client.getLongitude()) {
                    addressesToMakeDelivery.add(add);
                }
                if(phar.getLatitude() == add.getLatitude() && add.getLongitude() == add.getLongitude()){
                    startPoint = add;
                }
            }
        }
        Graph<Address, Double> graph = buildGraph(addresses);
        AdjacencyMatrixGraph<Address, Double> matrix = generateAdjacencyMatrixGraph(graph);

        return shortestPathForDelivery(addressesToMakeDelivery, matrix, startPoint, graph);
    }

    private void callTimer(String message) {

        TimerTask task = new TimerTask() {
            public void run() {
                Logger.getLogger(OrderController.class.getName()).log(Level.INFO, message);
            }
        };
        Timer timer = new Timer("Timer");
        timer.schedule(task, 10000);
    }


    public void updateStatusVehicle(Vehicle v) {
        vehicleHandler.updateStatusToParked(v.getLicensePlate());
    }
}



