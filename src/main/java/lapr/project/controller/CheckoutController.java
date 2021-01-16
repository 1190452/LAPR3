package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.utils.Distance;
import lapr.project.utils.DoPayment;

public class CheckoutController {
    private static final double TAX_PER_KILLOMETER = 0.2;//0,2 euro per km
    private final ClientDataHandler clientDataHandler;
    private final ClientOrderHandler clientOrderHandler;
    private final InvoiceHandler invoiceHandler;



    public CheckoutController(ClientDataHandler clientDataHandler, ClientOrderHandler clientOrderHandler, InvoiceHandler invoiceHandler) {
        this.clientDataHandler = clientDataHandler;
        this.clientOrderHandler = clientOrderHandler;
        this.invoiceHandler = invoiceHandler;

    }

    public double calculateTotalPrice(Cart cart, Pharmacy pharm) {
        Client cl = getClientByEmail(getUserSession().getEmail());
        return cart.getFinalPrice() + calculateDeliveryFee(cl, pharm);
    }

    public boolean checkoutProcess(Cart cart, boolean payWithCredits) {
        if (cart.getProductsTobuy().isEmpty()) {
            return false;
        }
        Client cl = getClientByEmail(getUserSession().getEmail());
        double price = cart.getFinalPrice();
        double weight = cart.getFinalWeight();

        int orderId = saveClientOrder(price, weight, cl.getIdClient());

        createProductOrders(cart, orderId);

        Invoice inv = null;

        if (!payWithCredits) {
            if (doPayment(cl, price)) {
                int id = addInvoice(price, cl.getIdClient(), orderId);
                inv = getInvoiceByID(id);
                clientOrderHandler.updateStockAfterPayment(orderId);
            }
        } else {
            //PAYMENT WITH CREDITS
            clientOrderHandler.updateClientCredits(orderId);

            int id = addInvoice(price, cl.getIdClient(), orderId);
            inv = getInvoiceByID(id);
            clientOrderHandler.updateStockAfterPayment(orderId);

        }

        sendMail(cl.getEmail(), inv);

        return true;
    }

    public double calculateDeliveryFee(Client cl, Pharmacy pharm) {
        double distance = Distance.distanceBetweenTwoAddresses(cl.getLatitude(), cl.getLatitude(), pharm.getLatitude(), pharm.getLongitude());
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

    public int saveClientOrder(double price, double weight, int idClient) {
        return clientOrderHandler.addClientOrder(new ClientOrder(price, weight, idClient));
    }

    public boolean createProductOrders(Cart cart, int orderId) {
        boolean verif = true;
        for (Cart.AuxProduct p : cart.getProductsTobuy()) {
            verif = clientOrderHandler.addProductOrder(orderId, p.getProduct().getId(), p.getStock());
            if (!verif) {
                return false;
            }
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
