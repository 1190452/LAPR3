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
      double expectedResult = 2772968.218625677;
      double result = ph.getNecessaryEnergy(20000, 50, 1, 5, 10);
      assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getNecessaryEnergy2() {
        double expectedResult = 2.05951691E8;
        double result = ph.getNecessaryEnergy(20000, 6, 2, 1, 10);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getTimeSpent() {
        double result = ph.getTimeSpent(10000);
        double expectedResult = 2000;
        assertEquals(expectedResult, result);
    }

    @Test
    void getAerodynamicDragForce1() {
        double expectedResult = 135.5;
        double result = ph.getAerodynamicDragForce(5, 1);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getAerodynamicDragForce2() {
        double expectedResult = 0.60;
        double result = ph.getAerodynamicDragForce(1, 2);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getAerodynamicDragForce3() {
        double expectedResult = 0;
        double result = ph.getAerodynamicDragForce(2, 3);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getAerodynamicDragForce4() {
        double expectedResult = 1.2041;
        double result = ph.getAerodynamicDragForce(2, 2);
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