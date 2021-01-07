package lapr.project.controller;



import lapr.project.data.UserDB;
import lapr.project.model.Address;
import lapr.project.model.Client;
import lapr.project.model.CreditCard;
import lapr.project.model.User;

public class UserController {
    private final UserDB userDataHandler;

    public UserController(UserDB userDataHandler){
        this.userDataHandler = userDataHandler;
    }

    public Client login(String email, String password) {
        return null;
    }

    public void addUserAsClient(String name, String email, String password, String role, int nif, int creditCardNumber, int creditCardMonthExpiration, int creditCardNumberYearExpiration, int ccv, double latitude, double longitude, String street) {
        Address add = new Address(latitude, longitude, street);
        add.save();
        CreditCard credcard = new CreditCard(creditCardNumber, creditCardMonthExpiration, creditCardNumberYearExpiration, ccv);
        credcard.save();
        Client client = new Client(name, email, nif, latitude, longitude, creditCardNumber);
        client.save();
        User userAsClient = new User(email, password, role);
        userAsClient.save();
    }

    public void addUserAsCourier(String name, String email, String password, double maxWeightCapacity, int pharmacyID, String courierRole) {
    }
}
