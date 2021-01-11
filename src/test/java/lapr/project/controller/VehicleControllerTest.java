package lapr.project.controller;

import lapr.project.data.VehicleHandler;
import lapr.project.model.Vehicle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VehicleControllerTest {

    private static VehicleController instance;

    public VehicleControllerTest() {

    }

    @BeforeAll
    static void beforeAll() throws SQLException {
        VehicleHandler vehicleHandlerMock = mock(VehicleHandler.class);
        Vehicle vehicle = new Vehicle("AH-87-LK",400,350,1,1,500,8.0,5000.0,430,4, 1);
        when(vehicleHandlerMock.getAllVehicles()).thenReturn(new ArrayList<>());
        vehicleHandlerMock.addVehicle(vehicle);

        instance = new VehicleController(vehicleHandlerMock);
    }

   @Test
    void addScooter() throws SQLException {
        Vehicle vehicle = new Vehicle("AH-87-LK",400,350,1,1,500,8.0,5000.0,430,4, 1);
        VehicleController vehicleController = mock(VehicleController.class);
        doNothing().when(vehicleController).addVehicle(vehicle.getLicensePlate(),vehicle.getMaxBattery(),vehicle.getActualBattery(),vehicle.getAh_battery(),vehicle.getV_battery(),vehicle.getEnginePower(),vehicle.getWeight(),vehicle.getIdPharmacy(),vehicle.getTypeVehicle());
        vehicleController.addVehicle(vehicle.getLicensePlate(),vehicle.getMaxBattery(),vehicle.getActualBattery(),vehicle.getAh_battery(),vehicle.getV_battery(),vehicle.getEnginePower(),vehicle.getWeight(),vehicle.getIdPharmacy(),vehicle.getTypeVehicle());

        verify(vehicleController, times(1)).addVehicle(vehicle.getLicensePlate(),vehicle.getMaxBattery(),vehicle.getActualBattery(),vehicle.getAh_battery(),vehicle.getV_battery(),vehicle.getEnginePower(),vehicle.getWeight(),vehicle.getIdPharmacy(),vehicle.getTypeVehicle());
        //instance.addScooter(vehicle.getLicencePlate(), vehicle.getMaxBattery(),vehicle.getActualBattery(),vehicle.getAh_battery(),vehicle.getV_battery(),vehicle.getEnginePower(),vehicle.getWeight(), vehicle.getIdPharmacy());
        //ArrayList<EletricScooter> lst = (ArrayList<EletricScooter>) instance.getEletricScooters();
        //assertEquals(vehicle, lst.get(0));
    }
    @Test
    void removeScooter() {
    }

    @Test
    void getAvailableVehicles() {
        List<Vehicle> expResult = new ArrayList<>();
        List<Vehicle> result = instance.getVehicles();

        assertEquals(expResult, result);


    }
}