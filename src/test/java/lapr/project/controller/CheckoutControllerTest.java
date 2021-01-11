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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CheckoutControllerTest {

    private static CheckoutController instance;

    public CheckoutControllerTest(){

    }

    @BeforeAll
    public static void setUpClass(){
        ClientDataHandler clientDataHandlerMock = mock(ClientDataHandler.class);
        ClientOrderHandler clientOrderHandlerMock = mock(ClientOrderHandler.class);
        InvoiceHandler invoiceHandlerMock = mock(InvoiceHandler.class);
        Client client = new Client("Ricardo", "client1@isep.ipp.pt", "qwerty", 189102816, 2332.91872, 827162.23234, new BigDecimal("1829102918271622"));
        when(clientDataHandlerMock.getClientByEmail(any(String.class))).thenReturn(client);

        ClientOrder order = new ClientOrder(1,new Date(1254441245),12,1,0,1,1);
        when(clientOrderHandlerMock.addClientOrder(any(ClientOrder.class))).thenReturn(order.getOrderId());


        when(clientOrderHandlerMock.addProductOrder(any(Integer.class), any(Integer.class), any(Integer.class))).thenReturn(true);

        instance = new CheckoutController(clientDataHandlerMock, clientOrderHandlerMock, invoiceHandlerMock);
    }

    @Test
    void checkoutProcess() {

    }


    @Test
    void getUserSession() {
        User u = new User("xandinho@gmail.com", "qwerty", "CLIENT");
        UserSession userSessionMock = mock(UserSession.class);

        when(userSessionMock.getUser()).thenReturn(u);

        UserSession.getInstance().setUser(u);


        assertEquals(instance.getUserSession(),UserSession.getInstance().getUser());
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
        int expectedResult=1;

        int result = instance.saveClientOrder(12,1,1);

        assertEquals(expectedResult, result);

    }

    @Test
    void createProductOrders() {
        ClientOrder order = new ClientOrder(1,new Date(1254441245),12,1,0,1,1);

        Cart cart = new Cart(45, 6, new ArrayList<>());

        cart.updateAddCart(new Product(1,"xarope","xarope para a tosse",6,0.5,1,2), 1);

        boolean actualResult=true;

        boolean result = instance.createProductOrders(cart, order.getOrderId());

        assertEquals(actualResult, result);

    }

    @Test
    void doPayment() {
    }

    @Test
    void createInvoice() {
    }
}