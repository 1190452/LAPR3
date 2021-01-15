package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdministratorTest {

    private final Administrator administrator;

    public AdministratorTest() {
        administrator = new Administrator("adminTest@gmail.com","qwerty","Jorge");
    }

    @Test
    void getName() {
        String name = administrator.getName();
        String expResult = "Jorge";
        assertEquals(expResult,name);
    }

    @Test
    void setName() {
        administrator.setName("Miguel");
        String name = administrator.getName();
        String expResult = "Miguel";
        assertEquals(expResult,name);
    }

    @Test
    void testToString() {
        String result = administrator.toString();
        String expResult = "Administrator{" +
                "name='" + "Jorge" + '\'' +
                ", email='" + "adminTest@gmail.com" + '\'' +
                '}';
        assertEquals(expResult,result);
    }

    @Test
    void setPassword() {
        administrator.setPassword("teste");
        String result = administrator.getPassword();
        String expResult = "teste";
        assertEquals(expResult, result);
    }

    @Test
    public void test1Equals() {
        User obj = null;
        Administrator instance = new Administrator("adminTest@gmail.com","qwerty","Jorge");
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test2Equals() {
        Object obj = null;
        Administrator instance = new Administrator("admin@isep.ipp.pt","qwerty","Administrator");
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test3Equals() {
        Administrator instance = new Administrator("admin@isep.ipp.pt","qwerty","Administrator");
        boolean expected = true;
        boolean result = instance.equals(instance);
        assertEquals(expected, result);
    }

    @Test
    public void test4Equals() {
        Administrator u = new Administrator("admin@isep.ipp.pt","qwerty","Administrator");
        Administrator instance = new Administrator("admin@isep.ipp.pt","qwerty","Administrator");
        boolean expected = true;
        boolean result = instance.equals(u);
        assertEquals(expected, result);
    }

    @Test
    public void test5Equals() {
        Administrator u = new Administrator("admin@isep.ipp.pt","qwerty","Administrator");
        Administrator instance = new Administrator("ad@isep.ipp.pt","12345","Admin");
        boolean expected = false;
        boolean result = instance.equals(u);
        assertEquals(expected, result);
    }


    @Test
    void testHashCode() {
        int expResult = 202849648;
        int result = administrator.hashCode();
        assertEquals(expResult,result);
    }
}