package lapr.project.model;


import java.sql.Date;
import java.util.Objects;

public class ClientOrder {

    private int orderId;
    private Date dateOrder;
    private double finalPrice;
    private double finalWeight;
    private int status;
    private int isComplete;
    private int clientId;
    private int deliveryId;

    public ClientOrder(double finalPrice, double finalWeight, int clientId, int isComplete) {
        this.finalPrice = finalPrice;
        this.finalWeight = finalWeight;
        this.clientId = clientId;
        this.isComplete = isComplete;
    }

    public ClientOrder(int orderId, Date dateOrder, double finalPrice, double finalWeight, int status,int isComplete ,int clientId, int deliveryId) {
        this.orderId = orderId;
        setDate(dateOrder);
        this.finalPrice = finalPrice;
        this.finalWeight = finalWeight;
        this.status = status;
        this.isComplete = isComplete;
        this.clientId = clientId;
        this.deliveryId = deliveryId;
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

    public int getDeliveryId() {
        return deliveryId;
    }

    public int getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(int isComplete) {
        this.isComplete = isComplete;
    }

    @Override
    public String toString() {
        return "ClientOrder{" +
                "orderId=" + orderId +
                ", dateOrder=" + dateOrder +
                ", finalPrice=" + finalPrice +
                ", finalWeight=" + finalWeight +
                ", status=" + status +
                ", clientId=" + clientId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientOrder that = (ClientOrder) o;
        return orderId == that.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}
