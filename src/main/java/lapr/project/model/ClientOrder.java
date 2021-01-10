package lapr.project.model;


import lapr.project.data.ClientOrderHandler;

import java.sql.Date;

public class ClientOrder {

    private int orderId;
    private Date dateOrder;
    private double finalPrice;
    private double finalWeight;
    private int status;
    private int clientId;

    public ClientOrder(double finalPrice, double finalWeight, int clientId) {
        this.finalPrice = finalPrice;
        this.finalWeight = finalWeight;
        this.clientId = clientId;
    }


    public ClientOrder(int orderId, Date dateOrder, double finalPrice, double finalWeight, int status, int clientId) {
        this.orderId = orderId;
        setDate(dateOrder);
        this.finalPrice = finalPrice;
        this.finalWeight = finalWeight;
        this.status = status;
        this.clientId = clientId;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public double getFinalWeight() {
        return finalWeight;
    }

    public void setFinalWeight(double finalWeight) {
        this.finalWeight = finalWeight;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        if(this.dateOrder != null)
            return new Date(dateOrder.getTime());
        else
            return null;
    }

    public void setDate(Date date) {
        if(date != null)
            this.dateOrder = new Date(date.getTime());
        else
            this.dateOrder = null;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int save() {
        int id=0;
        try {
            getOrder(this.orderId);
        } catch (IllegalArgumentException ex) {
            //Of the record does not exist, save it
            id=new ClientOrderHandler().addClientOrder(this);
        }
        return id;
    }

    public ClientOrder getOrder(int orderId){
        return new ClientOrderHandler().getClientOrder(orderId);
    }
}
