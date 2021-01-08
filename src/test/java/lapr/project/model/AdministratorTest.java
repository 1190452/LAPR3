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
    void getPassword() {
        String pwd = administrator.getPassword();
        String expResult = "qwerty";
        assertEquals(expResult,pwd);
    }

    @Test
    void setPassword() {
        administrator.setPassword("teste123");
        String pwd = administrator.getPassword();
        String expResult = "teste123";
        assertEquals(expResult,pwd);
    }

    @Test
    void getRole() {
        String role = administrator.getRole();
        String expResult = "ADMINISTRATOR";
        assertEquals(expResult,role);
    }


    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
        int hashCode = administrator.hashCode();
        int expResult = 242108138;
        assertEquals(expResult,hashCode);

    }

    @Test
    void testToString() {
    }
}