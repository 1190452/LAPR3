package lapr.project.controller;

import lapr.project.data.PharmacyDataHandler;
import lapr.project.data.UserSession;
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


    public boolean registerPharmacyandPark(String name, double latitude, double longitude, String street, int doorNumber, String zipCode, String locality, int maxCpacity, int maxChargingCapacity, int actualChargingCapacity, int power) {
           try{
               Address add = new Address(latitude, longitude, street, doorNumber, zipCode, locality);
               boolean addCheck = add.save();
               String administratorEmail = UserSession.getInstance().getUser().getEmail();
               Pharmacy phar = new Pharmacy(name, latitude, longitude,administratorEmail);
               boolean pharCheck = phar.save();

               int pharmacyID = pharmacyDataHandler.getPharmacy(phar.getName()).getId();
               Park park = new Park(maxCpacity, maxChargingCapacity, actualChargingCapacity, power, pharmacyID);
               boolean phakCheck = park.save();

               if(addCheck && pharCheck && phakCheck){
                   return true;
               }
           }catch (Exception e){
               WARNING.log(Level.WARNING, e.getMessage());
           }
        return false;
    }
}
