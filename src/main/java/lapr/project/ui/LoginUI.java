package lapr.project.ui;

import lapr.project.controller.PharmacyController;
import lapr.project.controller.UserController;
import lapr.project.data.*;
import lapr.project.model.Cart;
import lapr.project.model.Pharmacy;
import lapr.project.model.User;
import lapr.project.model.UserSession;
import oracle.ucp.util.Pair;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginUI {

    public static final Scanner READ = new Scanner(System.in);

    /**
     * Defined constants to avoid repetition
     */
    private static final String CLIENT_ROLE = "CLIENT";
    private static final String ADMINISTRATOR_ROLE = "ADMINISTRATOR";
    private static final String COURIER_ROLE = "COURIER";


    public LoginUI() {
        //Dummy constructor to be called and have acess to the instance methods of LoginUI
    }

    /**
     * Main menu for the login
     */
    public static void showLoginScreen() {
        System.out.println("\nINITIAL MENU\n------------");
        System.out.println("1- Login on the system   \n"
                + "2- Register on the System \n"
                + "0- Exit the application");
    }

    /**
     * Menu loop that allows navigation through the different menu options
     * @throws SQLException when detected a database access error or other errors
     * @throws IOException when an I/O exception of some sort has occurred
     */
    public void loginInterface() throws SQLException, IOException {
        String ch;
         do{
             showLoginScreen();
             ch = READ.next();
            switch (ch) {
                case "1":
                    loginUser();
                    break;
                case "2":
                    registerUserasClient();
                    break;
                case "0":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
         } while (true);
    }

    /**
     * Interface that allows a user to login into the system
     * @throws SQLException when detected a database access error or other errors
     * @throws IOException when an I/O exception of some sort has occurred
     */
    public void loginUser() throws SQLException, IOException {
        try {
            System.out.println("\nEmail:");
            String email = READ.next();

            System.out.println("\nPassword:");
            String password = READ.next();

            UserController uc = new UserController(new UserDataHandler(), new CourierDataHandler(), new ClientDataHandler(),new AddressDataHandler(), new CreditCardDataHandler());
            User user = uc.login(email, password);

            if(user.getRole().equalsIgnoreCase(ADMINISTRATOR_ROLE)){
                AdminUI adminUI = new AdminUI();
                UserSession.getInstance().setUser(user);
                adminUI.adminLoop();
            }else if(user.getRole().equalsIgnoreCase(CLIENT_ROLE)){
                ClientUI userUI = new ClientUI();
                UserSession.getInstance().setUser(user);
                Cart carClient = new Cart();
                PharmacyController ph = new PharmacyController(new PharmacyDataHandler(), new ParkHandler(), new AddressDataHandler(), new ClientDataHandler());
                List<Pair<Pharmacy, Double>> pharmacies = ph.getPharmaciesInformation();
                int pharID = pharmacies.get(0).get1st().getId();
                userUI.loginClient(carClient, pharID);
            }else if(user.getRole().equalsIgnoreCase(COURIER_ROLE)){
                CourierUI courierUI = new CourierUI();
                UserSession.getInstance().setUser(user);
                courierUI.loginCourier();
            }else{
                System.err.println("\nE-mail or Password are incorrect.\n");
            }
        }catch (IllegalArgumentException | InterruptedException exception) {
            Logger.getLogger(LoginUI.class.getName()).log(Level.WARNING, exception.getMessage());
        }
    }

    /**
     * Interface that allows the regist of a client into the system
     */
    private void registerUserasClient() {
        System.out.println("\nInsert your e-mail:");
        String email = READ.next();

        READ.nextLine();
        System.out.println("\nInsert your name:");
        String name = READ.nextLine();

        System.out.println("\nInsert your password:");
        String password = READ.next();

        System.out.println("\nInsert your NIF:");
        int nif = READ.nextInt();

        System.out.println("\nInsert your credit card number:");
        BigDecimal creditCardNumber = READ.nextBigDecimal();

        System.out.println("\nInsert your credit card month expiration:");
        int creditCardMonthExpiration = READ.nextInt();

        System.out.println("\nInsert your credit card year expiration:");
        int creditCardNumberYearExpiration = READ.nextInt();

        System.out.println("\nInsert your credit card ccv:");
        int ccv = READ.nextInt();

        System.out.println("\nInsert the latitude of your address");
        double latitude = READ.nextDouble();

        System.out.println("\nInsert the longitude of your address");
        double longitude = READ.nextDouble();

        System.out.println("\nInsert the altitude of your address");
        double altitude = READ.nextDouble();

        READ.nextLine();
        System.out.println("\nInsert your street address");
        String street = READ.nextLine();

        System.out.println("\nInsert your door number");
        int doorNumber = READ.nextInt();

        READ.nextLine();
        System.out.println("\nInsert your zipcode");
        String zipCode = READ.nextLine();

        System.out.println("\nInsert your locality");
        String locality = READ.nextLine();

        System.out.println("\nUsername:\t" + name
                + "\nE-mail:\t" + email
                + "\nPassword:\t" + password
                + "\nNIF:\t" + nif
                + "\nCredit Card Number:\t" + creditCardNumber
                + "\nCredit Card Month Expiration:\t" + creditCardMonthExpiration
                + "\nCredit Card Year Expiration:\t" + creditCardNumberYearExpiration
                + "\nCredit Card CCV:\t" + ccv
                + "\nLatitude:\t" + latitude
                + "\nLongitude:\t" + longitude
                + "\nStreet:\t" + street
                + "\nDoor Number:\t" + doorNumber
                + "\nZipCode:\t" + zipCode
                + "\nLocality:\t" + locality
        );
        System.out.println("Please confirm the provided information for registration: (Yes/No)");
        String confirmation = READ.nextLine();

        if (confirmation.equalsIgnoreCase("YES")) {
            UserController uc = new UserController(new UserDataHandler(), new CourierDataHandler(), new ClientDataHandler(), new AddressDataHandler(), new CreditCardDataHandler());
            uc.addUserAsClient(name, email, password, nif, creditCardNumber, creditCardMonthExpiration,creditCardNumberYearExpiration,
                    ccv, latitude, longitude, street, doorNumber, zipCode, locality, altitude);
            System.out.println("\n\nWelcome to  Menu " + name + "! Thank you.\n\n");
        }

    }

}
