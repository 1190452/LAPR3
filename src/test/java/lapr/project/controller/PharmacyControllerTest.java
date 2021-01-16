package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import oracle.ons.Cli;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

        Pharmacy phar = new Pharmacy(5, "ISEP","phar1@isep.ipp.pt", 2323, 23323, "isep@isep.ipp.pt");
        Pharmacy phar2 = new Pharmacy(7, "teste","teste@isep.ipp.pt", 21, 233323, "isep2@isep.ipp.pt");
        List<Pharmacy> lst = new ArrayList<>();
        lst.add(phar);
        when(pharmacyDataHandlerMock.getPharmacyByID(any(Integer.class))).thenReturn(phar);
        when(pharmacyDataHandlerMock.getPharmacyByName(any(String.class))).thenReturn(phar);
        when(pharmacyDataHandlerMock.getAllPharmacies()).thenReturn(lst);

        Park park = new Park(1,12,10,2,1,25,2,1);
        when(parkHandler.addPark(any(Park.class))).thenReturn(Boolean.TRUE);
        when(parkHandler.getParkByPharmacyId(any(Integer.class), any(Integer.class))).thenReturn(park);

        Address address = new Address(232.12, 212.981, "Rua xpto", 21, "222-981", "Porto");
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        AddressDataHandler addressDataHandler = mock(AddressDataHandler.class);
        when(addressDataHandler.getAddress(any(Double.class), any(Double.class))).thenReturn(address);
        when(addressDataHandler.getAllAddresses()).thenReturn(addresses);

        Client client = new Client("rafael@gmail.com", "CLIENT", 123, "Rafael", 718290182, 2897771.232, 23991.22981, new BigDecimal("8910281726172819"), 23);
        ClientDataHandler clientDataHandler = mock(ClientDataHandler.class);
        when(clientDataHandler.getClientByEmail(any(String.class))).thenReturn(client);

        instance = new PharmacyController(pharmacyDataHandlerMock,parkHandler, addressDataHandler,clientDataHandler);

    }

    @Test
    void addPharmacy() {
        PharmacyDataHandler pharmacyDataHandler =  mock(PharmacyDataHandler.class);
        when(pharmacyDataHandler.addPharmacy(any(Pharmacy.class))).thenReturn(Boolean.TRUE);
        PharmacyController pharmacyController =  new PharmacyController(pharmacyDataHandler, new ParkHandler(), new AddressDataHandler(), new ClientDataHandler());
        Pharmacy pharmacy = new Pharmacy(5,"phar1@isep.ipp.pt", "ISEP", 2323, 23323, "isep@isep.ipp.pt");
        boolean result = pharmacyController.addPharmacy(pharmacy.getName(),pharmacy.getLatitude(),pharmacy.getLongitude(),pharmacy.getEmailAdministrator(), pharmacy.getEmail());
        assertEquals(true, result);
    }

    @Test
    void addPharmacy2() {
        PharmacyDataHandler pharmacyDataHandler =  mock(PharmacyDataHandler.class);
        when(pharmacyDataHandler.addPharmacy(any(Pharmacy.class))).thenReturn(Boolean.FALSE);
        PharmacyController pharmacyController =  new PharmacyController(pharmacyDataHandler, new ParkHandler(), new AddressDataHandler(), new ClientDataHandler());
        boolean expResult = false;
        Pharmacy pharmacy = new Pharmacy(5,"phar1@isep.ipp.pt", "ISEP", 2323, 23323, "isep@isep.ipp.pt");
        boolean result = pharmacyController.addPharmacy(pharmacy.getName(),pharmacy.getLatitude(),pharmacy.getLongitude(),pharmacy.getEmailAdministrator(), pharmacy.getEmail());
        assertEquals(expResult, result);
    }


    @Test
    void getPharmacyByID() {
        Pharmacy pharmacy = new Pharmacy(5, "ISEP","phar1@isep.ipp.pt", 2323, 23323, "isep@isep.ipp.pt");
        Pharmacy result = instance.getPharmacyByID(pharmacy.getId());
        assertEquals(pharmacy, result);
    }

    @Test
    void getPharmacyByName() {
        Pharmacy pharmacy = new Pharmacy(5, "ISEP","phar1@isep.ipp.pt", 2323, 23323, "isep@isep.ipp.pt");
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
                power,parkIdType, "admin@isep.ipp.pt", "phar1@isep.ipp.pt");
        assertEquals(expResult, result);
    }

    @Test
    void addPark() {
        boolean expResult = true;
        Park park = new Park(1,12,10,2,1,25,2,1);
        boolean result = instance.addPark(park.getMaxCapacity(),park.getMaxChargingPlaces(),park.getPower(),park.getPharmacyID(),park.getIdParktype());
        assertEquals(expResult, result);

    }

    @Test
    void getPark() {
        Park park = new Park(1,12,10,2,1,25,2,1);
        Park result = instance.getPark(park.getPharmacyID(), park.getIdParktype());
        assertEquals(park, result);
    }

    @Test
    void getClientByEmail() {
        Client client = new Client("rafael@gmail.com", "CLIENT", 123, "Rafael", 718290182, 2897771.232, 23991.22981, new BigDecimal("8910281726172819"), 23);
        Client result = instance.getClientByEmail("rafael@gmail.com");
        assertEquals(client, result);
    }

    @Test
    void getAddressUser() {
        Client client = new Client("rafael@gmail.com", "CLIENT", 123, "Rafael", 718290182, 2897771.232, 23991.22981, new BigDecimal("8910281726172819"), 23);
        Address address = new Address(232.12, 212.981, "Rua xpto", 21, "222-981", "Porto");
        Address result = instance.getAddressUser(client);
        assertEquals(address,result);
    }

    @Test
    void getAllAddresses() {
        Address address = new Address(232.12, 212.981, "Rua xpto", 21, "222-981", "Porto");
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        List<Address> result = instance.getAllAdresses();
        assertEquals(addresses, result);
    }

    @Test
    void getAllPharmacies() {
        Pharmacy phar = new Pharmacy(5, "ISEP","phar1@isep.ipp.pt", 2323, 23323, "isep@isep.ipp.pt");
        List<Pharmacy> lst = new ArrayList<>();
        lst.add(phar);
        List<Pharmacy> result = instance.getAllPharmacies();
        assertEquals(lst,result);
    }
}