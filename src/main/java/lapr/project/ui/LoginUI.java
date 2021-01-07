package lapr.project.ui;

import lapr.project.controller.UserController;
import lapr.project.data.UserDataHandler;
import lapr.project.data.UserSession;
import lapr.project.model.Cart;
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
                    registerUserasClient();
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
        System.out.println("\nINITIAL MENU\n------------");
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
        UserController uc = new UserController(new UserDataHandler());
        user = uc.login(email, password);

        if(user.getRole().equalsIgnoreCase(ADMINISTRATOR_ROLE)){
            AdminUI adminUI = new AdminUI();
            adminUI.loop();
        }else if(user.getRole().equalsIgnoreCase(CLIENT_ROLE)){
            ClientUI userUI = new ClientUI();
            UserSession.getInstance().setUser(user);
            Cart carClient = new Cart();
            userUI.loginClient(carClient);
        }else if(user.getRole().equalsIgnoreCase(COURIER_ROLE)){
            CourierUI courierUI = new CourierUI();
            UserSession.getInstance().setUser(user);
            courierUI.loop();
        }else{
            System.err.println("\nE-mail or Password are incorrect.\n");
            loginInterface();
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
            UserController uc = new UserController(new UserDataHandler());
            uc.addUserAsClient(name, email, password, CLIENT_ROLE, nif, creditCardNumber, creditCardMonthExpiration,creditCardNumberYearExpiration,
                    ccv, latitude, longitude, street);
            System.out.println("\n\nWelcome to  Menu " + name + "! Thank you.\n\n");
            loginInterface();
        } else {
            loginInterface();
        }
    }

}
