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
        if(cart.getProductsTobuy().isEmpty()){
            return false;
        }

        User user = getUserSession();
        double price = cart.getFinalPrice();
        double weight = cart.getFinalWeight();

        Client cl = getClientByEmail(user.getEmail());

        int orderId = saveClientOrder(price, weight, cl.getIdClient());

        createProductOrders(cart, orderId);


        Invoice inv=null;
        if (doPayment(cl, price)) {
            int id = addInvoice(price, cl.getIdClient(), orderId);
            inv = getInvoiceByID(id);
        }
        sendMail(user.getEmail(), inv);

        return true;
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

    public int saveClientOrder(double price, double weight, int idClient){
        return clientOrderHandler.addClientOrder(new ClientOrder(price, weight, idClient));
    }

    public boolean createProductOrders(Cart cart, int orderId){
        boolean verif=true;
        for (Cart.AuxProduct p : cart.getProductsTobuy()) {
            verif=clientOrderHandler.addProductOrder(orderId, p.getProduct().getId(), p.getStock());
            if(!verif){
                return false;
            }
        }
        return verif;
    }

    public boolean doPayment(Client cl, double price){
        DoPayment dp = new DoPayment();
        return dp.doesPayment(cl, price);
    }

    public int addInvoice(double price, int idClient, int orderID){
        return invoiceHandler.addInvoice(new Invoice(price, idClient, orderID));
    }

    public Invoice getInvoiceByID(int id){
        return invoiceHandler.getInvoice(id);
    }


}
