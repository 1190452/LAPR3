package lapr.project.ui;

<<<<<<< HEAD
import lapr.project.controller.CheckoutController;
=======
import lapr.project.controller.ProductController;
import lapr.project.data.ProductDataHandler;
>>>>>>> 0732e975e1b66f7885a9d37bc7b94ebefdcbd217
import lapr.project.model.Cart;
import lapr.project.model.Product;

import java.util.List;
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
        ProductController pc = new ProductController(new ProductDataHandler());
        List<Product> products = pc.getMedicines();

        for (Product u : products) {
            System.out.println(u.toString());
        }

        System.out.println("\nPlease choose the id of the product you want to add to cart: ");
        int productID = READ.nextInt();
        Product product = null;
        for (Product u : products) {
            if(u.getId() == productID){
                 product = u;
            }
        }

        carClient.getProductsTobuy().add(product);
        pc.removeProduct(productID);


        loginClient(carClient);
    }

    private void removeFromCart(Cart carClient) {
    }

    private void checkout(Cart carClient) {
        CheckoutController c_contr=new CheckoutController();

        c_contr.checkoutProcess(carClient);

    }

}
