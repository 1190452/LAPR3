package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefillStockTest {

    private RefillStock refillStock;
    private RefillStock refillStock2;

    public RefillStockTest() {
        refillStock = new RefillStock(1,20,300,12,0,1,1);
        refillStock2 = new RefillStock(20,300,12,1,1);
    }

    @Test
    void getId() {
        assertEquals(1, refillStock.getId());
    }

    @Test
    void setId() {
        refillStock.setId(2);
        assertEquals(2,refillStock.getId());
    }

    @Test
    void getNecessaryEnergy() {
        assertEquals(20,refillStock.getNecessaryEnergy());
    }

    @Test
    void setNecessaryEnergy() {
        refillStock.setNecessaryEnergy(50);
        assertEquals(50,refillStock.getNecessaryEnergy());
    }

    @Test
    void getDistance() {
        assertEquals(300, refillStock.getDistance());
    }

    @Test
    void setDistance() {
        refillStock.setDistance(70);
        assertEquals(70,refillStock.getDistance());
    }

    @Test
    void getWeight() {
        assertEquals(12, refillStock.getWeight());
    }

    @Test
    void setWeight() {
        refillStock.setWeight(20);
        assertEquals(20,refillStock.getWeight());
    }

    @Test
    void getStatus() {
        assertEquals(0, refillStock.getStatus());
    }

    @Test
    void setStatus() {
        refillStock.setStatus(1);
        assertEquals(1, refillStock.getStatus());
    }

    @Test
    void getCourierID() {
        assertEquals(1, refillStock2.getCourierID());
    }

    @Test
    void setCourierID() {
        refillStock2.setCourierID(11);
        assertEquals(11,refillStock2.getCourierID());
    }

    @Test
    void getVehicleID() {
        assertEquals(1,refillStock2.getVehicleID());
    }

    @Test
    void setVehicleID() {
        refillStock2.setVehicleID(3);
        assertEquals(3,refillStock2.getVehicleID());
    }

    @Test
    void testToString() {
        String expResult = "RefillStock{" +
                "id=" + 1 +
                ", necessaryEnergy=" + 20.0 +
                ", distance=" + 300.0 +
                ", weight=" + 12.0 +
                ", status=" + 0 +
                ", courierID=" + 1 +
                ", vehicleID=" + 1 +
                '}';
        String result = refillStock.toString();
        assertEquals(expResult,result);
    }
}