package lapr.project.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhysicsTest {

    private Physics ph;

    public PhysicsTest(){
        ph = new Physics();
    }

    @Test
    void getNecessaryEnergy1() {
      double expectedResult = 0.8099;
      double result = Physics.getNecessaryEnergy(20000, 4, 1, 5, 10, 1, 90, 0.0020, 19999);
      assertEquals(expectedResult, result, 0.05);
    }

    @Test
    void getNecessaryEnergy3() {
        double expectedResult = 0.5088;
        double result = Physics.getNecessaryEnergy(20000, 4, 1, 5, 10, 1, 0, 0.0020, 19999);
        assertEquals(expectedResult, result, 0.05);
    }

    @Test
    void getNecessaryEnergy2() {
        double expectedResult = 1.900;
        double result = Physics.getNecessaryEnergy(20000, 4, 2, 1, 10, 1, 180, 0.002 ,19999);
        assertEquals(expectedResult, result, 0.001);
    }

    @Test
    void getNecessaryEnergy4() {
        double expectedResult = 1.8963;
        double result = Physics.getNecessaryEnergy(20000, 4, 2, 1, 10, 1, 0, 0.002, 19999);
        assertEquals(expectedResult, result, 0.001);
    }

    @Test
    void getTimeSpent2() {
        double result = Physics.getTimeSpent(10000, 4);
        double expectedResult = 0.694;
        assertEquals(expectedResult, result, 0.1);
    }

    @Test
    void getTimeSpent() {
        double result = Physics.getTimeSpent(10000, 4);
        double expectedResult = 2500.0;
        assertEquals(expectedResult, result, 0.1);
    }

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
        double result = Physics.getAerodynamicDragForce(1,5,4.50, 0, 0, 2);

        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getAerodynamicDragForce2() {
        double expectedResult = 2.19;
        double result = Physics.getAerodynamicDragForce(2,1,  4.50, 0, 0, 3);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getAerodynamicDragForce3() {
        double expectedResult = 4.388;
        double result = Physics.getAerodynamicDragForce(2,2, 4.50, 0, 0, 3);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getAerodynamicDragForce4() {
        double expectedResult = 4.388;
        double result = Physics.getAerodynamicDragForce(3,2, 4.50, 0, 0, 3);
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

}