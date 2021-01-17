package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PharmacyTest {
private Pharmacy pharmacy;
private Pharmacy pharmacy2;

public PharmacyTest(){
    pharmacy = new Pharmacy(4,"farmacia", "Farmácia Tirori",232.019, 41.1111, -8.9999, "admin@isep.ipp.pt");
    pharmacy2 = new Pharmacy(3,"farmacia3", "Farmácia Tirori",232.019, 41.1111, -8.9999, "admin@isep.ipp.pt");
}
    @Test
    public void getId() {
        int result = pharmacy.getId();
        int expected = 4;
        assertEquals(expected, result);
    }

    @Test
   public void getName() {
        String result = pharmacy.getName();
        String expected = "farmacia";
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
        double expected = 232.019;
        assertEquals(expected, result);
    }

    @Test
    public void getEmail() {
        String result = pharmacy.getEmail();
        String expected = "Farmácia Tirori";
        assertEquals(expected, result);
    }

    @Test
    public void setEmail() {
        pharmacy.setEmail("pharm11@isep.ipp.pt");
        String result = pharmacy.getEmail();
        String expected = "pharm11@isep.ipp.pt";
        assertEquals(expected, result);
    }

    @Test
    public void setID() {
        pharmacy.setId(2);
        double result = pharmacy.getId();
        double expected = 2;
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
        double expected = 41.1111;
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
            "id=" + 4 +
            ", name='" + "farmacia" + '\'' +
            ", latitude=" + 232.019 +
            ", longitude=" + 41.1111 +
            ", altitude=" + -8.9999 +
            ", emailAdministrator='" + "admin@isep.ipp.pt" + '\'' +
            ", email='" + "Farmácia Tirori" + '\'' +
            '}';

    assertEquals(expected,result);
    }

    @Test
    public void test1Equals() {
        Pharmacy obj = null;
        Pharmacy instance = new Pharmacy(4,"farmacia", "Farmácia Tirori",232.019, 41.1111, -8.9999, "admin@isep.ipp.pt");
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test2Equals() {
        Object obj = null;
        Pharmacy instance = new Pharmacy(4,"farmacia", "Farmácia Tirori",232.019, 41.1111, -8.9999, "admin@isep.ipp.pt");
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test3Equals() {
        Pharmacy instance = new Pharmacy(4,"farmacia", "Farmácia Tirori",232.019, 41.1111, -8.9999, "admin@isep.ipp.pt");
        boolean expected = true;
        boolean result = instance.equals(instance);
        assertEquals(expected, result);
    }

    @Test
    public void test4Equals() {
        Pharmacy p = new Pharmacy(4,"farmacia", "Farmácia Tirori",232.019, 41.1111, -8.9999, "admin@isep.ipp.pt");
        Pharmacy instance = new Pharmacy(4,"farmacia", "Farmácia Tirori",232.019, 41.1111, -8.9999, "admin@isep.ipp.pt");
        boolean expected = true;
        boolean result = instance.equals(p);
        assertEquals(expected, result);
    }

    @Test
    public void test5Equals() {
        Pharmacy p = new Pharmacy(4,"farmacia", "Farmácia Tirori",232.019, 41.1111, -8.9999, "admin@isep.ipp.pt");
        Pharmacy instance = new Pharmacy(3,"farmacia", "Farmácia Tirori",232.019, 41.1111, -8.9999, "admin@isep.ipp.pt");
        boolean expected = false;
        boolean result = instance.equals(p);
        assertEquals(expected, result);
    }


    @Test
    public void test6Equals() {
        Product p =  new Product(3,"benuron");
        Pharmacy instance = new Pharmacy(4,"farmacia", "Farmácia Tirori",232.019, 41.1111, -8.9999, "admin@isep.ipp.pt");
        boolean expected = false;
        boolean result = instance.equals(p);
        assertEquals(expected, result);
    }


    @Test
    public void testHashCode() {
        int result = pharmacy.hashCode();
        int expected = -474614849;
        assertEquals(expected, result);
    }
}