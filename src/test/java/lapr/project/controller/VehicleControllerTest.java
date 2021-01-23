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
import java.util.LinkedList;
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
        when(vehicleHandlerMock.getAllScooterAvailables(any(Integer.class), any(Double.class))).thenReturn(vehicle);
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
    void testgetAvailableScooter(){
        Vehicle expResult = new Vehicle(1, "AB-56-DD", 50, 47, 0, 0, 33, 11,23,56,5, 1, 10,2.0);
        Courier c = new Courier(1, "joao@isep.pt", "joao", 1321213, new BigDecimal("37272"), 21, 70, 1);
        Vehicle result = instance.getAvailableScooter(1, c.getEmail());
        assertEquals(expResult, result);
    }

    @Test
    void testgetAvailableScooter2(){
        Vehicle expResult = new Vehicle(1, "AB-56-DD", 50, 47, 0, 0, 33, 11,23,56,5, 2, 10,2.0);
        Courier c = new Courier(2, "joao@isep.pt", "joao", 1321213, new BigDecimal("37272"), 21, 70, 1);
        Vehicle result = instance.getAvailableScooter(1, c.getEmail());
        assertEquals(expResult, result);
    }

    @Test
    void testgetAvailableScooter3(){
        Vehicle expResult = null;
        e.expect(NullPointerException.class);
    }
   /*
    @Test
    void testparkScooter() throws IOException {
       boolean expResult = true;
       boolean result = instance.parkScooter(5, new Vehicle(1, "AB-56-DD", 50, 47, 0, 0, 33, 11,23,56,5, 1, 40,2.0));
       assertEquals(expResult, result);
    }*/

    @Test
    void testgetAvailableVehicles() {
        List<Vehicle> expResult = new ArrayList<>();
        Vehicle v = new Vehicle("AB-56-DD", 45, 12, 33, 11,23,56,5, 1, 88);
        expResult.add(v);
        List<Vehicle> result = instance.getVehicles();

        assertEquals(expResult, result);
    }

    @Test
    void testgetVehicle() {
        Vehicle vehicle = new Vehicle("AB-56-DD", 45, 12, 33, 11,23,56,5, 1, 88);
        Vehicle result = instance.getVehicle(vehicle.getLicensePlate());
        assertEquals(vehicle, result);
    }

    @Test
    void testremoveVehicle() {
        Vehicle vehicle = new Vehicle("AB-56-DD", 45, 12, 33, 11,23,56,5, 1, 88);
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
        Vehicle scooter = new Vehicle("AB-56-DD", 50, 470, 0, 0, 4, 1);
        VehicleHandler vehicleHandlerMock = mock(VehicleHandler.class);
        when(vehicleHandlerMock.addVehicle(any(Vehicle.class))).thenReturn(Boolean.TRUE);
        VehicleController vehicleController = new VehicleController(vehicleHandlerMock, new DeliveryHandler(), new ParkHandler(), new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());
        boolean result = vehicleController.addVehicle(scooter.getLicensePlate(), scooter.getMaxBattery(), scooter.getEnginePower(), scooter.getAhBattery(), scooter.getvBattery(), scooter.getIdPharmacy(), scooter.getTypeVehicle());
        assertTrue(result);
    }

    @Test
    void testaddVehicle2() {
        Vehicle scooter = new Vehicle("AB-56-DD", 50, 470, 0, 0, 4, 1);
        VehicleHandler vehicleHandlerMock = mock(VehicleHandler.class);
        when(vehicleHandlerMock.addVehicle(any(Vehicle.class))).thenReturn(Boolean.FALSE);
        VehicleController vehicleController = new VehicleController(vehicleHandlerMock, new DeliveryHandler(), new ParkHandler(), new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());
        boolean result = vehicleController.addVehicle(scooter.getLicensePlate(), scooter.getMaxBattery(), scooter.getEnginePower(), scooter.getAhBattery(), scooter.getvBattery(), scooter.getIdPharmacy(), scooter.getTypeVehicle());
        assertFalse(result);
    }
    /*
    @Test
    void testgetParkMoreClose() {

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
    void testgetParkMoreClose2() {
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
        when(vehicleHandlermock.getAllScooterAvailables(any(Integer.class), any(Double.class))).thenReturn(lstVehicle);
        when(vehicleHandlermock.updateStatusToParked(any(String.class))).thenReturn(Boolean.TRUE);
        when(vehicleHandlermock.updateIsChargingN(any(String.class))).thenReturn(Boolean.TRUE);
        when(vehicleHandlermock.associateVehicleToDelivery(any(Integer.class), any(String.class))).thenReturn(Boolean.TRUE);

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
        when(parkHandlermock.updateActualCapacityR(any(Integer.class))).thenReturn(Boolean.TRUE);

        VehicleHandler vehicleHandlermock = mock(VehicleHandler.class);
        when(vehicleHandlermock.getVehicle(any(String.class))).thenReturn(v);
        when(vehicleHandlermock.updateStatusToParked(any(String.class))).thenReturn(Boolean.TRUE);
        when(vehicleHandlermock.updateIsChargingY(any(String.class))).thenReturn(Boolean.TRUE);

        UserSession.getInstance().setUser(new User("admin@isep.ipp.pt","qwerty","Administrator"));

        VehicleController vehicleController = new VehicleController(vehicleHandlermock, new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());
        boolean result = vehicleController.parkScooter(1, v);
        assertTrue(result);
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

        UserSession.getInstance().setUser(new User("admin@isep.ipp.pt","qwerty","Administrator"));
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
        boolean result = vehicleController.parkScooter(1, new Vehicle(1, "AB-56-DD", 50, 47, 0, 0, 33, 11,23,56,5, 1, 40,2.0));
        assertFalse(result);
    }

    @Test
    void testParkDrone() throws IOException {
        Vehicle v = null;
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);

        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);

        VehicleHandler vehicleHandlermock = mock(VehicleHandler.class);
        when(vehicleHandlermock.getVehicle(any(String.class))).thenReturn(v);
        VehicleController vehicleController = new VehicleController(vehicleHandlermock, new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());

        boolean result = vehicleController.parkDrone(2,v);
        boolean expectedResult = false;

        assertEquals(expectedResult,result);


    }

    @Test
    void testParkDrone2() throws IOException {
        Vehicle v = new Vehicle(1, "AH-87-LK", 400, 370, 0, 1, 500, 8.0, 5000.0, 430, 4, 1, 10, 2.3);
        Park park = new Park(1, 12, 10, 2, 19, 25, 2, 1);

        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);
        when(parkHandlermock.updateActualCapacityR(any(Integer.class))).thenReturn(Boolean.TRUE);

        VehicleHandler vehicleHandlermock = mock(VehicleHandler.class);
        when(vehicleHandlermock.getVehicle(any(String.class))).thenReturn(v);
        when(vehicleHandlermock.updateStatusToParked(any(String.class))).thenReturn(Boolean.TRUE);
        when(vehicleHandlermock.updateIsChargingY(any(String.class))).thenReturn(Boolean.TRUE);

        UserSession.getInstance().setUser(new User("admin@isep.ipp.pt","qwerty","Administrator"));

        VehicleController vehicleController = new VehicleController(vehicleHandlermock, new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());

        boolean result = vehicleController.parkDrone(2,v);
        boolean expectedResult = false;

        assertEquals(expectedResult,result);


    }

    @Test
    void getAnotherParkToPark() {
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        Park park2 = new Park(2, 12, 10, 2, 1, 25, 2, 1);
        Pharmacy p = new Pharmacy(2,"test","test",23123,1241,214,"test");
        Pharmacy p2 = new Pharmacy(4,"test2","test2",231,12.21,0,"test");

        Address address = new Address(34, 45,"rua xpto", 2, "4500", "espinho");
        List<Park> listNormalParksD=new LinkedList<>();
        listNormalParksD.add(park);
        listNormalParksD.add(park2);

        VehicleController vehicleControllerMock = mock(VehicleController.class);
        when(vehicleControllerMock.getParkMoreClose(listNormalParksD,p.getId())).thenReturn(park);

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
        assertEquals(expectedResult,result);
    }

    @Test
    void getAnotherParkToPark2() {
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        Pharmacy p = new Pharmacy(2,"test","test",23123,1241,214,"test");

        Address address = new Address(34, 45,"rua xpto", 2, "4500", "espinho");
        List<Park> listNormalParksD=new LinkedList<>();
        listNormalParksD.add(park);

        VehicleController vehicleControllerMock = mock(VehicleController.class);
        when(vehicleControllerMock.getParkMoreClose(listNormalParksD,p.getId())).thenReturn(park);

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
        assertEquals(expectedResult,result);
    }

    @Test
    void parkVehicleInNormalPlaces() throws IOException {
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.updateActualCapacityR(any(Integer.class))).thenReturn(Boolean.TRUE);

        VehicleHandler vehicleHandlerMock = mock(VehicleHandler.class);
        when(vehicleHandlerMock.addVehicle(any(Vehicle.class))).thenReturn(Boolean.TRUE);

        Vehicle scooter = new Vehicle("AB-56-DD", 50, 470, 0, 0, 4, 1);

        boolean result =instance.parkVehicleInNormalPlaces(scooter,park,park.getPharmacyID(),scooter.getAhBattery(),scooter.getMaxBattery(), scooter.getActualBattery());
        assertFalse(result);

    }

    @Test
    void parkVehicleInNormalPlaces2() throws IOException {
        Vehicle vehicle = new Vehicle(1,"AH-87-LK",400,350,0,1,500,8.0,5000.0,430,4, 1,10,2.3);
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.updateActualCapacityR(any(Integer.class))).thenReturn(Boolean.TRUE);

        VehicleHandler vehicleHandlerMock = mock(VehicleHandler.class);
        when(vehicleHandlerMock.addVehicle(any(Vehicle.class))).thenReturn(Boolean.TRUE);
        when(vehicleHandlerMock.updateStatusToParked(any(String.class))).thenReturn(Boolean.TRUE);
        VehicleController vehicleController = new VehicleController(vehicleHandlerMock, new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());

        boolean result = vehicleController.parkVehicleInNormalPlaces(vehicle, park, park.getPharmacyID(), vehicle.getAhBattery(),vehicle.getMaxBattery(), vehicle.getActualBattery());

        assertTrue(result);

    }

    @Test
    void parkVehicleInChargingPlaces() throws IOException {
        Vehicle vehicle = new Vehicle(1,"AH-87-LK",400,350,0,1,500,8.0,5000.0,430,4, 1,10,2.3);
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.updateChargingPlacesR(any(Integer.class))).thenReturn(Boolean.TRUE);

        VehicleHandler vehicleHandlerMock = mock(VehicleHandler.class);
        when(vehicleHandlerMock.addVehicle(any(Vehicle.class))).thenReturn(Boolean.TRUE);
        when(vehicleHandlerMock.updateStatusToParked(any(String.class))).thenReturn(Boolean.TRUE);
        when(vehicleHandlerMock.updateIsChargingY(any(String.class))).thenReturn(Boolean.TRUE);

        VehicleController vehicleController = new VehicleController(vehicleHandlerMock, new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());

        boolean result = vehicleController.parkVehicleInChargingPlaces(vehicle, park, park.getPharmacyID(), vehicle.getAhBattery(),vehicle.getMaxBattery(), vehicle.getActualBattery());

        assertTrue(result);
    }

    @Test
    void parkVehicleInChargingPlaces2() throws IOException {
        Vehicle vehicle = new Vehicle(1,"AH-87-LK",400,350,0,1,500,8.0,5000.0,430,4, 1,10,2.3);
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.updateChargingPlacesR(any(Integer.class))).thenReturn(Boolean.FALSE);

        VehicleHandler vehicleHandlerMock = mock(VehicleHandler.class);
        when(vehicleHandlerMock.updateStatusToParked(any(String.class))).thenReturn(Boolean.FALSE);
        when(vehicleHandlerMock.updateIsChargingY(any(String.class))).thenReturn(Boolean.FALSE);

        UserSession.getInstance().setUser(new User("admin@isep.ipp.pt","qwerty","Administrator"));

        VehicleController vehicleController = new VehicleController(vehicleHandlerMock, new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());

        boolean result = vehicleController.parkVehicleInChargingPlaces(vehicle, park, park.getPharmacyID(), vehicle.getAhBattery(),vehicle.getMaxBattery(), vehicle.getActualBattery());

        assertFalse(result);
    }

    @Test
    void parkVehicleInChargingPlaces3() throws IOException {
        Vehicle vehicle = new Vehicle(1,"AH-87-LK",400,350,0,1,500,8.0,5000.0,430,4, 1,10,2.3);
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.updateChargingPlacesR(any(Integer.class))).thenReturn(Boolean.FALSE);

        VehicleHandler vehicleHandlerMock = mock(VehicleHandler.class);
        when(vehicleHandlerMock.updateStatusToParked(any(String.class))).thenReturn(Boolean.TRUE);
        when(vehicleHandlerMock.updateIsChargingY(any(String.class))).thenReturn(Boolean.FALSE);

        UserSession.getInstance().setUser(new User("admin@isep.ipp.pt","qwerty","Administrator"));

        VehicleController vehicleController = new VehicleController(vehicleHandlerMock, new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());

        boolean result = vehicleController.parkVehicleInChargingPlaces(vehicle, park, park.getPharmacyID(), vehicle.getAhBattery(),vehicle.getMaxBattery(), vehicle.getActualBattery());

        assertFalse(result);
    }

    @Test
    void parkVehicleInChargingPlaces4() throws IOException {
        Vehicle vehicle = new Vehicle(1,"AH-87-LK",400,350,0,1,500,8.0,5000.0,430,4, 1,10,2.3);
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.updateChargingPlacesR(any(Integer.class))).thenReturn(Boolean.FALSE);

        VehicleHandler vehicleHandlerMock = mock(VehicleHandler.class);
        when(vehicleHandlerMock.updateStatusToParked(any(String.class))).thenReturn(Boolean.FALSE);
        when(vehicleHandlerMock.updateIsChargingY(any(String.class))).thenReturn(Boolean.TRUE);

        UserSession.getInstance().setUser(new User("admin@isep.ipp.pt","qwerty","Administrator"));

        VehicleController vehicleController = new VehicleController(vehicleHandlerMock, new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());

        boolean result = vehicleController.parkVehicleInChargingPlaces(vehicle, park, park.getPharmacyID(), vehicle.getAhBattery(),vehicle.getMaxBattery(), vehicle.getActualBattery());

        assertFalse(result);
    }

    @Test
    void parkVehicleInChargingPlaces5() throws IOException {
        Vehicle vehicle = new Vehicle(1,"AH-87-LK",400,350,0,1,500,8.0,5000.0,430,4, 1,10,2.3);
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.updateChargingPlacesR(any(Integer.class))).thenReturn(Boolean.TRUE);

        VehicleHandler vehicleHandlerMock = mock(VehicleHandler.class);
        when(vehicleHandlerMock.updateStatusToParked(any(String.class))).thenReturn(Boolean.FALSE);
        when(vehicleHandlerMock.updateIsChargingY(any(String.class))).thenReturn(Boolean.FALSE);

        UserSession.getInstance().setUser(new User("admin@isep.ipp.pt","qwerty","Administrator"));

        VehicleController vehicleController = new VehicleController(vehicleHandlerMock, new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());

        boolean result = vehicleController.parkVehicleInChargingPlaces(vehicle, park, park.getPharmacyID(), vehicle.getAhBattery(),vehicle.getMaxBattery(), vehicle.getActualBattery());

        assertFalse(result);
    }

    @Test
    void parkVehicleInChargingPlaces6() throws IOException {
        Vehicle vehicle = new Vehicle(1,"AH-87-LK",400,350,0,1,500,8.0,5000.0,430,4, 1,10,2.3);
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.updateChargingPlacesR(any(Integer.class))).thenReturn(Boolean.FALSE);

        VehicleHandler vehicleHandlerMock = mock(VehicleHandler.class);
        when(vehicleHandlerMock.updateStatusToParked(any(String.class))).thenReturn(Boolean.TRUE);
        when(vehicleHandlerMock.updateIsChargingY(any(String.class))).thenReturn(Boolean.TRUE);

        UserSession.getInstance().setUser(new User("admin@isep.ipp.pt","qwerty","Administrator"));

        VehicleController vehicleController = new VehicleController(vehicleHandlerMock, new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());

        boolean result = vehicleController.parkVehicleInChargingPlaces(vehicle, park, park.getPharmacyID(), vehicle.getAhBattery(),vehicle.getMaxBattery(), vehicle.getActualBattery());

        assertFalse(result);
    }

    @Test
    void parkVehicleInChargingPlaces7() throws IOException {
        Vehicle vehicle = new Vehicle(1,"AH-87-LK",400,350,0,1,500,8.0,5000.0,430,4, 1,10,2.3);
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.updateChargingPlacesR(any(Integer.class))).thenReturn(Boolean.TRUE);

        VehicleHandler vehicleHandlerMock = mock(VehicleHandler.class);
        when(vehicleHandlerMock.updateStatusToParked(any(String.class))).thenReturn(Boolean.TRUE);
        when(vehicleHandlerMock.updateIsChargingY(any(String.class))).thenReturn(Boolean.FALSE);

        UserSession.getInstance().setUser(new User("admin@isep.ipp.pt","qwerty","Administrator"));

        VehicleController vehicleController = new VehicleController(vehicleHandlerMock, new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());

        boolean result = vehicleController.parkVehicleInChargingPlaces(vehicle, park, park.getPharmacyID(), vehicle.getAhBattery(),vehicle.getMaxBattery(), vehicle.getActualBattery());

        assertFalse(result);
    }

    @Test
    void parkVehicleInChargingPlaces8() throws IOException {
        Vehicle vehicle = new Vehicle(1,"AH-87-LK",400,350,0,1,500,8.0,5000.0,430,4, 1,10,2.3);
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.updateChargingPlacesR(any(Integer.class))).thenReturn(Boolean.TRUE);

        VehicleHandler vehicleHandlerMock = mock(VehicleHandler.class);
        when(vehicleHandlerMock.updateStatusToParked(any(String.class))).thenReturn(Boolean.FALSE);
        when(vehicleHandlerMock.updateIsChargingY(any(String.class))).thenReturn(Boolean.FALSE);

        UserSession.getInstance().setUser(new User("admin@isep.ipp.pt","qwerty","Administrator"));

        VehicleController vehicleController = new VehicleController(vehicleHandlerMock, new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());

        boolean result = vehicleController.parkVehicleInChargingPlaces(vehicle, park, park.getPharmacyID(), vehicle.getAhBattery(),vehicle.getMaxBattery(), vehicle.getActualBattery());

        assertFalse(result);
    }

    @Test
    void getAnotherParkToCharge() {
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        Park park2 = new Park(2, 12, 10, 2, 1, 25, 2, 1);
        Pharmacy p = new Pharmacy(2,"test","test",23123,1241,214,"test");
        Pharmacy p2 = new Pharmacy(4,"test2","test2",231,12.21,0,"test");

        Address address = new Address(34, 45,"rua xpto", 2, "4500", "espinho");
        List<Park> listNormalParksD=new LinkedList<>();
        listNormalParksD.add(park);
        listNormalParksD.add(park2);

        VehicleController vehicleControllerMock = mock(VehicleController.class);
        when(vehicleControllerMock.getParkMoreClose(listNormalParksD,p.getId())).thenReturn(park);

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
        assertEquals(expectedResult,result);
    }

    @Test
    void getAnotherParkToCharge2() {
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        Pharmacy p = new Pharmacy(2,"test","test",23123,1241,214,"test");
        Pharmacy p2 = new Pharmacy(4,"test2","test2",231,12.21,0,"test");

        Address address = new Address(34, 45,"rua xpto", 2, "4500", "espinho");
        List<Park> listNormalParksD=new LinkedList<>();
        listNormalParksD.add(park);

        VehicleController vehicleControllerMock = mock(VehicleController.class);
        when(vehicleControllerMock.getParkMoreClose(listNormalParksD,p.getId())).thenReturn(park);

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
        assertEquals(expectedResult,result);
    }

    /*
    @Test
    void sendEmailNotification2() throws IOException {
        Pharmacy p = new Pharmacy(1,"test","test@gmail.com",22321,1231,123,"email@gmail.com");        Vehicle v = new Vehicle(1, "AH-87-LK", 400, 10, 0, 1, 500, 8.0, 5000.0, 430, 4, 1, 10, 2.3);
        Vehicle vehicle = new Vehicle(1, "AH-87-LK", 400, 10, 0, 1, 500, 8.0, 5000.0, 430, 4, 1, 10, 2.3);

        ParkHandler parkHandlermock = mock(ParkHandler.class);
        VehicleHandler vehicleHandlermock = mock(VehicleHandler.class);

        VehicleController vehicleController = new VehicleController(vehicleHandlermock, new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());

       boolean result = vehicleController.sendEmailNotification(p.getId(),vehicle);
       boolean expectedResult = true;

       assertEquals(expectedResult, result);
    }
*/



    @Test
    void parkDrone2() throws IOException {
        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(null);

        VehicleController vehicleController = new VehicleController(new VehicleHandler(), new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());

        boolean result = vehicleController.parkDrone(2, new Vehicle(1,"AH-87-LK",400,350,0,1,500,8.0,5000.0,430,4, 1,10,2.3));

        assertFalse(result);
    }

    @Test
    void parkDrone3() throws IOException {
        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(null);

        VehicleController vehicleController = new VehicleController(new VehicleHandler(), new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());

        boolean result = vehicleController.parkDrone(2, null);

        assertFalse(result);
    }

    @Test
    void parkDrone4() throws IOException {
        Park park = new Park(1, 12, 10, 2, 1, 25, 2, 1);
        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);

        VehicleController vehicleController = new VehicleController(new VehicleHandler(), new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());

        boolean result = vehicleController.parkDrone(2, null);

        assertFalse(result);
    }

    @Test
    void parkDrone6() throws IOException {
        Park park = new Park(1, 12, 10, 2, -1, 25, 2, 1);
        ParkHandler parkHandlermock = mock(ParkHandler.class);
        when(parkHandlermock.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);

        VehicleController vehicleController = new VehicleController(new VehicleHandler(), new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());

        boolean result = vehicleController.parkDrone(2,  new Vehicle(1,"AH-87-LK",400,28,0,1,500,8.0,5000.0,430,4, 1,10,2.3));

        assertTrue(result);
    }

    @Test
    void parkDrone7() throws IOException {
        Pharmacy p = new Pharmacy(4,"farmacia", "Farmácia Tirori",232.019, 41.1111, -8.9999, "admin@isep.ipp.pt");
        Pharmacy p2 = new Pharmacy(3,"farmacia3", "Farmácia Tirori",232.019, 41.1111, -8.9999, "admin@isep.ipp.pt");
        Address adress = new Address(34, 45,"rua xpto", 2, "4500", "espinho");
        Park park = new Park(1, 12, -8, 2, -1, 25, 2, 1);
        Park park1 = new Park(2,12,10,2,1,25,2,1);
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
        when(addressDataHandlermock.getAddress(p.getLatitude(), p.getLongitude(), p.getAltitude())).thenReturn(adress);
        when(addressDataHandlermock.getAddress(p2.getLatitude(), p2.getLongitude(), p2.getAltitude())).thenReturn(adress);

        VehicleController vehicleController = new VehicleController(new VehicleHandler(), new DeliveryHandler(), parkHandlermock, new CourierDataHandler(), pharmacyDataHandlermock, addressDataHandlermock);

        boolean result = vehicleController.parkDrone(2,  new Vehicle(1,"AH-87-LK",400,28,0,1,500,8.0,5000.0,430,4, 1,10,2.3));

        assertFalse(result);
    }
}