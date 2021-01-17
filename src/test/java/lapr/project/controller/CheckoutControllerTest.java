package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CheckoutControllerTest {

    private static CheckoutController instance;
    private static ClientOrderHandler clientOrderHandlerMock;

    public CheckoutControllerTest() {

    }


    @BeforeAll
    public static void setUpClass() {
        ClientDataHandler clientDataHandlerMock = mock(ClientDataHandler.class);

        clientOrderHandlerMock = mock(ClientOrderHandler.class);
        InvoiceHandler invoiceHandlerMock = mock(InvoiceHandler.class);
        Client client = new Client("Ricardo", "client1@isep.ipp.pt", "qwerty", 189102816, 41.38344, -8.76364, new BigDecimal("1829102918271622"));

        client.setNumCredits(10000);
        when(clientDataHandlerMock.getClientByEmail(any(String.class))).thenReturn(client);

        ClientOrder order = new ClientOrder(1, new Date(1254441245), 12, 1, 0, 1, 1);
        when(clientOrderHandlerMock.addClientOrder(any(ClientOrder.class))).thenReturn(order.getOrderId());


        Invoice inv = new Invoice(1, new Date(1254441245), 12, 1, 1);
        when(invoiceHandlerMock.addInvoice(any(Invoice.class))).thenReturn(inv.getId());
        when(invoiceHandlerMock.getInvoice(any(Integer.class))).thenReturn(inv);

        User u = new User("client1@isep.ipp.pt", "qwerty", "CLIENT");
        UserSession userSessionMock = mock(UserSession.class);

        when(userSessionMock.getUser()).thenReturn(u);


        instance = new CheckoutController(clientDataHandlerMock, clientOrderHandlerMock, invoiceHandlerMock);

    }

    @Test
    void checkoutProcess() {
        User u = new User("client1@isep.ipp.pt", "querty", "CLIENT");
        UserSession userSessionMock = mock(UserSession.class);

        when(userSessionMock.getUser()).thenReturn(u);
        Pharmacy phar = new Pharmacy(1,"phar", "Farmácia Tirori", 2313.12, 41.1111, -8.9999, "admin@isep.ipp.pt");
        UserSession.getInstance().setUser(u);
        Cart cart = new Cart(45, 6, new ArrayList<>());
        List<Cart.AuxProduct> newList = new ArrayList<>();
        Cart.AuxProduct auxProduct = new Cart.AuxProduct(new Product("xarope", "xarope para a tosse", 6, 0.5, 1, 2), 5);
        newList.add(auxProduct);
        cart.setProductsTobuy(newList);


        boolean result = instance.checkoutProcess(cart, true);

        boolean expectedResult = true;

        assertEquals(result, expectedResult);

    }

    @Test
    void checkoutProcess3() {
        User u = new User("client1@isep.ipp.pt", "querty", "CLIENT");
        UserSession userSessionMock = mock(UserSession.class);

        when(userSessionMock.getUser()).thenReturn(u);
        Pharmacy phar = new Pharmacy(1,"phar", "Farmácia Tirori", 2313.12, 41.1111, -8.9999, "admin@isep.ipp.pt");
        UserSession.getInstance().setUser(u);
        Cart cart = new Cart(0, 0, new ArrayList<>());


        boolean result = instance.checkoutProcess(cart, true);

        boolean expectedResult = false;

        assertEquals(result, expectedResult);

    }

    @Test
    void checkoutProcess4() {
        User u = new User("client1@isep.ipp.pt", "querty", "CLIENT");
        UserSession userSessionMock = mock(UserSession.class);

        when(userSessionMock.getUser()).thenReturn(u);
        Pharmacy phar = new Pharmacy(1,"phar", "Farmácia Tirori", 2313.12, 41.1111, -8.9999, "admin@isep.ipp.pt");
        UserSession.getInstance().setUser(u);
        Cart cart = new Cart(0, 0, new ArrayList<>());


        boolean result = instance.checkoutProcess(cart, false);

        boolean expectedResult = false;

        assertEquals(result, expectedResult);

    }

    @Test
    void checkoutProcess2() {
        User u = new User("client1@isep.ipp.pt", "querty", "CLIENT");
        UserSession userSessionMock = mock(UserSession.class);

        when(userSessionMock.getUser()).thenReturn(u);
        Pharmacy phar = new Pharmacy(1,"phar", "Farmácia Tirori", 2313.12, 41.1111, -8.9999, "admin@isep.ipp.pt");
        UserSession.getInstance().setUser(u);
        Cart cart = new Cart(45, 6, new ArrayList<>());
        List<Cart.AuxProduct> newList = new ArrayList<>();
        Cart.AuxProduct auxProduct = new Cart.AuxProduct(new Product("xarope", "xarope para a tosse", 6, 0.5, 1, 2), 5);
        newList.add(auxProduct);
        cart.setProductsTobuy(newList);

        boolean result = instance.checkoutProcess(cart, false);
        instance.doPayment(instance.getClientByEmail("client1@isep.ipp.pt"), 45);

        boolean expectedResult = true;

        assertEquals(result, expectedResult);
        /*
        Cart cart = new Cart(0, 0, new ArrayList<>());
        Pharmacy phar = new Pharmacy(1, "Farmácia Tirori", "pharm1@isep.ipp.pt", 41.1111, -8.9999, "admin@isep.ipp.pt");


        boolean result = instance.checkoutProcess(cart, phar, false);

        boolean expectedResult = false;

        assertEquals(result, expectedResult);*/

    }


    @Test
    void checkoutProcess5() {
        User u = new User("client1@isep.ipp.pt", "querty", "CLIENT");
        UserSession userSessionMock = mock(UserSession.class);

        when(userSessionMock.getUser()).thenReturn(u);
        Pharmacy phar = new Pharmacy(1,"phar", "Farmácia Tirori", 2313.12, 41.1111, -8.9999, "admin@isep.ipp.pt");
        UserSession.getInstance().setUser(u);
        Cart cart = new Cart(45, 6, new ArrayList<>());
        List<Cart.AuxProduct> newList = new ArrayList<>();
        Cart.AuxProduct auxProduct = new Cart.AuxProduct(new Product("xarope", "xarope para a tosse", 6, 0.5, 1, 2), 5);
        newList.add(auxProduct);
        cart.setProductsTobuy(newList);

        boolean result = instance.checkoutProcess(cart, true);
        instance.doPayment(instance.getClientByEmail("client1@isep.ipp.pt"), 45);

        boolean expectedResult = true;

        assertEquals(result, expectedResult);
    }

    @Test
    void getUserSession() {
        User u = new User("xandinho@gmail.com", "qwerty", "CLIENT");
        UserSession userSessionMock = mock(UserSession.class);

        when(userSessionMock.getUser()).thenReturn(u);

        UserSession.getInstance().setUser(u);


        assertEquals(instance.getUserSession(), UserSession.getInstance().getUser());
    }

    @Test
    void getClientByEmail() {

        String email = "client1@isep.ipp.pt";

        Client expResult = new Client("Ricardo", "client1@isep.ipp.pt", "qwerty", 189102816, 2332.91872, 827162.23234, new BigDecimal("1829102918271622"));
        Client result = instance.getClientByEmail(email);
        assertEquals(expResult.getEmail(), result.getEmail());
    }

    @Test
    void saveClientOrder() {
        int expectedResult = 1;

        int result = instance.saveClientOrder(12, 1, 1);

        assertEquals(expectedResult, result);

    }

    @Test
    void createProductOrders() {

        when(clientOrderHandlerMock.addProductOrder(any(Integer.class), any(Integer.class), any(Integer.class))).thenReturn(true);
        ClientOrder order = new ClientOrder(1, new Date(1254441245), 12, 1, 0, 1, 1);

        Cart cart = new Cart(45, 6, new ArrayList<>());

        cart.updateAddCart(new Product(1, "xarope", "xarope para a tosse", 6, 0.5, 1, 2), 1);

        boolean actualResult = true;

        boolean result = instance.createProductOrders(cart, order.getOrderId());

        assertEquals(actualResult, result);

    }

    @Test
    void createProductOrders2() {

        when(clientOrderHandlerMock.addProductOrder(any(Integer.class), any(Integer.class), any(Integer.class))).thenReturn(false);
        ClientOrder order = new ClientOrder(1, new Date(1254441245), 12, 1, 0, 1, 1);

        Cart cart = new Cart(45, 6, new ArrayList<>());

        List<Cart.AuxProduct> newList = new ArrayList<>();
        Cart.AuxProduct auxProduct = new Cart.AuxProduct(new Product("xarope", "xarope para a tosse", 6, 0.5, 1, 2), -2);
        newList.add(auxProduct);
        cart.setProductsTobuy(newList);
        boolean actualResult = false;
        boolean result = instance.createProductOrders(cart, order.getOrderId());

        assertEquals(actualResult, result);

    }

    @Test
    void doPayment() {
        Client client = new Client("Ricardo", "client1@isep.ipp.pt", "qwerty", 189102816, 2332.91872, 827162.23234, new BigDecimal("1829102918271622"));

        double price = 100;

        boolean result = instance.doPayment(client, price);
        boolean expectedResult = true;

        assertEquals(result, expectedResult);

    }

    @Test
    void doPayment2() {
        Client client = new Client("Ricardo", "client1@isep.ipp.pt", "qwerty", 189102816, 2332.91872, 827162.23234, new BigDecimal("1829102918271622"));

        double price = -100;

        boolean result = instance.doPayment(client, price);
        boolean expectedResult = false;

        assertEquals(result, expectedResult);

    }


    @Test
    void sendMail() {
        assertEquals(true, instance.sendMail("client@gmail.com", new Invoice(1, new Date(1254441245), 12, 1, 1)));

    }

    @Test
    void addInvoice() {
        int result = instance.addInvoice(1, 1, 1);
        int expectedResult = 1;

        assertEquals(result, expectedResult);
    }

    @Test
    void getInvoiceByID() {
        Invoice result = instance.getInvoiceByID(1);

        Invoice expectedResult = new Invoice(1, new Date(1254441245), 12, 1, 1);

        assertEquals(result, expectedResult);
    }

    @Test
    void sendMail1() {
        assertEquals(false, instance.sendMail("", new Invoice(1, new Date(1254441245), 12, 1, 1)));

    }

    @Test
    void calculateTotalPrice() {
        User u = new User("client1@isep.ipp.pt", "querty", "CLIENT");
        UserSession userSessionMock = mock(UserSession.class);

        when(userSessionMock.getUser()).thenReturn(u);
        UserSession.getInstance().setUser(u);
        ClientOrder order = new ClientOrder(1, new Date(1254441245), 12, 1, 0, 1, 1);

        Cart cart = new Cart(45, 6, new ArrayList<>());
        Pharmacy phar = new Pharmacy(1,"phar", "Farmácia Tirori", 2313.12, 41.1111, -8.9999, "admin@isep.ipp.pt");

        double result = instance.calculateTotalPrice(cart, phar);
    }

    @Test
    void calculateTotalPrice2() {
        User u = new User("client1@isep.ipp.pt", "querty", "CLIENT");
        UserSession userSessionMock = mock(UserSession.class);

        when(userSessionMock.getUser()).thenReturn(u);
        UserSession.getInstance().setUser(u);
        ClientOrder order = new ClientOrder(1, new Date(1254441245), 0, 0, 0, 1, 1);

        Cart cart = new Cart(0, 0, new ArrayList<>());
        Pharmacy phar = new Pharmacy(1,"phar", "Farmácia Tirori", 2313.12, 41.1111, -8.9999, "admin@isep.ipp.pt");

        double result = instance.calculateTotalPrice(cart, phar);
    }

    @Test
    void calculateDeliveryFee() {
        Client cl = instance.getClientByEmail("client1@isep.ipp.pt");

        Pharmacy phar = new Pharmacy(1,"phar", "Farmácia Tirori", 41.14961, -8.61099, 0, "admin@isep.ipp.pt");

        double result = instance.calculateDeliveryFee(cl, phar);
        double expectedResult= 5792.455915491215;

        assertEquals(expectedResult, result);
    }

    @Test
    void generateInvoice() {

        Client client = new Client(1, "Alexandre", "alex@gmail.com", "rosa", 123456789, 234.816, 2715.9881,31313.10, new BigDecimal("1234567891057189"));

        Invoice expResult = new Invoice(1, new Date(1254441245), 12, 1, 1);

        Invoice result=instance.generateInvoice(12, client, 1);

        assertEquals(expResult, result);
    }

    @Test
    void updateStock() {


        when(clientOrderHandlerMock.updateStockAfterPayment(any(Integer.class))).thenReturn(true);

        boolean expResult=true;

        boolean result=instance.updateStock(1);

        assertEquals(expResult, result);
    }

    @Test
    void updateStock2() {


        when(clientOrderHandlerMock.updateStockAfterPayment(any(Integer.class))).thenReturn(false);

        boolean expResult=false;

        boolean result=instance.updateStock(0);

        assertEquals(expResult, result);
    }

    @Test
    void updateClientCredits() {
        when(clientOrderHandlerMock.updateClientCredits(any(Integer.class))).thenReturn(true);

        boolean expResult=true;

        boolean result=instance.updateClientCredits(1);

        assertEquals(expResult, result);

    }

    @Test
    void updateClientCredits2() {
        when(clientOrderHandlerMock.updateClientCredits(any(Integer.class))).thenReturn(false);

        boolean expResult=false;

        boolean result=instance.updateClientCredits(0);

        assertEquals(expResult, result);

    }
}