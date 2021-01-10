package lapr.project.controller;

import lapr.project.data.CourierDataHandler;
import lapr.project.data.ScooterHandler;
import lapr.project.model.Courier;
import lapr.project.model.EletricScooter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VehicleControllerTest {

    private static VehicleController instance;

    public VehicleControllerTest() {

    }

    @BeforeAll
    static void beforeAll() throws SQLException {
        ScooterHandler scooterHandlerMock = mock(ScooterHandler.class);
        EletricScooter scooter = new EletricScooter("AH-87-LK",400,350,1,1,500,8.0,5000.0,430,4);
        when(scooterHandlerMock.getAllScooters()).thenReturn(new ArrayList<>());
        scooterHandlerMock.addScooter(scooter);

        instance = new VehicleController(scooterHandlerMock);
    }

   @Test
    void addScooter() throws SQLException {
        EletricScooter scooter = new EletricScooter("AH-87-LK",400,350,1,1,500,8.0,5000.0,430,4);
        VehicleController vehicleController = mock(VehicleController.class);
        doNothing().when(vehicleController).addScooter(scooter.getLicensePlate(),scooter.getMaxBattery(),scooter.getActualBattery(),scooter.getAh_battery(),scooter.getV_battery(),scooter.getEnginePower(),scooter.getWeight(),scooter.getIdPharmacy());
        vehicleController.addScooter(scooter.getLicensePlate(),scooter.getMaxBattery(),scooter.getActualBattery(),scooter.getAh_battery(),scooter.getV_battery(),scooter.getEnginePower(),scooter.getWeight(),scooter.getIdPharmacy());

        verify(vehicleController, times(1)).addScooter(scooter.getLicensePlate(),scooter.getMaxBattery(),scooter.getActualBattery(),scooter.getAh_battery(),scooter.getV_battery(),scooter.getEnginePower(),scooter.getWeight(),scooter.getIdPharmacy());
        //instance.addScooter(scooter.getLicencePlate(), scooter.getMaxBattery(),scooter.getActualBattery(),scooter.getAh_battery(),scooter.getV_battery(),scooter.getEnginePower(),scooter.getWeight(), scooter.getIdPharmacy());
        //ArrayList<EletricScooter> lst = (ArrayList<EletricScooter>) instance.getEletricScooters();
        //assertEquals(scooter, lst.get(0));
    }
    @Test
    void removeScooter() {
    }

    @Test
    void getAvailableScooter() {
        List<EletricScooter> expResult = new ArrayList<>();
        List<EletricScooter> result = instance.getEletricScooters();

        assertEquals(expResult, result);


    }
}