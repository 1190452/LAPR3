package lapr.project.ui;


import lapr.project.controller.CheckoutController;
import lapr.project.controller.ProductController;
import lapr.project.data.*;
import lapr.project.model.Cart;
import lapr.project.model.Product;

import java.util.List;
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
        do {
            ClientMenu();
            ch = READ.nextLine();

            switch (ch) {
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
                    loginClient(carClient);
                    break;
            }
        } while (!ch.equals("0")) ;
    }

    private void addToCart(Cart carClient) {
        ProductController pc = new ProductController(new ProductDataHandler());
        List<Product> products = pc.getMedicines();

        for (Product u : products) {
            System.out.println(u.toString());
        }

        System.out.println("\nPlease choose the id of the product you want to add to cart: ");
        int productID = READ.nextInt();

        System.out.println("\nPlease choose the quantity of the product you want to add to cart: ");
        int stock = READ.nextInt();

        Product product = null;
        for (Product u : products) {
            if(u.getId() == productID){
                 product = u;
            }
        }

        if (product.getQuantityStock() < stock){
            System.out.println("Stock Indisponível");
        } else {
            List<Cart.AuxProduct> carProducts = carClient.getProductsTobuy();
            carProducts.add(new Cart.AuxProduct(product, stock));
            carClient.updateAddCart(product, stock);
        }
    }

    private void removeFromCart(Cart carClient) {
        List<Cart.AuxProduct> productsCart = carClient.getProductsTobuy();
        for (Cart.AuxProduct u : productsCart) {
            System.out.println(u.toString());
        }

        System.out.println("\nPlease choose the id of the product you want to add to cart: ");
        int productID = READ.nextInt();

        for (Cart.AuxProduct u : productsCart) {
            if (u.getProduct().getId() == productID) {
                carClient.updateRemoveCart(u);
                productsCart.remove(u);
                System.out.println("Product removed from the cart with sucess");
            }
        }
    }

    private void checkout(Cart carClient) {
        CheckoutController c_contr=new CheckoutController(new ClientDataHandler(), new ClientOrderHandler(), new InvoiceHandler());
        c_contr.checkoutProcess(carClient);
    }



}
