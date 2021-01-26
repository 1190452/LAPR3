package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.utils.DoPayment;
import lapr.project.utils.Physics;

import java.util.List;

public class CheckoutController {
    private static final double TAX_PER_KILLOMETER = 0.5;//0,5 euro per km
    private final ClientDataHandler clientDataHandler;
    private final ClientOrderHandler clientOrderHandler;
    private final InvoiceHandler invoiceHandler;
    private final RestockDataHandler restockDataHandler;


    /**
     * Constructor that instaciates all the handlers that this class needs
     * @param clientDataHandler
     * @param clientOrderHandler
     * @param invoiceHandler
     * @param restockDataHandler
     */
    public CheckoutController(ClientDataHandler clientDataHandler, ClientOrderHandler clientOrderHandler, InvoiceHandler invoiceHandler, RestockDataHandler restockDataHandler) {
        this.clientDataHandler = clientDataHandler;
        this.clientOrderHandler = clientOrderHandler;
        this.invoiceHandler = invoiceHandler;
        this.restockDataHandler = restockDataHandler;

    }

    /**
     * method to calculate the price of the order including delivery fee
     * @param cart
     * @param pharm
     * @return total price
     */
    public double calculateTotalPrice(Cart cart, Pharmacy pharm) {
        Client cl = getClientByEmail(getUserSession().getEmail());
        return cart.getFinalPrice() + calculateDeliveryFee(cl, pharm);
    }

    /**
     * method that manages the checkout process
     * @param cart
     * @param payWithCredits
     * @param restocks
     * @param countMisingProducts
     * @param stockMissing
     * @param price
     * @param productsPrice
     * @return boolean that confirms the operation was successful
     */
    public boolean checkoutProcess(Cart cart, boolean payWithCredits, List<RestockOrder> restocks, int countMisingProducts, int stockMissing, double price, double productsPrice) {
        if (cart.getProductsTobuy().isEmpty()) {
            return false;
        }
        Client cl = getClientByEmail(getUserSession().getEmail());

        double weight = cart.getFinalWeight();
        int orderId;


        if(countMisingProducts == 0){
             orderId = saveClientOrder(price, weight, cl.getIdClient(), 1);
        } else{
            orderId = saveClientOrder(price, weight, cl.getIdClient(), 0);
        }


        if(!restocks.isEmpty()) {
            for (RestockOrder r : restocks) {
                r.setClientOrderID(orderId);
                restockDataHandler.addRestock(r);
            }
        }

        createProductOrders(cart, orderId);


        Invoice inv=null;

        if (!payWithCredits) {
            if (doPayment(cl, price)) {
                inv=generateInvoice(price, cl, orderId, stockMissing,productsPrice);
            }
        } else {
            //PAYMENT WITH CREDITS
            updateClientCredits(orderId);
            inv=generateInvoice(price, cl, orderId, stockMissing,productsPrice);
        }

        sendMail(cl.getEmail(), inv);

        return true;
    }

    /**
     * method to remove credits from clients wallet when he uses them to checkout
     * @param orderId
     * @return boolean that confirms the operation was successful
     */
    public boolean updateClientCredits(int orderId){
        return clientOrderHandler.updateClientCredits(orderId);
    }

    /**
     * method to generate the invoice , update the stock and add the credits to the client
     * @param price
     * @param cl
     * @param orderId
     * @param stockMissing
     * @param productsPrice
     * @return Invoice
     */
    public Invoice generateInvoice(double price, Client cl, int orderId, int stockMissing,double productsPrice){
        int id = addInvoice(price, cl.getIdClient(), orderId);
        Invoice inv = getInvoiceByID(id);
        updateStock(orderId, stockMissing, productsPrice);
        return inv;
    }


    /**
     * method to update the stock
     * @param orderId
     * @param stockMissing
     * @param productsPrice
     * @return boolean that confirms the operation was successful
     */
    public boolean updateStock(int orderId, int stockMissing,double productsPrice){
        return clientOrderHandler.updateStockAfterPayment(orderId, stockMissing,productsPrice);
    }

    /**
     * method to calculate delivery fee
     * @param cl
     * @param pharm
     * @return delivery fee
     */
    public double calculateDeliveryFee(Client cl, Pharmacy pharm) {
        double distance = Physics.calculateDistanceWithElevation(cl.getLatitude(), pharm.getLatitudePharmacy(), cl.getLongitude(), pharm.getLongitudePharmacy(),cl.getAltitude(), pharm.getAltitudePharmacy());
        return (distance/1000) * TAX_PER_KILLOMETER;
    }

    /**
     * method to send email with the invoice info
     * @param email
     * @param inv
     * @return boolean that confirms the operation was successful
     */
    public boolean sendMail(String email, Invoice inv) {
        return EmailAPI.sendEmailToClient(email, inv);
    }

    /**
     * method that returns the current user session
     * @return user
     */
    public User getUserSession() {
        return UserSession.getInstance().getUser();
    }

    /**
     * method that returns client by email
     * @param email
     * @return client
     */
    public Client getClientByEmail(String email) {
        return clientDataHandler.getClientByEmail(email);
    }


    /**
     * method that saves the client order and return the id of the order
     * @param price
     * @param weight
     * @param idClient
     * @param isComplete
     * @return id of the order
     */
    public int saveClientOrder(double price, double weight, int idClient, int isComplete) {
        return clientOrderHandler.addClientOrder(new ClientOrder(price, weight, idClient, isComplete));
    }

    /**
     * method to create product orders
     * @param cart
     * @param orderId
     * @return boolean that confirms the operation was successful
     */
    public boolean createProductOrders(Cart cart, int orderId) {
        boolean verif = true;
        for (Cart.AuxProduct p : cart.getProductsTobuy()) {
            verif = clientOrderHandler.addProductOrder(orderId, p.getProduct().getId(), p.getStock());
        }
        return verif;
    }

    /**
     * method to simulate the payment
     * @param cl
     * @param price
     * @return boolean that confirms the operation was successful
     */
    public boolean doPayment(Client cl, double price) {
        DoPayment dp = new DoPayment();
        return dp.doesPayment(cl, price);
    }

    /**
     * method to add invoice to data base
     * @param price
     * @param idClient
     * @param orderID
     * @return id of the invoice
     */
    public int addInvoice(double price, int idClient, int orderID) {
        return invoiceHandler.addInvoice(new Invoice(price, idClient, orderID));
    }

    /**
     * method to get the invoice by the id
     * @param id
     * @return Invoice
     */
    public Invoice getInvoiceByID(int id) {
        return invoiceHandler.getInvoice(id);
    }

}
