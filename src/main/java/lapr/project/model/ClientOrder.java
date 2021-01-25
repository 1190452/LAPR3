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

    /**
     * Constructor ClientOrder with parameters
     * @param orderId order id
     * @param dateOrder order date
     * @param finalPrice order final price
     * @param finalWeight order final weight
     * @param status order status (1(done) or 0(undone))
     * @param isComplete order complete status (1(has all products) or 0(waiting for out of stock products))
     * @param clientId client id who placed the order
     * @param deliveryId delivery id that will deliver the order
     */
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

    public ClientOrder(double finalPrice, double finalWeight, int clientId, int isComplete) {
        this.finalPrice = finalPrice;
        this.finalWeight = finalWeight;
        this.clientId = clientId;
        this.isComplete = isComplete;
    }

    /**
     *
     * @return the final price
     */
    public double getFinalPrice() {
        return finalPrice;
    }

    /**
     *
     * @param finalPrice the final price to set
     */
    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    /**
     *
     * @return the final weight
     */
    public double getFinalWeight() {
        return finalWeight;
    }

    /**
     *
     * @param finalWeight the final weight to set
     */
    public void setFinalWeight(double finalWeight) {
        this.finalWeight = finalWeight;
    }

    /**
     *
     * @return the client id
     */
    public int getClientId() {
        return clientId;
    }

    /**
     *
     * @param clientId the client id to set
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     *
     * @return the order id
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     *
     * @param orderId the order id to set
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     *
     * @return the order date
     */
    public Date getDate() {
        if(this.dateOrder != null)
            return new Date(dateOrder.getTime());
        else
            return null;
    }

    /**
     *
     * @param date the order date to set
     */
    public void setDate(Date date) {
        if(date != null)
            this.dateOrder = new Date(date.getTime());
        else
            this.dateOrder = null;
    }

    /**
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     *
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     *
     * @return the delivery id
     */
    public int getDeliveryId() {
        return deliveryId;
    }

    /**
     *
     * @return the complete status
     */
    public int getIsComplete() {
        return isComplete;
    }

    /**
     *
     * @param isComplete the complete status to set
     */
    public void setIsComplete(int isComplete) {
        this.isComplete = isComplete;
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

}
