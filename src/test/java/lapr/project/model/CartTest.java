package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    private Cart cart;
    private Cart.AuxProduct auxProduct;

    public CartTest() {
        this.auxProduct = new Cart.AuxProduct(new Product("xarope","xarope para a tosse",6,0.5,1,2), 5);
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
    public void test1Equals() {
        Cart obj = null;
        Cart instance = new Cart(45, 6, new ArrayList<>());
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test2Equals() {
        Object obj = null;
        Cart instance = new Cart(45, 6, new ArrayList<>());
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test3Equals() {
        Cart instance = new Cart(45, 6, new ArrayList<>());
        boolean expected = true;
        boolean result = instance.equals(instance);
        assertEquals(expected, result);
    }

    @Test
    public void test4Equals() {
        Cart d = new Cart(45, 6, new ArrayList<>());
        Cart instance = new Cart(45, 6, new ArrayList<>());
        boolean expected = true;
        boolean result = instance.equals(d);
        assertEquals(expected, result);
    }

    @Test
    public void test5Equals() {
        Cart d = new Cart(45, 6, new ArrayList<>());
        Cart instance = new Cart(55, 5, new ArrayList<>());
        boolean expected = false;
        boolean result = instance.equals(d);
        assertEquals(expected, result);
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

    @Test
    void getProduct() {
        Product expResult = new Product("xarope","xarope para a tosse",6,0.5,1,2);
        assertEquals(expResult, auxProduct.getProduct());
    }

    @Test
    void setProduct() {
        auxProduct.setProduct(new Product("comprimido","comprimido para a tosse",6,0.5,1,2));
        Product expResult = new Product("comprimido","comprimido para a tosse",6,0.5,1,2);
        assertEquals(expResult,auxProduct.getProduct() );
    }

    @Test
    void getStock() {
        int expResult = 5;
        assertEquals(expResult, auxProduct.getStock());
    }

    @Test
    void setStock() {
        auxProduct.setStock(6);
        int expResult = 6;
        assertEquals(expResult, auxProduct.getStock());
    }

    @Test
    void toString2() {
        String result = auxProduct.toString();
        String expResult =  "AuxProduct{" +
                "product=" + auxProduct.getProduct() +
                ", stock=" + auxProduct.getStock() +
                '}';

        assertEquals(expResult,result);
    }
}