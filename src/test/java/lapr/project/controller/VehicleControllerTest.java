package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VehicleControllerTest {

    @Rule
    public final ExpectedException e = ExpectedException.none();

    private static   VehicleController instance;

    @BeforeAll
    public static void setUpClass() {

        DeliveryHandler deliveryHandlerMock = mock(DeliveryHandler.class);
        VehicleHandler vehicleHandlerMock = mock(VehicleHandler.class);
        ParkHandler parkDataHandlerMock = mock(ParkHandler.class);
        CourierDataHandler courierDataHandlerMock = mock(CourierDataHandler.class);
        PharmacyDataHandler pharmacyDataHandlerMock = mock(PharmacyDataHandler.class);
        Courier c = new Courier(1, "joao@isep.pt", "joao", 1321213, new BigDecimal("37272"), 21, 70, 1);

        when(courierDataHandlerMock.getCourierByEmail(any(String.class))).thenReturn(c);

        ArrayList<Vehicle> vehicle = new ArrayList<>();
        Pharmacy phar = new Pharmacy(5, "ISEP","phar1@iep.ipp.pt", 2323, 23323, "isep@isep.ipp.pt");
        Park park = new Park(1, 5,5, 5,5,5,5, 1);
        Vehicle scooter = new Vehicle(1, "AB-56-DD", 50, 47, 0, 0, 33, 11,23,56,5, 1, 40,2.0);
        vehicle.add(scooter);
        Vehicle drone = new Vehicle(1, "AB-56-DD", 50, 47, 0, 0, 33, 11,23,56,5, 1, 150,2.0);
        when(vehicleHandlerMock.getAllVehicles()).thenReturn(vehicle);
        when(vehicleHandlerMock.getAllScooterAvaiables(any(Integer.class))).thenReturn(vehicle);
        when(vehicleHandlerMock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);
        Courier courier = new Courier(1, "Joao");
        Delivery delivery = new Delivery(45, 333, 23,1,1);
        when(deliveryHandlerMock.getDeliveryByCourierId(courier.getIdCourier())).thenReturn(delivery);
        when(vehicleHandlerMock.getParkByPharmacyId(phar.getId(), park.getIdParktype())).thenReturn(park);
        when(vehicleHandlerMock.getVehicle(any(String.class))).thenReturn(scooter);
        when(vehicleHandlerMock.removeVehicle(any(String.class))).thenReturn(Boolean.TRUE);
        when(parkDataHandlerMock.getParkByPharmacyId(5,1)).thenReturn(park);
        when(vehicleHandlerMock.removeVehicle("AB-56-DD")).thenReturn(Boolean.TRUE);
        when(vehicleHandlerMock.addVehicle(any(Vehicle.class))).thenReturn(Boolean.TRUE);
        when(vehicleHandlerMock.addVehicle(drone)).thenReturn(Boolean.FALSE);
        instance = new VehicleController(vehicleHandlerMock, deliveryHandlerMock, parkDataHandlerMock,courierDataHandlerMock,pharmacyDataHandlerMock
        );
    }
    @Test
    void getAvailableScooter(){
        Vehicle expResult = new Vehicle(1, "AB-56-DD", 50, 47, 0, 0, 33, 11,23,56,5, 1, 10,2.0);
        Courier c = new Courier(1, "joao@isep.pt", "joao", 1321213, new BigDecimal("37272"), 21, 70, 1);
        Vehicle result = instance.getAvailableScooter(1, c.getEmail());
        assertEquals(expResult, result);
    }

    @Test
    void getAvailableScooter2(){
        Vehicle expResult = new Vehicle(1, "AB-56-DD", 50, 47, 0, 0, 33, 11,23,56,5, 2, 10,2.0);
        Courier c = new Courier(2, "joao@isep.pt", "joao", 1321213, new BigDecimal("37272"), 21, 70, 1);
        Vehicle result = instance.getAvailableScooter(1, c.getEmail());
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
        Vehicle v = new Vehicle("AB-56-DD", 45, 12, 33, 11,23,56,5, 1, 88);
        expResult.add(v);
        List<Vehicle> result = instance.getVehicles();

        assertEquals(expResult, result);
    }


    @Test
    void getVehicle() {
        Vehicle vehicle = new Vehicle("AB-56-DD", 45, 12, 33, 11,23,56,5, 1, 88);
        Vehicle result = instance.getVehicle(vehicle.getLicensePlate());
        assertEquals(vehicle, result);
    }

    @Test
    void removeVehicle() {
        Vehicle vehicle = new Vehicle("AB-56-DD", 45, 12, 33, 11,23,56,5, 1, 88);
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

    @Test
    void addVehicle() {
        Vehicle scooter = new Vehicle("AB-56-DD", 50, 470, 0, 0, 4, 1);
        boolean result = instance.addVehicle(scooter.getLicensePlate(), scooter.getMaxBattery(), scooter.getEnginePower(), scooter.getAhBattery(), scooter.getvBattery(), scooter.getIdPharmacy(), scooter.getTypeVehicle());
        assertEquals(false, result);
    }

    @Test
    void addVehicle2() {
        Vehicle drone = new Vehicle(1, "AB-56-DD", 50, 47, 0, 0, 33, 11,23,56,5, 1, 150,2.0);
        boolean result = instance.addVehicle(drone.getLicensePlate(), drone.getMaxBattery(), drone.getEnginePower(), drone.getAhBattery(), drone.getvBattery(), drone.getIdPharmacy(), drone.getTypeVehicle());
        assertEquals(false, result);
    }
}