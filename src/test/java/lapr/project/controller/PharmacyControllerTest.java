package lapr.project.controller;

import lapr.project.data.AddressDataHandler;
import lapr.project.data.ParkHandler;
import lapr.project.data.PharmacyDataHandler;
import lapr.project.data.UserSession;
import lapr.project.model.Address;
import lapr.project.model.Administrator;
import lapr.project.model.Pharmacy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

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
        AddressDataHandler addressDataHandlerMock = mock(AddressDataHandler.class);
        ParkHandler parkHandlerMock = mock(ParkHandler.class);
        Pharmacy phar = new Pharmacy(5, "ISEP", 2323, 23323, "isep@isep.ipp.pt");
        Address address = new Address(34, 45,"rua xpto", 2, "4500", "espinho");
        when(pharmacyDataHandlerMock.getPharmacy(any(Integer.class))).thenReturn(phar);
        when(addressDataHandlerMock.getAddress(any(Double.class), any(Double.class))).thenReturn(address);
        when(pharmacyDataHandlerMock.getPharmacy(any(String.class))).thenReturn(phar);
        instance = new PharmacyController(pharmacyDataHandlerMock,addressDataHandlerMock, parkHandlerMock);
        PharmacyController instance2 = new PharmacyController(pharmacyDataHandlerMock);
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
}