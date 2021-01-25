package lapr.project.model;

import java.math.BigDecimal;
import java.util.Objects;

public class CreditCard {

    BigDecimal cardNumber;
    int monthExpiration;
    int yearExpiration;
    int ccv;

    /**
     * Constructor CreditCard with parameters
     * @param cardNumber credit card number
     * @param monthExpiration credit card month of expiration
     * @param yearExpiration credit card year of expiration
     * @param ccv credit card ccv number
     */
    public CreditCard(BigDecimal cardNumber, int monthExpiration, int yearExpiration, int ccv) {
        this.cardNumber = cardNumber;
        this.monthExpiration = monthExpiration;
        this.yearExpiration = yearExpiration;
        this.ccv = ccv;
    }

    public CreditCard(BigDecimal cardNumber){
        this.cardNumber = cardNumber;
    }

    /**
     *
     * @return the credit card number
     */
    public BigDecimal getCardNumber() {
        return cardNumber;
    }

    /**
     *
     * @param number the credit card number to set
     */
    public void setCardNumber(BigDecimal number) {
        this.cardNumber = number;
    }

    /**
     *
     * @return the month of expiration
     */
    public int getMonthExpiration() {
        return monthExpiration;
    }

    /**
     *
     * @param monthExpiration the month of expiration to set
     */
    public void setMonthExpiration(int monthExpiration) {
        this.monthExpiration = monthExpiration;
    }

    /**
     *
     * @return the year of expiration
     */
    public int getYearExpiration() {
        return yearExpiration;
    }

    /**
     *
     * @param yearExpiration the year of expiration to set
     */
    public void setYearExpiration(int yearExpiration) {
        this.yearExpiration = yearExpiration;
    }

    /**
     *
     * @return the ccv
     */
    public int getCcv() {
        return ccv;
    }

    /**
     *
     * @param ccv the ccv to set
     */
    public void setCcv(int ccv) {
        this.ccv = ccv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditCard)) return false;
        CreditCard that = (CreditCard) o;
        return cardNumber.equals(that.cardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber);
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "number=" + cardNumber +
                ", monthExpiration=" + monthExpiration +
                ", yearExpiration=" + yearExpiration +
                ", ccv=" + ccv +
                '}';
    }

}
