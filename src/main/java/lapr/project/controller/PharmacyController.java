package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.Address;
import lapr.project.model.Client;
import lapr.project.model.Park;
import lapr.project.model.Pharmacy;
import lapr.project.utils.Distance;
import oracle.ucp.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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

    public boolean addPharmacy(String name, double latitude, double longitude, String emailAdministrator, String emailP) {

        try {
            getPharmacyByName(name);
        } catch (IllegalArgumentException ex) {
            //Of the record does not exist, save it
            Pharmacy pharmacy = new Pharmacy(name,emailP, latitude,longitude,emailAdministrator);
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


    public boolean registerPharmacyandPark(String name, String email,double latitude, double longitude, String street, int doorNumber, String zipCode, String locality, int maxCpacity, int maxChargingCapacity, int actualChargingCapacity, int power,int idParkType) {
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

    public List<Pharmacy> getAllPharmacies() {
        return pharmacyDataHandler.getAllPharmacies();
    }

    public List<Pair<Pharmacy, Double>> getPharmaciesInformation() {
        List<Pharmacy> listP = getAllPharmacies();
        List<Address> listA = getAllAdresses();
        List<Address> listPharmaciesAddresses = new ArrayList<>();
        List<Pair<Pharmacy, Double>> pharmaciesDistanceToUser = new ArrayList<>();
        for(Pharmacy p : listP){
            for(Address a : listA){
                if(p.getLatitude() == a.getLatitude() && p.getLongitude() == a.getLongitude()){
                    listPharmaciesAddresses.add(a);
                }
            }
        }

       String userEmail =  UserSession.getInstance().getUser().getEmail();
        Client c = getClientByEmail(userEmail);
        Address userAddress = getAddressUser(c);

        for(int i=0;i<listPharmaciesAddresses.size();i++){
            double distance = Distance.distanceBetweenTwoAddresses(listPharmaciesAddresses.get(i).getLatitude(), listPharmaciesAddresses.get(i).getLongitude(), userAddress.getLatitude(), userAddress.getLongitude());
            pharmaciesDistanceToUser.add(new Pair<>(listP.get(i), distance));
        }

        Collections.sort(pharmaciesDistanceToUser, new Comparator<Pair<Pharmacy, Double>>() {
            @Override
            public int compare(Pair<Pharmacy, Double> o1, Pair<Pharmacy, Double> o2) {
                if(o1.get2nd() > o2.get2nd() ) {
                    return 1;
                }else if(o1.get2nd() < o2.get2nd()) {
                    return -1;
                }else
                    return 0;
            }
        });


        return pharmaciesDistanceToUser;
    }

    private Client getClientByEmail(String userEmail) {
       return new ClientDataHandler().getClientByEmail(userEmail);
    }

    private Address getAddressUser(Client client) {
        return new AddressDataHandler().getAddress(client.getLatitude(), client.getLongitude());
    }

    private List<Address> getAllAdresses() {
        return new AddressDataHandler().getAllAddresses();
    }
}
