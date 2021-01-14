package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
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
        AddressDataHandler addressDataHandler = mock(AddressDataHandler.class);
        CreditCardDataHandler creditCardDataHandler = mock(CreditCardDataHandler.class);

        CourierDataHandler courierDataHandlerMock = mock(CourierDataHandler.class);
        Courier courier = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigDecimal("24586612344"),15,70,1);
        List<Courier> couriers = new ArrayList<>();
        couriers.add(courier);
        when(courierDataHandlerMock.getCourierList()).thenReturn(couriers);
        when(courierDataHandlerMock.getCourier(any(Double.class))).thenReturn(courier);

        UserDataHandler userDataHandlerMock = mock(UserDataHandler.class);
        User user = new User("admin@isep.ipp.pt","qwerty","Administrator");
        String emailAux = "admin@isep.ipp.pt";
        when(userDataHandlerMock.validateLogin(any(String.class), any(String.class))).thenReturn(emailAux);
        when(userDataHandlerMock.getByEmail(emailAux)).thenReturn(user);

        Client client = new Client(1, "Alexandre", "alex@gmail.com", "rosa", 123456789, 234.816, 2715.9881, new BigDecimal("1234567891057189"));
        ClientDataHandler clientDataHandlerMock = mock(ClientDataHandler.class);
        when(clientDataHandlerMock.getClient(any(Double.class))).thenReturn(client);
        when(clientDataHandlerMock.getClientByEmail(any(String.class))).thenReturn(client);

        when(clientDataHandlerMock.addClient(any(Client.class))).thenReturn(Boolean.TRUE);
        when(courierDataHandlerMock.addCourier(any(Courier.class))).thenReturn(Boolean.TRUE);
        when(courierDataHandlerMock.removeCourier(any(Integer.class))).thenReturn(Boolean.TRUE);
        when(userDataHandlerMock.addUser(any(String.class), any(String.class), any(String.class))).thenReturn(Boolean.TRUE);

        when(userDataHandlerMock.getByEmail(any(String.class))).thenReturn(user);

        CreditCard creditCard = new CreditCard(new BigDecimal("1254789645781236"), 12,2021,256);
        when(creditCardDataHandler.addCreditCard(any(CreditCard.class))).thenReturn(Boolean.TRUE);

        instance = new UserController(userDataHandlerMock, courierDataHandlerMock, clientDataHandlerMock,addressDataHandler,creditCardDataHandler);
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
        Client client = new Client( "Alexandre", "alex@gmail.com", "rosa", 123456789,
                234.816, 2715.9881, new BigDecimal("1234567891057189"));
        Address address = new Address(234.816, 2715.9881,"rua xpto", 2, "4500", "espinho");
        CreditCard creditCard = new CreditCard(new BigDecimal("1254789645781236"), 12,2021,256);
        boolean result = instance.addUserAsClient(client.getName(), client.getEmail(), client.getPassword(), client.getnif(),
                client.getCreditCardNumber(), creditCard.getMonthExpiration(),creditCard.getYearExpiration(),creditCard.getCcv(),
                address.getLatitude(),address.getLongitude(),address.getStreet(), address.getDoorNumber(),
                address.getZipCode(),address.getLocality());
        boolean expResult = true;
        assertEquals(expResult,result);
    }

    @Test
    void addUserAsCourier() {
        Courier courier = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigDecimal("24586612344"),25,70,1);
        boolean result = instance.addUserAsCourier(courier.getName(), courier.getEmail(), courier.getNIF(), courier.getNSS(), courier.getPassword(), courier.getWeight(), courier.getPharmacyID());
        boolean expResult = true;
        assertEquals(expResult,result);
    }

    @Test
    void removeCourier() {
        Courier courier = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigDecimal("24586612344"),15,70,1);
        boolean result = instance.removeCourier(courier.getIdCourier());
        boolean expResult = true;
        assertEquals(expResult,result);

    }

    @Test
    void getClient() {
        Client client =  new Client(1, "Alexandre", "alex@gmail.com", "rosa", 123456789, 234.816, 2715.9881, new BigDecimal("1234567891057189"));
        Client result = instance.getClient(client.getnif());
        assertEquals(client, result);
    }

    @Test
    void getCourier() {
        Courier courier = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigDecimal("24586612344"),15,70,1);
        Courier result = instance.getCourier(courier.getNIF());
        assertEquals(courier, result);
    }

    @Test
    void getUser() {
        User user = new User("admin@isep.ipp.pt","qwerty","Administrator");
        User result = instance.getUser(user.getEmail());
        assertEquals(user,result);
    }

    @Test
    void addUser() {
        User user = new User("admin@isep.ipp.pt","qwerty","Administrator");
        boolean result =instance.addUser(user.getEmail(),user.getPassword(),user.getRole());
        boolean expResult = false;
        assertEquals(expResult,result);
    }

    @Test
    void getClientByEmail() {
        Client client =  new Client(1, "Alexandre", "alex@gmail.com", "rosa", 123456789, 234.816, 2715.9881, new BigDecimal("1234567891057189"));
        Client result = instance.getClientByEmail(client.getEmail());
        assertEquals(client, result);
    }
}