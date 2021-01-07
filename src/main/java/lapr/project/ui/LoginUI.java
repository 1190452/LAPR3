package lapr.project.ui;

import lapr.project.controller.UserController;
import lapr.project.data.UserDB;
import lapr.project.data.UserSession;
import lapr.project.model.User;

import java.util.Scanner;

public class LoginUI {
    public static final Scanner READ = new Scanner(System.in);
    private static final String CLIENT_ROLE = "CLIENT";
    private static final String ADMINISTRATOR_ROLE = "ADMINISTRATOR";
    private static final String COURIER_ROLE = "COURIER";
    private int opt;

    public LoginUI() {
    }

    public void loginInterface() {

            showLoginScreen();
            opt = READ.nextInt();

            switch (opt) {
                case 1:
                    loginUser();
                    break;
                case 2:
                    registerUser();
                    break;
                case 0:
                    System.out.println("VOU EMBORA");
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }

    }

    public static void showLoginScreen() {
        System.out.println("\nMENU\n------------");
        System.out.println("1- Login on the system   \n"
                            + "2- Register on the System \n"
                            + "0- Exit the application");
    }

    public void loginUser() {

        System.out.println("\nEmail:");
        String email = READ.nextLine();

        System.out.println("\nPassword:");
        String password = READ.nextLine();
        User user = null;
        UserController uc = new UserController(new UserDB());
        user = uc.login(email, password);

        if(user.getRole().equalsIgnoreCase(ADMINISTRATOR_ROLE)){
            AdminUI adminUI = new AdminUI();
            adminUI.loop();
        }else if(user.getRole().equalsIgnoreCase(CLIENT_ROLE)){
            ClientUI userUI = new ClientUI();
            UserSession.getInstance().setUser(user);
            userUI.loop();
        }else if(user.getRole().equalsIgnoreCase(COURIER_ROLE)){
            CourierUI courierUI = new CourierUI();
            UserSession.getInstance().setUser(user);
            courierUI.loop();
        }else{
            System.err.println("\nE-mail or Password are incorrect.\n");
            loginInterface();
        }
    }

    private void registerUser() {
        System.out.println("To register a Courrier (1) or to register a Client (2)");
        opt = READ.nextInt();

        switch (opt) {
            case 1:
                registerUserasCourrier();
                break;
            case 2:
                registerUserasClient();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    private void registerUserasClient() {
        System.out.println("\nInsert your e-mail:");
        String email = READ.nextLine();

        System.out.println("\nInsert your name:");
        String name = READ.nextLine();

        System.out.println("\nInsert your password:");
        String password = READ.nextLine();

        System.out.println("\nInsert your NIF:");
        int nif = READ.nextInt();

        System.out.println("\nInsert your credit card number:");
        int creditCardNumber = READ.nextInt();

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

        System.out.println("Insert your street address");
        String street = READ.nextLine();   //acrescentar aqui validacoes mais tarde

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
                + "\nStreet:\t" + street);
        System.out.println("\nPlease confirm the provided information for registration: (Yes/No)");
        String confirmation = READ.nextLine();

        if (confirmation.equalsIgnoreCase("YES")) {
            UserController uc = new UserController(new UserDB());
            uc.addUserAsClient(name, email, password, CLIENT_ROLE, nif, creditCardNumber, creditCardMonthExpiration,creditCardNumberYearExpiration,
                    ccv, latitude, longitude, street);
            System.out.println("\n\nWelcome to  Menu " + name + "! Thank you.\n\n");
            loginInterface();
        } else {
            loginInterface();
        }
    }

    private void registerUserasCourrier() {
        System.out.println("\nInsert your e-mail:");
        String email = READ.nextLine();

        System.out.println("\nInsert your name:");
        String name = READ.nextLine();

        System.out.println("\nInsert your password:");
        String password = READ.nextLine();

        System.out.println("\nInsert the latitude of your address");
        double maxWeightCapacity = READ.nextFloat();

        System.out.println("\nInsert the ID of the pharmacy that you are going to work for");
        int pharmacyID = READ.nextInt();
;   //acrescentar aqui validacoes mais tarde

        System.out.println("\nUsername:\t" + name
                + "\nE-mail:\t" + email
                + "\nPassword:\t" + password
                + "\nMax Weight Capacity:\t" + maxWeightCapacity
                + "\nPharmacy ID:\t" + pharmacyID);
        System.out.println("\nPlease confirm the provided information for registration: (Yes/No)");
        String confirmation = READ.nextLine();

        if (confirmation.equalsIgnoreCase("YES")) {
            UserController uc = new UserController(new UserDB());
            uc.addUserAsCourier(name, email, password, maxWeightCapacity, pharmacyID, COURIER_ROLE);
            System.out.println("\n\nWelcome to  Menu " + name + "! Thank you.\n\n");
            loginInterface();
        } else {
            loginInterface();
        }
    }


}
