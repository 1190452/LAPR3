package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    private final Address address;

    public AddressTest() {
        address = new Address(34, 45,"rua xpto");
    }

    @Test
    void getLatitude() {
        double expResult = 34;
        assertEquals(expResult, address.getLatitude());
    }

    @Test
    void setLatitude() {
        address.setLatitude(10);
        double result = address.getLatitude();
        double expResult = 10;
        assertEquals(expResult,result);
    }

    @Test
    void getLongitude() {
        double expResult = 45;
        assertEquals(expResult, address.getLongitude());
    }

    @Test
    void setLongitude() {
        address.setLongitude(5);
        double result = address.getLongitude();
        double expResult  = 5;
        assertEquals(expResult,result);
    }

    @Test
    void getStreet() {
        String expResult = "rua xpto";
        assertEquals(expResult, address.getStreet());
    }

    @Test
    void setStreet() {
        address.setStreet("rua de cima");
        String result = address.getStreet();
        String expResult = "rua de cima";
        assertEquals(expResult,result);
    }
}