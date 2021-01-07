package lapr.project.controller;

import lapr.project.data.CourierDataHandler;
import lapr.project.data.UserDataHandler;
import lapr.project.model.*;

import java.util.List;

public class UserController {
    private final UserDataHandler userDataHandler;

    public UserController(UserDataHandler userDataHandler){
        this.userDataHandler = userDataHandler;
    }

    public User login(String email, String password) {
        User user = null;
        int id = userDataHandler.validateLogin(email, password);
        user = userDataHandler.getById(id);
        return user;
    }

    public void addUserAsClient(String name, String email, String pwd, String role, int nif, int creditCardNumber, int creditCardMonthExpiration, int creditCardNumberYearExpiration, int ccv, double latitude, double longitude, String street) {
        Address add = new Address(latitude, longitude, street);
        add.save();
        CreditCard credcard = new CreditCard(creditCardNumber, creditCardMonthExpiration, creditCardNumberYearExpiration, ccv);
        credcard.save();
        Client client = new Client(name, email, pwd, nif, latitude, longitude, creditCardNumber);
        client.save();
        User userAsClient = new User(email, pwd, role);
        userAsClient.save();
    }

    public void addUserAsCourier(String name, String email, int nif, String nss, String password, double maxWeightCapacity, double weight, int pharmacyID, String courierRole) {
       Courier courier = new Courier(email, name, nif, nss, maxWeightCapacity, weight, pharmacyID);
       courier.save();
       User userAsCourier = new User(email, password, courierRole);
       userAsCourier.save();
    }

    public List<Courier> getCourierList() {
        return new CourierDataHandler().getCourierList();


    }
}
