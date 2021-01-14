package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class ParkTest {

    private final Park park;
    private final Park park2;
    ParkTest() {
        park = new Park(1,12,10,2,1,25,2,1);
        park2 = new Park(10,5,1,4,2);
    }

    @Test
    void testToString() {
        String result = park.toString();
        String expResult = "Park{" +
                "id=" + 1 +
                ", maxCapacity=" + 12 +
                ", actualCapacity=" + 10 +
                ", maxChargingPlaces=" + 2 +
                ", actualChargingPlaces=" + 1 +
                ", power=" + 25 +
                ", pharmacyID=" + 2 +
                ", ParktypeID=" + 1+
                "}";
        assertEquals(expResult,result);
    }

    /**
     * Test of equals method, of class Park.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new Object();
        Park instance = new Park(2,10,8,5,3,1,4,2);
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Park.
     */
    @Test
    public void test2Equals() {
        System.out.println("equals");
        Courier obj = null;
        Park instance = new Park(2,10,8,5,3,1,4,2);
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Park.
     */
    @Test
    public void test3Equals() {
        System.out.println("equals");
        Park instance = new Park(2,10,8,5,3,1,4,2);
        boolean expResult = true;
        boolean result = instance.equals(instance);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Park.
     */
    @Test
    public void test4Equals() {
        System.out.println("equals");
        Park p = new Park(2,10,8,5,3,1,4,2);
        Park instance = new Park(2,10,8,5,3,1,4,2);
        boolean expResult = true;
        boolean result = instance.equals(p);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Park.
     */
    @Test
    public void test5Equals() {
        System.out.println("equals");
        Park p = new Park(2,10,8,5,3,1,2,2);
        Park instance = new Park(2,10,8,5,3,1,4,2);
        boolean expResult = false;
        boolean result = instance.equals(p);
        assertEquals(expResult, result);
    }
    /**
     * Test of equals method, of class Park.
     */
    @Test
    public void test6Equals() {
        System.out.println("equals");
        Park p = new Park(2,10,8,5,3,1,2,2);
        Park instance = new Park(5,10,8,5,3,1,2,2);
        boolean expResult = false;
        boolean result = instance.equals(p);
        assertEquals(expResult, result);
    }

    @Test
    void testHashCode() {
        int hash = park.hashCode();
        int expResult = 994;
        assertEquals(expResult, hash);
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
        int expResult = 5;
        assertEquals(expResult,park2.getMaxChargingPlaces());
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

    @Test
    void getPower() {
        int expResult = 25;
        assertEquals(expResult,park.getPower());
    }

    @Test
    void setPower() {
        park.setPower(400);
        double result = park.getPower();
        double expResult = 400;
        assertEquals(expResult,result);
    }

    @Test
    void getPharmacyID() {
        int id = park.getPharmacyID();
        int expResult = 2;
        assertEquals(expResult, id);
    }

    @Test
    void setPharmacyID() {
        park.setPharmacyID(7);
        int result = park.getPharmacyID();
        int expResult = 7;
        assertEquals(expResult, result);
    }

    @Test
    void getIdParktype() {
        int result = park.getIdParktype();
        assertEquals(1,result);
    }

    @Test
    void setIdParktype() {
        park.setIdParktype(2);
        int result = park.getIdParktype();
        assertEquals(2,result);
    }
}