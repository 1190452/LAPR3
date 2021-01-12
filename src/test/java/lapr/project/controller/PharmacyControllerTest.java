package lapr.project.controller;

import lapr.project.data.ParkHandler;
import lapr.project.data.PharmacyDataHandler;
import lapr.project.data.UserSession;
import lapr.project.model.Administrator;
import lapr.project.model.Park;
import lapr.project.model.Pharmacy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PharmacyControllerTest {
    private static PharmacyController instance;
    private PharmacyDataHandler pharmacyDataHandler;
    @Test
    void PharmacyController() {
        pharmacyDataHandler = new PharmacyDataHandler();
    }

    @BeforeAll
    public static void setUpClass() {
        PharmacyDataHandler pharmacyDataHandlerMock = mock(PharmacyDataHandler.class);
        ParkHandler parkHandler = mock(ParkHandler.class);

        Pharmacy phar = new Pharmacy(5, "ISEP", 2323, 23323, "isep@isep.ipp.pt");
        when(pharmacyDataHandlerMock.addPharmacy(any(Pharmacy.class))).thenReturn(Boolean.TRUE);
        when(pharmacyDataHandlerMock.getPharmacyByID(any(Integer.class))).thenReturn(phar);
        when(pharmacyDataHandlerMock.getPharmacyByName(any(String.class))).thenReturn(phar);

        Park park = new Park(1,12,10,2,1,25,2,1);
        when(parkHandler.addPark(any(Park.class))).thenReturn(Boolean.TRUE);
        when(parkHandler.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);

        instance = new PharmacyController(pharmacyDataHandlerMock,parkHandler);

    }

    @Test
    void addPharmacy() {
        boolean expResult = false;
        Pharmacy pharmacy = new Pharmacy(5, "ISEP", 2323, 23323, "isep@isep.ipp.pt");
        boolean result = instance.addPharmacy(pharmacy.getName(),pharmacy.getLatitude(),pharmacy.getLongitude(),pharmacy.getEmailAdministrator());
        assertEquals(expResult, result);
    }

    @Test
    void getPharmacyByID() {
        Pharmacy pharmacy = new Pharmacy(5, "ISEP", 2323, 23323, "isep@isep.ipp.pt");
        Pharmacy result = instance.getPharmacyByID(pharmacy.getId());
        assertEquals(pharmacy, result);
    }

    @Test
    void getPharmacyByName() {
        Pharmacy pharmacy = new Pharmacy(5, "ISEP", 2323, 23323, "isep@isep.ipp.pt");
        Pharmacy result = instance.getPharmacyByName(pharmacy.getName());
        assertEquals(pharmacy, result);
    }


    @Test
    void registerPharmacyandPark() {
        Administrator a = new Administrator("dnjnsdf", "ola", "ADMINISTRATOR");
        UserSession.getInstance().setUser(a);
        String name = "ola";
        double latitude = 3;
        double longitude = 5;
        String street = "dsfs";
        int doorNumber = 4;
        String zipCode = "4600";
        String locality = "Lobao";
        int maxCpacity = 8;
        int maxChargingCapacity = 5;
        int actualChargingCapacity = 5;
        int power = 56;
        int parkIdType = 1;
        boolean expResult = false;
        boolean result = instance.registerPharmacyandPark(name, latitude, longitude, street, doorNumber, zipCode, locality, maxCpacity, maxChargingCapacity,
                actualChargingCapacity, power,parkIdType);
        assertEquals(expResult, result);
    }

    @Test
    void addPark() {
        boolean expResult = false;
        Park park = new Park(1,12,10,2,1,25,2,1);
        boolean result = instance.addPark(park.getMaxCapacity(),park.getMaxChargingPlaces(),park.getActualChargingPlaces(),park.getPower(),park.getPharmacyID(),park.getIdParktype());
        assertEquals(expResult, result);

    }

    @Test
    void getPark() {
        Park park = new Park(1,12,10,2,1,25,2,1);
        Park result = instance.getPark(park.getPharmacyID(), park.getIdParktype());
        assertEquals(park, result);
    }
}