package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathTest {

    Path path;

    public PathTest() {
        this.path = new Path( 45,45,23,33,332,12,5,6,8);
    }


    @Test
    void getLatitudeA1() {
        Path expResult = new Path( 45,45,23,33,332,12,5,6,8);
        double result = path.getLatitudeFrom();
        assertEquals(expResult.getLatitudeFrom(), result);
    }

    @Test
    void setLatitudeA1() {
        path.setLatitudeFrom(45);
        double result = path.getLatitudeFrom();
        assertEquals(45, result);
    }

    @Test
    void getLatitudeA2() {
        Path expResult = new Path( 45,45,23,33,332,12,5,6,8);
        double result = path.getLatitudeTo();
        assertEquals(expResult.getLatitudeTo(), result);
    }

    @Test
    void setLatitudeA2() {
        path.setLatitudeTo(4345);
        double result = path.getLatitudeTo();
        assertEquals(4345, result);
    }

    @Test
    void getLongitudeA1() {
        Path expResult = new Path( 45,45,23,33,332,12,5,6,8);
        double result = path.getLongitudeFrom();
        assertEquals(expResult.getLongitudeFrom(), result);
    }

    @Test
    void setLongitudeA1() {
        path.setLongitudeFrom(23);
        double result = path.getLongitudeFrom();
        assertEquals(23, result);
    }

    @Test
    void getLongitudeA2() {
        Path expResult = new Path( 45,45,23,33,332,12,5,6,8);
        double result = path.getLongitudeTo();
        assertEquals(expResult.getLongitudeTo(), result);
    }

    @Test
    void setLongitudeA2() {
        path.setLongitudeTo(-5665);
        double result = path.getLongitudeTo();
        assertEquals(-5665, result);
    }

    @Test
    void getAltitudeA1() {
        Path expResult = new Path( 45,45,23,33,332,12,5,6,8);
        double result = path.getAltitudeFrom();
        assertEquals(expResult.getAltitudeFrom(), result);
    }

    @Test
    void setAltitudeA1() {
        path.setAltitudeFrom(334);
        double result = path.getAltitudeFrom();
        assertEquals(334, result);
    }

    @Test
    void getAltitudeA2() {
        Path expResult = new Path( 45,45,23,33,332,12,5,6,8);
        double result = path.getAltitudeTo();
        assertEquals(expResult.getAltitudeTo(), result);
    }

    @Test
    void setAltitudeA2() {
        path.setAltitudeTo(-4);
        double result = path.getAltitudeTo();
        assertEquals(-4, result);
    }

    @Test
    void getRoadRollingResistance() {
        double resistance = path.getRoadRollingResistance();
        assertEquals(5, resistance);
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
        assertEquals(6, windSpeed);
    }

    @Test
    void setWindspeed() {
        path.setWindspeed(40);
        double result = path.getWindspeed();
        assertEquals(40, result);
    }

    @Test
    void getWindDirection() {
        assertEquals(8, path.getWindDirection());
    }

    @Test
    void setWindDirection() {
        path.setWindDirection(10);
        assertEquals(10, path.getWindDirection());
    }

    @Test
    void testToString() {
        String expResult = "Path{" +
                "latitude_a1=" + 45.0 +
                ", longitude_a1=" + 45.0 +
                ", altitude_a1=" + 23.0 +
                ", latitude_a2=" + 33.0 +
                ", longitude_a2=" + 332.0 +
                ", altitude_a2=" + 12.0 +
                ", roadRollingResistance=" + 5.0 +
                ", windspeed=" + 6.0 +
                ", windDirection=" + 8.0 +
                '}';
        String result = path.toString();
        assertEquals(expResult,result);
    }

    @Test
    public void test1Equals() {
        Path obj = null;
        Path instance = new Path( 45,45,23,33,332,12,5,6,8);
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test2Equals() {
        Object obj = null;
        Path instance = new Path( 45,45,23,33,332,12,5,6,8);
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test3Equals() {
        Path instance =  new Path( 45,45,23,33,332,12,5,6,8);
        boolean expected = true;
        boolean result = instance.equals(instance);
        assertEquals(expected, result);
    }

    @Test
    public void test4Equals() {
        Path p = new Path( 45,45,23,33,332,12,5,6,8);
        Path instance = new Path( 45,45,23,33,332,12,5,6,8);
        boolean expected = true;
        boolean result = instance.equals(p);
        assertEquals(expected, result);
    }

    @Test
    public void test5Equals() {
        Path p = new Path( 45,57,23,33,332,12,5,6,8);
        Path instance = new Path( 4,57,23,33,332,12,5,6,8);
        boolean expected = false;
        boolean result = instance.equals(p);
        assertEquals(expected, result);
    }

    @Test
    public void test6Equals() {
        Path p = new Path( 45,45,77,33,332,12,5,6,8);
        Path instance = new Path( 45,45,77,33,12,12,5,6,8);
        boolean expected = false;
        boolean result = instance.equals(p);
        assertEquals(expected, result);
    }

    @Test
    void testHashCode() {
        int expResult = -1517768417;
        int result = path.hashCode();
        assertEquals(expResult,result);
    }
}