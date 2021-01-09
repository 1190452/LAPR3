package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PharmacyTest {
private Pharmacy pharmacy;
private Pharmacy pharmacy2;

public PharmacyTest(){
    pharmacy = new Pharmacy(1, "Farmácia Tirori", 41.1111, -8.9999, "admin@isep.ipp.pt");
    pharmacy2 = new Pharmacy("Farmácia Zé", 41.4411, -8.77999, "adminaa@isep.ipp.pt");
}
    @Test
    public void getId() {
        int result = pharmacy.getId();
        int expected = 1;
        assertEquals(expected, result);
    }

    @Test
   public void getName() {
        String result = pharmacy.getName();
        String expected = "Farmácia Tirori";
        assertEquals(expected, result);
    }

    @Test
    public void setName() {
        pharmacy.setName("Farmácia Joaquim Tirori");
        String result = pharmacy.getName();
        String expected = "Farmácia Joaquim Tirori";
        assertEquals(expected, result);
    }

    @Test
    public void getLatitude() {
        double result = pharmacy.getLatitude();
        double expected = 41.1111;
        assertEquals(expected, result);
    }

    @Test
    public void setLatitude() {
        pharmacy.setLatitude(2.2222);
        double result = pharmacy.getLatitude();
        double expected = 2.2222;
        assertEquals(expected, result);
    }

    @Test
    public void getLongitude() {
        double result = pharmacy.getLongitude();
        double expected = -8.9999;
        assertEquals(expected, result);
    }

    @Test
    public void setLongitude() {
        pharmacy.setLongitude(3.3333);
        double result = pharmacy.getLongitude();
        double expected = 3.3333;
        assertEquals(expected, result);
    }

    @Test
    public void getEmailAdministrator() {
        String result = pharmacy.getEmailAdministrator();
        String expected = "admin@isep.ipp.pt";
        assertEquals(expected, result);
    }

    @Test
    public void setEmailAdministrator() {
    pharmacy.setEmailAdministrator("admini@isep.ipp.pt");
        String result = pharmacy.getEmailAdministrator();
        String expected = "admini@isep.ipp.pt";
        assertEquals(expected, result);
    }

    @Test
    public void testToString() {
    String result = pharmacy.toString();
    String expected = "Pharmacy{" +
            "id=" + 1 +
            ", name='" + "Farmácia Tirori" + '\'' +
            ", latitude=" + 41.1111 +
            ", longitude=" + -8.9999 +
            ", emailAdministrator='" + "admin@isep.ipp.pt" + '\'' +
            '}';
    }

    @Test
    public void test1Equals() {
        Pharmacy obj = null;
        Pharmacy instance = new Pharmacy(1, "Farmácia Tirori", 41.1111, -8.9999, "admin@isep.ipp.pt");
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test2Equals() {
        Object obj = null;
        Pharmacy instance = new Pharmacy(1, "Farmácia Tirori", 41.1111, -8.9999, "admin@isep.ipp.pt");
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test3Equals() {
        Pharmacy instance = new Pharmacy(1, "Farmácia Tirori", 41.1111, -8.9999, "admin@isep.ipp.pt");
        boolean expected = true;
        boolean result = instance.equals(instance);
        assertEquals(expected, result);
    }

    @Test
    public void test4Equals() {
        Pharmacy p = new Pharmacy(1, "Farmácia Bombeiros", 40.998, 8.9999, "admin@isep.ipp.pt");
        Pharmacy instance = new Pharmacy(1, "Farmácia Tirori", 41.1111, -8.9999, "admin@isep.ipp.pt");
        boolean expected = true;
        boolean result = instance.equals(p);
        assertEquals(expected, result);
    }

    @Test
    public void test5Equals() {
        Pharmacy p = new Pharmacy(1, "Farmácia Bombeiros", 40.998, 8.9999, "admin@isep.ipp.pt");
        Pharmacy instance = new Pharmacy(2, "Farmácia Tirori", 41.1111, -8.9999, "admin@isep.ipp.pt");
        boolean expected = false;
        boolean result = instance.equals(p);
        assertEquals(expected, result);
    }





    @Test
    public void testHashCode() {
        int result = pharmacy.hashCode();
        int expected = 32;
        assertEquals(expected, result);
    }
}