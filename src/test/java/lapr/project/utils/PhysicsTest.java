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
      double expectedResult = 770.27;
      double result = ph.getNecessaryEnergy(20000, 50, 1, 5, 10, 3, 90);
      assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getNecessaryEnergy3() {
        double expectedResult = 690.02;
        double result = ph.getNecessaryEnergy(20000, 50, 1, 5, 10, 3, 100);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getNecessaryEnergy2() {
        double expectedResult = 57208.80;
        double result = ph.getNecessaryEnergy(20000, 6, 2, 1, 10, 3, 90);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getTimeSpent() {
        double result = ph.getTimeSpent(10000);
        double expectedResult = 0.5555;
        assertEquals(expectedResult, result, 0.1);
    }


    @Test
    void calculateAverageSpeedWithWindDirectionTest1(){
        double expectedResult = 5;
        double result = ph.calculateAverageSpeedWithWindDirection(5, 1, 90);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void calculateAverageSpeedWithWindDirectionTest2(){
        double expectedResult = 4;
        double result = ph.calculateAverageSpeedWithWindDirection(5, 1, 180);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void calculateAverageSpeedWithWindDirectionTest3(){
        double expectedResult = 6;
        double result = ph.calculateAverageSpeedWithWindDirection(5, 1, 0);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void calculateAverageSpeedWithWindDirectionTest4(){
        double expectedResult = 5;
        double result = ph.calculateAverageSpeedWithWindDirection(5, 1, 80);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void calculateAverageSpeedWithWindDirectionTest5(){
        double expectedResult = 5;
        double result = ph.calculateAverageSpeedWithWindDirection(5, 1, 250);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getAerodynamicDragForce1() {
        double expectedResult = 109.72;
        double result = ph.getAerodynamicDragForce(5, 1, 4.50);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getAerodynamicDragForce2() {
        double expectedResult = 0.60;
        double result = ph.getAerodynamicDragForce(1, 2, 4.50);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getAerodynamicDragForce3() {
        double expectedResult = 0;
        double result = ph.getAerodynamicDragForce(2, 3, 4.50);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getAerodynamicDragForce4() {
        double expectedResult = 1.2041;
        double result = ph.getAerodynamicDragForce(2, 2, 4.50);
        assertEquals(expectedResult, result, 0.5);
    }



    @Test
    void getRoadSlope() {
        double expectedResult = 420.3;
        double result = ph.getRoadSlope(100, 70, 30);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getRoadLoad() {
        double expectedResult = 1.96;
        double result = ph.getRoadLoad(100, 70, 30);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void calculatePathInclination() {
        double expectedResult = 0.44;
        double result = ph.calculatePathInclination(70, 30);
        assertEquals(expectedResult, result, 0.1);
    }

    @Test
    void getDroneImpulse() {
        double expectedResult = 6178.19;
        double result = ph.getDroneImpulse(3);
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
}