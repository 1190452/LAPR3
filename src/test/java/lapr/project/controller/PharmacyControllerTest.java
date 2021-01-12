package lapr.project.controller;

import lapr.project.data.PharmacyDataHandler;
import lapr.project.data.UserSession;
import lapr.project.model.Administrator;
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

        Pharmacy phar = new Pharmacy(5, "ISEP", 2323, 23323, "isep@isep.ipp.pt");
        when(pharmacyDataHandlerMock.addPharmacy(any(Pharmacy.class))).thenReturn(Boolean.TRUE);
        when(pharmacyDataHandlerMock.getPharmacyByID(any(Integer.class))).thenReturn(phar);
        when(pharmacyDataHandlerMock.getPharmacyByName(any(String.class))).thenReturn(phar);
        when(pharmacyDataHandlerMock.removePharmacy(any(Integer.class))).thenReturn(Boolean.TRUE);


        instance = new PharmacyController(pharmacyDataHandlerMock);

    }

    @Test
    void addPharmacy() {
        boolean expResult = true;
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
    void getDeletePharmacy() {
        Pharmacy pharmacy = new Pharmacy(5, "ISEP", 2323, 23323, "isep@isep.ipp.pt");
        boolean result = instance.deletePharmacy(pharmacy.getId());
        assertEquals(true, result);
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
        boolean expResult = true;
        boolean result = instance.registerPharmacyandPark(name, latitude, longitude, street, doorNumber, zipCode, locality, maxCpacity, maxChargingCapacity,
                actualChargingCapacity, power);
        assertEquals(result, expResult);
    }

    @Test
    void registerPharmacyandPark2() {
        Administrator a = new Administrator("dnjnsdf", "ola", "ADMINISTRATOR");
        UserSession.getInstance().setUser(a);
        String name = null;
        double latitude = 0;
        double longitude = 2;
        String street = "dsfs";
        int doorNumber = 4;
        String zipCode = "4600";
        String locality = "Lobao";
        int maxCpacity = 8;
        int maxChargingCapacity = 5;
        int actualChargingCapacity = 5;
        int power = 56;
        boolean expResult = false;
        boolean result = instance.registerPharmacyandPark(name, latitude, longitude, street, doorNumber, zipCode, locality, maxCpacity, maxChargingCapacity,
                actualChargingCapacity, power);
        assertEquals(result, expResult);
    }
}