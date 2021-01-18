package lapr.project.model;

public class RestockOrder {
    private int pharmReceiverID;
    private int pharmSenderID;
    private int id;
    private int productID;
    private int clientOrderID;
    private int productQuantity;
    private int status;
    private int idRefillStock;

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


    public int getPharmReceiverID() {
        return pharmReceiverID;
    }

    public void setPharmReceiverID(int pharmReceiverID) {
        this.pharmReceiverID = pharmReceiverID;
    }

    public int getPharmSenderID() {
        return pharmSenderID;
    }

    public void setPharmSenderID(int pharmSenderID) {
        this.pharmSenderID = pharmSenderID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getClientOrderID() {
        return clientOrderID;
    }

    public void setClientOrderID(int clientOrderID) {
        this.clientOrderID = clientOrderID;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIdRefillStock() {
        return idRefillStock;
    }

    public void setIdRefillStock(int idRefillStock) {
        this.idRefillStock = idRefillStock;
    }
}

