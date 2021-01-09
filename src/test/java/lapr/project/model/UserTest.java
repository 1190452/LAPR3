package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private final User user;

    public UserTest() {
        user = new User("admin@isep.ipp.pt","qwerty","Administrator");
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
    void hasEmail() {
        assertTrue(user.hasEmail("admin@isep.ipp.pt"));
    }

    @Test
    void hasPassword() {
        assertTrue(user.hasPassword("qwerty"));
    }

    @Test
    void testEquals() {
    }

    @Test
    void testToString() {
        String result = user.toString();
        String expResult = "Administrator - admin@isep.ipp.pt";
        assertEquals(expResult,result);
    }

    @Test
    void save() {
    }

    @Test
    void getUser() {
    }
}