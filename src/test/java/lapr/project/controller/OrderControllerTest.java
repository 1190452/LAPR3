package lapr.project.controller;

import lapr.project.data.CourierDataHandler;
import lapr.project.model.Courier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.SQLOutput;

import static org.junit.jupiter.api.Assertions.*;

class OrderControllerTest {

    private static OrderController instance;

    public OrderControllerTest() {
    }

    @BeforeAll
    public static void setUpClass() throws SQLException {

        CourierDataHandler courierDataHandlerMock = mock(CourierDataHandler.class);
        Courier courier = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigInteger("24586612344").intValue(),15,70,1);
        when(courierDataHandlerMock.getCourierByEmail(any(String.class))).thenReturn(courier);

        instance = new OrderController(courierDataHandlerMock);
    }

    @Test
    void getCourierByEmail() {
        System.out.println("getCourier");
        String email = "courier@isep.ipp.pt";

        Courier expResult = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigInteger("24586612344").intValue(),15,70,1);
        Courier result = instance.getCourierByEmail(email);
        assertEquals(expResult.getEmail(), result.getEmail());
    }
}