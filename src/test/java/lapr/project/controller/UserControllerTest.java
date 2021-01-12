package lapr.project.controller;

import lapr.project.data.ClientDataHandler;
import lapr.project.data.CourierDataHandler;
import lapr.project.data.UserDataHandler;
import lapr.project.model.Client;
import lapr.project.model.Courier;
import lapr.project.model.Product;
import lapr.project.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private static UserController instance;

    @BeforeAll
    static void beforeAll() {

        CourierDataHandler courierDataHandlerMock = mock(CourierDataHandler.class);
        Courier courier = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigDecimal("24586612344"),15,70,1);
        List<Courier> couriers = new ArrayList<>();
        couriers.add(courier);
        when(courierDataHandlerMock.getCourierList()).thenReturn(couriers);

        UserDataHandler userDataHandlerMock = mock(UserDataHandler.class);
        User user = new User("admin@isep.ipp.pt","qwerty","Administrator");
        String emailAux = new String("admin@isep.ipp.pt");
        when(userDataHandlerMock.validateLogin(any(String.class), any(String.class))).thenReturn(emailAux);
        when(userDataHandlerMock.getByEmail(emailAux)).thenReturn(user);

        Client client = new Client(1, "Alexandre", "alex@gmail.com", "rosa", 123456789, 234.816, 2715.9881, new BigDecimal("1234567891057189"));
        ClientDataHandler clientDataHandlerMock = mock(ClientDataHandler.class);
        when(clientDataHandlerMock.getClient(any(Double.class))).thenReturn(client);

        instance = new UserController(userDataHandlerMock, courierDataHandlerMock, clientDataHandlerMock);
    }

    @Test
    void getCourierList() {
        Courier courier = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigDecimal("24586612344"),15,70,1);
        List<Courier> expResult = new ArrayList<>();
        expResult.add(courier);
        List<Courier> result = instance.getCourierList();
        System.out.println(result);
        assertEquals(expResult,result);
    }


    @Test
    void login() {
        String email = "admin@isep.ipp.pt";
        String pass = "qwerty";

        User expResult = new User("admin@isep.ipp.pt","qwerty","Administrator");
        User result = instance.login(email, pass);
        assertEquals(expResult,result);
    }

    @Test
    void addUserAsClient() {
    }

    @Test
    void addUserAsCourier() {

        Courier courier = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigDecimal("24586612344"),15,70,1);
       instance.addUserAsCourier(courier.getName(), courier.getEmail(), courier.getNIF(), courier.getNSS(), courier.getPassword(), courier.getMaxWeightCapacity(), courier.getWeight(), courier.getPharmacyID(), courier.getRole());

    }

    @Test
    void removeCourier() {
        Courier courier = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigDecimal("24586612344"),15,70,1);
        instance.removeCourier(courier.getIdCourier());
    }

    @Test
    void getClient() {
        Client client =  new Client(1, "Alexandre", "alex@gmail.com", "rosa", 123456789, 234.816, 2715.9881, new BigDecimal("1234567891057189"));
        Client result = instance.getClient(client.getnif());
        assertEquals(client, result);
    }
}