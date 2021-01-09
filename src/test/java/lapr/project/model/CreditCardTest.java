package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreditCardTest {

    private final CreditCard creditCard;

    public CreditCardTest() {
        creditCard = new CreditCard(new BigInteger("1254789645781236").intValue(), 12,2021,256);
    }

    @Test
    void save() {
    }

    @Test
    void getCreditCard() {

    }

    @Test
    void getCardNumber() {
        int expResult = new BigInteger("1254789645781236").intValue();
        assertEquals(expResult,creditCard.getCardNumber());
    }

    @Test
    void setCardNumber() {
        creditCard.setCardNumber(new BigInteger("1254789645784446").longValue());
        long result = creditCard.getCardNumber();
        long expResult = new BigInteger("1254789645784446").longValue();
        assertEquals(expResult,result);
    }

    @Test
    void getMonthExpiration() {
        int expResult = 12;
        assertEquals(expResult,creditCard.getMonthExpiration());
    }

    @Test
    void setMonthExpiration() {
        creditCard.setMonthExpiration(11);
        int result = creditCard.getMonthExpiration();
        int expResult = 11;
        assertEquals(expResult,result);
    }

    @Test
    void getYearExpiration() {
        int expResult = 2021;
        assertEquals(expResult,creditCard.getYearExpiration());
    }

    @Test
    void setYearExpiration() {
        creditCard.setYearExpiration(2022);
        int result = creditCard.getYearExpiration();
        int expResult = 2022;
        assertEquals(expResult,result);
    }

    @Test
    void getCcv() {
        int expResult = 256;
        assertEquals(expResult,creditCard.getCcv());
    }

    @Test
    void setCcv() {
        creditCard.setCcv(477);
        int result = creditCard.getCcv();
        int expResult = 477;
        assertEquals(expResult,result);
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testToString() {
        String result = creditCard.toString();
        String expResult = "CreditCard{" +
                "number=" + new BigInteger("1254789645784446").intValue() +
                ", monthExpiration=" + 12 +
                ", yearExpiration=" + 2021 +
                ", ccv=" + 256 +
                '}';
    }
}