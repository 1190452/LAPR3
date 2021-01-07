package lapr.project.ui;

import lapr.project.controller.UserController;
import lapr.project.data.UserDataHandler;
import lapr.project.data.UserSession;
import lapr.project.model.User;

import java.util.Scanner;

public class LoginUI {
    public static final Scanner READ = new Scanner(System.in);
    private static final String CLIENT_ROLE = "CLIENT";
    private static final String ADMINISTRATOR_ROLE = "ADMINISTRATOR";
    private static final String COURIER_ROLE = "COURIER";

    public LoginUI() {
    }

    public void loginInterface() {
        int opt;

            showLoginScreen();
            opt = READ.nextInt();

            switch (opt) {
                case "1":
                    loginUser();
                    break;
                case "2":
                    registerUser();
                    break;
                case "0":
                    exit(0);
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }

    }

    public static void showLoginScreen() {
        System.out.println("\nSCREEN SHARING\n------------");
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
            AdminUI aui = new AdminUI();
            aui.loop();
        }else if(user.getRole().equalsIgnoreCase(CLIENT_ROLE)){
            UserUI userUI = new UserUI();
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
        System.out.println("To register a Courrier (1) or to register a User (2)");
    }


}
