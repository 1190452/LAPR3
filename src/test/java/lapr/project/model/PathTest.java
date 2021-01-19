package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathTest {

    Path path;

    public PathTest() {
        this.path = new Path(new Address(34, 45,"rua xpto", 2, "4500", "espinho"), new Address(50, 100,"rua xpto", 2, "4500", "espinho",40),28, 210, 12);
    }


    @Test
    void getA1() {
        Address a1 = new Address(34, 45,"rua xpto", 2, "4500", "espinho");
        Address result = path.getA1();
        assertEquals(a1, result);
    }

    @Test
    void setA1() {
        path.setA1(new Address(3400, 413235,"rua xpto", 2, "4500", "espinho"));
        Address expResult = new Address(3400, 413235,"rua xpto", 2, "4500", "espinho");
        Address result = path.getA1();
        assertEquals(expResult, result);
    }

    @Test
    void getA2() {
        Address a2 = new Address(50, 100,"rua xpto", 2, "4500", "espinho",40);
        Address result = path.getA2();
        assertEquals(a2, result);
    }

    @Test
    void setA2() {
        path.setA2(new Address(50, 100,"rua xpto", 2, "4500", "espinho",40));
        Address expResult = path.getA2();
        Address result = new Address(50, 100,"rua xpto", 2, "4500", "espinho",40);
        assertEquals(expResult, result);
    }

    @Test
    void getRoadRollingResistance() {
        double resistance = path.getRoadRollingResistance();
        assertEquals(28, resistance);
    }

    @Test
    void setRoadRollingResistance() {
        path.setRoadRollingResistance(30);
        double result = path.getRoadRollingResistance();
        assertEquals(30, result);
    }

    @Test
    void getWindspeed() {
        double windSpeed = path.getWindspeed();
        assertEquals(210.0, windSpeed);
    }

    @Test
    void setWindspeed() {
        path.setWindspeed(40);
        double result = path.getWindspeed();
        assertEquals(40, result);
    }
}