package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScooterTest {

    private final EletricScooter scooter;
    private final EletricScooter scooter2;

    ScooterTest() {
        scooter = new EletricScooter(2,400,350,1,500,8.0,5000.0,430,4);
        scooter2 = new EletricScooter(4, 500, 500,120, 25, 100, 400, 1);
    }


    @Test
    void getId() {
        int id = scooter.getId();
        int expId = 2;
        assertEquals(expId,id);

    }

    @Test
    void setId() {
        scooter.setId(4);
        int id = scooter.getId();
        int expResult = 4;
        assertEquals(expResult, id);

    }

    @Test
    void getMaxBattery() {
        double maxBattery = scooter.getMaxBattery();
        double expResult = 400;
        assertEquals(expResult, maxBattery);
    }

    @Test
    void setMaxBattery() {
        scooter.setMaxBattery(0);
        double id = scooter.getMaxBattery();
        double expResult = 0;
        assertEquals(expResult, id);
    }

    @Test
    void getActualBattery() {
        double actualBattery = scooter2.getActualBattery();
        double expResult = 500;
        assertEquals(expResult, actualBattery);
    }

    @Test
    void setActualBattery() {
        scooter.setMaxBattery(10);
        double maxBattery = scooter.getActualBattery();
        double expResult = 10;
        assertEquals(expResult,maxBattery);
    }

    @Test
    void getStatus() {
        int status = scooter.getStatus();
        int expResult = 1;
        assertEquals(expResult, status);
    }

    @Test
    void getIdPharmacy() {
        int idPharmacy = scooter.getIdPharmacy();
        int expResult = 4;
        assertEquals(expResult, idPharmacy);
    }

    @Test
    void setIdPharmacy() {
        scooter.setIdPharmacy(15);
        int idPharmacy = scooter.getIdPharmacy();
        int expResult = 15;
        assertEquals(expResult, idPharmacy);
    }

    @Test
    void setStatus() {
    }

    @Test
    void getEnginePower() {
    }

    @Test
    void setEnginePower() {
    }

    @Test
    void getAh_battery() {
    }

    @Test
    void setAh_battery() {
    }

    @Test
    void getV_battery() {
    }

    @Test
    void setV_battery() {
    }

    @Test
    void getWeight() {
    }

    @Test
    void setWeight() {
    }

    @Test
    void getBatteryPercentage() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testToString() {
    }
}