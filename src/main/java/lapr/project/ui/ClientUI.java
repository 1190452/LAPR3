package lapr.project.ui;

import lapr.project.model.Cart;

import java.util.Scanner;

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
                addToCart();
                break;
            case "2":
                removeFromCart();
                break;
            case "3":
                checkout();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    private void addToCart() {
    }

    private void removeFromCart() {
    }

    private void checkout() {
    }

}
