package lapr.project.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhysicsTest {

    private Physics ph;

    public PhysicsTest(){
        ph=new Physics();
    }

    @Test
    void getNecessaryEnergy() {
        double result = ph.getNecessaryEnergy(20000, 340,1, 5, 0,0, 121.019, 2817.01, 291.2,987.1);
        double actualResult = 798.3268666666668;
        assertEquals(actualResult, result, 1);
    }

    @Test
    void getTimeSpent() {
        double result = ph.getTimeSpent(20000);
        double actualResult = 1.1111;

        assertEquals(result, actualResult, 1);

    }
    /*  TODO IMPLEMENTAR O RESTO JÁ SÃO 4 DA MANHÃ
    @Test
    void getTotalPower() {
        double result = ph.getTotalPower(ph.getFrictionalForce(340));
        double actualResult = 13337.044;
        assertEquals(result, actualResult,1);
    }

    @Test
    void getFrictionalForce() {
        double result = ph.getFrictionalForce(340);
        double actualResult = 2667.4088;

        assertEquals(actualResult, result, 1);
    }
    */
}