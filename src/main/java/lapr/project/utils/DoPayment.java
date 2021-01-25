package lapr.project.utils;

import lapr.project.model.Client;

public class DoPayment {

    public DoPayment(){
        //Utility class
    }

    /**
     * Simulates the client payment
     * @param cl order client
     * @param price order price
     * @return true or false
     */
    public boolean doesPayment(Client cl, double price){

        return (price >= 0);
    }

}
