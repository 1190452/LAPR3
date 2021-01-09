package lapr.project.controller;

import lapr.project.data.PharmacyDataHandler;
import lapr.project.data.UserSession;
import lapr.project.model.Address;
import lapr.project.model.Park;
import lapr.project.model.Pharmacy;

public class PharmacyController {
    private final PharmacyDataHandler pharmacyDataHandler;

    public PharmacyController(PharmacyDataHandler pharmacyDataHandler) {
        this.pharmacyDataHandler = pharmacyDataHandler;
    }

    public void registerPharmacyandPark(String name, double latitude, double longitude, String street, int doorNumber, String zipCode, String locality, int maxCpacity, int actualCapacity, int maxChargingCapacity, int actualChargingCapacity, int power) {
        Address add = new Address(latitude, longitude, street, doorNumber, zipCode, locality);
        add.save();
        String administratorEmail = UserSession.getInstance().getUser().getEmail();
        Pharmacy phar = new Pharmacy(name, latitude, longitude,administratorEmail);
        phar.save();

        int pharmacyID = pharmacyDataHandler.getPharmacy(phar.getName()).getId();
        Park park = new Park(maxCpacity, maxChargingCapacity, actualChargingCapacity, power, pharmacyID);
        park.save();

    }
}
