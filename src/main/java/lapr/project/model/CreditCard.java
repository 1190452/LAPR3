package lapr.project.model;

import lapr.project.data.CreditCardDataHandler;


import java.util.Objects;

public class CreditCard {
    long cardNumber;
    int monthExpiration;
    int yearExpiration;
    int ccv;

    public CreditCard(long cardNumber, int monthExpiration, int yearExpiration, int ccv) {
        this.cardNumber = cardNumber;
        this.monthExpiration = monthExpiration;
        this.yearExpiration = yearExpiration;
        this.ccv = ccv;
    }

    public CreditCard(int cardNumber){
        this.cardNumber = cardNumber;
    }


    public void save() {
        try {
            getCreditCard(this.cardNumber);
        } catch (IllegalArgumentException ex) {
            //Of the record does not exist, save it
            new CreditCardDataHandler().addCreditCard(this);
        }
    }

    public static CreditCard getCreditCard(long cardNumber) {
        return new CreditCardDataHandler().getCreditCard(cardNumber);
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long number) {
        this.cardNumber = number;
    }

    public int getMonthExpiration() {
        return monthExpiration;
    }

    public void setMonthExpiration(int monthExpiration) {
        this.monthExpiration = monthExpiration;
    }

    public int getYearExpiration() {
        return yearExpiration;
    }

    public void setYearExpiration(int yearExpiration) {
        this.yearExpiration = yearExpiration;
    }

    public int getCcv() {
        return ccv;
    }

    public void setCcv(int ccv) {
        this.ccv = ccv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditCard)) return false;
        CreditCard that = (CreditCard) o;
        return cardNumber == that.cardNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, monthExpiration, yearExpiration, ccv);
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
