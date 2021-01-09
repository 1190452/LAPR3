package lapr.project.model;

import lapr.project.data.ClientDataHandler;
import lapr.project.data.CourierDataHandler;
import lapr.project.data.InvoiceHandler;

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
        this.dataInvoice=dataInvoice; // NOPMD
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
        return dataInvoice;
    }

    public void setDataInvoice(Date dataInvoice) { 
        this.dataInvoice = dataInvoice;
    }

    public int save() {
        int invoiceId=0;
        try {
            getInvoice(this.id);
        } catch (IllegalArgumentException ex) {
            //Of the record does not exist, save it
            invoiceId = new InvoiceHandler().addInvoice(this);
        }
        return invoiceId;
    }

    public static Invoice getInvoice(int id) {
        return new InvoiceHandler().getInvoice(id);
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
