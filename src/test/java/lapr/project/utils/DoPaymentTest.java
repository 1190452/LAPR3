package lapr.project.utils;

import lapr.project.model.Client;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class DoPaymentTest {

    private DoPayment dp;

    public DoPaymentTest(){
        dp=new DoPayment();
    }

    @Test
    void doesPayment() {
        boolean result = dp.doesPayment(new Client(1, "Jorge", "jorge@gmail.com", "qwerty", 132456789, 11,1,new BigInteger("1234567891057189").intValue()), 10);
        boolean actualResult = true;
        assertEquals(result, actualResult);

    }
}