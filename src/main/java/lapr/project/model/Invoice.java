package lapr.project.model;

import java.sql.Date;
import java.util.Objects;

public class Invoice {

    private int id;
    private Date dataInvoice;
    private double finalPrice;
    private int clientID;
    private int idOrder;

    /**
     * Constructor Invoice with parameters
     * @param id invoice id
     * @param dataInvoice invoice date
     * @param finalPrice order final price
     * @param clientID client id who placed the order
     * @param idOrder order id
     */
    public Invoice(int id, Date dataInvoice, double finalPrice, int clientID, int idOrder) {
        this.id=id;
        setDataInvoice(dataInvoice);
        this.finalPrice = finalPrice;
        this.clientID = clientID;
        this.idOrder = idOrder;
    }

    public Invoice(double finalPrice, int clientID, int idOrder) {
        this.finalPrice = finalPrice;
        this.clientID = clientID;
        this.idOrder = idOrder;
    }

    /**
     *
     * @return the invoice id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id the invoice id to set
     */
    public void setId(int id) {
        this.id = id;
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
     * @return the client id
     */
    public int getClientID() {
        return clientID;
    }

    /**
     *
     * @param clientID the client id to set
     */
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    /**
     *
     * @return the order id
     */
    public int getIdOrder() {
        return idOrder;
    }

    /**
     *
     * @param idOrder the order id to set
     */
    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    /**
     *
     * @return the invoice date
     */
    public Date getDataInvoice() {
        if(this.dataInvoice != null)
            return new Date(dataInvoice.getTime());
        else
            return null;
    }

    /**
     *
     * @param date the invoice date to set
     */
    public void setDataInvoice(Date date) {
        if(date != null)
            this.dataInvoice = new Date(date.getTime());
        else
            this.dataInvoice = null;
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
