package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VehicleControllerTest {

    @Rule
    public final ExpectedException e = ExpectedException.none();

    private static VehicleController instance;

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
        Pharmacy phar = new Pharmacy(4, "farmacia", "Farmácia Tirori", 232.019, 41.1111, -8.9999, "admin@isep.ipp.pt");
        Park park = new Park(1, 5, 5, 5, 5, 5, 5, 1);
        Vehicle scooter = new Vehicle(1, "AB-56-DD", 50, 47, 0, 0, 33, 11, 23, 56, 5, 1, 40, 2.0);
        vehicle.add(scooter);
        Vehicle drone = new Vehicle(1, "AB-56-DD", 50, 47, 0, 0, 33, 11, 23, 56, 5, 1, 150, 2.0);
        when(vehicleHandlerMock.getAllVehicles()).thenReturn(vehicle);
        when(vehicleHandlerMock.getAllScooterAvailables(any(Integer.class), any(Double.class))).thenReturn(vehicle);
        Courier courier = new Courier(1, "Joao");
        Delivery delivery = new Delivery(45, 333, 23, 1, "AK-LA-09");
        when(deliveryHandlerMock.getDeliveryByCourierId(courier.getIdCourier())).thenReturn(delivery);
        when(vehicleHandlerMock.getVehicle(any(String.class))).thenReturn(scooter);
        when(vehicleHandlerMock.removeVehicle(any(String.class))).thenReturn(Boolean.TRUE);
        when(parkDataHandlerMock.getParkByPharmacyId(5, 1)).thenReturn(park);
        when(parkDataHandlerMock.getParkByPharmacyId(1, 1)).thenReturn(park);
        when(vehicleHandlerMock.removeVehicle("AB-56-DD")).thenReturn(Boolean.TRUE);
        instance = new VehicleController(vehicleHandlerMock, deliveryHandlerMock, parkDataHandlerMock, courierDataHandlerMock, pharmacyDataHandlerMock, new AddressDataHandler());
    }

    @Test
    void testgetAvailableScooter() {
        Vehicle expResult = new Vehicle(1, "AB-56-DD", 50, 47, 0, 0, 33, 11, 23, 56, 5, 1, 10, 2.0);
        Courier c = new Courier(1, "joao@isep.pt", "joao", 1321213, new BigDecimal("37272"), 21, 70, 1);
        Vehicle result = instance.getAvailableScooter(1, c.getEmail());
        assertEquals(expResult, result);
    }

    @Test
    void testgetAvailableScooter2() {
        Vehicle expResult = new Vehicle(1, "AB-56-DD", 50, 47, 0, 0, 33, 11, 23, 56, 5, 2, 10, 2.0);
        Courier c = new Courier(2, "joao@isep.pt", "joao", 1321213, new BigDecimal("37272"), 21, 70, 1);
        Vehicle result = instance.getAvailableScooter(1, c.getEmail());
        assertEquals(expResult, result);
    }

    @Test
    void testgetAvailableScooter3() {
        Vehicle expResult = null;
        e.expect(NullPointerException.class);
    }

    @Test
    void testparkScooter() throws IOException {
       boolean expResult = false;
       boolean result = instance.parkScooter(5, new Vehicle(1, "AB-56-DD", 50, 47, 0, 0, 33, 11,23,56,5, 1, 40,2.0));
       assertEquals(expResult, result);
    }

    @Test
    void testgetAvailableVehicles() {
        List<Vehicle> expResult = new ArrayList<>();
        Vehicle v = new Vehicle("AB-56-DD", 45, 12, 33, 11, 23, 56, 5, 1, 88);
        expResult.add(v);
        List<Vehicle> result = instance.getVehicles();

        assertEquals(expResult, result);
    }

    @Test
    void testgetVehicle() {
        Vehicle vehicle = new Vehicle("AB-56-DD", 45, 12, 33, 11, 23, 56, 5, 1, 88);
        Vehicle result = instance.getVehicle(vehicle.getLicensePlate());
        assertEquals(vehicle, result);
    }

    @Test
    void testremoveVehicle() {
        Vehicle vehicle = new Vehicle("AB-56-DD", 45, 12, 33, 11, 23, 56, 5, 1, 88);
        boolean result = instance.removeVehicle(vehicle.getLicensePlate());
        boolean expResult = true;
        assertEquals(expResult, result);
    }

    @Test
    void testremoveVehicle2() {
        boolean expResult = false;
        String licensePlate = null;

        boolean result = instance.removeVehicle(licensePlate);
        assertEquals(expResult, result);
    }

    @Test
    void testaddVehicle() {
        Vehicle scooter = new Vehicle("AB-56-DD", 50, 470, 0, 0, 4, 1, 50, 0.3);
        VehicleHandler vehicleHandlerMock = mock(VehicleHandler.class);
        when(vehicleHandlerMock.addVehicle(any(Vehicle.class))).thenReturn(Boolean.TRUE);
        VehicleController vehicleController = new VehicleController(vehicleHandlerMock, new DeliveryHandler(), new ParkHandler(), new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());
        boolean result = vehicleController.addVehicle(scooter.getLicensePlate(), scooter.getMaxBattery(), scooter.getEnginePower(), scooter.getAhBattery(), scooter.getvBattery(), scooter.getIdPharmacy(), scooter.getTypeVehicle(), scooter.getFrontalArea(), scooter.getWeight());
        assertTrue(result);
    }

    @Test
    void testaddVehicle2() {
        Vehicle scooter = new Vehicle("AB-56-DD", 50, 470, 0, 0, 4, 1, 50, 0.3);
        VehicleHandler vehicleHandlerMock = mock(VehicleHandler.class);
        when(vehicleHandlerMock.addVehicle(any(Vehicle.class))).thenReturn(Boolean.FALSE);
        VehicleController vehicleController = new VehicleController(vehicleHandlerMock, new DeliveryHandler(), new ParkHandler(), new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());
        boolean result = vehicleController.addVehicle(scooter.getLicensePlate(), scooter.getMaxBattery(), scooter.getEnginePower(), scooter.getAhBattery(), scooter.getvBattery(), scooter.getIdPharmacy(), scooter.getTypeVehicle(), scooter.getFrontalArea(), scooter.getWeight());
        assertFalse(result);
    }


    @Test
    void testGetAvailableScooter() {
        Courier c = new Courier(1, "courier@isep.ipp.pt", "André", 122665789,
                new BigDecimal("24586612344"), 15, 70, 1);
        Delivery delivery = new Delivery(1, 25, 30, 10);
        Vehicle v = new Vehicle(1, "AH-87-LK", 400, 350, 0, 1, 500, 8.0, 5000.0, 430, 4, 1, 10, 2.3);
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        List<Vehicle> lstVehicle = new ArrayList<>();
        lstVehicle.add(v);

        CourierDataHandler courierDataHandlermock = mock(CourierDataHandler.class);
        when(courierDataHandlermock.getCourierByEmail(any(String.class))).thenReturn(c);

        ParkHandler parkHandlermock = mock(ParkHandler.class);
        doNothing().when(parkHandlermock).updateActualChargingPlacesA(any(Integer.class));
        when(parkHandlermock.getParkByPharmacyId(1,1)).thenReturn(park);

        DeliveryHandler deliveryHandlermock = mock(DeliveryHandler.class);
        when(deliveryHandlermock.getDeliveryByCourierId(any(Integer.class))).thenReturn(delivery);

        VehicleHandler vehicleHandlermock = mock(VehicleHandler.class);
        when(vehicleHandlermock.getAllScooterAvailables(any(Integer.class), any(Double.class))).thenReturn(lstVehicle);
        when(vehicleHandlermock.updateStatusToParked(any(String.class))).thenReturn(Boolean.TRUE);
        when(vehicleHandlermock.updateIsChargingN(any(String.class))).thenReturn(Boolean.TRUE);
        when(vehicleHandlermock.associateVehicleToDelivery(any(Integer.class), any(String.class))).thenReturn(Boolean.TRUE);

        VehicleController vehicleController = new VehicleController(vehicleHandlermock, deliveryHandlermock, parkHandlermock, courierDataHandlermock, new PharmacyDataHandler(), new AddressDataHandler());
        Vehicle result = vehicleController.getAvailableScooter(c.getIdCourier(), c.getEmail());
        assertEquals(v, result);
    }


    @Test
    void testGetAvailableScooter2() {
        Courier c = new Courier(1, "courier@isep.ipp.pt", "André", 122665789,
                new BigDecimal("24586612344"), 15, 70, 1);
        Delivery delivery = new Delivery(1, 10000, 30, 10);
        Vehicle v = new Vehicle(1, "AH-87-LK", 400, 350, 0, 1, 500, 8.0, 5000.0, 430, 4, 1, 10, 2.3);
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        List<Vehicle> lstVehicle = new ArrayList<>();
        lstVehicle.add(v);

        CourierDataHandler courierDataHandlermock = mock(CourierDataHandler.class);
        when(courierDataHandlermock.getCourierByEmail(any(String.class))).thenReturn(c);

        ParkHandler parkHandlermock = mock(ParkHandler.class);
        doNothing().when(parkHandlermock).updateActualChargingPlacesA(any(Integer.class));

        DeliveryHandler deliveryHandlermock = mock(DeliveryHandler.class);
        when(deliveryHandlermock.getDeliveryByCourierId(any(Integer.class))).thenReturn(delivery);

        VehicleHandler vehicleHandlermock = mock(VehicleHandler.class);
        when(vehicleHandlermock.getAllScooterAvailables(any(Integer.class), any(Double.class))).thenReturn(lstVehicle);
        when(vehicleHandlermock.updateStatusToParked(any(String.class))).thenReturn(Boolean.TRUE);
        when(vehicleHandlermock.updateIsChargingN(any(String.class))).thenReturn(Boolean.TRUE);
        when(vehicleHandlermock.associateVehicleToDelivery(any(Integer.class), any(String.class))).thenReturn(Boolean.TRUE);

        VehicleController vehicleController = new VehicleController(vehicleHandlermock, deliveryHandlermock, parkHandlermock, courierDataHandlermock, new PharmacyDataHandler(), new AddressDataHandler());
        Vehicle result = vehicleController.getAvailableScooter(c.getIdCourier(), c.getEmail());
        assertNull(result);
    }


    @Test
    void testParkScooter() {
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        Vehicle v = new Vehicle(1, "AH-87-LK", 400, 350, 0, 1, 500, 8.0, 5000.0, 430, 4, 1, 10, 2.3);

        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);
        when(parkHandlermock.updateActualCapacityR(any(Integer.class))).thenReturn(Boolean.TRUE);

        VehicleHandler vehicleHandlermock = mock(VehicleHandler.class);
        when(vehicleHandlermock.getVehicle(any(String.class))).thenReturn(v);
        when(vehicleHandlermock.updateStatusToParked(any(String.class))).thenReturn(Boolean.TRUE);
        when(vehicleHandlermock.updateIsChargingY(any(String.class))).thenReturn(Boolean.TRUE);

        UserSession.getInstance().setUser(new User("admin@isep.ipp.pt", "qwerty", "Administrator"));

        VehicleController vehicleController = new VehicleController(vehicleHandlermock, new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());
        boolean result = vehicleController.parkScooter(1, v);
        assertFalse(result);
    }


    @Test
    void testParkScooter2() throws IOException {

        Park park = new Park(1, 12, 10, 2, 0, 25, 2, 1);
        Vehicle v = new Vehicle(1, "AH-87-LK", 400, 5, 0, 1, 500, 8.0, 5000.0, 430, 4, 1, 10, 2.3);

        List<Park> lst = new ArrayList<>();
        lst.add(park);

        VehicleController vehicleControllermock = mock(VehicleController.class);
        when(vehicleControllermock.getAnotherParkToCharge(any(Integer.class), any(Integer.class))).thenReturn(Boolean.TRUE);


        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);
        when(parkHandlermock.updateActualCapacityR(any(Integer.class))).thenReturn(Boolean.TRUE);
        when(parkHandlermock.getParkWithCPlaces(any(Integer.class))).thenReturn(lst);

        VehicleHandler vehicleHandlermock = mock(VehicleHandler.class);
        when(vehicleHandlermock.getVehicle(any(String.class))).thenReturn(v);
        when(vehicleHandlermock.updateStatusToParked(any(String.class))).thenReturn(Boolean.TRUE);
        when(vehicleHandlermock.updateIsChargingY(any(String.class))).thenReturn(Boolean.TRUE);

        boolean result = vehicleControllermock.parkScooter(1, v);

        assertFalse(result);
    }


    @Test
    void testParkScooter3() throws IOException {
        Park park = new Park(1, 12, 10, 2, 0, 25, 2, 1);
        Vehicle v = new Vehicle(1, "AH-87-LK", 400, 370, 0, 1, 500, 8.0, 5000.0, 430, 4, 1, 10, 2.3);

        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);
        when(parkHandlermock.updateActualCapacityR(any(Integer.class))).thenReturn(Boolean.TRUE);

        VehicleController vehicleControllermock = mock(VehicleController.class);
        when(vehicleControllermock.getAnotherParkToPark(any(Integer.class), any(Integer.class))).thenReturn(Boolean.TRUE);

        VehicleHandler vehicleHandlermock = mock(VehicleHandler.class);
        when(vehicleHandlermock.getVehicle(any(String.class))).thenReturn(v);
        when(vehicleHandlermock.updateStatusToParked(any(String.class))).thenReturn(Boolean.TRUE);
        when(vehicleHandlermock.updateIsChargingY(any(String.class))).thenReturn(Boolean.TRUE);

        UserSession.getInstance().setUser(new User("admin@isep.ipp.pt", "qwerty", "Administrator"));
        boolean result = vehicleControllermock.parkScooter(1, v);
        assertFalse(result);
    }

    @Test
    void testParkScooter5() throws IOException {
        Park park = null;
        Vehicle v = null;

        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);

        VehicleHandler vehicleHandlermock = mock(VehicleHandler.class);
        when(vehicleHandlermock.getVehicle(any(String.class))).thenReturn(v);

        VehicleController vehicleController = new VehicleController(vehicleHandlermock, new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());
        boolean result = vehicleController.parkScooter(1, new Vehicle(1, "AB-56-DD", 50, 47, 0, 0, 33, 11, 23, 56, 5, 1, 40, 2.0));
        assertFalse(result);
    }

    @Test
    void testParkDrone() throws IOException, InterruptedException {
        Vehicle v = null;
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);

        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);

        VehicleHandler vehicleHandlermock = mock(VehicleHandler.class);
        when(vehicleHandlermock.getVehicle(any(String.class))).thenReturn(v);
        VehicleController vehicleController = new VehicleController(vehicleHandlermock, new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());

        boolean result = vehicleController.parkDrone(2, v);
        boolean expectedResult = false;

        assertEquals(expectedResult, result);


    }

    @Test
    void testParkDrone3() throws IOException, InterruptedException {
        Vehicle v = new Vehicle(1, "AH-87-LK", 400, 370, 0, 1, 500, 8.0, 5000.0, 430, 4, 1, 10, 2.3);
        Park park = new Park(1, 12, -4, 2, 0, 25, 2, 1);

        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);
        when(parkHandlermock.updateActualCapacityR(any(Integer.class))).thenReturn(Boolean.TRUE);

        VehicleController vehicleControllermock = mock(VehicleController.class);
        when(vehicleControllermock.getAnotherParkToPark(any(Integer.class), any(Integer.class))).thenReturn(Boolean.TRUE);

        VehicleHandler vehicleHandlermock = mock(VehicleHandler.class);
        when(vehicleHandlermock.getVehicle(any(String.class))).thenReturn(v);
        when(vehicleHandlermock.updateStatusToParked(any(String.class))).thenReturn(Boolean.TRUE);
        when(vehicleHandlermock.updateIsChargingY(any(String.class))).thenReturn(Boolean.TRUE);

        UserSession.getInstance().setUser(new User("admin@isep.ipp.pt", "qwerty", "Administrator"));

        boolean result = vehicleControllermock.parkDrone(2, v);

        assertFalse(result);


    }

    @Test
    void getAnotherParkToPark() {
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        Park park2 = new Park(2, 12, 10, 2, 1, 25, 2, 1);
        Pharmacy p = new Pharmacy(2, "test", "test", 23123, 1241, 214, "test");
        Pharmacy p2 = new Pharmacy(4, "test2", "test2", 231, 12.21, 0, "test");

        Address address = new Address(34, 45, "rua xpto", 2, "4500", "espinho");
        List<Park> listNormalParksD = new LinkedList<>();
        listNormalParksD.add(park);
        listNormalParksD.add(park2);

        VehicleController vehicleControllerMock = mock(VehicleController.class);
        when(vehicleControllerMock.getParkMoreClose(listNormalParksD, p.getId())).thenReturn(park);

        PharmacyDataHandler pharmacyDataHandlerMock = mock(PharmacyDataHandler.class);
        when(pharmacyDataHandlerMock.getPharmacyByID(any(Integer.class))).thenReturn(p);
        when(pharmacyDataHandlerMock.getPharmacyByID(p2.getId())).thenReturn(p2);

        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);
        when(parkHandlermock.getParkWithNPlaces(any(Integer.class))).thenReturn(listNormalParksD);

        AddressDataHandler addressDataHandlermock = mock(AddressDataHandler.class);
        when(addressDataHandlermock.getAddress(any(Double.class), any(Double.class), any(Double.class))).thenReturn(address);

        VehicleController vehicleController = new VehicleController(new VehicleHandler(), new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), pharmacyDataHandlerMock, addressDataHandlermock);

        boolean result = vehicleController.getAnotherParkToPark(park.getId(), p.getId());

        boolean expectedResult = true;
        assertEquals(expectedResult, result);
    }

    @Test
    void getAnotherParkToPark3() {
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        Pharmacy p = new Pharmacy(2, "test", "test", 23123, 1241, 214, "test");

        Address address = new Address(34, 45, "rua xpto", 2, "4500", "espinho");
        List<Park> listNormalParksD = new LinkedList<>();
        listNormalParksD.add(park);

        VehicleController vehicleControllerMock = mock(VehicleController.class);
        when(vehicleControllerMock.getParkMoreClose(listNormalParksD, p.getId())).thenReturn(park);

        PharmacyDataHandler pharmacyDataHandlerMock = mock(PharmacyDataHandler.class);
        when(pharmacyDataHandlerMock.getPharmacyByID(any(Integer.class))).thenReturn(p);

        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(null);
        when(parkHandlermock.getParkWithNPlaces(any(Integer.class))).thenReturn(listNormalParksD);

        AddressDataHandler addressDataHandlermock = mock(AddressDataHandler.class);
        when(addressDataHandlermock.getAddress(any(Double.class), any(Double.class), any(Double.class))).thenReturn(address);

        VehicleController vehicleController = new VehicleController(new VehicleHandler(), new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), pharmacyDataHandlerMock, addressDataHandlermock);

        boolean result = vehicleController.getAnotherParkToPark(park.getId(), p.getId());

        boolean expectedResult = false;
        assertEquals(expectedResult, result);
    }

    @Test
    void getAnotherParkToPark2() {
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        Pharmacy p = new Pharmacy(2, "test", "test", 23123, 1241, 214, "test");

        Address address = new Address(34, 45, "rua xpto", 2, "4500", "espinho");
        List<Park> listNormalParksD = new LinkedList<>();
        listNormalParksD.add(park);

        VehicleController vehicleControllerMock = mock(VehicleController.class);
        when(vehicleControllerMock.getParkMoreClose(listNormalParksD, p.getId())).thenReturn(park);

        PharmacyDataHandler pharmacyDataHandlerMock = mock(PharmacyDataHandler.class);
        when(pharmacyDataHandlerMock.getPharmacyByID(any(Integer.class))).thenReturn(p);

        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(null);
        when(parkHandlermock.getParkWithNPlaces(any(Integer.class))).thenReturn(listNormalParksD);

        AddressDataHandler addressDataHandlermock = mock(AddressDataHandler.class);
        when(addressDataHandlermock.getAddress(any(Double.class), any(Double.class), any(Double.class))).thenReturn(address);

        VehicleController vehicleController = new VehicleController(new VehicleHandler(), new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), pharmacyDataHandlerMock, addressDataHandlermock);

        boolean result = vehicleController.getAnotherParkToPark(park.getId(), p.getId());

        boolean expectedResult = false;
        assertEquals(expectedResult, result);
    }

    @Test
    void getAnotherParkToPark4() {
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        Pharmacy p = new Pharmacy(2, "test", "test", 23123, 1241, 214, "test");

        Address address = new Address(34, 45, "rua xpto", 2, "4500", "espinho");
        List<Park> listNormalParksD = new LinkedList<>();
        listNormalParksD.add(park);

        VehicleController vehicleControllerMock = mock(VehicleController.class);
        when(vehicleControllerMock.getParkMoreClose(listNormalParksD, p.getId())).thenReturn(park);

        PharmacyDataHandler pharmacyDataHandlerMock = mock(PharmacyDataHandler.class);
        when(pharmacyDataHandlerMock.getPharmacyByID(any(Integer.class))).thenReturn(p);

        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);
        when(parkHandlermock.getParkWithNPlaces(any(Integer.class))).thenReturn(listNormalParksD);

        AddressDataHandler addressDataHandlermock = mock(AddressDataHandler.class);
        when(addressDataHandlermock.getAddress(any(Double.class), any(Double.class), any(Double.class))).thenReturn(address);

        VehicleController vehicleController = new VehicleController(new VehicleHandler(), new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), pharmacyDataHandlerMock, addressDataHandlermock);

        boolean result = vehicleController.getAnotherParkToPark(park.getId(), p.getId());

        boolean expectedResult = false;
        assertEquals(expectedResult, result);
    }


    @Test
    void getAnotherParkToCharge() {
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        Park park2 = new Park(2, 12, 10, 2, 1, 25, 2, 1);
        Pharmacy p = new Pharmacy(2, "test", "test", 23123, 1241, 214, "test");
        Pharmacy p2 = new Pharmacy(4, "test2", "test2", 231, 12.21, 0, "test");

        Address address = new Address(34, 45, "rua xpto", 2, "4500", "espinho");
        List<Park> listNormalParksD = new LinkedList<>();
        listNormalParksD.add(park);
        listNormalParksD.add(park2);

        VehicleController vehicleControllerMock = mock(VehicleController.class);
        when(vehicleControllerMock.getParkMoreClose(listNormalParksD, p.getId())).thenReturn(park);

        PharmacyDataHandler pharmacyDataHandlerMock = mock(PharmacyDataHandler.class);
        when(pharmacyDataHandlerMock.getPharmacyByID(any(Integer.class))).thenReturn(p);
        when(pharmacyDataHandlerMock.getPharmacyByID(p2.getId())).thenReturn(p2);

        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);
        when(parkHandlermock.getParkWithCPlaces(any(Integer.class))).thenReturn(listNormalParksD);

        AddressDataHandler addressDataHandlermock = mock(AddressDataHandler.class);
        when(addressDataHandlermock.getAddress(any(Double.class), any(Double.class), any(Double.class))).thenReturn(address);

        VehicleController vehicleController = new VehicleController(new VehicleHandler(), new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), pharmacyDataHandlerMock, addressDataHandlermock);

        boolean result = vehicleController.getAnotherParkToCharge(park.getId(), p.getId());

        boolean expectedResult = true;
        assertTrue(result);
    }

    @Test
    void getAnotherParkToCharge2() {
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        Pharmacy p = new Pharmacy(2, "test", "test", 23123, 1241, 214, "test");
        Pharmacy p2 = new Pharmacy(4, "test2", "test2", 231, 12.21, 0, "test");

        Address address = new Address(34, 45, "rua xpto", 2, "4500", "espinho");
        List<Park> listNormalParksD = new LinkedList<>();
        listNormalParksD.add(park);

        VehicleController vehicleControllerMock = mock(VehicleController.class);
        when(vehicleControllerMock.getParkMoreClose(listNormalParksD, p.getId())).thenReturn(park);

        PharmacyDataHandler pharmacyDataHandlerMock = mock(PharmacyDataHandler.class);
        when(pharmacyDataHandlerMock.getPharmacyByID(any(Integer.class))).thenReturn(p);
        when(pharmacyDataHandlerMock.getPharmacyByID(p2.getId())).thenReturn(p2);

        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);
        when(parkHandlermock.getParkWithCPlaces(any(Integer.class))).thenReturn(listNormalParksD);

        AddressDataHandler addressDataHandlermock = mock(AddressDataHandler.class);
        when(addressDataHandlermock.getAddress(any(Double.class), any(Double.class), any(Double.class))).thenReturn(address);

        VehicleController vehicleController = new VehicleController(new VehicleHandler(), new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), pharmacyDataHandlerMock, addressDataHandlermock);

        boolean result = vehicleController.getAnotherParkToCharge(park.getId(), p.getId());

        boolean expectedResult = false;
        assertEquals(expectedResult, result);
    }

    @Test
    void getAnotherParkToCharge3() {
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        Pharmacy p = new Pharmacy(2, "test", "test", 23123, 1241, 214, "test");
        Pharmacy p2 = new Pharmacy(4, "test2", "test2", 231, 12.21, 0, "test");

        Address address = new Address(34, 45, "rua xpto", 2, "4500", "espinho");
        List<Park> listNormalParksD = new LinkedList<>();
        listNormalParksD.add(park);

        VehicleController vehicleControllerMock = mock(VehicleController.class);
        when(vehicleControllerMock.getParkMoreClose(listNormalParksD, p.getId())).thenReturn(park);

        PharmacyDataHandler pharmacyDataHandlerMock = mock(PharmacyDataHandler.class);
        when(pharmacyDataHandlerMock.getPharmacyByID(any(Integer.class))).thenReturn(p);
        when(pharmacyDataHandlerMock.getPharmacyByID(p2.getId())).thenReturn(p2);

        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(null);
        when(parkHandlermock.getParkWithCPlaces(any(Integer.class))).thenReturn(listNormalParksD);

        AddressDataHandler addressDataHandlermock = mock(AddressDataHandler.class);
        when(addressDataHandlermock.getAddress(any(Double.class), any(Double.class), any(Double.class))).thenReturn(address);

        VehicleController vehicleController = new VehicleController(new VehicleHandler(), new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), pharmacyDataHandlerMock, addressDataHandlermock);

        boolean result = vehicleController.getAnotherParkToCharge(park.getId(), p.getId());

        boolean expectedResult = false;
        assertEquals(expectedResult, result);
    }

    @Test
    void getAnotherParkToCharge4() {
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        Pharmacy p = new Pharmacy(2, "test", "test", 23123, 1241, 214, "test");
        Pharmacy p2 = new Pharmacy(4, "test2", "test2", 231, 12.21, 0, "test");

        Address address = new Address(34, 45, "rua xpto", 2, "4500", "espinho");
        List<Park> listNormalParksD = new LinkedList<>();
        listNormalParksD.add(park);

        VehicleController vehicleControllerMock = mock(VehicleController.class);
        when(vehicleControllerMock.getParkMoreClose(listNormalParksD, p.getId())).thenReturn(park);

        PharmacyDataHandler pharmacyDataHandlerMock = mock(PharmacyDataHandler.class);
        when(pharmacyDataHandlerMock.getPharmacyByID(any(Integer.class))).thenReturn(p);
        when(pharmacyDataHandlerMock.getPharmacyByID(p2.getId())).thenReturn(null);

        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);
        when(parkHandlermock.getParkWithCPlaces(any(Integer.class))).thenReturn(listNormalParksD);

        AddressDataHandler addressDataHandlermock = mock(AddressDataHandler.class);
        when(addressDataHandlermock.getAddress(any(Double.class), any(Double.class), any(Double.class))).thenReturn(address);

        VehicleController vehicleController = new VehicleController(new VehicleHandler(), new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), pharmacyDataHandlerMock, addressDataHandlermock);

        boolean result = vehicleController.getAnotherParkToCharge(park.getId(), p.getId());

        boolean expectedResult = false;
        assertEquals(expectedResult, result);
    }


    @Test
    void parkDrone2() throws IOException, InterruptedException {
        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(null);

        VehicleController vehicleController = new VehicleController(new VehicleHandler(), new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());

        boolean result = vehicleController.parkDrone(2, new Vehicle(1, "AH-87-LK", 400, 350, 0, 1, 500, 8.0, 5000.0, 430, 4, 1, 10, 2.3));

        assertFalse(result);
    }

    @Test
    void parkDrone3() throws IOException, InterruptedException {
        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(null);

        VehicleController vehicleController = new VehicleController(new VehicleHandler(), new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());

        boolean result = vehicleController.parkDrone(2, null);

        assertFalse(result);
    }

    @Test
    void parkDrone4() throws IOException, InterruptedException {
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);

        VehicleController vehicleController = new VehicleController(new VehicleHandler(), new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());

        boolean result = vehicleController.parkDrone(2, null);

        assertFalse(result);
    }


    @Test
    void parkDrone7() throws IOException, InterruptedException {
        Pharmacy p = new Pharmacy(4, "farmacia", "Farmácia Tirori", 232.019, 41.1111, -8.9999, "admin@isep.ipp.pt");
        Pharmacy p2 = new Pharmacy(3, "farmacia3", "Farmácia Tirori", 232.019, 41.1111, -8.9999, "admin@isep.ipp.pt");
        Address adress = new Address(34, 45, "rua xpto", 2, "4500", "espinho");
        Park park = new Park(1, 12, -8, 2, -1, 25, 2, 1);
        Park park1 = new Park(2, 12, 10, 2, 1, 25, 2, 1);
        List<Park> lst = new ArrayList<>();
        lst.add(park);
        lst.add(park1);
        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);
        when(parkHandlermock.getParkWithNPlaces(any(Integer.class))).thenReturn(lst);


        PharmacyDataHandler pharmacyDataHandlermock = mock(PharmacyDataHandler.class);
        when(pharmacyDataHandlermock.getPharmacyByID(p.getId())).thenReturn(p);
        when(pharmacyDataHandlermock.getPharmacyByID(any(Integer.class))).thenReturn(p2);


        AddressDataHandler addressDataHandlermock = mock(AddressDataHandler.class);
        when(addressDataHandlermock.getAddress(p.getLatitudePharmacy(), p.getLongitudePharmacy(), p.getAltitudePharmacy())).thenReturn(adress);
        when(addressDataHandlermock.getAddress(p2.getLatitudePharmacy(), p2.getLongitudePharmacy(), p2.getAltitudePharmacy())).thenReturn(adress);

        VehicleController vehicleController = new VehicleController(new VehicleHandler(), new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), pharmacyDataHandlermock, addressDataHandlermock);

        boolean result = vehicleController.parkDrone(2, new Vehicle(1, "AH-87-LK", 400, 28, 0, 1, 500, 8.0, 5000.0, 430, 4, 1, 10, 2.3));

        assertFalse(result);
    }

    @Test
    void writeInfo() throws IOException {
        File file = new File("test");
        FileWriter myWriter = new FileWriter(file);
        boolean result = instance.writeInfo(myWriter, new Park(2, 12, 10, 2, 1, 25, 2, 1), null, 3, 10, 2020, 10, 2, 20);
        myWriter.close();
        assertFalse(result);
        Files.delete(Paths.get(file.getAbsolutePath()));
    }

    @Test
    void testWriteInfo() throws IOException {
        boolean result = instance.writeInfo(new FileWriter("teste"), null, null, 10,20,30,10,10,10);
        assertFalse(result);
    }

    @Test
    void testWriteInfo2() throws IOException {
        boolean result = instance.writeInfo(new FileWriter("teste"), new Park(1,12,10,2,1,25,2,1), new Vehicle(1,"AH-87-LK",400,350,0,1,500,8.0,5000.0,430,4, 1,10,2.3), 10,20,30,10,10,10);
        assertTrue(result);
    }
}