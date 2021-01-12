package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.model.Graph.Graph;
import lapr.project.utils.Distance;
import oracle.ucp.util.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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


        Courier courier = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigDecimal("24586612344"),15,70,1);
        Pharmacy phar = new Pharmacy(5, "ISEP", 2323, 23323, "isep@isep.ipp.pt");
        Address address = new Address(34, 45,"rua xpto", 2, "4500", "espinho");
        Address address2 = new Address(2323, 23323,"rua xpto", 2, "4500", "espinho");
        Client client = new Client(1, "dsfsf", "fjdnsf", "qwerty", 123456789, 34 , 45, new BigDecimal("1231231231231231"));
        Delivery delivery = new Delivery(32,22,781,1, 0);
        List<Delivery> aux = new ArrayList<>();
        aux.add(delivery);
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        addresses.add(address2);
        when(courierDataHandlerMock.getCourierByEmail(any(String.class))).thenReturn(courier);
        when(courierDataHandlerMock.getCourier(any(Double.class))).thenReturn(courier);
        when(pharmacyDataHandlerMock.getPharmacyByID(any(Integer.class))).thenReturn(phar);
        when(addressDataHandlerMock.getAllAddresses()).thenReturn(addresses);
        when(clientDataHandlerMock.getClientByID(any(Integer.class))).thenReturn(client);
        when(clientDataHandlerMock.getClientByEmail(any(String.class))).thenReturn(client);
       when(deliveryHandlerMock.getDeliverysByCourierId(any(Integer.class))).thenReturn(aux);

        ClientOrder clientOrder = new ClientOrder(1,new Date(1254441245),12,1,0,1,1);
        LinkedHashMap<Integer,ClientOrder> orders = new LinkedHashMap<>();
        orders.put(1,clientOrder);
        when(clientOrderHandlerMock.getUndoneOrders()).thenReturn(orders);
        instance = new OrderController(clientOrderHandlerMock, courierDataHandlerMock, addressDataHandlerMock, clientDataHandlerMock, pharmacyDataHandlerMock, deliveryHandlerMock);

    }

    @Test
    void getCourierByNif() {
        double nif = 122665789;

        Courier expResult = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigDecimal("24586612344"),15,70,1);
        Courier result = instance.getCourierByNIF(nif);
        assertEquals(expResult.getNIF(), result.getNIF());
    }

    @Test
    void getUndoneOrders() {
        ClientOrder clientOrder = new ClientOrder(1,new Date(1254441245),12,1,0,1,1);
        LinkedHashMap<Integer,ClientOrder> expResult = new LinkedHashMap<>();
        expResult.put(1,clientOrder);
        LinkedHashMap<Integer,ClientOrder> result = instance.getUndoneOrders();
        assertEquals(expResult,result);
    }

    @Test
    void getPharmByID() {
        int id = 5;
        Pharmacy expResult = new Pharmacy(5, "ISEP", 2323, 23323, "isep@isep.ipp.pt");
        Pharmacy result = instance.getPharmByID(id);
        assertEquals(expResult.getName(), result.getName());
    }

    @Test
    void buildGraph() throws SQLException {
        Address address = new Address(34, 45,"rua xpto", 2, "4500", "espinho");
        Address address2 = new Address(2323, 23323,"rua xpto", 2, "4500", "espinho");
        Graph<Address, Double> expResult = new Graph<>(true);
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        addresses.add(address2);
        expResult.insertVertex(address);
        expResult.insertVertex(address2);
        double distance = Distance.distanceBetweenTwoAddresses(address.getLatitude(), address.getLongitude(), address2.getLatitude(), address2.getLongitude());
        expResult.insertEdge(address, address2, distance, distance);
        expResult.insertEdge(address2, address, distance, distance);
        Graph<Address, Double> result = instance.buildGraph(addresses);
        assertEquals(result, expResult);

    }

    @Test
    void processDelivery() throws SQLException {
        ClientOrder clientOrder = new ClientOrder(1,new Date(1254441245),12,1,0,1,1);
        Pharmacy phar = new Pharmacy(5, "ISEP", 2323, 23323, "isep@isep.ipp.pt");
        Address address = new Address(34, 45,"rua xpto", 2, "4500", "espinho");
        Address address2 = new Address(2323, 23323,"rua xpto", 2, "4500", "espinho");
        List<ClientOrder> ordersInThisDelivery = new ArrayList<>();
        ordersInThisDelivery.add(clientOrder);
        List<Pair<LinkedList<Address>, Double>> expResult = new ArrayList<>();
        LinkedList<Address> path = new LinkedList<>();
        path.add(address2);
        path.add(address);
        path.add(address2);
        double distance = Distance.distanceBetweenTwoAddresses(address.getLatitude(), address.getLongitude(), address2.getLatitude(), address2.getLongitude());
        distance += distance;
        expResult.add(new Pair<>(path, distance));
        List<Pair<LinkedList<Address>, Double>> result = instance.processDelivery(ordersInThisDelivery, phar);
        assertEquals(result, expResult);
    }

    @Test
    void getTotalEnergy() {
        double expResult = 0.39226600000000006;
        double result = instance.getTotalEnergy(15, 12);
        assertEquals(expResult, result);
    }

    @Test
    void getTotalEnergy2() {
        double expResult = 0;
        double result = instance.getTotalEnergy(15, 0);
        assertEquals(expResult, result);
    }

    @Test
    void getOrdersWeight() {
        ClientOrder clientOrder = new ClientOrder(1,new Date(1254441245),12,1,0,1,1);
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

        Courier expResult = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigDecimal("24586612344"),15,70,1);
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
        Courier courier = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigDecimal("24586612344"),15,70,1);
        Delivery delivery = new Delivery(32,22,781,1, 0);
        List<Delivery> expResult = new ArrayList<>();
        expResult.add(delivery);
        List<Delivery> result = instance.getDeliverysByCourierId(1);
        assertEquals(result, expResult);
    }
}