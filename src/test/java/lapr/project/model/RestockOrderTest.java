package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestockOrderTest {
    private RestockOrder restock1;
    private RestockOrder restock2;

    RestockOrderTest() {
        this.restock1 = new RestockOrder(1, 1, 4, 2, 5, 7, 0, 10);
        this.restock2 = new RestockOrder(5, 3,5,6,9, 4, 20);
    }


    @Test
    void getPharmReceiverID() {
        assertEquals(1, restock1.getPharmReceiverID());
    }

    @Test
    void setPharmReceiverID() {
        restock1.setPharmReceiverID(7);
        assertEquals(7, restock1.getPharmReceiverID());
    }

    @Test
    void getPharmSenderID() {
        assertEquals(4, restock1.getPharmSenderID());
    }

    @Test
    void setPharmSenderID() {
        restock1.setPharmSenderID(7);
        assertEquals(7, restock1.getPharmSenderID());
    }

    @Test
    void getId() {
        assertEquals(1, restock1.getId());
    }

    @Test
    void setId() {
        restock1.setId(13);
        assertEquals(13, restock1.getId());
    }

    @Test
    void getProductID() {
        assertEquals(2, restock1.getProductID());
    }

    @Test
    void setProductID() {
        restock1.setProductID(5);
        assertEquals(5, restock1.getProductID());
    }

    @Test
    void getClientOrderID() {
        assertEquals(5, restock1.getClientOrderID());
    }

    @Test
    void setClientOrderID() {
        restock1.setClientOrderID(20);
        assertEquals(20, restock1.getClientOrderID());
    }

    @Test
    void getProductQuantity() {
        restock1.setProductQuantity(2);
        assertEquals(2, restock1.getProductQuantity());
    }

    @Test
    void setProductQuantity() {
        restock1.setProductQuantity(40);
        assertEquals(40, restock1.getProductQuantity());
    }

    @Test
    void testToString() {
        String expResult = "RestockOrder{" +
                "pharmReceiverID=" + 1 +
                ", pharmSenderID=" + 4 +
                ", id=" + 1 +
                ", productID=" + 2 +
                ", clientOrderID=" + 5 +
                ", productQuantity=" + 7 +
                ", status=" + 0 +
                ", idRefillStock=" + 10 +
                '}';
        String result = restock1.toString();
        assertEquals(expResult, result);
    }

    @Test
    void getStatus() {
        assertEquals(0,restock1.getStatus());
    }

    @Test
    void setStatus() {
        restock1.setStatus(1);
        assertEquals(1,restock1.getStatus());
    }

    @Test
    void getIdRefillStock() {
        assertEquals(10, restock1.getIdRefillStock());
    }

    @Test
    void setIdRefillStock() {
        restock2.setIdRefillStock(10);
        assertEquals(10, restock2.getIdRefillStock());
    }

    @Test
    void testEquals() {
        RestockOrder obj = null;
        RestockOrder instance = new RestockOrder(1, 1, 4, 2, 5, 7, 0, 10);
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);

    }

    @Test
    void testEquals3() {
        Object obj = null;
        RestockOrder instance = new RestockOrder(1, 1, 4, 2, 5, 7, 0, 10);
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);

    }

    @Test
    void testEquals4() {
        RestockOrder instance = new RestockOrder(1, 1, 4, 2, 5, 7, 0, 10);
        boolean expected = true;
        boolean result = instance.equals(instance);
        assertEquals(expected, result);

    }

    @Test
    void testEquals2() {
        RestockOrder obj = new RestockOrder(1, 1, 4, 2, 5, 7, 0, 10);

        RestockOrder instance = new RestockOrder(1, 1, 4, 2, 5, 7, 0, 10);
        boolean expected = true;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);

    }
}