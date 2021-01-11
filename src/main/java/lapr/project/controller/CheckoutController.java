package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.utils.DoPayment;

public class CheckoutController {
    private final ClientDataHandler clientDataHandler;
    private final ClientOrderHandler clientOrderHandler;
    private final InvoiceHandler invoiceHandler;
    public CheckoutController(ClientDataHandler clientDataHandler, ClientOrderHandler clientOrderHandler, InvoiceHandler invoiceHandler){
        this.clientDataHandler = clientDataHandler;
        this.clientOrderHandler = clientOrderHandler;
        this.invoiceHandler = invoiceHandler;
    }

    public void checkoutProcess(Cart cart){
        User user = getUserSession();

        double price = cart.getFinalPrice();
        double weight = cart.getFinalWeight();

        Client cl = clientDataHandler.getClientByEmail(user.getEmail());

        ClientOrder ord = new ClientOrder(price, weight, cl.getIdClient());

        int orderId=ord.save();


        for(Cart.AuxProduct p : cart.getProductsTobuy()){
            clientOrderHandler.addProductOrder(orderId,p.getProduct().getId(),p.getStock());
        }

        DoPayment dp = new DoPayment();

        int invoiceId=0;
        if(dp.doesPayment(cl, price)){
            Invoice inv = new Invoice(price, cl.getIdClient(),orderId);
            invoiceId=inv.save();
        }

        Invoice inv = invoiceHandler.getInvoice(invoiceId);

        EmailAPI.sendEmailToClient(user.getEmail(), inv);


    }

    public User getUserSession(){
        return UserSession.getInstance().getUser();
    }



}
