package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestockTest {
    private Restock restock1;
    private Restock restock2;

    RestockTest() {
        this.restock1 = new Restock(1, 4, 2, "product", 4, 10);
        this.restock2 = new Restock(5, 3,"x", 4, 20);
    }


    @Test
    void getPharmReceiverID() {
        assertEquals(4, restock1.getPharmReceiverID());
    }

    @Test
    void setPharmReceiverID() {
        restock1.setPharmReceiverID(7);
        assertEquals(7, restock1.getPharmReceiverID());
    }

    @Test
    void getPharmSenderID() {
        assertEquals(2, restock1.getPharmSenderID());
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
    void getProductName() {
        assertEquals("product", restock1.getProductName());
    }

    @Test
    void setProductName() {
        restock1.setProductName("xpto");
        assertEquals("xpto", restock1.getProductName());
    }

    @Test
    void getClientID() {
        assertEquals(4, restock1.getClientID());
    }

    @Test
    void setClientID() {
        restock1.setClientID(20);
        assertEquals(20, restock1.getClientID());
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
        String expResult = "Restock{" +
                "pharmReceiverID=" + 4 +
                ", pharmSenderID=" + 2 +
                ", id=" + 1 +
                ", productName='" + "product" + '\'' +
                ", clientID=" + 4 +
                ", productQuantity=" + 10 +
                '}';
        String result = restock1.toString();
        assertEquals(expResult, result);
    }
}