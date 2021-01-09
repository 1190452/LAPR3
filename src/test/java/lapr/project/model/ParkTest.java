package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkTest {

    private final Park park;

    ParkTest() {
        park = new Park(1,12,10,2,1);
    }

    @Test
    void testToString() {
        String result = park.toString();
        String expResult = "Park{" +
                "id='" + 1 + '\'' +
                ", maxCapacity=" + 12 +
                ", actualCapacity=" + 10 +
                ", maxChargingPlaces=" + 2 +
                ", actualChargingPlaces=" + 1 +
                '}';
        assertEquals(expResult,result);
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void getId() {
        int expResult = 1;
        assertEquals(expResult,park.getId());
    }

    @Test
    void setId() {
        park.setId(2);
        int result = park.getId();
        int expResult = 2;
        assertEquals(expResult,result);
    }

    @Test
    void getMaxCapacity() {
        int expResult = 12;
        assertEquals(expResult,park.getMaxCapacity());
    }

    @Test
    void setMaxCapacity() {
        park.setMaxCapacity(2);
        int result = park.getMaxCapacity();
        int expResult = 2;
        assertEquals(expResult,result);
    }

    @Test
    void getActualCapacity() {
        int expResult = 10;
        assertEquals(expResult,park.getActualCapacity());
    }

    @Test
    void setActualCapacity() {
        park.setActualCapacity(2);
        int result = park.getActualCapacity();
        int expResult = 2;
        assertEquals(expResult,result);
    }

    @Test
    void getMaxChargingPlaces() {
        int expResult = 2;
        assertEquals(expResult,park.getMaxChargingPlaces());
    }

    @Test
    void setMaxChargingPlaces() {
        park.setMaxChargingPlaces(1);
        int result = park.getMaxChargingPlaces();
        int expResult = 1;
        assertEquals(expResult,result);
    }

    @Test
    void getActualChargingPlaces() {
        int expResult = 1;
        assertEquals(expResult,park.getActualChargingPlaces());
    }

    @Test
    void setActualChargingPlaces() {
        park.setActualChargingPlaces(1);
        int result = park.getActualChargingPlaces();
        int expResult = 1;
        assertEquals(expResult,result);
    }
}