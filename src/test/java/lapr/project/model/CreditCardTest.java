package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreditCardTest {

    private final CreditCard creditCard;
    private final CreditCard creditCard2;

    public CreditCardTest() {
        creditCard = new CreditCard(new BigDecimal("1254789645781236"), 12,2021,256);
        creditCard2 = new CreditCard(new BigDecimal("1254789645781236"));
    }

    @Test
    void save() {
    }

    @Test
    void getCreditCard() {

    }

    @Test
    void getCardNumber() {
        BigDecimal expResult = new BigDecimal("1254789645781236");
        assertEquals(expResult,creditCard.getCardNumber());
    }

    @Test
    void setCardNumber() {
        creditCard.setCardNumber(new BigDecimal("1254789645784446"));
        BigDecimal result = creditCard.getCardNumber();
        BigDecimal expResult = new BigDecimal("1254789645784446");
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
    public void test1Equals() {
        CreditCard obj = null;
        CreditCard instance = new CreditCard(new BigDecimal("1254789645781236"), 12,2021,256);
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test2Equals() {
        Object obj = null;
        CreditCard instance = new CreditCard(new BigDecimal("1254789645781236"), 12,2021,256);
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test3Equals() {
        CreditCard instance = new CreditCard(new BigDecimal("1254789645781236"), 12,2021,256);
        boolean expected = true;
        boolean result = instance.equals(instance);
        assertEquals(expected, result);
    }

    @Test
    public void test4Equals() {
        CreditCard d = new CreditCard(new BigDecimal("1254789645781236"), 12,2021,256);
        CreditCard instance = new CreditCard(new BigDecimal("1254789645781236"), 12,2021,256);
        boolean expected = true;
        boolean result = instance.equals(d);
        assertEquals(expected, result);
    }

    @Test
    public void test5Equals() {
        CreditCard d = new CreditCard(new BigDecimal("1254789645781236"), 12,2021,256);
        CreditCard instance = new CreditCard(new BigDecimal("1254789645781136"), 10,2022,255);
        boolean expected = false;
        boolean result = instance.equals(d);
        assertEquals(expected, result);
    }

    @Test
    void testHashCode() {
        int hash = -117808988;
        assertEquals(hash, creditCard.hashCode());
    }

    @Test
    void testToString() {
        String result = creditCard.toString();
        String expResult = "CreditCard{" +
                "number=" + new BigDecimal("1254789645781236") +
                ", monthExpiration=" + 12 +
                ", yearExpiration=" + 2021 +
                ", ccv=" + 256 +
                '}';
        assertEquals(expResult,result);
    }
}