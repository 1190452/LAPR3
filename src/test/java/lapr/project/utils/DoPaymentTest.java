package lapr.project.utils;

import lapr.project.model.Client;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DoPaymentTest {

    private DoPayment dp;

    public DoPaymentTest(){
        dp=new DoPayment();
    }

    @Test
    void doesPayment() {
        boolean result = dp.doesPayment(new Client(1, "Jorge", "jorge@gmail.com", "qwerty", 132456789, 11,1,213.109,new BigDecimal("1234567891057189")), 10);
        boolean actualResult = true;
        assertEquals(result, actualResult);

    }

    @Test
    void doesPayment2() {
        boolean result = dp.doesPayment(new Client(1, "Jorge", "jorge@gmail.com", "qwerty", 132456789, 11,1,213.10,new BigDecimal("1234567891057189")), -10);
        boolean actualResult = false;
        assertEquals(result, actualResult);

    }

    @Test
    void doesPayment3() {
        boolean result = dp.doesPayment(new Client(1, "Jorge", "jorge@gmail.com", "qwerty", 132456789, 11,1,109.10,new BigDecimal("1234567891057189")), 0);
        boolean actualResult = true;
        assertEquals(result, actualResult);

    }
}