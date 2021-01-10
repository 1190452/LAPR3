package lapr.project.controller;

import lapr.project.data.AddressDataHandler;
import lapr.project.data.ClientOrderHandler;
import lapr.project.data.CourierDataHandler;
import lapr.project.model.Address;
import lapr.project.model.ClientOrder;
import lapr.project.model.Courier;
import lapr.project.model.Graph.Graph;
import lapr.project.model.adjacencyMatrixGraph.AdjacencyMatrixGraph;
import lapr.project.model.adjacencyMatrixGraph.GraphAlgorithms;
import lapr.project.utils.Distance;

import java.util.LinkedHashMap;
import java.util.List;

public class OrderController {

    private ClientOrderHandler clientOrderHandler;
    private CourierDataHandler courierDataHandler;
    private AddressDataHandler addressDataHandler;
    private Graph<Address,Double> citygraph;

    public OrderController(ClientOrderHandler clh, CourierDataHandler cdh, AddressDataHandler addressDataHandler){
        this.clientOrderHandler = clh;
        this.courierDataHandler = cdh;
        this.addressDataHandler = addressDataHandler;
        citygraph = new Graph<>(true);
    }

    public OrderController(CourierDataHandler courierDataHandler) {
        this.courierDataHandler = courierDataHandler;
    }

    public Courier getCourierByEmail(String email){
        return courierDataHandler.getCourierByEmail(email);
    }

    public Courier getCourierByNIF(double nif) {return courierDataHandler.getCourier(nif); }

    public LinkedHashMap<Integer, ClientOrder> getUndoneOrders(){
        return clientOrderHandler.getUndoneOrders();
    }

    public void processDelivery(List<ClientOrder> ordersInThisDelivery) {
        List<Address> addresses = addressDataHandler.getAllAddresses();
        citygraph = buildGraph(addresses);
        AdjacencyMatrixGraph adjacencyMatrix = generateAdjacencyMatrix(citygraph);






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

        for(int i = 0; i<addresses.size();i++){
            Address add1 = addresses.get(i);
            Address add2 = addresses.get(i);
            double weight = Distance.distanceBetweenTwoAddresses(add1.getLatitude(),add1.getLongitude(), add2.getLatitude(), add2.getLongitude());
            citygraph.insertEdge(add1, add2, weight, weight);
        }

        Address add1 = addresses.get(0);
        Address add2 = addresses.get(addresses.size()-1);
        double weight = Distance.distanceBetweenTwoAddresses(add1.getLatitude(),add1.getLongitude(), add2.getLatitude(), add2.getLongitude());
        citygraph.insertEdge(add2, add1, weight, weight);//Verificar se o grafo ficou direito

        return citygraph;
    }

}
