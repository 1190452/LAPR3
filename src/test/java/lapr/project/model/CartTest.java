package lapr.project.model;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    private Cart cart;
    private Cart cart2;

    private Cart.AuxProduct auxProduct;

    public CartTest() {
        this.auxProduct = new Cart.AuxProduct(new Product("xarope","xarope para a tosse",6,0.5,1,2), 5);
        this.cart = new Cart(45, 6, new ArrayList<>());
        this.cart2 = new Cart();
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
        List<Cart.AuxProduct> p=new ArrayList<>();
        p.add(new Cart.AuxProduct(new Product("xarope","xarope para a tosse",6,0.5,1,2), 5));


        cart.setProductsTobuy(p);

        List<Cart.AuxProduct> aux = cart.getProductsTobuy();


        List<Cart.AuxProduct> expResult = new ArrayList<>();
        expResult.add(new Cart.AuxProduct(new Product("xarope","xarope para a tosse",6,0.5,1,2), 5));

        assertEquals(expResult, aux);

    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    void setProductsTobuy() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> cart.setProductsTobuy(null));

        assertTrue(thrown.getMessage().contains("The list productsToBuy is null"));



    }

    @Test
    void setProductsTobuy2() {
        List<Cart.AuxProduct> newList = new ArrayList<>();
        Cart.AuxProduct auxProduct = new Cart.AuxProduct(new Product("xarope","xarope para a tosse",6,0.5,1,2), 5);
        newList.add(auxProduct);
        cart.setProductsTobuy(newList);

        List<Cart.AuxProduct> expResult = cart.getProductsTobuy();
        assertEquals(expResult, newList);
    }

    @Test
    void updateAddCart() {
        cart.updateAddCart(new Product("xarope","xarope para a tosse",6,0.5,1,2),5);
        double finalPrice = cart.getFinalPrice();
        double expResult = 75;
        assertEquals(expResult, finalPrice);

        double weight = cart.getFinalWeight();
        double expResult2 = 8.5;
        assertEquals(expResult2, weight);
    }

    @Test
    void updateRemoveCart() {
        cart.updateRemoveCart(new Cart.AuxProduct(new Product("xarope","xarope para a tosse",6,0.5,1,2),5));
        double finalPrice = cart.getFinalPrice();
        double expResult = 15.0;
        assertEquals(expResult, finalPrice);

        double weight = cart.getFinalWeight();
        double expResult2 = 3.5;
        assertEquals(expResult2, weight);
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
    public void test6Equals() {
        Product p = new Product(1,"xarope","xarope para a tosse",6,0.5,1,2);
        Cart instance = new Cart(55, 5, new ArrayList<>());
        boolean expected = false;
        boolean result = instance.equals(p);
        assertEquals(expected, result);
    }

    @Test
    public void test7Equals() {
        Cart instance = new Cart(55, 5, new ArrayList<>());
        Object obj = null;
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }


    @Test
    void testHashCode() {
        int hash = 1078362143;
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
        String expResult =  "Cart:\n" +
                auxProduct.getProduct() +
                " | Quantity: " + auxProduct.getStock() +
                '\n';

        assertEquals(expResult,result);
    }

    @Test
    public void test10Equals() {
        Cart.AuxProduct d = new Cart.AuxProduct(new Product("xarope","xarope para a tosse",6,0.5,1,2),5);
        Cart.AuxProduct instance = new Cart.AuxProduct(new Product("xarope","xarope para a tosse",6,0.5,1,2),5);
        boolean expected = true;
        boolean result = instance.equals(d);
        assertEquals(expected, result);
    }

    @Test
    public void test11Equals() {
        Cart.AuxProduct d = null;
        Cart.AuxProduct instance = new Cart.AuxProduct(new Product("xarope","xarope para a tosse",6,0.5,1,2),5);
        boolean expected = false;
        boolean result = instance.equals(d);
        assertEquals(expected, result);
    }

    @Test
    public void test12Equals() {
        Cart.AuxProduct d = null;
        Product instance = new Product(1,"xarope","xarope para a tosse",6,0.5,1,2);

        boolean expected = false;
        boolean result = instance.equals(d);
        assertEquals(expected, result);
    }

    @Test
    public void test13Equals() {
        Object d = null;
        Cart.AuxProduct instance = new Cart.AuxProduct(new Product("xarope","xarope para a tosse",6,0.5,1,2),5);
        boolean expected = false;
        boolean result = instance.equals(d);
        assertEquals(expected, result);
    }

    @Test
    void testHashCode1() {
        int expResult = 62;
        int result = auxProduct.hashCode();
        assertEquals(expResult,result);
    }
}