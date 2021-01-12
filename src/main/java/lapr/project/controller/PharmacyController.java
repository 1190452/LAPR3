package lapr.project.controller;

import lapr.project.data.PharmacyDataHandler;
import lapr.project.model.Address;
import lapr.project.model.Park;
import lapr.project.model.Pharmacy;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PharmacyController {
    private final PharmacyDataHandler pharmacyDataHandler;
    private static final Logger WARNING = Logger.getLogger(PharmacyController.class.getName());


    public PharmacyController(PharmacyDataHandler pharmacyDataHandler) {
        this.pharmacyDataHandler = pharmacyDataHandler;
    }

    public boolean addPharmacy(String name, double latitude, double longitude, String emailAdministrator) {
        boolean added;
        Pharmacy pharmacy = new Pharmacy(name,latitude,longitude,emailAdministrator);
        added =  pharmacyDataHandler.addPharmacy(pharmacy);
        return added;
    }

    public Pharmacy getPharmacyByName(String name) {
        return pharmacyDataHandler.getPharmacyByName(name);
    }

    public Pharmacy getPharmacyByID(int id) {
        return pharmacyDataHandler.getPharmacyByID(id);
    }

    public boolean deletePharmacy(int id) {
        return pharmacyDataHandler.removePharmacy(id);
    }


    public boolean registerPharmacyandPark(String name, double latitude, double longitude, String street, int doorNumber, String zipCode, String locality, int maxCpacity, int maxChargingCapacity, int actualChargingCapacity, int power) {
           try{
               Address add = new Address(latitude, longitude, street, doorNumber, zipCode, locality);
               boolean addCheck = add.save();
               Pharmacy phar;
               try {
                   phar = getPharmacyByName(name);
               }catch (Exception e) {
                   Logger.getLogger(PharmacyController.class.getName()).log(Level.WARNING, e.getMessage());
                   return false;
               }

               int pharmacyID = phar.getId();
               Park park = new Park(maxCpacity, maxChargingCapacity, actualChargingCapacity, power, pharmacyID);
               boolean phakCheck = park.save();

               if(addCheck && phakCheck){
                   return true;
               }
           }catch (Exception e){
               WARNING.log(Level.WARNING, e.getMessage());
           }
        return false;
    }
}
