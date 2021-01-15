package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.data.AddressDataHandler;
import lapr.project.data.ParkHandler;
import lapr.project.data.PharmacyDataHandler;
import lapr.project.model.*;
import lapr.project.utils.Distance;
import oracle.ucp.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PharmacyController {
    private final PharmacyDataHandler pharmacyDataHandler;
    private final ParkHandler parkHandler;
    private final AddressDataHandler addressDataHandler;
    private final ClientDataHandler clientDataHandler;
    private static final Logger WARNING = Logger.getLogger(PharmacyController.class.getName());


    public PharmacyController(PharmacyDataHandler pharmacyDataHandler, ParkHandler parkHandler, AddressDataHandler addressDataHandler, ClientDataHandler clientDataHandler) {
        this.parkHandler = parkHandler;
        this.pharmacyDataHandler = pharmacyDataHandler;
        this.addressDataHandler = addressDataHandler;
        this.clientDataHandler = clientDataHandler;
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

    public boolean addPark(int maxCapacity, int maxChargingPlaces, double power, int pharmacyID, int idParktype) {
        try {
            if(getPark(pharmacyID,idParktype)!=null){
                return false;
            }
        } catch (IllegalArgumentException ex) {
            //Of the record does not exist, save it
            Park park = new Park(maxCapacity,maxChargingPlaces,power,pharmacyID,idParktype);
            parkHandler.addPark(park);
            return true;
        }
        return false;
    }

    public Park getPark(int pharmacyID, int parkTypeID) {
        return parkHandler.getParkByPharmacyId(pharmacyID,parkTypeID);
    }




    public boolean registerPharmacyandPark(String name, double latitude, double longitude, String street, int doorNumber, String zipCode, String locality, int maxCpacity, int maxChargingCapacity, double power,int idParkType, String emailAdmin, String emailP) {
           try{
               Address add = new Address(latitude, longitude, street, doorNumber, zipCode, locality);
               boolean addCheck = addressDataHandler.addAddress(add);
               boolean isAdded;
               isAdded = addPharmacy(name,latitude,longitude,emailAdmin,emailP);

               if(isAdded && addCheck) {
                   int pharmacyID = getPharmacyByName(name).getId();
                   if(idParkType == 3) {
                       Park parkScooter = new Park(maxCpacity, maxChargingCapacity, power, pharmacyID, 1);
                       Park parkDrone = new Park(maxCpacity, maxChargingCapacity, power, pharmacyID, 2);
                       boolean parkScooterCheck = addPark(parkScooter.getMaxCapacity(), parkScooter.getMaxChargingPlaces(), parkScooter.getPower(), parkScooter.getPharmacyID(),parkScooter.getIdParktype());
                       boolean parkDroneCheck = addPark(parkDrone.getMaxCapacity(), parkDrone.getMaxChargingPlaces(), parkDrone.getPower(), parkDrone.getPharmacyID(),parkDrone.getIdParktype());
                       if(parkDroneCheck && parkScooterCheck)
                           return true;
                   }else {
                       Park park = new Park(maxCpacity, maxChargingCapacity, power, pharmacyID, idParkType);
                       boolean parkCheck = addPark(park.getMaxCapacity(), park.getMaxChargingPlaces(), park.getPower(), park.getPharmacyID(),park.getIdParktype());
                       if(parkCheck)
                           return true;
                   }
               }
           }catch (Exception e){
               WARNING.log(Level.WARNING, e.getMessage());
               return false;
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
            double distance = Distance.distanceBetweenTwoAddressesWithElevation(listPharmaciesAddresses.get(i).getLatitude(), listPharmaciesAddresses.get(i).getLongitude(), userAddress.getLatitude(), userAddress.getLongitude());
            pharmaciesDistanceToUser.add(new Pair<>(listP.get(i), distance));
        }

        pharmaciesDistanceToUser.sort((o1, o2) -> {
            if (o1.get2nd() > o2.get2nd()) {
                return 1;
            } else if (o1.get2nd() < o2.get2nd()) {
                return -1;
            } else
                return 0;
        });


        return pharmaciesDistanceToUser;
    }

    public Client getClientByEmail(String userEmail) {
       return clientDataHandler.getClientByEmail(userEmail);
    }

    public Address getAddressUser(Client client) {
        return addressDataHandler.getAddress(client.getLatitude(), client.getLongitude());
    }

    public List<Address> getAllAdresses() {
        return addressDataHandler.getAllAddresses();
    }
}
