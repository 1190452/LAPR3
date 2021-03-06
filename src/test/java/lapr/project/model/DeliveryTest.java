package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeliveryTest {

    private final Delivery delivery;
    private final Delivery delivery2;

    public DeliveryTest() {
        delivery = new Delivery(1,25,30,40);
        delivery2 = new Delivery(25,30,40,1,"AK-LA-09");
    }

    @Test
    void testToString() {
        String result = delivery.toString();
        String expResult =
                "Delivery{" +
                        "id=" + 1 +
                        ", necessaryEnergy=" + 25.0 +
                        ", distance=" + 30.0 +
                        ", weight=" + 40.0 +
                        ", courierID=" + 0 +
                        ", licensePlate=" + null +
                        '}';
        assertEquals(expResult,result);
    }

    @Test
    public void test1Equals() {
        Delivery obj = null;
        Delivery instance = new Delivery(1,25,30,40);
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test2Equals() {
        Object obj = null;
        Delivery instance = new Delivery(1,25,30,40);
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test3Equals() {
        Delivery instance = new Delivery(1,25,30,40);
        boolean expected = true;
        boolean result = instance.equals(instance);
        assertEquals(expected, result);
    }

    @Test
    public void test4Equals() {
        Delivery d = new Delivery(1,25,30,40);
        Delivery instance = new Delivery(1,25,30,40);
        boolean expected = true;
        boolean result = instance.equals(d);
        assertEquals(expected, result);
    }

    @Test
    public void test5Equals() {
        Delivery d = new Delivery(1,25,30,40);
        Delivery instance = new Delivery(2,20,30,40);
        boolean expected = false;
        boolean result = instance.equals(d);
        assertEquals(expected, result);
    }

    @Test
    public void test6Equals() {
        Product p =  new Product(3,"benuron");
        Delivery instance = new Delivery(2,20,30,40);
        boolean expected = false;
        boolean result = instance.equals(p);
        assertEquals(expected, result);
    }

    @Test
    void testHashCode() {
        int hash = delivery.hashCode();
        int expResult = 500009952;
        assertEquals(expResult, hash);
    }

    @Test
    void getId() {
        int expResult = 1;
        assertEquals(expResult,delivery.getId());
    }

    @Test
    void setId() {
        delivery.setId(3);
        int result = delivery.getId();
        int expResult = 3;
        assertEquals(expResult,result);
    }

    @Test
    void getNecessaryEnergy() {
        double expResult = 25;
        assertEquals(expResult,delivery.getNecessaryEnergy());
    }

    @Test
    void setNecessaryEnergy() {
        delivery.setNecessaryEnergy(40);
        double result = delivery.getNecessaryEnergy();
        double expResult = 40;
        assertEquals(expResult,result);
    }

    @Test
    void getDistance() {
        double expResult = 30;
        assertEquals(expResult, delivery.getDistance());
    }

    @Test
    void setDistance() {
        delivery.setDistance(20);
        double result = delivery.getDistance();
        double expResult = 20;
        assertEquals(expResult,result);
    }

    @Test
    void getWeight() {
        double expResult = 40;
        assertEquals(expResult,delivery.getWeight());
    }

    @Test
    void getCourierID() {
        int expResult = 0;
        assertEquals(expResult,delivery.getCourierID());
    }

    @Test
    void getVehicleID() {
        String expResult = "AK-LA-09";
        assertEquals(expResult,delivery2.getLicensePlate());
    }

    @Test
    void setCourierID() {
        delivery.setCourierID(9);
        int expResult = 9;
        int result = delivery.getCourierID();
        assertEquals(expResult,result);
    }

    @Test
    void setVehicleID() {
        delivery.setLicensePlate("AK-LA-02");
        String  expResult = "AK-LA-02";
        String result = delivery.getLicensePlate();
        assertEquals(expResult,result);
    }



    @Test
    void setWeight() {
        delivery.setWeight(15);
        double result = delivery.getWeight();
        double expResult = 15;
        assertEquals(result,expResult);
    }
}