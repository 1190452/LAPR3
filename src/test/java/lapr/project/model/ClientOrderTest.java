package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class ClientOrderTest {

    private final ClientOrder clientOrder;
    private final ClientOrder clientOrder2;

    public ClientOrderTest() {
        clientOrder = new ClientOrder(12,1,1);
        clientOrder2 = new ClientOrder(1,new Date(1254441245),12,1,0,1);
    }

    @Test
    void getFinalPrice() {
        double expResult = 12;
        assertEquals(expResult,clientOrder.getFinalPrice());
    }

    @Test
    void setFinalPrice() {
        clientOrder.setFinalPrice(15);
        double result = clientOrder.getFinalPrice();
        double expResult = 15;
        assertEquals(expResult,result);
    }

    @Test
    void getFinalWeight() {
        double expResult = 1;
        assertEquals(expResult,clientOrder.getFinalWeight());
    }

    @Test
    void setFinalWeight() {
        clientOrder.setFinalWeight(5);
        double result = clientOrder.getFinalWeight();
        double expResult = 5;
        assertEquals(expResult,result);
    }

    @Test
    void getClientId() {
        double expResult = 1;
        assertEquals(expResult,clientOrder.getClientId());
    }

    @Test
    void setClientId() {
        clientOrder.setClientId(2);
        double result = clientOrder.getClientId();
        double expResult = 2;
        assertEquals(expResult,result);
    }

    @Test
    void getOrderId() {
        int expResult = 1;
        assertEquals(expResult, clientOrder2.getOrderId());
    }

    @Test
    void setOrderId() {
        clientOrder2.setOrderId(5);
        double result = clientOrder2.getOrderId();
        double expResult = 5;
        assertEquals(expResult,result);
    }

    @Test
    void getDate() {
        Date expResult = new Date(1254441245);
        assertEquals(expResult, clientOrder2.getDate());
    }

    @Test
    void setDate() {
        clientOrder2.setDate(new Date(1265874));
        Date result = clientOrder2.getDate();
        Date expResult = new Date(1265874);
        assertEquals(expResult,result);
    }

    @Test
    void getStatus() {
        int expResult = 0;
        assertEquals(expResult, clientOrder2.getStatus());
    }

    @Test
    void setStatus() {
        clientOrder2.setStatus(1);
        double result = clientOrder2.getStatus();
        double expResult = 1;
        assertEquals(expResult,result);
    }

    @Test
    void save() {
    }

    @Test
    void getOrder() {

    }
}