package lapr.project.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DistanceTest {
    private final Distance distance;

    DistanceTest(Distance distance) {
        this.distance = distance;
    }

    @Test
    void distanceBetweenTwoAddresses() {
        double result = Distance.distanceBetweenTwoAddresses(234.816, 2715.9881,2332.91872, 827162.23234);

        double actualResult = 81017.1;

        assertEquals(result, actualResult, 2);
    }
}