package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElectricScooterTest {

    private final EletricScooter scooter;
    private final EletricScooter scooter2;
    private final EletricScooter scooter3;

    ElectricScooterTest() {
        scooter = new EletricScooter(2,400,350,1,500,8.0,5000.0,430,4);
        scooter2 = new EletricScooter(420,100,600,8,120,5000.0,4);
        scooter3 = new EletricScooter(9);

    }


    @Test
    void getId() {
        int id = scooter.getId();
        int expId = 2;
        assertEquals(expId,id);

    }

    @Test
    void getMaxBattery() {
        double maxBattery = scooter.getMaxBattery();
        double expResult = 400;
        assertEquals(expResult, maxBattery);
    }

    @Test
    void setMaxBattery() {
        scooter.setMaxBattery(0);
        double id = scooter.getMaxBattery();
        double expResult = 0;
        assertEquals(expResult, id);
    }

    @Test
    void getActualBattery() {
        double actualBattery = scooter2.getActualBattery();
        double expResult = 100;
        assertEquals(expResult, actualBattery);
    }

    @Test
    void setActualBattery() {
        scooter.setActualBattery(10);
        double maxBattery = scooter.getActualBattery();
        double expResult = 10;
        assertEquals(expResult,maxBattery);
    }

    @Test
    void getStatus() {
        int status = scooter.getStatus();
        int expResult = 1;
        assertEquals(expResult, status);
    }

    @Test
    void getIdPharmacy() {
        int idPharmacy = scooter.getIdPharmacy();
        int expResult = 4;
        assertEquals(expResult, idPharmacy);
    }

    @Test
    void setIdPharmacy() {
        scooter.setIdPharmacy(15);
        int idPharmacy = scooter.getIdPharmacy();
        int expResult = 15;
        assertEquals(expResult, idPharmacy);
    }

    @Test
    void setStatus() {
        scooter.setStatus(2);
        int status = scooter.getStatus();
        int expResult = 2;
        assertEquals(expResult, status);
    }

    @Test
    void getEnginePower() {
        double engineP = scooter.getEnginePower();
        double expResult = 500.0;
        assertEquals(expResult, engineP);
    }

    @Test
    void setEnginePower() {
        scooter.setEnginePower(600);
        double enginePower = scooter.getEnginePower();
        int expResult = 600;
        assertEquals(expResult, enginePower);
    }

    @Test
    void getAh_battery() {
        double ah_battery = scooter.getAh_battery();
        double expResult = 8.0;
        assertEquals(expResult, ah_battery);
    }

    @Test
    void setAh_battery() {
        scooter.setAh_battery(280);
        double battery = scooter.getAh_battery();
        int expResult = 280;
        assertEquals(expResult, battery);
    }

    @Test
    void getV_battery() {
        double v_battery = scooter.getV_battery();
        double expResult = 5000.0;
        assertEquals(expResult, v_battery);
    }

    @Test
    void setV_battery() {
        scooter.setV_battery(20);
        double v_battery = scooter.getV_battery();
        double expResult = 20;
        assertEquals(expResult, v_battery);
    }

    @Test
    void getWeight() {
        double weight = scooter.getWeight();
        double expResult = 430;
        assertEquals(expResult, weight);
    }

    @Test
    void setWeight() {
        scooter.setWeight(2500);
        double weight = scooter.getWeight();
        int expResult = 2500;
        assertEquals(expResult, weight);
    }

    @Test
    void getBatteryPercentage() {
        double percentage = scooter.getBatteryPercentage();
        double expResult = 88.0;
        assertEquals(expResult, percentage);
    }

    @Test
    public void test1Equals() {
        EletricScooter obj = null;
        EletricScooter instance = new EletricScooter(2,400,350,1,500,8.0,5000.0,430,4);
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test2Equals() {
        Object obj = null;
        EletricScooter instance = new EletricScooter(2,400,350,1,500,8.0,5000.0,430,4);
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test3Equals() {
        EletricScooter instance = new EletricScooter(2,400,350,1,500,8.0,5000.0,430,4);
        boolean expected = true;
        boolean result = instance.equals(instance);
        assertEquals(expected, result);
    }

    @Test
    public void test4Equals() {
        EletricScooter p = new EletricScooter(2,400,350,1,500,8.0,5000.0,430,4);
        EletricScooter instance = new EletricScooter(2,400,350,1,500,8.0,5000.0,430,4);
        boolean expected = true;
        boolean result = instance.equals(p);
        assertEquals(expected, result);
    }

    @Test
    public void test5Equals() {
        EletricScooter p = new EletricScooter(2,400,350,1,500,8.0,5000.0,430,4);
        EletricScooter instance = new EletricScooter(19,400,350,1,500,8.0,5000.0,430,4);
        boolean expected = false;
        boolean result = instance.equals(p);
        assertEquals(expected, result);
    }

    @Test
    void testHashCode() {
        int hash = scooter.hashCode();
        int expResult = 33;
        assertEquals(expResult,hash);
    }

    @Test
    void testToString() {
        String expResult = "EletricScooter{" +
                "id=" + 2 +
                ", maxBattery=" + 400.0 +
                ", actualBattery=" + 350.0 +
                ", status=" + 1 +
                ", enginePower=" + 500.0 +
                ", ah_battery=" + 8.0 +
                ", v_battery=" + 5000.0 +
                ", weight=" + 430.0 +
                ", idPharmacy=" + 4 +
                '}';
        String result = scooter.toString();
        assertEquals(expResult,result);
    }
}