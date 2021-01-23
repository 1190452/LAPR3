package lapr.project.controller;


import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.utils.Physics;
import lapr.project.utils.graph.AdjacencyMatrixGraph;
import lapr.project.utils.graphbase.Graph;
import oracle.ucp.util.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    private static OrderController instance;
    private ClientOrderHandler clientOrderHandler;
    private CourierDataHandler courierDataHandler;
    private AddressDataHandler addressDataHandler;
    private ClientDataHandler clientDataHandler;
    private PharmacyDataHandler pharmacyDataHandler;
    private DeliveryHandler deliveryHandler;
    private Graph<Address, Double> Graph;

    public OrderControllerTest() {
        clientOrderHandler = new ClientOrderHandler();
        courierDataHandler = new CourierDataHandler();
        addressDataHandler = new AddressDataHandler();
        clientDataHandler = new ClientDataHandler();
        pharmacyDataHandler = new PharmacyDataHandler();
        this.deliveryHandler = new DeliveryHandler();
        Graph = new Graph<>(true);
    }

    @BeforeAll
    public static void setUpClass() {

        CourierDataHandler courierDataHandlerMock = mock(CourierDataHandler.class);
        ClientOrderHandler clientOrderHandlerMock = mock(ClientOrderHandler.class);
        AddressDataHandler addressDataHandlerMock = mock(AddressDataHandler.class);
        PharmacyDataHandler pharmacyDataHandlerMock = mock(PharmacyDataHandler.class);
        ClientDataHandler clientDataHandlerMock = mock(ClientDataHandler.class);
        DeliveryHandler deliveryHandlerMock = mock(DeliveryHandler.class);
        VehicleHandler vehicleHandlerMock = mock(VehicleHandler.class);
        RestockDataHandler restockDataHandlerMock = mock(RestockDataHandler.class);
        RefillStockDataHandler refillStockDataHandlerMock = mock(RefillStockDataHandler.class);


        Courier courier = new Courier(1, "courier@isep.ipp.pt", "André", 122665789,
                new BigDecimal("24586612344"), 15, 70, 1);
        Pharmacy phar = new Pharmacy(5, "ISEP", "phar1@isep.ipp.pt", 213.123, 2323, 23323, "isep@isep.ipp.pt");
        Address address = new Address(34, 45, "rua xpto", 2, "4500", "espinho");
        Address address2 = new Address(2323, 23323, "rua xpto", 2, "4500", "espinho");
        Client client = new Client(1, "dsfsf", "fjdnsf", "qwerty", 123456789, 34, 45, 12, new BigDecimal("1231231231231231"));
        Delivery delivery = new Delivery(32, 22, 781, 1, "AK-LA-09");
        List<Delivery> aux = new ArrayList<>();
        aux.add(delivery);
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        addresses.add(address2);

        when(courierDataHandlerMock.getCourierByEmail(any(String.class))).thenReturn(courier);
        when(courierDataHandlerMock.getCourier(any(Double.class))).thenReturn(courier);
        when(courierDataHandlerMock.updateSatusCourier(1)).thenReturn(Boolean.TRUE);

        when(pharmacyDataHandlerMock.getPharmacyByID(any(Integer.class))).thenReturn(phar);
        when(addressDataHandlerMock.getAllAddresses()).thenReturn(addresses);
        when(clientDataHandlerMock.getClientByID(any(Integer.class))).thenReturn(client);
        when(clientDataHandlerMock.getClientByEmail(any(String.class))).thenReturn(client);
        when(deliveryHandlerMock.getDeliverysByCourierId(any(Integer.class))).thenReturn(aux);

        ClientOrder clientOrder = new ClientOrder(1, new Date(1254441245), 12, 1, 0, 0, 1, 1);
        LinkedHashMap<Integer, ClientOrder> orders = new LinkedHashMap<>();
        orders.put(1, clientOrder);
        when(clientOrderHandlerMock.getUndoneOrders(any(Integer.class))).thenReturn(orders);

        List<Courier> courierList = new ArrayList<>();
        courierList.add(courier);
        when(courierDataHandlerMock.getAvailableCouriers(any(Integer.class))).thenReturn(courierList);

        List<Pharmacy> pharmacyList = new ArrayList<>();
        pharmacyList.add(phar);
        when(pharmacyDataHandlerMock.getAllPharmacies()).thenReturn(pharmacyList);

        Vehicle vehicle = new Vehicle("AH-87-LK", 400, 350, 500, 8.0, 5000.0, 430, 4, 2, 88);

        List<Vehicle> drones = new ArrayList<>();
        drones.add(vehicle);
        Vehicle vehicle2 = new Vehicle("AH-87-LK", 5, 350, 500, 8.0, 5000.0, 430, 4, 2, 88);
        List<Vehicle> drones2 = new ArrayList<>();
        drones2.add(vehicle2);
        when(vehicleHandlerMock.getDronesAvailable(any(Integer.class), any(Double.class))).thenReturn(drones);
        when(deliveryHandlerMock.getDeliveryByDroneId(any(Integer.class))).thenReturn(new Delivery(25, 30, 40, 0, "AK-LA-09"));
        when(clientOrderHandlerMock.updateStatusOrder(any(Integer.class), any(Integer.class))).thenReturn(true);
        when(vehicleHandlerMock.updateStatusToParked(any(String.class))).thenReturn(true);

        List<RestockOrder> restocks = new ArrayList<>();

        restocks.add(new RestockOrder(1, 1, 4, 2, 5, 7, 0, 10));
        restocks.add(new RestockOrder(1, 3, 5, 6, 9, 4, 20));

        when(restockDataHandlerMock.getRestockList(any(Integer.class))).thenReturn(restocks);

        when(deliveryHandlerMock.updateStatusDelivery(any(Integer.class))).thenReturn(true);

        when(addressDataHandlerMock.getAllAddresses()).thenReturn(addresses);
        Park p = new Park(1,12,10,2,1,25,5,1);
        when(vehicleHandlerMock.getParkByPharmacyId(5,2)).thenReturn(p);
        when(clientDataHandlerMock.getClientByClientOrderID(1)).thenReturn(client);

        instance = new OrderController(clientOrderHandlerMock, courierDataHandlerMock, addressDataHandlerMock,
                clientDataHandlerMock, pharmacyDataHandlerMock, deliveryHandlerMock, vehicleHandlerMock, refillStockDataHandlerMock, restockDataHandlerMock);

    }

    @Test
    void getCourierByNif() {
        double nif = 122665789;

        Courier expResult = new Courier(1, "courier@isep.ipp.pt", "André", 122665789,
                new BigDecimal("24586612344"), 15, 70, 1);
        Courier result = instance.getCourierByNIF(nif);
        assertEquals(expResult.getNif(), result.getNif());
    }

    @Test
    void getUndoneOrders() {
        ClientOrder clientOrder = new ClientOrder(1, new Date(1254441245), 12, 1, 0, 0, 1, 1);
        Map<Integer, ClientOrder> expResult = new LinkedHashMap<>();
        expResult.put(1, clientOrder);
        Map<Integer, ClientOrder> result = instance.getUndoneOrders(0);
        assertEquals(expResult, result);
    }

    @Test
    void getPharmByID() {
        int id = 5;
        Pharmacy expResult = new Pharmacy(5, "ISEP", "phar1@isep.ipp.pt", 213.123, 2323, 23323, "isep@isep.ipp.pt");
        Pharmacy result = instance.getPharmByID(id);
        assertEquals(expResult.getName(), result.getName());
    }

    @Test
    void buildGraph() {
        Address address = new Address(34, 45, "rua xpto", 2, "4500", "espinho");
        Address address2 = new Address(2323, 23323, "rua xpto", 2, "4500", "espinho");
        Graph<Address, Double> expResult = new Graph<>(true);
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        addresses.add(address2);
        expResult.insertVertex(address);
        expResult.insertVertex(address2);
        double distance = Physics.calculateDistanceWithElevation(address.getLatitude(), address2.getLatitude(), address.getLongitude(), address2.getLongitude(), address.getAltitude(), address2.getAltitude());
        expResult.insertEdge(address, address2, distance, distance);
        expResult.insertEdge(address2, address, distance, distance);
        Graph<Address, Double> result = instance.buildDistanceGraph(addresses, 1);
        assertEquals(result, expResult);

    }

    @Test
    void buildGraph2() {
        Address address = new Address(34, 45, "rua xpto", 2, "4500", "espinho");
        Address address2 = new Address(2323, 23323, "rua xpto", 2, "4500", "espinho");
        Graph<Address, Double> expResult = new Graph<>(true);
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        addresses.add(address2);
        expResult.insertVertex(address);
        expResult.insertVertex(address2);
        double distance = Physics.calculateDistanceWithElevation(address.getLatitude(), address2.getLatitude(), address.getLongitude(), address2.getLongitude(), address.getAltitude(), address2.getAltitude());
        expResult.insertEdge(address, address2, distance, distance);
        expResult.insertEdge(address2, address, distance, distance);
        Graph<Address, Double> result = instance.buildDistanceGraph(addresses, 2);
        assertEquals(result, expResult);

    }


    @Test
    void getTotalEnergy() {
        double expResult = 4.2;
        double result = instance.getTotalEnergy(200.0, 1, 5.0, 10.0, 30.0, 40.10, 40.78, -8.33, -8.99);
        assertEquals(expResult, result, 0.1);
    }

    @Test
    void getTotalEnergy2() {
        double expResult = 8;
        double result = instance.getTotalEnergy(12.0, 2, 1.0, 0.0, 0.0, 40.10, 40.78, 8.33, 8.99);
        assertEquals(expResult, result, 0.1);
    }

    @Test
    void getOrdersWeight() {
        ClientOrder clientOrder = new ClientOrder(1, new Date(1254441245), 12, 1, 0, 0, 1, 1);
        List<ClientOrder> ordersInThisDelivery = new ArrayList<>();
        ordersInThisDelivery.add(clientOrder);
        double expResult = 1;
        double result = instance.getOrdersWeight(ordersInThisDelivery);
        assertEquals(expResult, result);
    }

    @Test
    void getOrdersWeight2() {
        List<ClientOrder> ordersInThisDelivery = new ArrayList<>();
        double expResult = 0;
        double result = instance.getOrdersWeight(ordersInThisDelivery);
        assertEquals(expResult, result);
    }

    @Test
    void getCourierByEmail() {
        String email = "courier@isep.ipp.pt";

        Courier expResult = new Courier(1, "courier@isep.ipp.pt", "André", 122665789,
                new BigDecimal("24586612344"), 15, 70, 1);
        Courier result = instance.getCourierByEmail(email);
        assertEquals(expResult.getEmail(), result.getEmail());
    }

    @Test
    void getCourierByEmail2() {
        String email = "";
        String expResult = "courier@isep.ipp.pt";
        Courier result = instance.getCourierByEmail("");
        assertEquals(expResult, result.getEmail());
    }

    @Test
    void getDeliverysByCourierId() {
        Courier courier = new Courier(1, "courier@isep.ipp.pt", "André", 122665789,
                new BigDecimal("24586612344"), 15, 70, 1);
        Delivery delivery = new Delivery(32, 22, 781, 1, "AK-LA-09");
        List<Delivery> expResult = new ArrayList<>();
        expResult.add(delivery);
        List<Delivery> result = instance.getDeliverysByCourierId(1);
        assertEquals(result, expResult);
    }

    @Test
    void getAvailableCouriers() {
        Courier courier = new Courier(1, "courier@isep.ipp.pt", "André", 122665789,
                new BigDecimal("24586612344"), 15, 70, 1);
        List<Courier> expResult = new ArrayList<>();
        expResult.add(courier);
        List<Courier> result = instance.getAvailableCouriers(1);
        assertEquals(expResult, result);
    }

    @Test
    void getAllPharmacies() {
        Pharmacy phar = new Pharmacy(5, "ISEP", "phar1@isep.ipp.pt", 213.123, 2323, 23323, "isep@isep.ipp.pt");
        List<Pharmacy> expResult = new ArrayList<>();
        expResult.add(phar);
        List<Pharmacy> result = instance.getAllPharmacies();
        assertEquals(expResult, result);
    }

    @Test
    void getDronesAvailable() {
        Vehicle vehicle = new Vehicle("AH-87-LK", 400, 350, 500, 8.0, 5000.0, 430, 5, 2, 88);
        List<Vehicle> expResult = new ArrayList<>();
        expResult.add(vehicle);
        List<Vehicle> result = instance.getDronesAvailable(5, 15);
        assertEquals(expResult, result);
    }


    @Test
    void getCourierEmail() {
        User user = new User("qq@gmail.com", "qw", "COURIER");
        UserSession.getInstance().setUser(user);
        String expResult = instance.getCourierEmail();
        assertEquals(expResult, UserSession.getInstance().getUser().getEmail());

    }


    @Test
    void testGetAvailableCouriers() {
        List<Courier> result = instance.getAvailableCouriers(5);
        List<Courier> expectedResult = new ArrayList<>();

        expectedResult.add(new Courier(1, "courier@isep.ipp.pt", "André", 122665789,
                new BigDecimal("24586612344"), 15, 70, 1));

        assertEquals(result, expectedResult);
    }

    @Test
    void updateStatusDelivery() {
        DeliveryHandler deliveryHandler = mock(DeliveryHandler.class);
        when(deliveryHandler.updateStatusDelivery(any(Integer.class))).thenReturn(Boolean.TRUE);
        OrderController orderController = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(), deliveryHandler, new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler());
        orderController.updateStatusDelivery(2);
    }

    @Test
    void sendMailToAllClients() {
    }

    @Test
    void getPermutations() {
        Address address = new Address(34, 45, "rua xpto", 2, "4500", "espinho");
        Address address2 = new Address(2323, 23323, "rua xpto", 2, "4500", "espinho");
        Address address3 = new Address(45, 656, "rua xpto", 2, "4500", "espinho");
        Graph<Address, Double> graph = new Graph<>(true);
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        addresses.add(address2);
        addresses.add(address3);
        graph.insertVertex(address);
        graph.insertVertex(address2);
        graph.insertVertex(address3);
        double distance = Physics.calculateDistanceWithElevation(address.getLatitude(), address2.getLatitude(), address.getLongitude(), address2.getLongitude(), address.getAltitude(), address2.getAltitude());
        graph.insertEdge(address, address2, 1.0, distance);
        double distance2 = Physics.calculateDistanceWithElevation(address2.getLatitude(), address3.getLatitude(), address2.getLongitude(), address3.getLongitude(), address2.getAltitude(), address3.getAltitude());
        graph.insertEdge(address2, address3, 1.0, distance2);
        double distance3 = distance + distance2;
        AdjacencyMatrixGraph<Address, Double> matrix = new AdjacencyMatrixGraph<>();
        matrix.insertVertex(address);
        matrix.insertVertex(address2);
        matrix.insertVertex(address3);
        matrix.insertEdge(address, address2, distance);
        matrix.insertEdge(address, address3, distance3);
        matrix.insertEdge(address2, address, distance);
        matrix.insertEdge(address2, address3, distance2);
        matrix.insertEdge(address3, address2, distance2);
        matrix.insertEdge(address3, address, distance3);
        List<Pair<LinkedList<Address>, Double>> result = instance.getPermutations(addresses, matrix);

        List<Pair<LinkedList<Address>, Double>> expected = new ArrayList<>();
        LinkedList<Address> permute1List = new LinkedList<>();
        permute1List.add(address);
        permute1List.add(address2);
        expected.add(new Pair<>(permute1List, distance));

        permute1List = new LinkedList<>();
        permute1List.add(address);
        permute1List.add(address3);
        expected.add(new Pair<>(permute1List, distance3));

        permute1List = new LinkedList<>();
        permute1List.add(address2);
        permute1List.add(address);
        expected.add(new Pair<>(permute1List, distance));

        permute1List = new LinkedList<>();
        permute1List.add(address2);
        permute1List.add(address3);
        expected.add(new Pair<>(permute1List, distance2));

        permute1List = new LinkedList<>();
        permute1List.add(address3);
        permute1List.add(address);
        expected.add(new Pair<>(permute1List, distance3));

        permute1List = new LinkedList<>();
        permute1List.add(address3);
        permute1List.add(address2);
        expected.add(new Pair<>(permute1List, distance2));

        assertEquals(expected, result, "Lists should be equal");
    }

    @Test
    void generateAdjacencyMatrixGraph() {
        Address address = new Address(34, 45, "rua xpto", 2, "4500", "espinho");
        Address address2 = new Address(2323, 23323, "rua xpto", 2, "4500", "espinho");
        Address address3 = new Address(45, 656, "rua xpto", 2, "4500", "espinho");
        Graph<Address, Double> graph = new Graph<>(true);
        graph.insertVertex(address);
        graph.insertVertex(address2);
        graph.insertVertex(address3);
        double distance = Physics.calculateDistanceWithElevation(address.getLatitude(), address2.getLatitude(), address.getLongitude(), address2.getLongitude(), address.getAltitude(), address2.getAltitude());
        graph.insertEdge(address, address2, 1.0, distance);
        double distance2 = Physics.calculateDistanceWithElevation(address2.getLatitude(), address3.getLatitude(), address2.getLongitude(), address3.getLongitude(), address2.getAltitude(), address3.getAltitude());
        graph.insertEdge(address2, address3, 1.0, distance2);
        double distance3 = distance + distance2;
        AdjacencyMatrixGraph<Address, Double> expResult = new AdjacencyMatrixGraph<>();
        expResult.insertVertex(address);
        expResult.insertVertex(address2);
        expResult.insertVertex(address3);
        expResult.insertEdge(address, address2, distance);
        expResult.insertEdge(address, address3, distance3);
        expResult.insertEdge(address2, address, distance);
        expResult.insertEdge(address2, address3, distance2);
        expResult.insertEdge(address3, address2, distance2);
        expResult.insertEdge(address3, address, distance3);
        AdjacencyMatrixGraph result = instance.generateAdjacencyMatrixGraph(graph);
        assertEquals(expResult, result);
    }

    @Test
    void createDroneDelivery() {
        Pharmacy phar = new Pharmacy(5, "ISEP", "phar1@isep.ipp.pt", 2323, 23323, 3, "isep@isep.ipp.pt");
        ClientOrder clientOrder = new ClientOrder(1, new Date(1254441245), 12, 1, 0, 0, 1, 1);
        LinkedList<ClientOrder> ordersInThisDelivery = new LinkedList<>();
        ordersInThisDelivery.add(clientOrder);
        Vehicle expResult = new Vehicle("AH-87-LK", 5, 350, 500, 8.0, 5000.0, 430, 4, 2, 88);
        List<Path> path = new ArrayList<>();
        Vehicle result = instance.createDroneDelivery(ordersInThisDelivery, phar, 45, path, 2);

        assertEquals(result, expResult);
    }

    @Test
    void createDroneDelivery2() {
        Pharmacy phar = new Pharmacy(5, "ISEP", "phar1@isep.ipp.pt", 213.123, 2323, 23323, "isep@isep.ipp.pt");
        LinkedList<ClientOrder> ordersInThisDelivery = new LinkedList<>();
        Vehicle expResult = null;

        List<Path> path = new ArrayList<>();
        Vehicle result = instance.createDroneDelivery(ordersInThisDelivery, phar, 0, path, 2);
        assertEquals(result, expResult);
    }


    @Test
    void createDeliveryByScooter() {

        Pharmacy phar = new Pharmacy(5, "ISEP", "phar1@isep.ipp.pt", 2323, 23323, 3, "isep@isep.ipp.pt");
        ClientOrder clientOrder = new ClientOrder(1, new Date(1254441245), 12, 1, 0, 0, 1, 1);
        LinkedList<ClientOrder> ordersInThisDelivery = new LinkedList<>();

        Address address = new Address(34, 45, "rua xpto", 2, "4500", "espinho");
        Address address2 = new Address(2323, 23323, "rua nhgjg", 2, "4545600", "er");


        double distance = Physics.calculateDistanceWithElevation(address.getLatitude(), address2.getLatitude(), address.getLongitude(), address2.getLongitude(), address.getAltitude(), address2.getAltitude());


        LinkedList<Address> aux = new LinkedList<>();
        aux.add(address2);
        aux.add(address);

        Pair<LinkedList<Address>, Double> pair = new Pair<>(aux, distance);

        List<Courier> avC = new ArrayList<>();
        avC.add(new Courier(1, "courier@isep.ipp.pt", "André", 122665789,
                new BigDecimal("24586612344"), 15, 70, 1));
        when(instance.getAvailableCouriers(any(Integer.class))).thenReturn(avC);

        CourierDataHandler courierDataHandlerMock = mock(CourierDataHandler.class);
        when(courierDataHandlerMock.getAvailableCouriers(5)).thenReturn(avC);
        ordersInThisDelivery.add(clientOrder);
        double weight = 7;
        boolean expecResult = true;
        List<Path> path = new ArrayList<>();
        boolean result = instance.createDeliveryByScooter(ordersInThisDelivery, phar, weight, path,2 );
        assertEquals(expecResult, result);

    }


    @Test
    void updateStatusOrder() {
        ClientOrderHandler clientOrderHandler = mock(ClientOrderHandler.class);
        when(clientOrderHandler.updateStatusOrder(any(Integer.class), any(Integer.class))).thenReturn(Boolean.TRUE);

        OrderController orderController = new OrderController(clientOrderHandler, new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(), new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler());
        boolean result = orderController.updateStatusOrder(1, 4);
        assertTrue(result);
    }

    @Test
    void updateStatusOrder2() {
        ClientOrderHandler clientOrderHandler = mock(ClientOrderHandler.class);
        when(clientOrderHandler.updateStatusOrder(any(Integer.class), any(Integer.class))).thenReturn(Boolean.FALSE);

        OrderController orderController = new OrderController(clientOrderHandler, new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(), new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(),
                new RestockDataHandler());
        boolean result = orderController.updateStatusOrder(1, 4);
        assertFalse(result);
    }


    @Test
    void testUpdateStatusDelivery() {
        boolean expResult = true;

        boolean result = instance.updateStatusDelivery(1);

        assertEquals(expResult, result);
    }

    @Test
    void getRestockList() {
        List<RestockOrder> expResult = new ArrayList<>();

        expResult.add(new RestockOrder(1, 1, 4, 2, 5, 7, 0, 10));
        expResult.add(new RestockOrder(1, 3, 5, 6, 9, 4, 20));

        List<RestockOrder> result = instance.getRestockList(1);

        assertEquals(expResult, result);

    }


    @Test
    void updateStatusVehicle() {
        Vehicle vehicle = new Vehicle(1, "AH-87-LK", 400, 350, 0, 1, 500, 8.0, 5000.0, 430, 4, 1, 10, 2.3);


        boolean result = instance.updateStatusVehicle(vehicle);

        boolean expResult = true;

        assertEquals(expResult, result);


    }

    @Test
    void testUpdateStatusDelivery1() {
        DeliveryHandler deliveryHandlermock = mock(DeliveryHandler.class);
        when(deliveryHandlermock.updateStatusDelivery(any(Integer.class))).thenReturn(false);

        OrderController orderController = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(), deliveryHandlermock, new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler());
        boolean expResult = false;

        boolean result = orderController.updateStatusDelivery(1);

        assertEquals(expResult, result);
    }

    @Test
    void testUpdateStatusVehicle() {
        VehicleHandler vehicleHandlermock = mock(VehicleHandler.class);
        when(vehicleHandlermock.updateStatusToParked(any(String.class))).thenReturn(false);

        OrderController orderController = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(), new DeliveryHandler(), vehicleHandlermock, new RefillStockDataHandler(), new RestockDataHandler());

        boolean result = orderController.updateStatusVehicle(new Vehicle(1, "AH-87-LK", 400, 350, 0, 1, 500, 8.0, 5000.0, 430, 4, 1, 10, 2.3));

        assertFalse(result);
    }

    @Test
    void buildEnergyGraph() {
        Address address = new Address(34, 45, "rua xpto", 2, "4500", "espinho", 10);
        Address address2 = new Address(2323, 23323, "rua xpto", 2, "4500", "espinho", 11);
        Graph<Address, Double> expResult = new Graph<>(true);
        List<Path> p = new ArrayList<>();

        p.add(new Path(address, address2, 0, 0, 0));

        p.add(new Path(address2, address, 0, 0, 0));


        double distanceWithElevation = Physics.calculateDistanceWithElevation(address.getLatitude(), address2.getLatitude(), address.getLongitude(), address2.getLongitude(), address.getAltitude(), address2.getAltitude());

        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        addresses.add(address2);
        expResult.insertVertex(address);
        expResult.insertVertex(address2);
        double distance = Physics.getNecessaryEnergy(distanceWithElevation, 10, 1, 2, 1, 10, 10, 0.05,1);
        expResult.insertEdge(address, address2, distance, distance);
        expResult.insertEdge(address2, address, distance, distance);
        Graph<Address, Double> result = instance.buildEnergyGraph(addresses, 1, p, 2);
        assertEquals(result, expResult);
    }

    @Test
    void buildEnergyGraph2() {
        Address address = new Address(34, 45, "rua xpto", 2, "4500", "espinho", 10);
        Address address2 = new Address(2323, 23323, "rua xpto", 2, "4500", "espinho", 11);
        Graph<Address, Double> expResult = new Graph<>(true);
        List<Path> p = new ArrayList<>();

        p.add(new Path(address, address2, 0, 0, 0));

        p.add(new Path(address2, address, 0, 0, 0));


        double distanceWithElevation = Physics.calculateDistanceWithElevation(address.getLatitude(), address2.getLatitude(), address.getLongitude(), address2.getLongitude(), address.getAltitude(), address2.getAltitude());

        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        addresses.add(address2);
        expResult.insertVertex(address);
        expResult.insertVertex(address2);
        double distance = Physics.getNecessaryEnergy(distanceWithElevation, 10, 2, 2, 1, 10, 10, 0.05,1);
        expResult.insertEdge(address, address2, distance, distance);
        expResult.insertEdge(address2, address, distance, distance);
        Graph<Address, Double> result = instance.buildEnergyGraph(addresses, 2, p, 2);
        assertEquals(result, expResult);
    }

    @Test
    void getAllAddresses() {
        List<Address> result = instance.getAllAddresses();
        Address address = new Address(34, 45, "rua xpto", 2, "4500", "espinho");
        Address address2 = new Address(2323, 23323, "rua xpto", 2, "4500", "espinho");

        List<Address> expResult = new ArrayList<>();
        expResult.add(address);
        expResult.add(address2);

        assertEquals(expResult, result);
    }

    @Test
    void createRestockRequestByDrone() {
        Address address = new Address(34, 45, "rua xpto", 2, "4500", "espinho", 10);
        Address address2 = new Address(2323, 23323, "rua xpto", 2, "4500", "espinho", 11);

        List<RestockOrder> restocklistToMakeDelivery = new ArrayList<>();
        restocklistToMakeDelivery.add(new RestockOrder(1,5,6,1,1,1,0,1));
        double weightSum = 10;
        List<Pharmacy> points = new ArrayList<>();
        Pharmacy phar = new Pharmacy(5, "ISEP", "phar1@isep.ipp.pt", 34, 45, 10, "isep@isep.ipp.pt");
        Pharmacy phar2 = new Pharmacy(6, "ISEP", "phar1@isep.ipp.pt", 2323, 23323, 11, "isep@isep.ipp.pt");

        points.add(phar);
        points.add(phar2);
        double distance = 10;
        List<Path> pathPairs = new ArrayList<>();
        pathPairs.add(new Path(address,address2,0,0,0));
        pathPairs.add(new Path(address2,address,0,0,0));

        Vehicle vehicle = new Vehicle("AH-87-LK", 400, 350, 500, 8.0, 5000.0, 430, 4, 2, 88);

        Pair<Integer, Vehicle> result = instance.createRestockRequestByDrone(restocklistToMakeDelivery,weightSum,points,distance,pathPairs);
        Pair<Integer, Vehicle> expResult = new Pair<>(5, vehicle);

        assertEquals(result, expResult);
     }

    @Test
    void getAllPathsPairs() {

        List<Address> addresses = new ArrayList<>();

        List<Path> pathList = new ArrayList<>();

        List<Path>  expResult= new ArrayList<>();

        List<Path> result= instance.getAllPathsPairs(addresses,pathList);

        assertEquals(expResult, result);

    }

    @Test
    void getNecessaryEnergy() {
        Address address = new Address(34, 45, "rua xpto", 2, "4500", "espinho", 30);
        Address address2 = new Address(2323, 23323, "rua xpto", 2, "4500", "espinho", 50);
        List<Path> p = new ArrayList<>();

        p.add(new Path(address, address2, 24.6, 70, 12));

        p.add(new Path(address2, address, 38.1, 90, 28));

        OrderController orderController = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(), new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler());

        double result = orderController.getNecessaryEnergy(p,30);

        assertEquals(20, p.get(0).getA2().getAltitude() - p.get(0).getA1().getAltitude());

        assertEquals(275098.8120851026, result);
    }

    @Test
    void testGetAllAddresses() {
        Address address = new Address(34, 45, "rua xpto", 2, "4500", "espinho", 30);
        Address address2 = new Address(2323, 23323, "rua xpto", 2, "4500", "espinho", 50);
        List<Path> p = new ArrayList<>();

        List<Address> a = new ArrayList<>();
        a.add(address);
        a.add(address2);

        p.add(new Path(address, address2, 24.6, 70, 12));

        p.add(new Path(address2, address, 38.1, 90, 28));

        OrderController orderController = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(), new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler());

        List<Path> result = orderController.getAllPathsPairs(a, p);
        List<Path> expResult = new ArrayList<>();
        expResult.add(new Path(address, address2, 24.6, 70, 12));
        expResult.add(new Path(address2, address, 38.1, 90, 28));
        assertEquals(expResult, result);

    }


    @Test
    void updateStatusCourier() {
        Courier courier = new Courier(1, "courier@isep.ipp.pt", "André", 122665789,
                new BigDecimal("24586612344"), 15, 70, 1);
        instance.updateSatusCourier(courier.getIdCourier());
    }

    /*
    @Test
    void getAllPathsPairs() {
        List<Address> addresses = new ArrayList<>();
        Address a1 = new Address(34, 45, "rua xpto", 2, "4500", "espinho");
        Address a2 = new Address(34.05, 45.006, "rua xpty", 2, "4500", "espinho");
        Address a3 = new Address(34.002, 45.004, "rua xpto", 2, "4500", "espinho");
        addresses.add(a1);
        addresses.add(a2);
        addresses.add(a3);

        List<Path> pathList = new ArrayList<>();

        List<Path>  expResult= new ArrayList<>();

        expResult.add(new Path(a1, a2, anyInt(),anyInt(),anyInt()));
        expResult.add(new Path(a1, a3, anyInt(),anyInt(),anyInt()));
        expResult.add(new Path(a2, a1, anyInt(),anyInt(),anyInt()));
        expResult.add(new Path(a2, a3, anyInt(),anyInt(),anyInt()));
        expResult.add(new Path(a3, a1, anyInt(),anyInt(),anyInt()));
        expResult.add(new Path(a3, a2, anyInt(),anyInt(),anyInt()));
        //expResult.add();

        List<Path> result= instance.getAllPathsPairs(addresses,pathList);


        assertEquals(result, expResult);

    }*/
}


