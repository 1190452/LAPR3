package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourierTest {

    private final Courier courier;
    private final Courier courier2;
    private final Courier courier3;
    private final Courier courier4;

    public CourierTest() {
        courier = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigDecimal("24586612344"),15,70,1);
        courier2 = new Courier(1, "Antonio");
        courier3 = new Courier("courier1@isep.ipp.pt", "qwerty","COURIER", "Name", 12313213, new BigDecimal("24586612343"), 60, 3);
        courier4 = new Courier("courier@isep.ipp.pt","André",122665789,
                new BigDecimal("24586612344"),70,1);
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
        int expResult = 122665789;
        assertEquals(expResult, courier.getNIF());
    }

    @Test
    void getNSS() {
        BigDecimal nss = courier.getNSS();
        BigDecimal expResut = new BigDecimal("24586612344");
        assertEquals(expResut, nss);
    }

    @Test
    void getMaxWeightCapacity() {
        double maxWeight = courier.getMaxWeightCapacity();
        double expResult = 15;
        assertEquals(expResult, maxWeight);
    }

    @Test
    void setName() {
        courier.setName("Patricia");
        String name = courier.getName();
        String expResult = "Patricia";
        assertEquals(expResult, name);
    }

    @Test
    void setNIF() {
        courier.setNIF(122665788);
        double nif = courier.getNIF();
        double expResult = 122665788;
        assertEquals(expResult, nif);
    }

    @Test
    void setNSS() {
        courier.setNSS(new BigDecimal("24586312144"));
        BigDecimal NSS = courier.getNSS();
        BigDecimal expResult = new BigDecimal("24586312144");
        assertEquals(expResult, NSS);
    }

    @Test
    void setMaxWeightCapacity() {
        courier.setMaxWeightCapacity(50);
        double maxWeight = courier.getMaxWeightCapacity();
        double expResult = 50;
        assertEquals(expResult, maxWeight);
    }

    /**
     * Test of equals method, of class Courier.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new Object();
        Courier instance = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigDecimal("24586612344"),15,70,1);
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Courier.
     */
    @Test
    public void test2Equals() {
        System.out.println("equals");
        Courier obj = null;
        Courier instance = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigDecimal("24586612344"),15,70,1);
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Courier.
     */
    @Test
    public void test3Equals() {
        System.out.println("equals");
        Courier instance = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigDecimal("24586612344"),15,70,1);
        boolean expResult = true;
        boolean result = instance.equals(instance);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Courier.
     */
    @Test
    public void test4Equals() {
        System.out.println("equals");
        Courier b = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigDecimal("24586612344"),15,70,1);
        Courier instance = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigDecimal("24586612344"),15,70,1);
        boolean expResult = true;
        boolean result = instance.equals(b);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Courier.
     */
    @Test
    public void test5Equals() {
        System.out.println("equals");
        Courier b = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigDecimal("24586612344"),15,70,1);
        Courier instance = new Courier(2,"courie2r@isep.ipp.pt","José",122665199,
                new BigDecimal("24586612344"),20,76,2);
        boolean expResult = false;
        boolean result = instance.equals(b);
        assertEquals(expResult, result);
    }

    @Test
    public void test6Equals() {
        Product p =  new Product(3,"benuron");
        Courier instance = new Courier(2,"courie2r@isep.ipp.pt","José",122665199,
                new BigDecimal("24586612344"),20,76,2);
        boolean expected = false;
        boolean result = instance.equals(p);
        assertEquals(expected, result);
    }

    @Test
    void testHashCode() {
        int hash = courier.hashCode();
        int expResult = 32;
        assertEquals(expResult, hash);
    }

    @Test
    void save() {
    }

    @Test
    void getCourier() {
    }

    @Test
    void setIdCourier() {
        courier.setIdCourier(4);
        int id = courier.getIdCourier();
        int expResult = 4;
        assertEquals(expResult,id);
    }

    @Test
    void getPharmacyID() {
        int id = courier.getPharmacyID();
        int expResult = 1;
        assertEquals(expResult,id);
    }

    @Test
    void setPharmacyID() {
        courier.setPharmacyID(6);
        int id = courier.getPharmacyID();
        int expId = 6;
        assertEquals(expId, id);
    }

    @Test
    void getEmail() {
        String email = courier.getEmail();
        String expEmail = "courier@isep.ipp.pt";
        assertEquals(expEmail, email);
    }

    @Test
    void setEmail() {
        courier.setEmail("c@gmail.com");
        String email = courier.getEmail();
        String expResult = "c@gmail.com";
        assertEquals(expResult, email);

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
                ", NSS=" + new BigDecimal("24586612344") +
                ", maxWeightCapacity=" + 15.0 +
                ", weight=" + 70.0 +
                ", pharmacyID=" + 1 +
                '}';
        assertEquals(expResult,result);
    }
}