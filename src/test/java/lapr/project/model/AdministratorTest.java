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
}