package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.utils.Physics;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        Pharmacy phar = new Pharmacy(4,"farmacia", "Farmácia Tirori",232.019, 41.1111, -8.9999, "admin@isep.ipp.pt");
        Park park = new Park(1, 5,5, 5,5,5,5, 1);
        Vehicle scooter = new Vehicle(1, "AB-56-DD", 50, 47, 0, 0, 33, 11,23,56,5, 1, 40,2.0);
        vehicle.add(scooter);
        Vehicle drone = new Vehicle(1, "AB-56-DD", 50, 47, 0, 0, 33, 11,23,56,5, 1, 150,2.0);
        when(vehicleHandlerMock.getAllVehicles()).thenReturn(vehicle);
        when(vehicleHandlerMock.getAllScooterAvailables(any(Integer.class))).thenReturn(vehicle);
        when(vehicleHandlerMock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);
        Courier courier = new Courier(1, "Joao");
        Delivery delivery = new Delivery(45, 333, 23,1,"AK-LA-09");
        when(deliveryHandlerMock.getDeliveryByCourierId(courier.getIdCourier())).thenReturn(delivery);
        when(vehicleHandlerMock.getParkByPharmacyId(phar.getId(), park.getIdParktype())).thenReturn(park);
        when(vehicleHandlerMock.getVehicle(any(String.class))).thenReturn(scooter);
        when(vehicleHandlerMock.removeVehicle(any(String.class))).thenReturn(Boolean.TRUE);
        when(parkDataHandlerMock.getParkByPharmacyId(5,1)).thenReturn(park);
        when(vehicleHandlerMock.removeVehicle("AB-56-DD")).thenReturn(Boolean.TRUE);
        instance = new VehicleController(vehicleHandlerMock, deliveryHandlerMock, parkDataHandlerMock,courierDataHandlerMock,pharmacyDataHandlerMock, new AddressDataHandler());
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
       boolean result = instance.parkScooter(5, new Vehicle(1, "AB-56-DD", 50, 47, 0, 0, 33, 11,23,56,5, 1, 40,2.0));
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
        VehicleHandler vehicleHandlerMock = mock(VehicleHandler.class);
        when(vehicleHandlerMock.addVehicle(any(Vehicle.class))).thenReturn(Boolean.TRUE);
        VehicleController vehicleController = new VehicleController(vehicleHandlerMock, new DeliveryHandler(), new ParkHandler(), new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());
        boolean result = vehicleController.addVehicle(scooter.getLicensePlate(), scooter.getMaxBattery(), scooter.getEnginePower(), scooter.getAhBattery(), scooter.getvBattery(), scooter.getIdPharmacy(), scooter.getTypeVehicle());
        assertEquals(true, result);
    }

    @Test
    void addVehicle2() {
        Vehicle scooter = new Vehicle("AB-56-DD", 50, 470, 0, 0, 4, 1);
        VehicleHandler vehicleHandlerMock = mock(VehicleHandler.class);
        when(vehicleHandlerMock.addVehicle(any(Vehicle.class))).thenReturn(Boolean.FALSE);
        VehicleController vehicleController = new VehicleController(vehicleHandlerMock, new DeliveryHandler(), new ParkHandler(), new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());
        boolean result = vehicleController.addVehicle(scooter.getLicensePlate(), scooter.getMaxBattery(), scooter.getEnginePower(), scooter.getAhBattery(), scooter.getvBattery(), scooter.getIdPharmacy(), scooter.getTypeVehicle());
        assertEquals(false, result);
    }
    /*
    @Test
    void getParkMoreClose() {

        Address address = new Address(1231.91, 281.091, "xxxxx", 21, "2490-201", "Porto");
        Address address2 = new Address(10131.91, 28211.091, "xxxxx", 21, "2490-201", "Porto");
        Pharmacy pharmacy = new Pharmacy(2,"farmacia", "Farmácia Tirori",1231.91, 281.091, 0, "admin@isep.ipp.pt");
        Pharmacy pharmacy2 = new Pharmacy(1,"farmacia", "Farmácia Tirori",2321.019, 413.1111, -81.9999, "admin@isep.ipp.pt");
        Park park = new Park(1,12,10,2,1,25,2,1);
        List<Park> parks = new ArrayList<>();
        parks.add(park);

        AddressDataHandler addressDataHandler = mock(AddressDataHandler.class);
        when(addressDataHandler.getAddress(pharmacy.getLatitude(), pharmacy.getLongitude(),pharmacy.getAltitude())).thenReturn(address);
        when(addressDataHandler.getAddress(pharmacy2.getLatitude(), pharmacy2.getLongitude(),pharmacy2.getAltitude())).thenReturn(address2);
        PharmacyDataHandler pharmacyDataHandler = mock(PharmacyDataHandler.class);
        when(pharmacyDataHandler.getPharmacyByID(any(Integer.class))).thenReturn(pharmacy2);


        Park result = instance.getParkMoreClose(parks,  1);
        int expectedResult=1;


        assertEquals(expectedResult, result);



    }*/

    @Test
    void getParkMoreClose2() {
        /*
        Address address = new Address(232.019, 41.1111, "xxxxx", 21, "2490-201", "Porto");
        Address address2 = new Address(213, 1, "xxxxx", 21, "2490-201", "Porto");
        Pharmacy pharmacy = new Pharmacy(4,"farmacia", "Farmácia Tirori",232.019, 41.1111, -8.9999, "admin@isep.ipp.pt");
        Pharmacy pharmacy2 = new Pharmacy(10,"farmacia", "Farmácia Tirori",213, 1, -981, "admin@isep.ipp.pt");
        Park park = new Park(1,12,10,2,1,25,2,1);
        Park park2 = new Park(4,12,10,2,1,25,2,1);
        List<Park> parks = new ArrayList<>();
        parks.add(park);
        parks.add(park2);

        AddressDataHandler addressDataHandler = mock(AddressDataHandler.class);
        when(addressDataHandler.getAddress(pharmacy.getLatitude(), pharmacy.getLongitude())).thenReturn(address);
        when(addressDataHandler.getAddress(pharmacy2.getLatitude(), pharmacy2.getLongitude())).thenReturn(address2);
        PharmacyDataHandler pharmacyDataHandler = mock(PharmacyDataHandler.class);
        when(pharmacyDataHandler.getPharmacyByID(pharmacy2.getId())).thenReturn(pharmacy2);
        when(pharmacyDataHandler.getPharmacyByID(pharmacy.getId())).thenReturn(pharmacy);


        VehicleController vehicleController = new VehicleController(new VehicleHandler(), new DeliveryHandler(),new ParkHandler(), new CourierDataHandler(), pharmacyDataHandler, addressDataHandler);
        Park result = vehicleController.getParkMoreClose(parks,  1);
        
        //assertEquals(park2, result);  TODO*/


    }

    @Test
    void testGetAvailableScooter() {
        Courier c = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigDecimal("24586612344"),15,70,1);
        Delivery delivery = new Delivery(1, 25, 30, 10);
        Vehicle v = new Vehicle(1,"AH-87-LK",400,350,0,1,500,8.0,5000.0,430,4, 1,10,2.3);
        Park park = new Park(1,12,10,2,1,25,2,1);
        List<Vehicle> lstVehicle = new ArrayList<>();
        lstVehicle.add(v);

        CourierDataHandler courierDataHandlermock = mock(CourierDataHandler.class);
        when(courierDataHandlermock.getCourierByEmail(any(String.class))).thenReturn(c);

        ParkHandler parkHandlermock = mock(ParkHandler.class);
        doNothing().when(parkHandlermock).updateActualChargingPlacesA(any(Integer.class));

        DeliveryHandler deliveryHandlermock = mock(DeliveryHandler.class);
        when(deliveryHandlermock.getDeliveryByCourierId(any(Integer.class))).thenReturn(delivery);

        VehicleHandler vehicleHandlermock = mock(VehicleHandler.class);
        when(vehicleHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);
        when(vehicleHandlermock.getAllScooterAvailables(any(Integer.class))).thenReturn(lstVehicle);
        doNothing().when(vehicleHandlermock).updateStatusToParked(any(String.class));
        doNothing().when(vehicleHandlermock).updateIsChargingN(any(String.class));
        doNothing().when(vehicleHandlermock).associateVehicleToDelivery(any(Integer.class), any(String.class));

        VehicleController vehicleController = new VehicleController(vehicleHandlermock, deliveryHandlermock, parkHandlermock, courierDataHandlermock, new PharmacyDataHandler(), new AddressDataHandler());
        Vehicle result = vehicleController.getAvailableScooter(c.getIdCourier(), c.getEmail());
        assertEquals(v, result);
    }

    @Test
    void testGetAvailableScooter2() {
        Courier c = new Courier(1,"courier@isep.ipp.pt","André",122665789,
                new BigDecimal("24586612344"),15,70,1);
        Delivery delivery = new Delivery(1, 10000, 30, 10);
        Vehicle v = new Vehicle(1,"AH-87-LK",400,350,0,1,500,8.0,5000.0,430,4, 1,10,2.3);
        Park park = new Park(1,12,10,2,1,25,2,1);
        List<Vehicle> lstVehicle = new ArrayList<>();
        lstVehicle.add(v);

        CourierDataHandler courierDataHandlermock = mock(CourierDataHandler.class);
        when(courierDataHandlermock.getCourierByEmail(any(String.class))).thenReturn(c);

        ParkHandler parkHandlermock = mock(ParkHandler.class);
        doNothing().when(parkHandlermock).updateActualChargingPlacesA(any(Integer.class));

        DeliveryHandler deliveryHandlermock = mock(DeliveryHandler.class);
        when(deliveryHandlermock.getDeliveryByCourierId(any(Integer.class))).thenReturn(delivery);

        VehicleHandler vehicleHandlermock = mock(VehicleHandler.class);
        when(vehicleHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);
        when(vehicleHandlermock.getAllScooterAvailables(any(Integer.class))).thenReturn(lstVehicle);
        doNothing().when(vehicleHandlermock).updateStatusToParked(any(String.class));
        doNothing().when(vehicleHandlermock).updateIsChargingN(any(String.class));
        doNothing().when(vehicleHandlermock).associateVehicleToDelivery(any(Integer.class), any(String.class));

        VehicleController vehicleController = new VehicleController(vehicleHandlermock, deliveryHandlermock, parkHandlermock, courierDataHandlermock, new PharmacyDataHandler(), new AddressDataHandler());
        Vehicle result = vehicleController.getAvailableScooter(c.getIdCourier(), c.getEmail());
        assertNull(result);
    }

    @Test
    void testParkScooter() throws IOException {
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        Vehicle v = new Vehicle(1, "AH-87-LK", 400, 350, 0, 1, 500, 8.0, 5000.0, 430, 4, 1, 10, 2.3);

        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);
        doNothing().when(parkHandlermock).updateActualCapacityR(any(Integer.class));

        VehicleHandler vehicleHandlermock = mock(VehicleHandler.class);
        when(vehicleHandlermock.getVehicle(any(String.class))).thenReturn(v);
        doNothing().when(vehicleHandlermock).updateStatusToParked(any(String.class));
        doNothing().when(vehicleHandlermock).updateIsChargingY(any(String.class));

        VehicleController vehicleController = new VehicleController(vehicleHandlermock, new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());
        boolean result = vehicleController.parkScooter(1, v);
        assertTrue(result);
    }

    @Test
    void testParkScooter2() throws IOException {

        Park park = new Park(1, 12, 10, 2, 0, 25, 2, 1);
        Vehicle v = new Vehicle(1, "AH-87-LK", 400, 5, 0, 1, 500, 8.0, 5000.0, 430, 4, 1, 10, 2.3);

        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);
        doNothing().when(parkHandlermock).updateActualCapacityR(any(Integer.class));

        VehicleHandler vehicleHandlermock = mock(VehicleHandler.class);
        when(vehicleHandlermock.getVehicle(any(String.class))).thenReturn(v);
        doNothing().when(vehicleHandlermock).updateStatusToParked(any(String.class));
        doNothing().when(vehicleHandlermock).updateIsChargingY(any(String.class));

        VehicleController vehicleController = new VehicleController(vehicleHandlermock, new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());
   //     boolean result = vehicleController.parkScooter(1, v.getLicensePlate());
     //   assertFalse(result);
    }

    @Test
    void testParkScooter3() throws IOException {
        Park park = new Park(1, 12, 10, 2, 0, 25, 2, 1);
        Vehicle v = new Vehicle(1, "AH-87-LK", 400, 10, 0, 1, 500, 8.0, 5000.0, 430, 4, 1, 10, 2.3);

        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);
        doNothing().when(parkHandlermock).updateActualCapacityR(any(Integer.class));

        VehicleHandler vehicleHandlermock = mock(VehicleHandler.class);
        when(vehicleHandlermock.getVehicle(any(String.class))).thenReturn(v);
        doNothing().when(vehicleHandlermock).updateStatusToParked(any(String.class));
        doNothing().when(vehicleHandlermock).updateIsChargingY(any(String.class));

        VehicleController vehicleController = new VehicleController(vehicleHandlermock, new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());
        boolean result = vehicleController.parkScooter(1, v);
        assertTrue(result);
    }

    @Test
    void testParkScooter4() throws IOException {
        Park park = new Park(1, 12, 0, 2, 0, 25, 2, 1);
        Vehicle v = new Vehicle(1, "AH-87-LK", 400, 10, 0, 1, 500, 8.0, 5000.0, 430, 4, 1, 10, 2.3);

        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);
        doNothing().when(parkHandlermock).updateActualCapacityR(any(Integer.class));

        VehicleHandler vehicleHandlermock = mock(VehicleHandler.class);
        when(vehicleHandlermock.getVehicle(any(String.class))).thenReturn(v);
        doNothing().when(vehicleHandlermock).updateStatusToParked(any(String.class));
        doNothing().when(vehicleHandlermock).updateIsChargingY(any(String.class));

        VehicleController vehicleController = new VehicleController(vehicleHandlermock, new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());
        //boolean result = vehicleController.parkScooter(1, v.getLicensePlate());
        //assertFalse(result);
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
        boolean result = vehicleController.parkScooter(1, new Vehicle(1, "AB-56-DD", 50, 47, 0, 0, 33, 11,23,56,5, 1, 40,2.0));
        assertFalse(result);
    }


    @Test
    void parkDrone() {

    }
}