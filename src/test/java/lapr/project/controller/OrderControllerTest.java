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
    private ParkHandler parkHandler;
    private Graph<Address, Double> Graph;

    public OrderControllerTest() {
        clientOrderHandler = new ClientOrderHandler();
        courierDataHandler = new CourierDataHandler();
        addressDataHandler = new AddressDataHandler();
        clientDataHandler = new ClientDataHandler();
        pharmacyDataHandler = new PharmacyDataHandler();
        parkHandler = new ParkHandler();
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
        ParkHandler parkHandlerMock = mock(ParkHandler.class);
        PathDataHandler pathDataHandlermock = mock(PathDataHandler.class);


        Courier courier = new Courier(1, "courier@isep.ipp.pt", "André", 122665789,
                new BigDecimal("24586612344"), 15, 70, 1);
        Pharmacy phar = new Pharmacy(5, "ISEP", "phar1@isep.ipp.pt", 213.123, 2323, 23323, "isep@isep.ipp.pt");
        Address address = new Address(34, 45, "rua xpto", 2, "4500", "espinho");
        Address address2 = new Address(2323, 23323, "rua xpto", 2, "4500", "espinho");
        Client client = new Client(1, "dsfsf", "fjdnsf", "qwerty", 123456789, 34, 45, 12, new BigDecimal("1231231231231231"));
        Delivery delivery = new Delivery(32, 22, 781, 1, "AK-LA-09");
        Park park = new Park(1,12,10,2,1,25,2,1);
        Park park2 = new Park(1,12,10,2,1,25,2,2);



        List<Delivery> aux = new ArrayList<>();
        aux.add(delivery);
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        addresses.add(address2);
        List<Path> path = new ArrayList<>();
        path.add(new Path(34,45,12,12,67,9,0.5,3,45));
        when(pathDataHandlermock.getAllPaths()).thenReturn(path);

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
        Vehicle scooter = new Vehicle("AH-17-LK", 400, 350, 500, 8.0, 5000.0, 430, 4, 1, 88);

        List<Vehicle> drones = new ArrayList<>();
        drones.add(vehicle);
        Vehicle vehicle2 = new Vehicle(1,"AH-87-LK", 400, 350, 1,1,500, 8.0, 5000.0, 430, 4, 2, 88,0.5);

        List<Vehicle> drones2 = new ArrayList<>();
        drones2.add(vehicle2);

        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(scooter);
        when(vehicleHandlerMock.getDronesAvailable(5, 10)).thenReturn(drones2);
        when(vehicleHandlerMock.getDronesAvailable(any(Integer.class), any(Double.class))).thenReturn(drones);
        when(vehicleHandlerMock.getAllScooterAvailables(any(Integer.class), any(Double.class))).thenReturn(vehicleList);
        when(deliveryHandlerMock.getDeliveryByDroneId(any(Integer.class))).thenReturn(new Delivery(25, 30, 40, 0, "AK-LA-09"));
        when(clientOrderHandlerMock.updateStatusOrder(any(Integer.class), any(Integer.class))).thenReturn(true);
        when(vehicleHandlerMock.updateStatusToParked(any(String.class))).thenReturn(true);

        List<RestockOrder> restocks = new ArrayList<>();

        restocks.add(new RestockOrder(1, 1, 4, 2, 5, 7, 0, 10));
        restocks.add(new RestockOrder(1, 3, 5, 6, 9, 4, 20));

        when(restockDataHandlerMock.getRestockList(any(Integer.class))).thenReturn(restocks);

        when(deliveryHandlerMock.updateStatusDelivery(any(Integer.class))).thenReturn(true);

        when(addressDataHandlerMock.getAllAddresses()).thenReturn(addresses);
        Park park3 = new Park(1,12,10,2,1,25,4,2);
        when(clientDataHandlerMock.getClientByClientOrderID(1)).thenReturn(client);

        when(parkHandlerMock.getParkByPharmacyId(5, 1)).thenReturn(park);
        when(parkHandlerMock.getParkByPharmacyId(5, 2)).thenReturn(park2);
        when(parkHandlerMock.getParkByPharmacyId(4, 2)).thenReturn(park3);

        instance = new OrderController(clientOrderHandlerMock, courierDataHandlerMock, addressDataHandlerMock,
                clientDataHandlerMock, pharmacyDataHandlerMock, deliveryHandlerMock, vehicleHandlerMock, refillStockDataHandlerMock, restockDataHandlerMock, parkHandlerMock,pathDataHandlermock);

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

    /*
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
        Graph<Address, Double> result = instance.buildDistanceGraph(addresses, 1,new ArrayList<>());
        boolean resultf = expResult.equals(result);
        assertTrue(resultf);

    }*/

    /*
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
        Graph<Address, Double> result = instance.buildDistanceGraph(addresses, 2, new ArrayList<>());;
        boolean resultf = expResult.equals(result);
        assertTrue(resultf);
    }*/


    @Test
    void getTotalEnergy() {
        double expResult = 1.828394565886806;
        double result = instance.getTotalEnergy(200.0, 1, 5.0, 10.0, 30.0, 40.10, 40.78, -8.33, -8.99);
        assertEquals(expResult, result, 0.1);
    }

    @Test
    void getTotalEnergy2() {
        double expResult = 0.22540961489252576;
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

    /*@Test
    void getDronesAvailable() {

        Vehicle expResult = new Vehicle("AH-87-LK", 400, 350, 500, 8.0, 5000.0, 430, 5, 2, 88);
        Vehicle result = instance.getDronesAvailable(5, 15);
        assertEquals(expResult, result);
    }*/


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
    void testGetAllPaths() {
        List<Path> expResult = new ArrayList<>();
        expResult.add(new Path(34,45,12,12,67,9,0.5,3,45));
        List<Path> result = instance.getAllPaths();

        assertEquals(result, expResult);
    }

    @Test
    void updateStatusDelivery() {
        DeliveryHandler deliveryHandler = mock(DeliveryHandler.class);
        when(deliveryHandler.updateStatusDelivery(any(Integer.class))).thenReturn(Boolean.TRUE);
        OrderController orderController = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(), deliveryHandler, new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());
        orderController.updateStatusDelivery(2);
    }

    @Test
    void sendMailToAllClients() {
        List<String> lst = new ArrayList<>();
        lst.add("teste");
        ClientOrderHandler clientOrderHandlermock = mock(ClientOrderHandler.class);
        when(clientOrderHandlermock.getClientEmailByDelivery(any(Integer.class))).thenReturn(lst);

        OrderController orderController = new OrderController(clientOrderHandlermock, new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(),new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());

        orderController.sendMailToAllClients(4);
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
       // assertEquals(expResult, result); TODO Verificar o erro (antes dava)
    }

   /* @Test
    void createDroneDelivery() {
        Pharmacy phar = new Pharmacy(5, "ISEP", "phar1@isep.ipp.pt", 2323, 23323, 3, "isep@isep.ipp.pt");
        ClientOrder clientOrder = new ClientOrder(1, new Date(1254441245), 12, 1, 0, 0, 1, 1);
        List<ClientOrder> ordersInThisDelivery = new LinkedList<>();
        ordersInThisDelivery.add(clientOrder);
        Vehicle expResult = new Vehicle("AH-87-LK", 5, 350, 500, 8.0, 5000.0, 430, 4, 2, 88);
        Vehicle result = instance.createDroneDelivery(ordersInThisDelivery, phar, 45.0, 2.0, 45.0);

        assertEquals(result, expResult);
    }*/

    @Test
    void createDroneDelivery2() {
        Pharmacy phar = new Pharmacy(5, "ISEP", "phar1@isep.ipp.pt", 213.123, 2323, 23323, "isep@isep.ipp.pt");
        List<ClientOrder> ordersInThisDelivery = new LinkedList<>();
        Vehicle expResult = null;

        Pair<Vehicle, Integer> result = instance.createDroneDelivery(ordersInThisDelivery, phar, 0, 2, 45);
        assertNull(result);
    }


    @Test
    void createDeliveryByScooter() {

        Pharmacy phar = new Pharmacy(5, "ISEP", "phar1@isep.ipp.pt", 2323, 23323, 3, "isep@isep.ipp.pt");
        ClientOrder clientOrder = new ClientOrder(1, new Date(1254441245), 12, 1, 0, 0, 1, 1);
        List<ClientOrder> ordersInThisDelivery = new LinkedList<>();

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
        int expecResult = 0;
        int result = instance.createDeliveryByScooter(ordersInThisDelivery, phar, weight, 2,45);
        assertEquals(expecResult, result);

    }


    @Test
    void updateStatusOrder() {
        ClientOrderHandler clientOrderHandler = mock(ClientOrderHandler.class);
        when(clientOrderHandler.updateStatusOrder(any(Integer.class), any(Integer.class))).thenReturn(Boolean.TRUE);

        OrderController orderController = new OrderController(clientOrderHandler, new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(), new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());
        boolean result = orderController.updateStatusOrder(1, 4);
        assertTrue(result);
    }

    @Test
    void updateStatusOrder2() {
        ClientOrderHandler clientOrderHandler = mock(ClientOrderHandler.class);
        when(clientOrderHandler.updateStatusOrder(any(Integer.class), any(Integer.class))).thenReturn(Boolean.FALSE);

        OrderController orderController = new OrderController(clientOrderHandler, new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(), new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(),
                new RestockDataHandler(), new ParkHandler(), new PathDataHandler());
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

        OrderController orderController = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(), deliveryHandlermock, new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());
        boolean expResult = false;

        boolean result = orderController.updateStatusDelivery(1);

        assertEquals(expResult, result);
    }

    @Test
    void testUpdateStatusVehicle() {
        VehicleHandler vehicleHandlermock = mock(VehicleHandler.class);
        when(vehicleHandlermock.updateStatusToParked(any(String.class))).thenReturn(false);

        OrderController orderController = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(), new DeliveryHandler(), vehicleHandlermock, new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());

        boolean result = orderController.updateStatusVehicle(new Vehicle(1, "AH-87-LK", 400, 350, 0, 1, 500, 8.0, 5000.0, 430, 4, 1, 10, 2.3));

        assertFalse(result);
    }

    @Test
    void buildEnergyGraph() {
        Address address = new Address(34, 45, "rua xpto", 2, "4500", "espinho", 10);
        Address address2 = new Address(2323, 23323, "rua xpto", 2, "4500", "espinho", 11);
        Graph<Address, Double> expResult = new Graph<>(true);
        List<Path> p = new ArrayList<>();

        p.add(0, new Path(address.getLatitude(),address.getLongitude(),address.getAltitude(), address2.getLatitude(),address2.getLongitude(),address2.getAltitude(), 0, 0, 0));

        p.add(1,new Path(address2.getLatitude(),address2.getLongitude(),address2.getAltitude(), address.getLatitude(),address.getLongitude(),address.getAltitude(), 0, 0, 0));


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

        p.add(new Path(address.getLatitude(),address.getLongitude(),address.getAltitude(), address2.getLatitude(),address2.getLongitude(),address2.getAltitude(), 0, 0, 0));

        p.add(new Path(address2.getLatitude(),address2.getLongitude(),address2.getAltitude(), address.getLatitude(),address.getLongitude(),address.getAltitude(), 0, 0, 0));


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
        pathPairs.add(new Path(address.getLatitude(),address.getLongitude(),address.getAltitude(), address2.getLatitude(),address2.getLongitude(),address2.getAltitude(), 0, 0, 0));
        pathPairs.add(new Path(address2.getLatitude(),address2.getLongitude(),address2.getAltitude(), address.getLatitude(),address2.getLongitude(),address2.getAltitude(), 0, 0, 0));

        Vehicle vehicle = new Vehicle("AH-87-LK", 400, 350, 500, 8.0, 5000.0, 430, 4, 2, 88);

        Pair<Integer, Vehicle> result = instance.createRestockRequestByDrone(restocklistToMakeDelivery,weightSum,points,distance, 45, 1);
        Pair<Integer, Vehicle> expResult = new Pair<>(5, vehicle);

        assertEquals(result, expResult);
     }

     @Test
     void createRestockRequestByElectricScooter() {
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
         pathPairs.add(new Path(address.getLatitude(),address.getLongitude(),address.getAltitude(), address2.getLatitude(),address2.getLongitude(),address2.getAltitude(), 0, 0, 0));
         pathPairs.add(new Path(address2.getLatitude(),address2.getLongitude(),address2.getAltitude(), address.getLatitude(),address2.getLongitude(),address2.getAltitude(), 0, 0, 0));

         Vehicle scooter = new Vehicle("AH-17-LK", 400, 350, 500, 8.0, 5000.0, 430, 4, 1, 88);


         Pair<Integer, Vehicle> result = instance.createRestockRequestByEletricScooter(restocklistToMakeDelivery,weightSum,points,distance, 45, 1);
         Pair<Integer, Vehicle> expResult = new Pair<>(5, scooter);

         assertEquals(result, expResult);
     }

    @Test
    void createRestockRequestByElectricScooter2() {
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
        pathPairs.add(new Path(address.getLatitude(),address.getLongitude(),address.getAltitude(), address2.getLatitude(),address2.getLongitude(),address2.getAltitude(), 0, 0, 0));
        pathPairs.add(new Path(address2.getLatitude(),address2.getLongitude(),address2.getAltitude(), address.getLatitude(),address2.getLongitude(),address2.getAltitude(), 0, 0, 0));

        Vehicle scooter = new Vehicle("AH-17-LK", 400, 350, 500, 8.0, 5000.0, 430, 4, 1, 88);


        Pair<Integer, Vehicle> result = instance.createRestockRequestByEletricScooter(restocklistToMakeDelivery,weightSum,points,distance,10, 1);
        Pair<Integer, Vehicle> expResult = new Pair<>(5, scooter);

        assertEquals(result, expResult);
    }



    @Test
    void updateStatusCourier() {
        Courier courier = new Courier(1, "courier@isep.ipp.pt", "André", 122665789,
                new BigDecimal("24586612344"), 15, 70, 1);
        instance.updateSatusCourier(courier.getIdCourier());
    }



    @Test
    void associateVehicleToDelivery() {
        VehicleHandler vehicleHandlermock = mock(VehicleHandler.class);
        when(vehicleHandlermock.associateVehicleToDelivery(any(Integer.class), any(String.class))).thenReturn(Boolean.TRUE);
        OrderController orderController = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(),new DeliveryHandler(), vehicleHandlermock, new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());
        boolean result = orderController.associateVehicleToDelivery(4, "AB-98-AP");
        assertTrue(result);
    }

    @Test
    void associateVehicleToDelivery2() {
        VehicleHandler vehicleHandlermock = mock(VehicleHandler.class);
        when(vehicleHandlermock.associateVehicleToDelivery(any(Integer.class), any(String.class))).thenReturn(Boolean.FALSE);
        OrderController orderController = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(),new DeliveryHandler(), vehicleHandlermock, new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());
        boolean result = orderController.associateVehicleToDelivery(4, "AB-98-AP");
        assertFalse(result);
    }


    @Test
    void addPath() {
        Path p = new Path( 45,45,23,33,332,12,5,6,8);
        PathDataHandler pathDataHandlermock = mock(PathDataHandler.class);
        when(pathDataHandlermock.addPath(any(Path.class))).thenReturn(Boolean.TRUE);

        OrderController orderController = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(),new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), pathDataHandlermock);

        boolean result = orderController.addPath(p.getLatitudeFrom(),p.getLongitudeFrom(),p.getAltitudeFrom(),p.getLatitudeTo(), p.getLongitudeTo(), p.getAltitudeTo(), p.getRoadRollingResistance(), p.getWindDirection(), p.getWindspeed());

        assertTrue(result);
    }

    @Test
    void addPath2() {
        Path p = new Path( 45,45,23,33,332,12,5,6,8);
        PathDataHandler pathDataHandlermock = mock(PathDataHandler.class);
        when(pathDataHandlermock.addPath(any(Path.class))).thenReturn(Boolean.FALSE);

        OrderController orderController = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(),new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), pathDataHandlermock);

        boolean result = orderController.addPath(p.getLatitudeFrom(),p.getLongitudeFrom(),p.getAltitudeFrom(),p.getLatitudeTo(), p.getLongitudeTo(), p.getAltitudeTo(), p.getRoadRollingResistance(), p.getWindDirection(), p.getWindspeed());

        assertFalse(result);
    }

    @Test
    void getAddressesToMakeDelivery() {
            Address add1 = new Address(34, 45,"rua xpto", 2, "4500", "espinho",40);
            Address add2 = new Address(59, 12, "rua", 3, "4202", "porto", 30);
            List<ClientOrder> lstOrders = new ArrayList<>();
            ClientOrder order = new ClientOrder(20, 10, 1, 1);
            lstOrders.add(order);
            List<Address> lstaddresses = new ArrayList<>();
            lstaddresses.add(add1);
            List<Address> lstAddressesDelivery = new ArrayList<>();
            lstAddressesDelivery.add(add2);

            ClientDataHandler clientDataHandlermock = mock(ClientDataHandler.class);
            when(clientDataHandlermock.getClientByID(any(Integer.class))).thenReturn(new Client(1, "Alexandre", "alex@gmail.com", "rosa", 123456789, 234.816, 2715.9881,123.109, new BigDecimal("1234567891057189")));

            Pharmacy pharmacy = new Pharmacy(5,"farmacia", "Farmácia Tirori",232.019, 41.1111, -8.9999, "admin@isep.ipp.pt");

            OrderController orderController = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), clientDataHandlermock, new PharmacyDataHandler(),new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());

            Address result = orderController.getAddressesToMakeDelivery(lstOrders, lstaddresses, lstAddressesDelivery, pharmacy);

            assertNull(result);

    }

    @Test
    void getAddressesToMakeDelivery2() {
        Address add1 = new Address(34, 45,"rua xpto", 2, "4500", "espinho",40);
        Address add2 = new Address(59, 12, "rua", 3, "4202", "porto", 30);
        Address add3 = new Address(60, 13, "rua", 3, "4202", "porto", 0);
        List<ClientOrder> lstOrders = new ArrayList<>();
        ClientOrder order = new ClientOrder(20, 10, 1, 1);
        lstOrders.add(order);
        List<Address> lstaddresses = new ArrayList<>();
        lstaddresses.add(add1);
        lstaddresses.add(add3);
        List<Address> lstAddressesDelivery = new ArrayList<>();
        lstAddressesDelivery.add(add2);

        ClientDataHandler clientDataHandlermock = mock(ClientDataHandler.class);
        when(clientDataHandlermock.getClientByID(any(Integer.class))).thenReturn(new Client(1, "Alexandre", "alex@gmail.com", "rosa", 123456789, 60, 13,0, new BigDecimal("1234567891057189")));

        Pharmacy pharmacy = new Pharmacy(5,"farmacia", "Farmácia Tirori",34, 45, 40, "admin@isep.ipp.pt");

        OrderController orderController = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), clientDataHandlermock, new PharmacyDataHandler(),new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());

        Address result = orderController.getAddressesToMakeDelivery(lstOrders, lstaddresses, lstAddressesDelivery, pharmacy);

        assertEquals(add1,result);

    }

    @Test
    void getAddressesToMakeRestock() {
        Address add1 = new Address(34, 45,"rua xpto", 2, "4500", "espinho",40);
        Address add2 = new Address(59, 12, "rua", 3, "4202", "porto", 30);
        Address add3 = new Address(60, 13, "rua", 3, "4202", "porto", 0);
        Pharmacy phar = new Pharmacy(5, "ISEP", "phar1@isep.ipp.pt", 213.123, 2323, 23323, "isep@isep.ipp.pt");

        List<RestockOrder> lstOrders = new ArrayList<>();
        RestockOrder order = new RestockOrder(1,5,1,5,1,10,1,4);
        lstOrders.add(order);
        List<Address> lstaddresses = new ArrayList<>();
        lstaddresses.add(add1);
        lstaddresses.add(add3);
        List<Address> lstAddressesDelivery = new ArrayList<>();
        lstAddressesDelivery.add(add2);

        PharmacyDataHandler pharmacyDataHandlermock = mock(PharmacyDataHandler.class);
        when(pharmacyDataHandlermock.getPharmacyByID(any(Integer.class))).thenReturn(phar);

        ClientDataHandler clientDataHandlermock = mock(ClientDataHandler.class);
        when(clientDataHandlermock.getClientByClientOrderID(any(Integer.class))).thenReturn(new Client(1, "Alexandre", "alex@gmail.com", "rosa", 123456789, 60, 13,0, new BigDecimal("1234567891057189")));

        Pharmacy pharmacy = new Pharmacy(5,"farmacia", "Farmácia Tirori",34, 45, 40, "admin@isep.ipp.pt");

        OrderController orderController = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), clientDataHandlermock, pharmacyDataHandlermock,new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());

        Address result = orderController.getAddressesToMakeRestock(lstOrders, lstaddresses, lstAddressesDelivery, pharmacy);

        assertEquals(add1,result);

    }

    @Test
    void estimateCostPathForDelivery() {
        Address add1 = new Address(34, 45,"rua xpto", 2, "4500", "espinho",40);
        Address add2 = new Address(59, 12, "rua", 3, "4202", "porto", 30);
        Address add3 = new Address(60, 13, "rua", 3, "4202", "porto", 0);
        List<ClientOrder> lstOrders = new ArrayList<>();
        ClientOrder order = new ClientOrder(20, 10, 1, 1);
        lstOrders.add(order);
        List<Address> lstaddresses = new ArrayList<>();
        lstaddresses.add(add1);
        lstaddresses.add(add3);
        List<Address> lstAddressesDelivery = new ArrayList<>();
        lstAddressesDelivery.add(add2);
        List<Path> lstPath = new ArrayList<>();
        lstPath.add(new Path(add1.getLatitude(),add1.getLongitude(),add1.getAltitude(),add3.getLatitude(),add3.getLongitude(),add3.getAltitude(),25,10,10));

        ClientDataHandler clientDataHandlermock = mock(ClientDataHandler.class);
        when(clientDataHandlermock.getClientByID(any(Integer.class))).thenReturn(new Client(1, "Alexandre", "alex@gmail.com", "rosa", 123456789, 60, 13,0, new BigDecimal("1234567891057189")));

        Pharmacy pharmacy = new Pharmacy(5,"farmacia", "Farmácia Tirori",34, 45, 40, "admin@isep.ipp.pt");

        OrderController orderController = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), clientDataHandlermock, new PharmacyDataHandler(),new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());

        Pair<LinkedList<Address>, Double> result = orderController.estimateCostPathForDelivery(lstaddresses, lstOrders, pharmacy, 1, lstPath, 10);

        assertNull(result);
    }

    @Test
    void estimateCostPathForDelivery2() {
        Address add1 = new Address(34, 45,"rua xpto", 2, "4500", "espinho",40);
        Address add2 = new Address(59, 12, "rua", 3, "4202", "porto", 30);
        Address add3 = new Address(60, 13, "rua", 3, "4202", "porto", 0);
        List<ClientOrder> lstOrders = new ArrayList<>();
        ClientOrder order = new ClientOrder(20, 10, 1, 1);
        lstOrders.add(order);
        List<Address> lstaddresses = new ArrayList<>();
        lstaddresses.add(add1);
        lstaddresses.add(add3);
        List<Path> lstPath = new ArrayList<>();
        lstPath.add(new Path(add1.getLatitude(),add1.getLongitude(),add1.getAltitude(),add3.getLatitude(),add3.getLongitude(),add3.getAltitude(),25,10,10));

        ClientDataHandler clientDataHandlermock = mock(ClientDataHandler.class);
        when(clientDataHandlermock.getClientByID(any(Integer.class))).thenReturn(new Client(1, "Alexandre", "alex@gmail.com", "rosa", 123456789, 60, 13,0, new BigDecimal("1234567891057189")));

        Pharmacy pharmacy = new Pharmacy(5,"farmacia", "Farmácia Tirori",34, 45, 40, "admin@isep.ipp.pt");

        OrderController orderController = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), clientDataHandlermock, new PharmacyDataHandler(),new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());

        Pair<LinkedList<Address>, Double> result = orderController.estimateCostPathForDelivery(lstaddresses, lstOrders, pharmacy, 1, lstPath, 0);

        assertNull(result);
    }

    @Test
    void estimateCostPathForRestock() {
        Address add1 = new Address(34, 45,"rua xpto", 2, "4500", "espinho",40);
        Address add2 = new Address(59, 12, "rua", 3, "4202", "porto", 30);
        Address add3 = new Address(60, 13, "rua", 3, "4202", "porto", 0);


        List<RestockOrder> lstOrders = new ArrayList<>();
        RestockOrder order = new RestockOrder(1,5,1,5,1,10,1,4);
        lstOrders.add(order);
        List<Address> lstaddresses = new ArrayList<>();
        lstaddresses.add(add1);
        lstaddresses.add(add3);
        List<Path> lstPath = new ArrayList<>();
        lstPath.add(new Path(add1.getLatitude(),add1.getLongitude(),add1.getAltitude(),add3.getLatitude(),add3.getLongitude(),add3.getAltitude(),25,10,10));

        Pharmacy phar = new Pharmacy(5, "ISEP", "phar1@isep.ipp.pt", 213.123, 2323, 23323, "isep@isep.ipp.pt");
        PharmacyDataHandler pharmacyDataHandlermock = mock(PharmacyDataHandler.class);
        when(pharmacyDataHandlermock.getPharmacyByID(any(Integer.class))).thenReturn(phar);

        ClientDataHandler clientDataHandlermock = mock(ClientDataHandler.class);
        when(clientDataHandlermock.getClientByClientOrderID(any(Integer.class))).thenReturn(new Client(1, "Alexandre", "alex@gmail.com", "rosa", 123456789, 60, 13,0, new BigDecimal("1234567891057189")));

        Pharmacy pharmacy = new Pharmacy(5,"farmacia", "Farmácia Tirori",34, 45, 40, "admin@isep.ipp.pt");

        OrderController orderController = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), clientDataHandlermock, pharmacyDataHandlermock,new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());

        Pair<LinkedList<Address>, Double> result = orderController.estimateCostPathForRestock(lstaddresses, lstOrders, pharmacy, 1, lstPath, 10);

        LinkedList<Address> explst = new LinkedList<>();
        explst.add(add1);explst.add(add3);
        Pair<LinkedList<Address>, Double> expectedResult = new Pair<>(explst,72127.14415356214);

        assertNull(result);
    }

    @Test
    void estimateCostPathForRestock2() {
        Address add1 = new Address(34, 45,"rua xpto", 2, "4500", "espinho",40);
        Address add2 = new Address(59, 12, "rua", 3, "4202", "porto", 30);
        Address add3 = new Address(60, 13, "rua", 3, "4202", "porto", 0);
        List<RestockOrder> lstOrders = new ArrayList<>();
        RestockOrder order = new RestockOrder(1,5,1,5,1,10,1,4);
        lstOrders.add(order);
        List<Address> lstaddresses = new ArrayList<>();
        lstaddresses.add(add1);
        lstaddresses.add(add3);
        List<Path> lstPath = new ArrayList<>();
        lstPath.add(new Path(add1.getLatitude(),add1.getLongitude(),add1.getAltitude(),add3.getLatitude(),add3.getLongitude(),add3.getAltitude(),25,10,10));

        Pharmacy phar = new Pharmacy(5, "ISEP", "phar1@isep.ipp.pt", 213.123, 2323, 23323, "isep@isep.ipp.pt");
        PharmacyDataHandler pharmacyDataHandlermock = mock(PharmacyDataHandler.class);
        when(pharmacyDataHandlermock.getPharmacyByID(any(Integer.class))).thenReturn(phar);

        ClientDataHandler clientDataHandlermock = mock(ClientDataHandler.class);
        when(clientDataHandlermock.getClientByClientOrderID(any(Integer.class))).thenReturn(new Client(1, "Alexandre", "alex@gmail.com", "rosa", 123456789, 60, 13,0, new BigDecimal("1234567891057189")));

        Pharmacy pharmacy = new Pharmacy(5,"farmacia", "Farmácia Tirori",34, 45, 40, "admin@isep.ipp.pt");

        OrderController orderController = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), clientDataHandlermock, pharmacyDataHandlermock,new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());

        Pair<LinkedList<Address>, Double> result = orderController.estimateCostPathForRestock(lstaddresses, lstOrders, pharmacy, 1, lstPath, 0);

        LinkedList<Address> explst = new LinkedList<>();
        explst.add(add1);explst.add(add3);
        Pair<LinkedList<Address>, Double> expectedResult = new Pair<>(explst,3702793.7708192044);

        assertNull(result);
    }

    @Test
    void getNecessaryEnergy() {
        Address add1 = new Address(34, 45,"rua xpto", 2, "4500", "espinho",40);
        Address add2 = new Address(59, 12, "rua", 3, "4202", "porto", 30);
        Address add3 = new Address(60, 13, "rua", 3, "4202", "porto", 0);
        Address add4 = new Address(60, 13, "rua", 3, "4202", "porto", 0);
        LinkedList<Address> pathsDelivery = new LinkedList<>();
        pathsDelivery.add(add1);
        pathsDelivery.add(add2);
        List<Path> pathPairs = new LinkedList<>();
        pathPairs.add(new Path(add1.getLatitude(),add1.getLongitude(),add1.getAltitude(),add2.getLatitude(),add2.getLongitude(),add2.getAltitude(), 10, 20, 20));

        OrderController orderController = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(),new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());

        double result = orderController.getNecessaryEnergy(pathsDelivery, 10, pathPairs, 1);

        assertEquals(28706.6210819059,result);

    }

    @Test
    void getNecessaryEnergy2() {
        Address add1 = new Address(34, 45,"rua xpto", 2, "4500", "espinho",40);
        Address add2 = new Address(59, 12, "rua", 3, "4202", "porto", 30);
        Address add3 = new Address(60, 13, "rua", 3, "4202", "porto", 0);
        Address add4 = new Address(60, 13, "rua", 3, "4202", "porto", 0);
        LinkedList<Address> pathsDelivery = new LinkedList<>();
        pathsDelivery.add(add1);
        pathsDelivery.add(add2);
        List<Path> pathPairs = new LinkedList<>();
        pathPairs.add(new Path(add1.getLatitude(),add1.getLongitude(),add1.getAltitude(),add2.getLatitude(),add2.getLongitude(),add2.getAltitude(), 10, 20, 20));

        OrderController orderController = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(),new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());

        double result = orderController.getNecessaryEnergy(pathsDelivery, 10, pathPairs, 2);

        assertEquals(5.979317246794144,result);

    }

    @Test
    void getNecessaryEnergy3() {
        Address add1 = new Address(34, 45,"rua xpto", 2, "4500", "espinho",40);
        Address add2 = new Address(59, 12, "rua", 3, "4202", "porto", 30);
        Address add3 = new Address(60, 13, "rua", 3, "4202", "porto", 0);
        Address add4 = new Address(60, 13, "rua", 3, "4202", "porto", 0);
        LinkedList<Address> pathsDelivery = new LinkedList<>();
        pathsDelivery.add(add3);
        pathsDelivery.add(add4);
        List<Path> pathPairs = new LinkedList<>();
        pathPairs.add(new Path(add1.getLatitude(),add1.getLongitude(),add1.getAltitude(),add2.getLatitude(),add2.getLongitude(),add2.getAltitude(), 10, 20, 20));

        OrderController orderController = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(),new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());

        double result = orderController.getNecessaryEnergy(pathsDelivery, 10, pathPairs, 2);

        assertEquals(0.0,result);

    }


    @Test
    void buildDistanceGraph() {
        Address address = new Address(34, 45, "rua xpto", 2, "4500", "espinho", 10);
        Address address2 = new Address(2323, 23323, "rua xpto", 2, "4500", "espinho", 11);
        Graph<Address, Double> expResult = new Graph<>(true);
        List<Path> p = new ArrayList<>();

        p.add(0, new Path(address.getLatitude(),address.getLongitude(),address.getAltitude(), address2.getLatitude(),address2.getLongitude(),address2.getAltitude(), 0, 0, 0));

        p.add(1,new Path(address2.getLatitude(),address2.getLongitude(),address2.getAltitude(), address.getLatitude(),address.getLongitude(),address.getAltitude(), 0, 0, 0));


        double distanceWithElevation = Physics.calculateDistanceWithElevation(address.getLatitude(), address2.getLatitude(), address.getLongitude(), address2.getLongitude(), address.getAltitude(), address2.getAltitude());

        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        addresses.add(address2);
        expResult.insertVertex(address);
        expResult.insertVertex(address2);
        double distance = Physics.getNecessaryEnergy(distanceWithElevation, 10, 1, 2, 1, 10, 10, 0.05,1);
        expResult.insertEdge(address, address2, distance, distance);
        expResult.insertEdge(address2, address, distance, distance);
        Graph<Address, Double> result = instance.buildDistanceGraph(addresses, 1, p);
        assertEquals(result, expResult);
    }

    @Test
    void buildDistanceGraph2() {
        Address address = new Address(34, 45, "rua xpto", 2, "4500", "espinho", 10);
        Address address2 = new Address(2323, 23323, "rua xpto", 2, "4500", "espinho", 11);
        Graph<Address, Double> expResult = new Graph<>(true);
        List<Path> p = new ArrayList<>();

        p.add(0, new Path(address.getLatitude(),address.getLongitude(),address.getAltitude(), address2.getLatitude(),address2.getLongitude(),address2.getAltitude(), 0, 0, 0));

        p.add(1,new Path(address2.getLatitude(),address2.getLongitude(),address2.getAltitude(), address.getLatitude(),address.getLongitude(),address.getAltitude(), 0, 0, 0));


        double distanceWithElevation = Physics.calculateDistanceWithElevation(address.getLatitude(), address2.getLatitude(), address.getLongitude(), address2.getLongitude(), address.getAltitude(), address2.getAltitude());

        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        addresses.add(address2);
        expResult.insertVertex(address);
        expResult.insertVertex(address2);
        double distance = Physics.getNecessaryEnergy(distanceWithElevation, 10, 1, 2, 1, 10, 10, 0.05,1);
        expResult.insertEdge(address, address2, distance, distance);
        expResult.insertEdge(address2, address, distance, distance);
        Graph<Address, Double> result = instance.buildDistanceGraph(addresses, 2, p);
        assertEquals(result, expResult);
    }


    @Test
    void getDronesAvailable() {

        Vehicle vehicle = new Vehicle(1,"AH-87-LK",400,350,0,1,500,8.0,5000.0,430,4, 2,10,2.3);
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(vehicle);

        VehicleHandler vehicleHandlermock = mock(VehicleHandler.class);
        when(vehicleHandlermock.getDronesAvailable(any(Integer.class), any(Integer.class))).thenReturn(vehicleList);

        OrderController orderController = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(),new DeliveryHandler(), vehicleHandlermock, new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());

        Vehicle result = orderController.getDronesAvailable(4, 20);

        assertNull(result);

    }

    @Test
    void getDronesAvailable2() {

        Vehicle vehicle = new Vehicle(1,"AH-87-LK",400,350,0,1,500,8.0,5000.0,430,4, 2,10,2.3);
        Park park = new Park(1,12,10,2,1,25,4,2);

        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(vehicle);

        /*VehicleHandler vehicleHandlermock = mock(VehicleHandler.class);
        when(vehicleHandlermock.getDronesAvailable(4, 20)).thenReturn(vehicleList);
        when(vehicleHandlermock.getParkByPharmacyId(4, 2)).thenReturn(park);
        doNothing().when(vehicleHandlermock).updateStatusToBusy("AH-87-LK");

        OrderController orderController = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(),new DeliveryHandler(), vehicleHandlermock, new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());
*/
        Vehicle result = instance.getDronesAvailable(4, 20);

        assertEquals(vehicle, result);

    }


}


