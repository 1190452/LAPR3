package lapr.project.controller;

import lapr.project.data.ClientDataHandler;
import lapr.project.data.UserSession;
import lapr.project.model.Cart;
import lapr.project.model.Client;
import lapr.project.model.ClientOrder;
import lapr.project.model.User;

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

        ord.save();

        

    }



}
