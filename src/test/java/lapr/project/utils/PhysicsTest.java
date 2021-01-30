package lapr.project.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhysicsTest {

    @Test
    void getNecessaryEnergy1() {
      double expectedResult = 0.3350124231452421;
      double result = Physics.getNecessaryEnergy(20000, 4, 1, 5, 10, 1, 90, 0.0020, 19999);
      assertEquals(expectedResult, result, 0.05);
    }

    @Test
    void getNecessaryEnergy3() {
        double expectedResult = 0.21794714536746437;
        double result = Physics.getNecessaryEnergy(20000, 4, 1, 5, 10, 1, 0, 0.0020, 19999);
        assertEquals(expectedResult, result, 0.05);
    }

    @Test
    void getNecessaryEnergy2() {
        double expectedResult = 0.024178271249480063;
        double result = Physics.getNecessaryEnergy(20000, 4, 2, 1, 10, 1, 180, 0.002 ,19999);
        assertEquals(expectedResult, result, 0.001);
    }

    @Test
    void getNecessaryEnergy4() {
        double expectedResult = 0.018077802954813395;
        double result = Physics.getNecessaryEnergy(20000, 4, 2, 1, 10, 1, 0, 0.002, 19999);
        assertEquals(expectedResult, result, 0.001);
    }

    @Test
    void getTimeSpent2() {
        double result = Physics.getTimeSpent(10000, 4);
        double expectedResult = 2500;
        assertEquals(expectedResult, result, 0.1);
    }

    @Test
    void getTimeSpent() {
        double result = Physics.getTimeSpent(10000, 4);
        double expectedResult = 2500.0;
        assertEquals(expectedResult, result, 0.1);
    }

    @Test
    void getAerodynamicDragForce1() {
        double expectedResult = 42.67029375;
        double result = Physics.getAerodynamicDragForce(1,5,4.50, 0, 0, 2);

        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getAerodynamicDragForce2() {
        double expectedResult = 3.291708375;
        double result = Physics.getAerodynamicDragForce(2,1,  4.50, 0, 0, 3);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getAerodynamicDragForce3() {
        double expectedResult = 6.58341675;
        double result = Physics.getAerodynamicDragForce(2,2, 4.50, 0, 0, 3);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getAerodynamicDragForce4() {
        double expectedResult = 6.58341675;
        double result = Physics.getAerodynamicDragForce(3,2, 4.50, 0, 0, 3);
        assertEquals(expectedResult, result, 0.5);
    }



    @Test
    void getRoadSlope() {
        double expectedResult = 7.33;
        double result = Physics.getRoadSlope(100, 70, 30);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getRoadLoad() {
        double expectedResult = 1.96;
        double result = Physics.getRoadLoad(100, 70, 30, 0.0020);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void calculatePathInclination() {
        double expectedResult = 0.007;
        double result = Physics.calculatePathInclination(70, 30);
        assertEquals(expectedResult, result, 0.1);
    }

    @Test
    void getDroneImpulse() {
        double expectedResult = 102.829;
        double result = Physics.getDroneImpulse(3, 1);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void calculateDistanceWithElevation() {
        double expectedResult = 28954;
        double result = Physics.calculateDistanceWithElevation(41.38344, 41.149967, -8.76364, -8.610243, 10, 30);
        assertEquals(expectedResult, result, 1);
    }

    @Test
    void linearDistanceTo() {
        double expectedResult = 17813.7;
        double result = Physics.linearDistanceTo(40.222, 40.358, -8.333, -8.444);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getLiftPotency() {
        double result = Physics.getLiftPotency(40, 5);
        double expResult = 265.75;

        assertEquals(result, expResult, 2);
    }

    @Test
    void getLiftPotency2() {
        double airDensity = 1.2041;
        double droneWidth = 1;
        double calculateSpeedWithWind = 8;
        double result = Physics.getLiftPotency(40, calculateSpeedWithWind);
        double expResult = 166.09916119923597;

        double denominator =  airDensity * Math.pow(droneWidth, 2) * calculateSpeedWithWind;
        assertEquals(9.6328, denominator);

        assertEquals(expResult,result);
    }

    @Test
    void calculateDistanceTheScooterCanDo() {
        double actualBattery = 10.0;
        double maxBattery = 20.0;
        double enginePower = 90.0;
        double time = ((actualBattery*maxBattery)/100.0)/enginePower;
        double CONSTANT_AVERAGE_VELOCITY = 5;
        double VEHICLE_EFFICIENCY = 1;
        assertEquals(0.022222222222222223, time);
        double result = Physics.calculateDistanceTheScooterCanDo(actualBattery,maxBattery,enginePower);
        double expResult = CONSTANT_AVERAGE_VELOCITY * time * VEHICLE_EFFICIENCY;
        assertEquals(expResult, result);
    }
}