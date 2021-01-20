package lapr.project.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhysicsTest {

    @Test
    void getNecessaryEnergy1() {
      double expectedResult = 7.70;
      double result = Physics.getNecessaryEnergy(20000, 50, 1, 5, 10, 3, 90, 0.0020);
      assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getNecessaryEnergy3() {
        double expectedResult = 6.216;
        double result = Physics.getNecessaryEnergy(20000, 50, 1, 5, 10, 3, 100, 0.0020);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getNecessaryEnergy2() {
        double expectedResult = 0.1168;
        double result = Physics.getNecessaryEnergy(20000, 6, 2, 1, 10, 3, 90, 0.002);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getTimeSpent() {
        double result = Physics.getTimeSpent(10000, 4);
        double expectedResult = 0.694;
        assertEquals(expectedResult, result, 0.1);
    }


    @Test
    void calculateAverageSpeedWithWindDirectionTest1(){
        double expectedResult = 5;
        double result = Physics.calculateAverageSpeedWithWindDirection(5, 1, 90);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void calculateAverageSpeedWithWindDirectionTest2(){
        double expectedResult = 4;
        double result = Physics.calculateAverageSpeedWithWindDirection(5, 1, 180);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void calculateAverageSpeedWithWindDirectionTest3(){
        double expectedResult = 6;
        double result = Physics.calculateAverageSpeedWithWindDirection(5, 1, 0);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void calculateAverageSpeedWithWindDirectionTest4(){
        double expectedResult = 5;
        double result = Physics.calculateAverageSpeedWithWindDirection(5, 1, 80);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void calculateAverageSpeedWithWindDirectionTest5(){
        double expectedResult = 5;
        double result = Physics.calculateAverageSpeedWithWindDirection(5, 1, 250);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getAerodynamicDragForce1() {
        double expectedResult = 109.72;
        double result = Physics.getAerodynamicDragForce(5, 1, 4.50);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getAerodynamicDragForce2() {
        double expectedResult = 0.60;
        double result = Physics.getAerodynamicDragForce(1, 2, 4.50);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getAerodynamicDragForce3() {
        double expectedResult = 0;
        double result = Physics.getAerodynamicDragForce(2, 3, 4.50);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getAerodynamicDragForce4() {
        double expectedResult = 1.2041;
        double result = Physics.getAerodynamicDragForce(2, 2, 4.50);
        assertEquals(expectedResult, result, 0.5);
    }



    @Test
    void getRoadSlope() {
        double expectedResult = 420.3;
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
        double expectedResult = 0.44;
        double result = Physics.calculatePathInclination(70, 30);
        assertEquals(expectedResult, result, 0.1);
    }

    @Test
    void getDroneImpulse() {
        double expectedResult = 4119.17;
        double result = Physics.getDroneImpulse(3, 1, 4);
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
    void getHeadWindRatio() {
        double result = Physics.getHeadWindRatio(100, 200, 30);
        double expResult = 3.132308735953028;
        assertEquals(expResult, result);
    }

    @Test
    void getHeadWindRatio2() {
        double result = Physics.getHeadWindRatio(100, 120, 30);
        double expResult = 1.6666666666666659;
        assertEquals(expResult, result);
    }

    @Test
    void getHorizontalForce() {
        double distance = 30;
        double headWindRatio = 15;
        double weight = 24;
        double liftToDrag = 5;
        double potency = 150;
        double averageVelocity = 9;

        double result = Physics.getHorizontalForce(distance, headWindRatio, weight, liftToDrag, potency, averageVelocity);

        double vRatio = distance / (1-headWindRatio);
        assertEquals(-2.142857142857143, vRatio);

        double vMass = (weight + 1.5) / (370 * liftToDrag);
        assertEquals(0.013783783783783784, vMass);


        assertEquals(-35.74382239382239, result);

    }
}