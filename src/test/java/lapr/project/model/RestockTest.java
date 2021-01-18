package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestockTest {
    private RestockOrder restock1;
    private RestockOrder restock2;

    RestockTest() {
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
    void getProducID() {
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
}