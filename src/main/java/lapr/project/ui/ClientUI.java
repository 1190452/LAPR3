package lapr.project.ui;


import lapr.project.controller.CheckoutController;
import lapr.project.controller.OrderController;
import lapr.project.controller.ProductController;
import lapr.project.data.*;
import lapr.project.model.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientUI {
    public static final Scanner READ = new Scanner(System.in);

    public static void clientMenu(){
        System.out.println("CLIENT MENU\n"
                +"\n1-Add To Cart"
                +"\n2-Remove From Cart"
                +"\n3-Checkout"
                +"\n0-Exit"
        );
    }

    public void loginClient(Cart carClient, int pharID) {
        String ch;
        do {
            clientMenu();
            ch = READ.next();
            switch (ch) {
                case "1":
                    addToCart(carClient, pharID);
                    break;
                case "2":
                    removeFromCart(carClient);
                    break;
                case "3":
                    checkout(carClient, pharID);
                    break;
                default:
                    break;
            }
        } while (!ch.equals("0")) ;
    }

    private void addToCart(Cart carClient, int pharmID) {
        ProductController pc = new ProductController(new ProductDataHandler(), new PharmacyDataHandler());
        List<Product> products = pc.getMedicines(pharmID);

        if(products != null) {
            for (Product u : products) {
                System.out.println(u.toString());
            }

            System.out.println("\nPlease choose the id of the product you want to add to cart: ");
            int productID = READ.nextInt();

            int stock;
            System.out.println("\nPlease choose the quantity of the product you want to add to cart: ");
            do {
                stock = READ.nextInt();
            }while(stock <= 0);


            Product product = null;
            for (Product u : products) {
                if(u.getId() == productID){
                    product = u;
                }
            }

            List<Cart.AuxProduct> carProducts = carClient.getProductsTobuy();
            Cart.AuxProduct prodBuy = new  Cart.AuxProduct(product, stock);
            carProducts.add(prodBuy);
            carClient.setProductsTobuy(carProducts);
            carClient.updateAddCart(product, stock);
        }else {
            System.out.println("There are no products available");
        }


    }

    private void removeFromCart(Cart carClient) {
        List<Cart.AuxProduct> productsCart = carClient.getProductsTobuy();
        if(productsCart != null) {
            for (Cart.AuxProduct u : productsCart) {
                System.out.println(u.toString());
            }

            System.out.println("\nPlease choose the id of the product you want to remove to cart: ");
            int productID = READ.nextInt();

            List<Cart.AuxProduct> clonedLst = new ArrayList<>(productsCart);

            for (Cart.AuxProduct u : clonedLst) {
                if (u.getProduct().getId() == productID) {
                    carClient.updateRemoveCart(u);
                    productsCart.remove(u);
                    System.out.println("Product removed from the cart with sucess");
                }
            }
        }else {
            System.out.println("There are no products available");
        }

    }

    private void checkout(Cart carClient, int pharmID) {
        int countMissingProducts = 0;
        CheckoutController cContr=new CheckoutController(new ClientDataHandler(), new ClientOrderHandler(), new InvoiceHandler(), new RestockDataHandler());
        List<Cart.AuxProduct> productsClient = carClient.getProductsTobuy();
        ProductController pc = new ProductController(new ProductDataHandler(), new PharmacyDataHandler());
        List<Product> products = pc.getMedicines(pharmID);
        List<RestockOrder> restocks = new ArrayList<>();
        Pharmacy receiver = new PharmacyDataHandler().getPharmacyByID(pharmID);
        int stockMissing=0;
        for(Cart.AuxProduct product : productsClient){
            for(Product prodPhar : products){
                if(product.getProduct().getName().equalsIgnoreCase(prodPhar.getName()) && product.getStock() > prodPhar.getQuantityStock()){
                    stockMissing = product.getStock() - prodPhar.getQuantityStock();
                    List<Pharmacy> pharms = pc.getPharmaciesStock(product.getProduct().getName(), stockMissing, receiver.getId());
                    if(!pharms.isEmpty()){
                        Pharmacy pharmacyCloser = pc.getPharmacyCloser(pharms,receiver);
                        try(FileWriter myWriter = new FileWriter("stockMissing.txt")) {
                            myWriter.write("Delivery note from " +receiver.getName() + " to " + pharmacyCloser.getName());
                            myWriter.write("\nTo be received: " + prodPhar.getName() + " Quantity: "+ stockMissing);
                            Logger.getLogger(OrderController.class.getName()).log(Level.INFO, "Delivery note issued.");
                        }catch (IOException e) {
                            Logger.getLogger(OrderController.class.getName()).log(Level.WARNING, "There was an error issuing the delivery note." + e.getMessage());
                        }
                        int clientOrderID = 0;
                        RestockOrder r = new RestockOrder(receiver.getId(), pharms.get(0).getId(), product.getProduct().getId(), clientOrderID, stockMissing, 0, 0);
                        restocks.add(r);
                        countMissingProducts++;
                    }else{
                        String emailClient = UserSession.getInstance().getUser().getEmail();
                        EmailAPI.sendEmailToClient(emailClient, product.getProduct());
                        Cart.AuxProduct productRemove = new Cart.AuxProduct(product.getProduct(), product.getStock());
                        carClient.getProductsTobuy().remove(productRemove);
                    }
                }
            }
        }
        double producstPrice = carClient.getFinalPrice();
        System.out.println("Your cart:\n");
        for(Cart.AuxProduct p : carClient.getProductsTobuy()){
            System.out.println(p.toString());
        }

        double price = cContr.calculateTotalPrice(carClient, receiver);

        System.out.println("Final price with delivery fee:"+price);


        System.out.println("Do you Confirm?\n");
        System.out.println("1-Yes\n");
        System.out.println("2-No\n");



        int i=READ.nextInt();

        switch (i){
            case 1:
                Client c=cContr.getClientByEmail(UserSession.getInstance().getUser().getEmail());
                if(c.getNumCredits()>price){
                    System.out.println("You have a total of "+c.getNumCredits()+" credits.\n");
                    System.out.println("Do you want to use them in this checkout?\n");
                    System.out.println("1-Yes\n");
                    System.out.println("2-No\n");
                    int i1=READ.nextInt();
                    switch(i1){
                        case 1:
                            cContr.checkoutProcess(carClient, true, restocks, countMissingProducts, stockMissing, price, producstPrice);
                            break;
                        case 2:
                            break;
                        default:
                            System.out.println("Insert valid option...");
                            break;
                    }
                }
                cContr.checkoutProcess(carClient, false, restocks, countMissingProducts, stockMissing,price, producstPrice);

                break;
            case 2:
                System.out.println("Canceled");
                break;

            default:
                System.out.println("Insert valid option\n");
        }
    }
    


}
