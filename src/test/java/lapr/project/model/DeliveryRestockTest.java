package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeliveryRestockTest {
    private final DeliveryRestock deliveryRestock;

    DeliveryRestockTest() {
        this.deliveryRestock = new DeliveryRestock(1, 2);
    }


    @Test
    void getRestock() {
        List<Restock> lst = deliveryRestock.getRestock();
        List<Restock> expResult = new ArrayList<>();
        assertEquals(expResult, lst);
    }

    @Test
    void getCourierID() {
        int result = deliveryRestock.getCourierID();
        int expResult = 1;
        assertEquals(expResult, result);
    }

    @Test
    void getVehicleid() {
        int result = deliveryRestock.getVehicleid();
        int expResult = 2;
        assertEquals(expResult, result);
    }
}