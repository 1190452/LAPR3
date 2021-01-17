package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressTest {

    private final Address address;

    public AddressTest() {

        address = new Address(34, 45,"rua xpto", 2, "4500", "espinho");
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

    @Test
    void setZipCode() {
        address.setZipCode("3500");
        String result = address.getZipCode();
        String expResult = "3500";
        assertEquals(expResult, result);
    }

    @Test
    void setLocality() {
        address.setLocality("Porto");
        String result = address.getLocality();
        String expResult = "Porto";
        assertEquals(expResult, result);
    }

    @Test
    void setDoorNumber() {
        address.setDoorNumber(41);
        int result = address.getDoorNumber();
        int expResult = 41;
        assertEquals(expResult, result);
    }


    @Test
    void testToString() {
        String result = address.toString();
        String expResult = "Address{" +
                "latitude=" + 34.0 +
                ", longitude=" + 45.0 +
                ", street='" + "rua xpto" + '\'' +
                ", doorNumber=" + 2 +
                ", zipCode='" + 4500 + '\'' +
                ", locality='" + "espinho" + '\'' +
                ", altitude='" + 0.0 + '\'' +
                '}';
        assertEquals(expResult,result);
    }

    @Test
    public void test1Equals() {
        Address obj = null;
        Address instance = new Address(34, 45,"rua xpto", 2, "4500", "espinho");
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test2Equals() {
        Object obj = null;
        Address instance = new Address(34, 45,"rua xpto", 2, "4500", "espinho");
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test3Equals() {
        Address instance = new Address(34, 45,"rua xpto", 2, "4500", "espinho");
        boolean expected = true;
        boolean result = instance.equals(instance);
        assertEquals(expected, result);
    }

    @Test
    public void test4Equals() {
        Address instance = new Address(3345434, 4545,"rua xpto", 2, "4500", "espinho");
        Address ad = new Address(34, 45,"rua xpto", 2, "4500", "espinho");
        boolean expected = false;
        boolean result = instance.equals(ad);
        assertEquals(expected, result);
    }

    @Test
    public void test5Equals() {
        Address instance = new Address(34, 45445,"rua xpto", 2, "4500", "espinho");
        Address ad = new Address(34, 45,"rua xpto", 2, "4500", "espinho");
        boolean expected = false;
        boolean result = instance.equals(ad);
        assertEquals(expected, result);
    }


    @Test
    public void test6Equals() {
        Product p =  new Product(3,"benuron");
        Address instance = new Address(34, 45445,"rua xpto", 2, "4500", "espinho");
        boolean expected = false;
        boolean result = instance.equals(p);
        assertEquals(expected, result);
    }

    @Test
    public void test7Equals() {
        Address instance = new Address(34, 4545,"rua xpto", 2, "4500", "espinho");
        Address ad = new Address(34, 45,"rua xpto", 2, "4500", "espinho");
        boolean expected = false;
        boolean result = instance.equals(ad);
        assertEquals(expected, result);
    }

    @Test
    public void test8Equals() {
        Address instance = new Address(34, 45,"rua fdsf", 2, "43434", "Lobao");
        Address ad = new Address(34, 45,"rua xpto", 2, "4500", "espinho");
        boolean expected = true;
        boolean result = instance.equals(ad);
        assertEquals(expected, result);
    }


    @Test
    public void testHashCode() {
        int result = address.hashCode();
        int expected = 136676289;
        assertEquals(expected, result);
    }

}