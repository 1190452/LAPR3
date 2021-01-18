package lapr.project.ui;


import lapr.project.controller.CheckoutController;
import lapr.project.controller.ProductController;
import lapr.project.data.*;
import lapr.project.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientUI {
    public static final Scanner READ = new Scanner(System.in);

    private static final double TAXA_ENTREGA=5.0;

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
            ch = READ.nextLine();
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
        ProductController pc = new ProductController(new ProductDataHandler(), new PharmacyDataHandler(), new RestockDataHandler());
        List<Product> products = pc.getMedicines(pharmID);

        if(products != null) {
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
            ;
            List<Cart.AuxProduct> carProducts = carClient.getProductsTobuy();
            carProducts.add(new Cart.AuxProduct(product, stock));
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
        ProductController pc = new ProductController(new ProductDataHandler(), new PharmacyDataHandler(), new RestockDataHandler());
        List<Product> products = pc.getMedicines(pharmID);
        List<RestockOrder> restocks = new ArrayList<>();
        Pharmacy receiver = new PharmacyDataHandler().getPharmacyByID(pharmID);
        for(Cart.AuxProduct product : productsClient){
            for(Product prodPhar : products){
                if(product.getProduct().getName().equalsIgnoreCase(prodPhar.getName()) && product.getStock() > prodPhar.getQuantityStock()){
                    int stockMissing = product.getStock() - prodPhar.getQuantityStock();
                    List<Pharmacy> pharms = pc.getPharmaciesStock(product.getProduct().getName(), stockMissing);
                    if(!pharms.isEmpty()){
                        Pharmacy pharmacyCloser = pc.getPharmacyCloser(pharms,receiver);
                        pc.sendEmail(pharmacyCloser,prodPhar,stockMissing);
                        int clientOrderID = 0;
                        RestockOrder r = pc.createRestock(prodPhar.getId(), pharms.get(0).getId(), pharmID, stockMissing, clientOrderID);
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
                    System.out.println("You have a total of "+c.getNumCredits()+".\n");
                    System.out.println("Do you want to use them in this checkout?\n");
                    System.out.println("1-Yes\n");
                    System.out.println("2-No\n");
                    int i1=READ.nextInt();
                    switch(i1){
                        case 1:
                            cContr.checkoutProcess(carClient, true, restocks, countMissingProducts);
                            break;
                        case 2:
                            break;
                        default:
                            System.out.println("Insert valid option...");
                            break;
                    }
                }
                cContr.checkoutProcess(carClient, false, restocks, countMissingProducts);
                break;
            case 2:
                System.out.println("Canceled");
                break;

            default:
                System.out.println("Insert valid option\n");
        }



    }




}
