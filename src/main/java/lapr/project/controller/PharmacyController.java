package lapr.project.controller;

import lapr.project.data.ParkHandler;
import lapr.project.data.PharmacyDataHandler;
import lapr.project.model.Address;
import lapr.project.model.Park;
import lapr.project.model.Pharmacy;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PharmacyController {
    private final PharmacyDataHandler pharmacyDataHandler;
    private final ParkHandler parkHandler;
    private static final Logger WARNING = Logger.getLogger(PharmacyController.class.getName());


    public PharmacyController(PharmacyDataHandler pharmacyDataHandler, ParkHandler parkHandler) {
        this.parkHandler = parkHandler;
        this.pharmacyDataHandler = pharmacyDataHandler;
    }

    public boolean addPharmacy(String name, double latitude, double longitude, String emailAdministrator) {

        try {
            getPharmacyByName(name);
        } catch (IllegalArgumentException ex) {
            //Of the record does not exist, save it
            Pharmacy pharmacy = new Pharmacy(name,latitude,longitude,emailAdministrator);
            pharmacyDataHandler.addPharmacy(pharmacy);
            return true;
        }
        return false;
    }

    public Pharmacy getPharmacyByName(String name) {
        return pharmacyDataHandler.getPharmacyByName(name);
    }

    public Pharmacy getPharmacyByID(int id) {
        return pharmacyDataHandler.getPharmacyByID(id);
    }

    public boolean addPark(int maxCapacity, int maxChargingPlaces, int actualChargingPlaces, int power, int pharmacyID, int idParktype) {
        try {
            if(getPark(pharmacyID,idParktype)!=null){
                return false;
            }
        } catch (IllegalArgumentException ex) {
            //Of the record does not exist, save it
            Park park = new Park(maxCapacity,maxChargingPlaces,actualChargingPlaces, power,pharmacyID,idParktype);
            parkHandler.addPark(park);
            return true;
        }
        return false;
    }

    public Park getPark(int pharmacyID, int parkTypeID) {
        return parkHandler.getParkByPharmacyId(pharmacyID,parkTypeID);
    }


    public boolean registerPharmacyandPark(String name, double latitude, double longitude, String street, int doorNumber, String zipCode, String locality, int maxCpacity, int maxChargingCapacity, int actualChargingCapacity, int power,int idParkType) {
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
               Park park = new Park(maxCpacity, maxChargingCapacity, actualChargingCapacity, power, pharmacyID, idParkType);
               boolean phakCheck = addPark(park.getMaxCapacity(), park.getMaxChargingPlaces(), park.getActualChargingPlaces(),park.getPower(), park.getPharmacyID(),park.getIdParktype());

               if(addCheck && phakCheck){
                   return true;
               }
           }catch (Exception e){
               WARNING.log(Level.WARNING, e.getMessage());
           }
        return false;
    }
}
