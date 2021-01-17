package lapr.project.model;

public class Restock {
    private int pharmReceiverID;
    private int pharmSenderID;
    private int id;
    private String productName;
    private int clientID;
    private int productQuantity;

    public Restock(int id,int pharmReceiverID, int pharmSenderID, String productName, int clientID, int productQuantity) {
        this.pharmReceiverID = pharmReceiverID;
        this.pharmSenderID = pharmSenderID;
        this.id = id;
        this.productName = productName;
        this.clientID = clientID;
        this.productQuantity = productQuantity;

    }

    public Restock(int pharmReceiverID, int pharmSenderID, String productName, int clientID, int productQuantity) {
        this.pharmReceiverID = pharmReceiverID;
        this.pharmSenderID = pharmSenderID;
        this.productName = productName;
        this.clientID = clientID;
        this.productQuantity = productQuantity;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }



    @Override
    public String toString() {
        return "Restock{" +
                "pharmReceiverID=" + pharmReceiverID +
                ", pharmSenderID=" + pharmSenderID +
                ", id=" + id +
                ", productName='" + productName + '\'' +
                ", clientID=" + clientID +
                ", productQuantity=" + productQuantity +
                '}';
    }
}

