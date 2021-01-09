package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.utils.DoPayment;

public class CheckoutController {

    public CheckoutController(){
        //construtor para aceder aos metodos da classe a partir da instancia
    }

    public void checkoutProcess(Cart cart){
        User user = UserSession.getInstance().getUser();
        double price = cart.getFinalPrice();
        double weight = cart.getFinalWeight();

        Client cl = new ClientDataHandler().getClientByEmail(user.getEmail());

        ClientOrder ord = new ClientOrder(price, weight, cl.getIdClient());

        int orderId=ord.save();

        ClientOrderHandler coh = new ClientOrderHandler();
        for(Cart.AuxProduct p : cart.getProductsTobuy()){
            coh.addProductOrder(orderId,p.getProduct().getId(),p.getStock());
        }

        DoPayment dp = new DoPayment();

        int invoiceId=0;
        if(dp.doesPayment(cl, price)){
            Invoice inv = new Invoice(price, cl.getIdClient(),orderId);
            invoiceId=inv.save();
        }

        Invoice inv = new InvoiceHandler().getInvoice(invoiceId);

        EmailAPI.sendEmailToClient(cl.getEmail(), inv);


    }



}
