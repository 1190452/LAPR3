package lapr.project.controller;

import lapr.project.data.CourierDataHandler;
import lapr.project.data.UserDataHandler;
import lapr.project.model.*;

import java.math.BigDecimal;
import java.util.List;

public class UserController {
    private final UserDataHandler userDataHandler;
    private final CourierDataHandler courierDataHandler;
    public UserController(UserDataHandler userDataHandler, CourierDataHandler courierDataHandler){
        this.userDataHandler = userDataHandler;
        this.courierDataHandler = courierDataHandler;
    }



    public User login(String email, String password) {
        User user = null;
        String emailAux = userDataHandler.validateLogin(email, password);
        user = userDataHandler.getByEmail(emailAux);
        return user;
    }

    public void addUserAsClient(String name, String email, String pwd, String role, int nif, BigDecimal creditCardNumber, int creditCardMonthExpiration, int creditCardNumberYearExpiration, int ccv, double latitude, double longitude, String street, int doorNum, String zipcode, String locality) {
        Address add = new Address(latitude, longitude, street, doorNum, zipcode, locality);
        add.save();
        CreditCard credcard = new CreditCard(creditCardNumber, creditCardMonthExpiration, creditCardNumberYearExpiration, ccv);
        credcard.save();
        Client client = new Client(name, email, pwd, nif, latitude, longitude, creditCardNumber);
        client.save();
        User userAsClient = new User(email, pwd, role);
        userAsClient.save();
    }

    public void addUserAsCourier(String name, String email, int nif, BigDecimal nss, String password, double maxWeightCapacity, double weight, int pharmacyID, String courierRole) {
        User userAsCourier = new User(email, password, courierRole);
        userAsCourier.save();
       Courier courier = new Courier(email, name, nif, nss, maxWeightCapacity, weight, pharmacyID);
       courier.save();
    }

    public List<Courier> getCourierList() {
        return courierDataHandler.getCourierList();
    }

    public void removeCourier(int id) {
        Courier c = new Courier(id, "");
        c.delete();
    }
}
