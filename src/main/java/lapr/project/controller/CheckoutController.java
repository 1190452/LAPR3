package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.utils.DoPayment;

public class CheckoutController {
    private final ClientDataHandler clientDataHandler;
    private final ClientOrderHandler clientOrderHandler;
    private final InvoiceHandler invoiceHandler;

    public CheckoutController(ClientDataHandler clientDataHandler, ClientOrderHandler clientOrderHandler, InvoiceHandler invoiceHandler) {
        this.clientDataHandler = clientDataHandler;
        this.clientOrderHandler = clientOrderHandler;
        this.invoiceHandler = invoiceHandler;
    }

    public boolean checkoutProcess(Cart cart) {
        User user = getUserSession();
        double price = cart.getFinalPrice();
        double weight = cart.getFinalWeight();

        Client cl = getClientByEmail(user.getEmail());

        int orderId = saveClientOrder(price, weight, cl.getIdClient());

        createProductOrders(cart, orderId);

        int invoiceId = 0;
        Invoice inv=null;
        if (doPayment(cl, price)) {
            inv = createInvoice(price, cl.getIdClient(), orderId);
        }
        return sendMail(user.getEmail(), inv);
    }

    private boolean sendMail(String email, Invoice inv) {
        return EmailAPI.sendEmailToClient(email, inv);
    }

    public User getUserSession() {
        return UserSession.getInstance().getUser();
    }

    public Client getClientByEmail(String email) {
        return clientDataHandler.getClientByEmail(email);
    }

    public int saveClientOrder(double price, double weight, int idClient){
        return clientOrderHandler.addClientOrder(new ClientOrder(price, weight, idClient));
    }

    public boolean createProductOrders(Cart cart, int orderId){
        boolean verif=true;
        for (Cart.AuxProduct p : cart.getProductsTobuy()) {
            verif=clientOrderHandler.addProductOrder(orderId, p.getProduct().getId(), p.getStock());
        }
        return verif;
    }

    public boolean doPayment(Client cl, double price){
        DoPayment dp = new DoPayment();
        return dp.doesPayment(cl, price);
    }

    public Invoice createInvoice(double price, int idClient, int orderID){
        Invoice inv = new Invoice(price, idClient, orderID);
        int id=inv.save();
        return invoiceHandler.getInvoice(id);
    }


}
