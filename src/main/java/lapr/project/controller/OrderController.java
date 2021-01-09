package lapr.project.controller;

import lapr.project.data.ClientOrderHandler;
import lapr.project.data.CourierDataHandler;
import lapr.project.model.ClientOrder;
import lapr.project.model.Courier;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrderController {

    private ClientOrderHandler clientOrderHandler;
    private CourierDataHandler courierDataHandler;

    public OrderController(ClientOrderHandler clh, CourierDataHandler cdh){
        this.clientOrderHandler = clh;
        this.courierDataHandler = cdh;
    }

    public Courier getCourierByEmail(String email){
        return courierDataHandler.getCourierByEmail(email);
    }

    public LinkedHashMap<Integer, ClientOrder> getUndoneOrders(){
        return clientOrderHandler.getUndoneOrders();
    }

}
