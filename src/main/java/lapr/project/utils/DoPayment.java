package lapr.project.utils;

import lapr.project.model.Client;

public class DoPayment {

    public DoPayment(){
        //metodo para instanciar o payment
    }

    public boolean doesPayment(Client cl, double price){
        //metodo de simulação de pagamentos
        return (price >= 0);
    }

}
