package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    private Cart cart;



    public CartTest() {
        this.cart = new Cart(45, 6, new ArrayList<>());
    }

    @Test
    void getFinalPrice() {
        double expResult = 45;
        assertEquals(expResult, cart.getFinalPrice());
    }

    @Test
    void setFinalPrice() {
        cart.setFinalPrice(32);
        double expResult = 32;
        assertEquals(expResult, cart.getFinalPrice());
    }

    @Test
    void getFinalWeight() {
        double expResult = 6;
        assertEquals(expResult, cart.getFinalWeight());
    }

    @Test
    void setFinalWeight() {
        cart.setFinalWeight(2);
        double expResult = 2;
        assertEquals(expResult, cart.getFinalWeight());
    }

    @Test
    void getProductsTobuy() {

    }

    @Test
    void setProductsTobuy() {
    }

    @Test
    void updateAddCart() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
        int hash = 193918048;
        assertEquals(hash, cart.hashCode());
    }

    @Test
    void testToString() {
        String result = cart.toString();
        String expResult =  "Cart{" +
                ", finalPrice=" + 45.0 +
                ", finalWeight=" + 6.0 +
                ", productsTobuy=" + "[]" +
                '}';

        assertEquals(expResult,result);
    }

    @Test
    void updateRemoveCart() {
    }
}