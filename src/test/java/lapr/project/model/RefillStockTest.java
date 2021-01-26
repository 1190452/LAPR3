package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefillStockTest {

    private RefillStock refillStock;
    private RefillStock refillStock2;

    public RefillStockTest() {
        refillStock = new RefillStock(1,20,300,12,0,1,"ae");
        refillStock2 = new RefillStock(20,300,12,1,"ae");
    }

    @Test
    void getId() {
        assertEquals(1, refillStock.getIdRefillStock());
    }

    @Test
    void setId() {
        refillStock.setIdRefillStock(2);
        assertEquals(2,refillStock.getIdRefillStock());
    }

    @Test
    void getNecessaryEnergy() {
        assertEquals(20,refillStock.getNecessaryEnergyRestock());
    }

    @Test
    void setNecessaryEnergy() {
        refillStock.setNecessaryEnergyRestock(50);
        assertEquals(50,refillStock.getNecessaryEnergyRestock());
    }

    @Test
    void getDistance() {
        assertEquals(300, refillStock.getDistanceRestock());
    }

    @Test
    void setDistance() {
        refillStock.setDistanceRestock(70);
        assertEquals(70,refillStock.getDistanceRestock());
    }

    @Test
    void getWeight() {
        assertEquals(12, refillStock.getWeightRestock());
    }

    @Test
    void setWeight() {
        refillStock.setWeightRestock(20);
        assertEquals(20,refillStock.getWeightRestock());
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
    void getVehicleLicensePlate() {
        assertEquals("ae",refillStock2.getlicensePlate());
    }

    @Test
    void setVehicleID() {
        refillStock2.setlicensePlate("Boas");
        assertEquals("Boas",refillStock2.getlicensePlate());
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
                ", License Plate=" + "ae" +
                '}';
        String result = refillStock.toString();
        assertEquals(expResult,result);
    }
}