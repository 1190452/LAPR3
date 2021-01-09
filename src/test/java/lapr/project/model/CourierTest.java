package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class CourierTest {

    private final Courier courier;

    public CourierTest() {
        courier = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigInteger("24586612344").intValue(),15,70,1);
    }

    @Test
    void setWeight() {
        courier.setWeight(60);
        double result = courier.getWeight();
        double expResult = 60;
        assertEquals(expResult,result);

    }

    @Test
    void getIdCourier() {
        int expResult = 1;
        assertEquals(expResult, courier.getIdCourier());
    }

    @Test
    void getWeight() {
        double expResult = 70;
        assertEquals(expResult, courier.getWeight());
    }

    @Test
    void getName() {
        String expResult = "André";
        assertEquals(expResult, courier.getName());
    }

    @Test
    void getNIF() {
        double expResult = 122665789;
        assertEquals(expResult, courier.getNIF());
    }

    @Test
    void getNSS() {
        //double
    }

    @Test
    void getMaxWeightCapacity() {
    }

    @Test
    void setName() {
    }

    @Test
    void setNIF() {
    }

    @Test
    void setNSS() {
    }

    @Test
    void setMaxWeightCapacity() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void save() {
    }

    @Test
    void getCourier() {
    }

    @Test
    void setIdCourier() {
    }

    @Test
    void getPharmacyID() {
    }

    @Test
    void setPharmacyID() {
    }

    @Test
    void getEmail() {
    }

    @Test
    void setEmail() {
    }

    @Test
    void delete() {
    }

    @Test
    void testToString() {
        String result = courier.toString();
        String expResult = "Courier{" +
                "idCourier=" + 1 +
                ", name='" + "André" + '\'' +
                ", email='" + "courier@isep.ipp.pt" + '\'' +
                ", NIF=" + 122665789 +
                ", NSS=" + new BigInteger("24586612344").intValue() +
                ", maxWeightCapacity=" + 15 +
                ", weight=" + 70 +
                ", pharmacyID=" + 1 +
                '}';
    }
}