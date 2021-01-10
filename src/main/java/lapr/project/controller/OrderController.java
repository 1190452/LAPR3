package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.model.Graph.Graph;
import lapr.project.model.adjacencyMatrixGraph.AdjacencyMatrixGraph;
import lapr.project.model.adjacencyMatrixGraph.GraphAlgorithms;
import lapr.project.utils.Distance;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class OrderController {

    private ClientOrderHandler clientOrderHandler;
    private CourierDataHandler courierDataHandler;
    private AddressDataHandler addressDataHandler;
    private ClientDataHandler clientDataHandler;
    private PharmacyDataHandler pharmacyDataHandler;
    private Graph<Address,Double> citygraph;


    public OrderController(ClientOrderHandler clh, CourierDataHandler cdh, AddressDataHandler addressDataHandler, ClientDataHandler clientDataHandler, PharmacyDataHandler pharmacyDataHandler){
        this.clientOrderHandler = clh;
        this.courierDataHandler = cdh;
        this.addressDataHandler = addressDataHandler;
        this.clientDataHandler = clientDataHandler;
        this.pharmacyDataHandler = pharmacyDataHandler;
        citygraph = new Graph<>(true);
    }

    public Courier getCourierByEmail(String email){
        return courierDataHandler.getCourierByEmail(email);
    }

    public Courier getCourierByNIF(double nif) {return courierDataHandler.getCourier(nif); }

    public LinkedHashMap<Integer, ClientOrder> getUndoneOrders(){
        return clientOrderHandler.getUndoneOrders();
    }

    public void processDelivery(List<ClientOrder> ordersInThisDelivery, Pharmacy pharmacy) {
        List<Address> addresses = addressDataHandler.getAllAddresses();
        List<Address> addressesToMakeDelivery = new ArrayList<>();
        citygraph = buildGraph(addresses);
        Address startPoint = null;

        for(ClientOrder co : ordersInThisDelivery){
            Client client = clientDataHandler.getClientByID(co.getClientId());
            for(Address add : addresses){
                if(add.getLatitude() == client.getLatitude() && add.getLongitude() == client.getLongitude()){
                    addressesToMakeDelivery.add(add);
                }
                if(add.getLatitude() == pharmacy.getLatitude() && add.getLongitude() == pharmacy.getLongitude()){
                    startPoint = add;
                }
            }
        }

       //AdjacencyMatrixGraph adjacencyMatrix = generateAdjacencyMatrix(citygraph);

        ArrayList<LinkedList<Address>> allpaths = GraphAlgorithms.allPaths(citygraph, startPoint, startPoint);

        System.out.println("ola");
    /*
        for(int p=0; p<addressesToMakeDelivery.size();p++){
            if(adjacencyMatrix.getEdge(startPoint, addressesToMakeDelivery.get(p)) != null){
                EdgeAsDoubleGraphAlgorithms.shortestPath(adjacencyMatrix, startPoint, addressesToMakeDelivery.get(p), new LinkedList<Address>());
            } else {
                List<Address> directConect = (List<Address>) adjacencyMatrix.directConnections(startPoint);
            }
        }*/

    }

    private AdjacencyMatrixGraph generateAdjacencyMatrix(Graph<Address, Double> citygraph) {
        AdjacencyMatrixGraph adjencyMatrix = new AdjacencyMatrixGraph(citygraph.numVertices());

        for (Address a1 : citygraph.vertices()) {
            adjencyMatrix.insertVertex(a1);
        }

        for (Address a1 : citygraph.vertices()) {
            for (Address a2 : citygraph.vertices()) {
                if (citygraph.getEdge(a1, a2) != null && !a1.equals(a2)) {
                    adjencyMatrix.insertEdge(a1, a2, citygraph.getEdge(a1, a2).getWeight());
                }
            }
        }
        adjencyMatrix = GraphAlgorithms.transitiveClosure(adjencyMatrix, null);
        return adjencyMatrix;
    }

    private Graph<Address, Double> buildGraph(List<Address> addresses) {

        for(Address add : addresses){
            citygraph.insertVertex(add);
        }

        for(int i = 0; i<addresses.size()-1;i++){
            Address add1 = addresses.get(i);
            Address add2 = addresses.get(i+1);
            double weight = Distance.distanceBetweenTwoAddresses(add1.getLatitude(),add1.getLongitude(), add2.getLatitude(), add2.getLongitude());
            citygraph.insertEdge(add1, add2, weight, weight);
        }

        Address add1 = addresses.get(0);
        Address add2 = addresses.get(addresses.size()-1);
        double weight = Distance.distanceBetweenTwoAddresses(add1.getLatitude(),add1.getLongitude(), add2.getLatitude(), add2.getLongitude());
        citygraph.insertEdge(add1, add2, weight, weight);//Verificar se o grafo ficou direito

        return citygraph;
    }

    public Pharmacy getPharmByID(int pharmacyID) {
        return pharmacyDataHandler.getPharmacy(pharmacyID);
    }
}
