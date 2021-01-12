package lapr.project.controller;

import lapr.project.data.DeliveryHandler;
import lapr.project.data.ParkHandler;
import lapr.project.data.VehicleHandler;
import lapr.project.model.*;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VehicleControllerTest {

    @Rule
    public final ExpectedException e = ExpectedException.none().none();

    private static   VehicleController instance;

    @BeforeAll
    public static void setUpClass() throws SQLException {

        DeliveryHandler deliveryHandlerMock = mock(DeliveryHandler.class);
        VehicleHandler vehicleHandlerMock = mock(VehicleHandler.class);
        ParkHandler parkDataHandlerMock = mock(ParkHandler.class);

        ArrayList<Vehicle> vehicle = new ArrayList<>();
        Pharmacy phar = new Pharmacy(5, "ISEP", 2323, 23323, "isep@isep.ipp.pt");
        Park park = new Park(1, 5,5, 5,5,5,5);
        Vehicle v = new Vehicle(1, "AB-56-DD", 50, 47, 0, 0, 33, 11,23,56,5, 1);
        vehicle.add(v);
        when(vehicleHandlerMock.getAllVehicles()).thenReturn(vehicle);
        Courier courier = new Courier(1, "Joao");
        Delivery delivery = new Delivery(45, 333, 23);
        when(deliveryHandlerMock.getDeliveryByCourierId(courier.getIdCourier())).thenReturn(delivery);
        when(vehicleHandlerMock.getParkByPharmacyId(phar.getId())).thenReturn(park);
        when(vehicleHandlerMock.getVehicle(any(String.class))).thenReturn(v);
        when(vehicleHandlerMock.addVehicle(any(Vehicle.class))).thenReturn(Boolean.TRUE);
        when(vehicleHandlerMock.removeVehicle(any(String.class))).thenReturn(Boolean.TRUE);
        when(parkDataHandlerMock.getParkByPharmacyId(5)).thenReturn(park);
        when(vehicleHandlerMock.removeVehicle("AB-56-DD")).thenReturn(Boolean.TRUE);
        instance = new VehicleController(vehicleHandlerMock, deliveryHandlerMock, parkDataHandlerMock);
    }
    @Test
    void getAvailableScooter(){
        Vehicle expResult = new Vehicle(1, "AB-56-DD", 50, 47, 0, 0, 33, 11,23,56,5, 1);
        Vehicle result = instance.getAvailableScooter(1);
        assertEquals(expResult, result);
    }

    @Test
    void getAvailableScooter2(){
        Vehicle expResult = new Vehicle(1, "AB-56-DD", 50, 47, 0, 0, 33, 11,23,56,5, 2);
        Vehicle result = instance.getAvailableScooter(1);
        assertEquals(expResult, result);
    }

    @Test
    void getAvailableScooter3(){
        Vehicle expResult = null;
        e.expect(NullPointerException.class);
    }

    @Test
    void parkScooter() throws IOException {
       boolean expResult = true;
       boolean result = instance.parkScooter(5, "AB-56-DD");
       assertEquals(expResult, result);
    }

    @Test
    void getAvailableVehicles() {
        List<Vehicle> expResult = new ArrayList<>();
        Vehicle v = new Vehicle("AB-56-DD", 45, 12, 33, 11,23,56,5, 1);
        expResult.add(v);
        List<Vehicle> result = instance.getVehicles();

        assertEquals(expResult, result);
    }

    @Test
    void addVehicle() throws SQLException {
        boolean expResult = true;
        Vehicle vehicle = new Vehicle("AB-56-DD", 45, 12, 33, 11,23,56,5, 1);
        boolean result = instance.addVehicle(vehicle.getLicensePlate(),vehicle.getMaxBattery(),vehicle.getActualBattery(),vehicle.getEnginePower(), vehicle.getAh_battery(), vehicle.getV_battery(), vehicle.getWeight(), vehicle.getIdPharmacy(), vehicle.getTypeVehicle());
        assertEquals(expResult, result);
    }


    @Test
    void getVehicle() {
        Vehicle vehicle = new Vehicle("AB-56-DD", 45, 12, 33, 11,23,56,5, 1);
        Vehicle result = instance.getVehicle(vehicle.getLicensePlate());
        assertEquals(vehicle, result);
    }

    @Test
    void removeVehicle() {
        Vehicle vehicle = new Vehicle("AB-56-DD", 45, 12, 33, 11,23,56,5, 1);
        boolean result = instance.removeVehicle(vehicle.getLicensePlate());
        boolean expResult = true;
        assertEquals(expResult, result);
    }


    @Test
    void removeVehicle2() {
        boolean expResult = false;
        String licensePlate = null;

        boolean result = instance.removeVehicle(licensePlate);
        assertEquals(expResult, result);
    }

}