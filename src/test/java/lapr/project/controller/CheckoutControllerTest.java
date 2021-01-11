package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.Cart;
import lapr.project.model.Client;
import lapr.project.model.Courier;
import lapr.project.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
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
}