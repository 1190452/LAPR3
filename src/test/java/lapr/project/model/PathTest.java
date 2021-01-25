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
        double result = path.getLatitude_a1();
        assertEquals(expResult.getLatitude_a1(), result);
    }

    @Test
    void setLatitudeA1() {
        path.setLatitude_a1(45);
        double result = path.getLatitude_a1();
        assertEquals(45, result);
    }

    @Test
    void getLatitudeA2() {
        Path expResult = new Path( 45,45,23,33,332,12,5,6,8);
        double result = path.getLatitude_a2();
        assertEquals(expResult.getLatitude_a2(), result);
    }

    @Test
    void setLatitudeA2() {
        path.setLatitude_a2(4345);
        double result = path.getLatitude_a2();
        assertEquals(4345, result);
    }

    @Test
    void getLongitudeA1() {
        Path expResult = new Path( 45,45,23,33,332,12,5,6,8);
        double result = path.getLongitude_a1();
        assertEquals(expResult.getLongitude_a1(), result);
    }

    @Test
    void setLongitudeA1() {
        path.setLongitude_a1(23);
        double result = path.getLongitude_a1();
        assertEquals(23, result);
    }

    @Test
    void getLongitudeA2() {
        Path expResult = new Path( 45,45,23,33,332,12,5,6,8);
        double result = path.getLongitude_a2();
        assertEquals(expResult.getLongitude_a2(), result);
    }

    @Test
    void setLongitudeA2() {
        path.setLongitude_a2(-5665);
        double result = path.getLongitude_a2();
        assertEquals(-5665, result);
    }

    @Test
    void getAltitudeA1() {
        Path expResult = new Path( 45,45,23,33,332,12,5,6,8);
        double result = path.getAltitude_a1();
        assertEquals(expResult.getAltitude_a1(), result);
    }

    @Test
    void setAltitudeA1() {
        path.setAltitude_a1(334);
        double result = path.getAltitude_a1();
        assertEquals(334, result);
    }

    @Test
    void getAltitudeA2() {
        Path expResult = new Path( 45,45,23,33,332,12,5,6,8);
        double result = path.getAltitude_a2();
        assertEquals(expResult.getAltitude_a2(), result);
    }

    @Test
    void setAltitudeA2() {
        path.setAltitude_a2(-4);
        double result = path.getAltitude_a2();
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