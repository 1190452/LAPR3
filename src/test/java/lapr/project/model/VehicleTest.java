package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VehicleTest {

    private final Vehicle vehicle;
    private final Vehicle vehicle2;
    private final Vehicle vehicle3;

    VehicleTest() {
        vehicle = new Vehicle(1,"AH-87-LK",400,350,0,1,500,8.0,5000.0,430,4, 1);
        vehicle2 = new Vehicle("AH-87-LK",400,350,500,8.0,5000.0,430,4, 2);
        vehicle3 = new Vehicle("AH-87-LK");
    }

    @Test
    void getLicencePlate() {
        String id = vehicle.getLicensePlate();
        String expResult = "AH-87-LK";
        assertEquals(expResult,id);

    }

    @Test
    void getMaxBattery() {
        double maxBattery = vehicle.getMaxBattery();
        double expResult = 400;
        assertEquals(expResult, maxBattery);
    }

    @Test
    void setMaxBattery() {
        vehicle.setMaxBattery(0);
        double id = vehicle.getMaxBattery();
        double expResult = 0;
        assertEquals(expResult, id);
    }

    @Test
    void getActualBattery() {
        double actualBattery = vehicle.getActualBattery();
        double expResult = 350;
        assertEquals(expResult, actualBattery);
    }

    @Test
    void setActualBattery() {
        vehicle.setActualBattery(10);
        double maxBattery = vehicle.getActualBattery();
        double expResult = 10;
        assertEquals(expResult,maxBattery);
    }

    @Test
    void getStatus() {
        int status = vehicle.getStatus();
        int expResult = 0;
        assertEquals(expResult, status);
    }

    @Test
    void getIdPharmacy() {
        int idPharmacy = vehicle.getIdPharmacy();
        int expResult = 4;
        assertEquals(expResult, idPharmacy);
    }

    @Test
    void setIdPharmacy() {
        vehicle.setIdPharmacy(15);
        int idPharmacy = vehicle.getIdPharmacy();
        int expResult = 15;
        assertEquals(expResult, idPharmacy);
    }

    @Test
    void setStatus() {
        vehicle.setStatus(2);
        int status = vehicle.getStatus();
        int expResult = 2;
        assertEquals(expResult, status);
    }

    @Test
    void getEnginePower() {
        double engineP = vehicle.getEnginePower();
        double expResult = 500.0;
        assertEquals(expResult, engineP);
    }

    @Test
    void setEnginePower() {
        vehicle.setEnginePower(600);
        double enginePower = vehicle.getEnginePower();
        int expResult = 600;
        assertEquals(expResult, enginePower);
    }

    @Test
    void getAh_battery() {
        double ah_battery = vehicle.getAh_battery();
        double expResult = 8.0;
        assertEquals(expResult, ah_battery);
    }

    @Test
    void setAh_battery() {
        vehicle.setAh_battery(280);
        double battery = vehicle.getAh_battery();
        int expResult = 280;
        assertEquals(expResult, battery);
    }

    @Test
    void getV_battery() {
        double v_battery = vehicle.getV_battery();
        double expResult = 5000.0;
        assertEquals(expResult, v_battery);
    }

    @Test
    void setV_battery() {
        vehicle.setV_battery(20);
        double v_battery = vehicle.getV_battery();
        double expResult = 20;
        assertEquals(expResult, v_battery);
    }

    @Test
    void getWeight() {
        double weight = vehicle.getWeight();
        double expResult = 430;
        assertEquals(expResult, weight);
    }

    @Test
    void setWeight() {
        vehicle.setWeight(2500);
        double weight = vehicle.getWeight();
        int expResult = 2500;
        assertEquals(expResult, weight);
    }

    @Test
    void getBatteryPercentage() {
        double percentage = vehicle.getBatteryPercentage();
        double expResult = 88.0;
        assertEquals(expResult, percentage);
    }

    @Test
    void getIsCharging() {
        int isCharging = vehicle.getIsCharging();
        int expResult = 1;
        assertEquals(expResult, isCharging);
    }

    @Test
    void setIsCharging() {
        vehicle.setIsCharging(1);
        int result = vehicle.getIsCharging();
        int expResult = 1;
        assertEquals(expResult, result);
    }

    @Test
    void setTypeVehicle() {
        vehicle.setTypeVehicle(2);
        int result = vehicle.getTypeVehicle();
        assertEquals(2, result);
    }

    @Test
    public void test1Equals() {
        Vehicle obj = null;
        Vehicle instance = new Vehicle(1,"AH-87-LK",400,350,0,1,500,8.0,5000.0,430,4, 1);
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test2Equals() {
        Object obj = null;
        Vehicle instance = new Vehicle(1,"AH-87-LK",400,350,0,1,500,8.0,5000.0,430,4, 1);
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test3Equals() {
        Vehicle instance = new Vehicle(1,"AH-87-LK",400,350,0,1,500,8.0,5000.0,430,4, 1);
        boolean expected = true;
        boolean result = instance.equals(instance);
        assertEquals(expected, result);
    }

    @Test
    public void test4Equals() {
        Vehicle p = new Vehicle(1,"AH-87-LK",400,350,0,1,500,8.0,5000.0,430,4, 1);
        Vehicle instance = new Vehicle(1,"AH-87-LK",400,350,0,1,500,8.0,5000.0,430,4, 1);
        boolean expected = true;
        boolean result = instance.equals(p);
        assertEquals(expected, result);
    }

    @Test
    public void test5Equals() {
        Vehicle p = new Vehicle(1,"AH-87-LK",400,350,0,1,500,8.0,5000.0,430,4, 1);
        Vehicle instance = new Vehicle(1,"AH-57-LK",400,350,0,1,500,8.0,5000.0,430,4, 1);
        boolean expected = false;
        boolean result = instance.equals(p);
        assertEquals(expected, result);
    }

    @Test
    public void test6Equals() {
        Product p =  new Product(3,"benuron");
        Vehicle instance = new Vehicle(1,"AH-87-LK",400,350,0,1,500,8.0,5000.0,430,4, 1);
        boolean expected = false;
        boolean result = instance.equals(p);
        assertEquals(expected, result);
    }

    @Test
    void testHashCode() {
        int hash = vehicle.hashCode();
        int expResult = -1863976442;
        assertEquals(expResult,hash);
    }

    @Test
    void testToString() {
        String expResult = "Vehicle{" +
                "id=" + 1 +
                ", licensePlate=" + "AH-87-LK" +
                ", maxBattery=" + 400.0 +
                ", actualBattery=" + 350.0 +
                ", status=" + 0 +
                ", isCharging=" + 1 +
                ", enginePower=" + 500.0 +
                ", ah_battery=" + 8.0 +
                ", v_battery=" + 5000.0 +
                ", weight=" + 430.0 +
                ", idPharmacy=" + 4 +
                ", typeVehicle=" + 1 +
                '}';
        String result = vehicle.toString();
        assertEquals(expResult,result);
    }

    @Test
    void getId() {
        int expResult = 1;
        assertEquals(expResult,vehicle.getId());
    }

    @Test
    void setId() {
        vehicle.setId(2);
        int result = vehicle.getId();
        assertEquals(2, result);
    }
}