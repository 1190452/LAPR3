package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;

import java.math.BigDecimal;
import java.util.List;

public class UserController {
    private final UserDataHandler userDataHandler;
    private final CourierDataHandler courierDataHandler;
    private final ClientDataHandler clientDataHandler;
    private final AddressDataHandler addressDataHandler;
    private final CreditCardDataHandler creditCardDataHandler;


    /**
     * Constructor that instaciates all the handlers that this class needs
     * @param userDataHandler
     * @param courierDataHandler
     * @param clientDataHandler
     * @param addressDataHandler
     * @param creditCardDataHandler
     */
    public UserController(UserDataHandler userDataHandler, CourierDataHandler courierDataHandler, ClientDataHandler clientDataHandler, AddressDataHandler addressDataHandler, CreditCardDataHandler creditCardDataHandler){
        this.userDataHandler = userDataHandler;
        this.courierDataHandler = courierDataHandler;
        this.clientDataHandler = clientDataHandler;
        this.addressDataHandler = addressDataHandler;
        this.creditCardDataHandler = creditCardDataHandler;
    }


    /**
     * method to create user and add the user to the data base
     * @param email
     * @param password
     * @param role
     * @return boolean that confirms the operation was successful
     */
    public boolean addUser(String email, String password, String role) {
        boolean added;
        User user = new User(email, password, role);
        added = userDataHandler.addUser(user);
        return added;
    }

    /**
     * method to get the user by his email
     * @param email
     * @return user
     */
    public User getUser(String email) {
        return userDataHandler.getByEmail(email);
    }

    /**
     * method to get the courier by his nif
     * @param nif
     * @return courier
     */
    public Courier getCourier(int nif) {
        return courierDataHandler.getCourier(nif);
    }

    /**
     * method to get the client by his nif
     * @param nif
     * @return client
     */
    public Client getClient(int nif) {
        return clientDataHandler.getClient(nif);
    }

    /**
     * method to get the client by his email
     * @param email
     * @return client
     */
    public Client getClientByEmail(String email) {
        return clientDataHandler.getClientByEmail(email);
    }


    /**
     * method to do the login
     * @param email
     * @param password
     * @return user
     */
    public User login(String email, String password) {
        User user;
        String emailAux = userDataHandler.validateLogin(email, password);
        user = userDataHandler.getByEmail(emailAux);
        return user;
    }


    /**
     * mehod to register client in the system
     * @param name
     * @param email
     * @param pwd
     * @param nif
     * @param creditCardNumber
     * @param creditCardMonthExpiration
     * @param creditCardNumberYearExpiration
     * @param ccv
     * @param latitude
     * @param longitude
     * @param street
     * @param doorNum
     * @param zipcode
     * @param locality
     * @param altitude
     * @return boolean that confirms the operation was successful
     */
    public boolean addUserAsClient(String name, String email, String pwd, int nif, BigDecimal creditCardNumber, int creditCardMonthExpiration, int creditCardNumberYearExpiration, int ccv, double latitude, double longitude, String street, int doorNum, String zipcode, String locality, double altitude) {
        Address add = new Address(latitude, longitude, street, doorNum, zipcode, locality,altitude);
        addressDataHandler.addAddress(add);
        CreditCard credcard = new CreditCard(creditCardNumber, creditCardMonthExpiration, creditCardNumberYearExpiration, ccv);
        creditCardDataHandler.addCreditCard(credcard);
        Client client = new Client(name, email, pwd, nif, latitude, longitude,altitude, creditCardNumber);
        addUser(email,pwd, "CLIENT");
        return clientDataHandler.addClient(client);
    }


    /**
     * mehod to register courier in the system
     * @param name
     * @param email
     * @param nif
     * @param nss
     * @param password
     * @param weight
     * @param pharmacyID
     * @return boolean that confirms the operation was successful
     */
    public boolean addUserAsCourier(String name, String email, int nif, BigDecimal nss, String password, double weight, int pharmacyID) {
        Courier courier = new Courier(email, name, nif, nss, weight, pharmacyID);
        addUser(email, password, "COURIER");
        return courierDataHandler.addCourier(courier);
    }

    /**
     * @return List of all the courier
     */
    public List<Courier> getCourierList() {
        return courierDataHandler.getCourierList();
    }

    /**
     * @param id
     * @return courier by its id
     */
    public boolean removeCourier(int id) {
        return courierDataHandler.removeCourier(id);
    }
}
