package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryTest {

    private final Delivery delivery;

    public DeliveryTest() {
        delivery = new Delivery(1,25,30,40);
    }

    @Test
    void testToString() {
        String result = delivery.toString();
        String expResult = "Delivery{" +
                "id='" + 1 + '\'' +
                ", necessaryEnergy=" + 25.0 +
                ", distance=" + 30 +
                ", weight=" + 40 +
                '}';
    }

    @Test
    void testEquals() {
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
    void setWeight() {
        delivery.setWeight(15);
        double result = delivery.getWeight();
        double expResult = 15;
        assertEquals(result,expResult);
    }
}