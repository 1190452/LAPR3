package lapr.project.ui;

import lapr.project.controller.CheckoutController;
import lapr.project.model.Cart;

import java.util.Scanner;
import java.util.zip.CheckedOutputStream;

public class ClientUI {
    public static final Scanner READ = new Scanner(System.in);

    public static void ClientMenu(){
        System.out.println("CLIENT MENU\n"
                +"\n1-Add To Cart"
                +"\n2-Remove From Cart"
                +"\n3-Checkout"
                +"\n0-Exit"
        );
    }

    public void loginClient(Cart carClient) {
        String ch;
        ClientMenu();
        ch = READ.nextLine();

        switch(ch){
            case "1":
                addToCart(carClient);
                break;
            case "2":
                removeFromCart(carClient);
                break;
            case "3":
                checkout(carClient);
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    private void addToCart(Cart carClient) {

        loginClient(carClient);
    }

    private void removeFromCart(Cart carClient) {
    }

    private void checkout(Cart carClient) {
        CheckoutController c_contr=new CheckoutController();

        c_contr.checkoutProcess(carClient);

    }

}
