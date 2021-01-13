package lapr.project.model;

import java.sql.Date;
import java.util.Objects;

public class Invoice {
    private int id;
    private Date dataInvoice;
    private double finalPrice;
    private int clientID;
    private int idOrder;


    public Invoice(double finalPrice, int clientID, int idOrder) {
        this.finalPrice = finalPrice;
        this.clientID = clientID;
        this.idOrder = idOrder;
    }

    public Invoice(int id, Date dataInvoice, double finalPrice, int clientID, int idOrder) {
        this.id=id;
        setDataInvoice(dataInvoice);
        this.finalPrice = finalPrice;
        this.clientID = clientID;
        this.idOrder = idOrder;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return getId() == invoice.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public Date getDataInvoice() {
        if(this.dataInvoice != null)
            return new Date(dataInvoice.getTime());
        else
            return null;
    }

    public void setDataInvoice(Date date) {
        if(date != null)
            this.dataInvoice = new Date(date.getTime());
        else
            this.dataInvoice = null;
    }


    @Override
    public String toString() {
        return "YOUR ORDER\nInvoice{" +
                "id=" + id +
                ", dataInvoice=" + dataInvoice +
                ", finalPrice=" + finalPrice +
                ", clientID=" + clientID +
                ", idOrder=" + idOrder +
                '}';
    }
}
