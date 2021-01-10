package lapr.project.controller;

import lapr.project.data.AddressDataHandler;
import lapr.project.data.ClientOrderHandler;
import lapr.project.data.CourierDataHandler;
import lapr.project.model.ClientOrder;
import lapr.project.model.Courier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderControllerTest {

    private static OrderController instance;


    public OrderControllerTest() {
    }

    @BeforeAll
    public static void setUpClass() throws SQLException {

        CourierDataHandler courierDataHandlerMock = mock(CourierDataHandler.class);
        ClientOrderHandler clientOrderHandlerMock = mock(ClientOrderHandler.class);
        AddressDataHandler addressDataHandlerMock = mock(AddressDataHandler.class);

        Courier courier = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigInteger("24586612344").intValue(),15,70,1);
        when(courierDataHandlerMock.getCourierByEmail(any(String.class))).thenReturn(courier);
        when(courierDataHandlerMock.getCourier(any(Double.class))).thenReturn(courier);

        ClientOrder clientOrder = new ClientOrder(1,new Date(1254441245),12,1,0,1,1);
        LinkedHashMap<Integer,ClientOrder> orders = new LinkedHashMap<>();
        orders.put(1,clientOrder);
        when(clientOrderHandlerMock.getUndoneOrders()).thenReturn(orders);

        //instance = new OrderController(clientOrderHandlerMock, courierDataHandlerMock,addressDataHandlerMock );

    }

    /*  TODO IMPLEMENT THE METHODS
    @Test
    void getCourierByEmail() {
        String email = "courier@isep.ipp.pt";

        Courier expResult = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigInteger("24586612344").intValue(),15,70,1);
        Courier result = instance.getCourierByEmail(email);
        assertEquals(expResult.getEmail(), result.getEmail());
    }


    @Test
    void getCourierByNif() {
        double nif = 122665789;

        Courier expResult = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigInteger("24586612344").intValue(),15,70,1);
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
    }*/
}