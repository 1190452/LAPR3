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

    public UserController(UserDataHandler userDataHandler, CourierDataHandler courierDataHandler, ClientDataHandler clientDataHandler, AddressDataHandler addressDataHandler, CreditCardDataHandler creditCardDataHandler){
        this.userDataHandler = userDataHandler;
        this.courierDataHandler = courierDataHandler;
        this.clientDataHandler = clientDataHandler;
        this.addressDataHandler = addressDataHandler;
        this.creditCardDataHandler = creditCardDataHandler;
    }


    public boolean addUser(String email, String password, String role) {
        try {
            getUser(email);
        } catch (IllegalArgumentException ex) {
            //Of the record does not exist, save it
            User u = new User(email, password, role);
            return userDataHandler.addUser(u);
        }
        return false;
    }

    public User getUser(String email) {
        return userDataHandler.getUser(email);
    }

    public Courier getCourier(int nif) {
        return courierDataHandler.getCourier(nif);
    }

    public Client getClient(int nif) {
        return clientDataHandler.getClient(nif);
    }

    public User login(String email, String password) {
        User user;
        String emailAux = userDataHandler.validateLogin(email, password);
        user = userDataHandler.getByEmail(emailAux);
        return user;
    }

    public boolean addUserAsClient(String name, String email, String pwd, int nif, BigDecimal creditCardNumber, int creditCardMonthExpiration, int creditCardNumberYearExpiration, int ccv, double latitude, double longitude, String street, int doorNum, String zipcode, String locality) {
        Address add = new Address(latitude, longitude, street, doorNum, zipcode, locality);
        addressDataHandler.addAddress(add);
        CreditCard credcard = new CreditCard(creditCardNumber, creditCardMonthExpiration, creditCardNumberYearExpiration, ccv);
        creditCardDataHandler.addCreditCard(credcard);
        Client client = new Client(name, email, pwd, nif, latitude, longitude, creditCardNumber);
        addUser(email,pwd, "CLIENT");
        return clientDataHandler.addClient(client);
    }

    public boolean addUserAsCourier(String name, String email, int nif, BigDecimal nss, String password, double maxWeightCapacity, double weight, int pharmacyID) {
        Courier courier = new Courier(email, name, nif, nss, maxWeightCapacity, weight, pharmacyID);
        addUser(email, password, "COURIER");
        return courierDataHandler.addCourier(courier);
    }

    public List<Courier> getCourierList() {
        return courierDataHandler.getCourierList();
    }

    public boolean removeCourier(int id) {
        return courierDataHandler.removeCourier(id);
    }
}
