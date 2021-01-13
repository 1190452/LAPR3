package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private final User user;
    private final User user2;
    private final User user3;

    public UserTest() {

        user = new User("admin@isep.ipp.pt","qwerty","Administrator");
        user2 = new User("admin@isep.ipp.pt","Administrator");
        user3 = new User( new User("admin@isep.ipp.pt","qwerty","Administrator"));
    }

    @Test
    void getEmail() {
        String expResult = "admin@isep.ipp.pt";
        assertEquals(expResult,user.getEmail());
    }

    @Test
    void getRole() {
        String expResult = "qwerty";
        assertEquals(expResult,user.getPassword());
    }

    @Test
    void getPassword() {
        String expResult = "Administrator";
        assertEquals(expResult, user.getRole());
    }

    @Test
    void setEmail() {
        user.setEmail("client@isep.ipp.pt");
        String result = user.getEmail();
        String expResult = "client@isep.ipp.pt";
        assertEquals(expResult,result);
    }

    @Test
    void setPassword() {
        user.setPassword("123456");
        String result = user.getPassword();
        String expResult = "123456";
        assertEquals(expResult,result);
    }

    @Test
    void setRole() {
        user.setRole("client");
        String result = user.getRole();
        String expResult = "client";
        assertEquals(expResult,result);
    }


    @Test
    public void test1Equals() {
        User obj = null;
        User instance = new User("admin@isep.ipp.pt","qwerty","Administrator");
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test2Equals() {
        Object obj = null;
        User instance = new User("admin@isep.ipp.pt","qwerty","Administrator");
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test3Equals() {
        User instance = new User("admin@isep.ipp.pt","qwerty","Administrator");
        boolean expected = true;
        boolean result = instance.equals(instance);
        assertEquals(expected, result);
    }

    @Test
    public void test4Equals() {
        User u = new User("admin@isep.ipp.pt","qwerty","Administrator");
        User instance = new User ("admin@isep.ipp.pt","qwerty","Administrator");
        boolean expected = true;
        boolean result = instance.equals(u);
        assertEquals(expected, result);
    }

    @Test
    public void test5Equals() {
        User u = new User("admin@isep.ipp.pt","qwerty","Administrator");
        User instance = new User("ad@isep.ipp.pt","12345","Admin");
        boolean expected = false;
        boolean result = instance.equals(u);
        assertEquals(expected, result);
    }

    @Test
    public void test6Equals() {
        Product p = new Product(1, "Vacina", "Cura tudo", 3.0, 1.0, 1, 5);
        User instance = new User("ad@isep.ipp.pt","12345","Admin");
        boolean expected = false;
        boolean result = instance.equals(p);
        assertEquals(expected, result);
    }

    @Test
    void testToString() {
        String result = user.toString();
        String expResult = "Administrator - admin@isep.ipp.pt";
        assertEquals(expResult,result);
    }

    @Test
    void testHashCode() {
        int result = user.hashCode();
        int expected = -139864240;
        assertEquals(expected, result);
    }
}