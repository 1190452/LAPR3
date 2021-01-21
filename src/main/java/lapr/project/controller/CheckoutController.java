package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.utils.DoPayment;
import lapr.project.utils.Physics;

import java.util.List;

public class CheckoutController {
    private static final double TAX_PER_KILLOMETER = 0.2;//0,2 euro per km
    private final ClientDataHandler clientDataHandler;
    private final ClientOrderHandler clientOrderHandler;
    private final InvoiceHandler invoiceHandler;
    private final RestockDataHandler restockDataHandler;



    public CheckoutController(ClientDataHandler clientDataHandler, ClientOrderHandler clientOrderHandler, InvoiceHandler invoiceHandler, RestockDataHandler restockDataHandler) {
        this.clientDataHandler = clientDataHandler;
        this.clientOrderHandler = clientOrderHandler;
        this.invoiceHandler = invoiceHandler;
        this.restockDataHandler = restockDataHandler;

    }

    public double calculateTotalPrice(Cart cart, Pharmacy pharm) {
        Client cl = getClientByEmail(getUserSession().getEmail());
        return cart.getFinalPrice() + calculateDeliveryFee(cl, pharm);
    }

    public boolean checkoutProcess(Cart cart, boolean payWithCredits, List<RestockOrder> restocks, int countMisingProducts, int stockMissing, double price) {
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
                inv=generateInvoice(price, cl, orderId, stockMissing);
            }
        } else {
            //PAYMENT WITH CREDITS
            updateClientCredits(orderId);
            inv=generateInvoice(price, cl, orderId, stockMissing);
        }

        sendMail(cl.getEmail(), inv);

        return true;
    }

    public boolean updateClientCredits(int orderId){
        return clientOrderHandler.updateClientCredits(orderId);
    }

    public Invoice generateInvoice(double price, Client cl, int orderId, int stockMissing){
        int id = addInvoice(price, cl.getIdClient(), orderId);
        Invoice inv = getInvoiceByID(id);
        updateStock(orderId, stockMissing);
        return inv;
    }

    public boolean updateStock(int orderId, int stockMissing){
        return clientOrderHandler.updateStockAfterPayment(orderId, stockMissing);
    }

    public double calculateDeliveryFee(Client cl, Pharmacy pharm) {
        double distance = Physics.calculateDistanceWithElevation(cl.getLatitude(), pharm.getLatitude(), cl.getLongitude(), pharm.getLongitude(),cl.getAltitude(), pharm.getAltitude());
        return distance * TAX_PER_KILLOMETER;
    }

    public boolean sendMail(String email, Invoice inv) {
        return EmailAPI.sendEmailToClient(email, inv);
    }

    public User getUserSession() {
        return UserSession.getInstance().getUser();
    }

    public Client getClientByEmail(String email) {
        return clientDataHandler.getClientByEmail(email);
    }

    public int saveClientOrder(double price, double weight, int idClient, int isComplete) {
        return clientOrderHandler.addClientOrder(new ClientOrder(price, weight, idClient, isComplete));
    }

    public boolean createProductOrders(Cart cart, int orderId) {
        boolean verif = true;
        for (Cart.AuxProduct p : cart.getProductsTobuy()) {
            verif = clientOrderHandler.addProductOrder(orderId, p.getProduct().getId(), p.getStock());
        }
        return verif;
    }

    public boolean doPayment(Client cl, double price) {
        DoPayment dp = new DoPayment();
        return dp.doesPayment(cl, price);
    }

    public int addInvoice(double price, int idClient, int orderID) {
        return invoiceHandler.addInvoice(new Invoice(price, idClient, orderID));
    }

    public Invoice getInvoiceByID(int id) {
        return invoiceHandler.getInvoice(id);
    }

}
