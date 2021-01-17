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
      double expectedResult = 3778395.1483214633;
      double result = ph.getNecessaryEnergy(20000, 50, 1, 5, 10, 30, 40.000, 40.256, -8.365, -8.333);
      assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getNecessaryEnergy2() {
        double expectedResult = 2.05951691E8;
        double result = ph.getNecessaryEnergy(20000, 6, 2, 1, 10, 30, 40.000, 40.256, -8.365, -8.333);
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
    void getRoadSlope() {
        double expectedResult = 78.84;
        double result = ph.getRoadSlope(100, 10, 30, 40.254, 40.258, -8.369, -8.2410);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void getRoadLoad() {
        double expectedResult = 1.96;
        double result = ph.getRoadLoad(100, 10, 30, 40.254, 40.4552, -8.254, -8.369);
        assertEquals(expectedResult, result, 0.5);
    }

    @Test
    void calculatePathInclination() {
        double expectedResult = 0.09;
        double result = ph.calculatePathInclination(10, 30, 40.222, 40.333, -8.333, -8.369);
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