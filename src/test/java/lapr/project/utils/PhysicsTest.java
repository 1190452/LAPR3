package lapr.project.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhysicsTest {

    @Test
    void getNecessaryEnergy1() {
      double expectedResult = 1.3095369703087942;
      double result = Physics.getNecessaryEnergy(20000, 4, 1, 5, 10, 1, 90, 0.0020, 19999);
      assertEquals(expectedResult, result, 0.05);
    }

    @Test
    void getNecessaryEnergy3() {
        double expectedResult = 1.0030600730865722;
        double result = Physics.getNecessaryEnergy(20000, 4, 1, 5, 10, 1, 0, 0.0020, 19999);
        assertEquals(expectedResult, result, 0.05);
    }

    @Test
    void getNecessaryEnergy2() {
        double expectedResult = 0.08535453283458619;
        double result = Physics.getNecessaryEnergy(20000, 4, 2, 1, 10, 1, 180, 0.002 ,19999);
        assertEquals(expectedResult, result, 0.001);
    }

    @Test
    void getNecessaryEnergy4() {
        double expectedResult = 0.06855489260753285;
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
        double expectedResult = 67.05331875;
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
        double expResult = 5490.881361958213;

        assertEquals(result, expResult, 2);
    }

    @Test
    void getLiftPotency2() {
        double airDensity = 1.2041;
        double droneWidth = 1;
        double calculateSpeedWithWind = 8;
        double result = Physics.getLiftPotency(40, calculateSpeedWithWind);
        double expResult = 3431.8008512238835;

        double denominator =  airDensity * Math.pow(droneWidth, 2) * calculateSpeedWithWind;
        assertEquals(9.6328, denominator);

        assertEquals(expResult,result);
    }


}