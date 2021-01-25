package lapr.project.model;

import java.util.Objects;

public class RestockOrder {
    private int pharmReceiverID;
    private int pharmSenderID;
    private int id;
    private int productID;
    private int clientOrderID;
    private int productQuantity;
    private int status;
    private int idRefillStock;

    /**
     * Constructor RestockOrder with parameters
     * @param id restock order id
     * @param pharmReceiverID pharmacy id that will receive the product
     * @param pharmSenderID pharmacy id that will send the product
     * @param productID product id that needs restock
     * @param clientOrderID client order id waiting for restock
     * @param productQuantity product quantity to be received
     * @param status order status (1(done) or 0(undone))
     * @param idRefillStock refill stock id that will deliver the restock order
     */
    public RestockOrder(int id, int pharmReceiverID, int pharmSenderID, int productID, int clientOrderID, int productQuantity, int status, int idRefillStock) {
        this.pharmReceiverID = pharmReceiverID;
        this.pharmSenderID = pharmSenderID;
        this.id = id;
        this.productID = productID;
        this.clientOrderID = clientOrderID;
        this.productQuantity = productQuantity;
        this.status = status;
        this.idRefillStock = idRefillStock;
    }

    public RestockOrder(int pharmReceiverID, int pharmSenderID, int productID, int clientOrderID, int productQuantity, int status, int idRefillStock) {
        this.pharmReceiverID = pharmReceiverID;
        this.pharmSenderID = pharmSenderID;
        this.productID = productID;
        this.clientOrderID = clientOrderID;
        this.productQuantity = productQuantity;
        this.status = status;
        this.idRefillStock = idRefillStock;
    }


    /**
     *
     * @return the pharmacy receiver id
     */
    public int getPharmReceiverID() {
        return pharmReceiverID;
    }

    /**
     *
     * @param pharmReceiverID the pharmacy receiver id to set
     */
    public void setPharmReceiverID(int pharmReceiverID) {
        this.pharmReceiverID = pharmReceiverID;
    }

    /**
     *
     * @return the pharmacy sender id
     */
    public int getPharmSenderID() {
        return pharmSenderID;
    }

    /**
     *
     * @param pharmSenderID the pharmacy sender id to set
     */
    public void setPharmSenderID(int pharmSenderID) {
        this.pharmSenderID = pharmSenderID;
    }

    /**
     *
     * @return the restock id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id the restock id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the product id
     */
    public int getProductID() {
        return productID;
    }

    /**
     *
     * @param productID the product id to set
     */
    public void setProductID(int productID) {
        this.productID = productID;
    }

    /**
     *
     * @return the client order id
     */
    public int getClientOrderID() {
        return clientOrderID;
    }

    /**
     *
     * @param clientOrderID the client order id to set
     */
    public void setClientOrderID(int clientOrderID) {
        this.clientOrderID = clientOrderID;
    }

    /**
     *
     * @return the product quantity
     */
    public int getProductQuantity() {
        return productQuantity;
    }

    /**
     *
     * @param productQuantity the product quantity to set
     */
    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
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
     * @return the refill stock id
     */
    public int getIdRefillStock() {
        return idRefillStock;
    }

    /**
     *
     * @param idRefillStock the refill stock id to set
     */
    public void setIdRefillStock(int idRefillStock) {
        this.idRefillStock = idRefillStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestockOrder rorder = (RestockOrder) o;
        return getId() == rorder.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(pharmReceiverID, pharmSenderID, id, productID, clientOrderID, productQuantity, status, idRefillStock);
    }

    @Override
    public String toString() {
        return "RestockOrder{" +
                "pharmReceiverID=" + pharmReceiverID +
                ", pharmSenderID=" + pharmSenderID +
                ", id=" + id +
                ", productID=" + productID +
                ", clientOrderID=" + clientOrderID +
                ", productQuantity=" + productQuantity +
                ", status=" + status +
                ", idRefillStock=" + idRefillStock +
                '}';
    }

}

