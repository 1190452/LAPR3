package lapr.project.model;

import java.time.LocalDate;
import java.util.Objects;

public class Invoice {

    private int invoiceID;
    private String clientID;
    private LocalDate date;
    private double finalPrice;

    public Invoice(int invoiceID, String clientID, LocalDate date, double finalPrice) {
        this.invoiceID = invoiceID;
        this.clientID = clientID;
        this.date = date;
        this.finalPrice = finalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return invoiceID == invoice.invoiceID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceID);
    }


}
