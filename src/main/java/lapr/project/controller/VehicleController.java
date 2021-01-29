package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.ui.CourierUI;
import lapr.project.utils.Physics;
import oracle.ucp.util.Pair;

import java.io.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class VehicleController {

    private final VehicleHandler vehicleHandler;
    private final DeliveryHandler deliveryHandler;
    private final ParkHandler parkHandler;
    private final CourierDataHandler courierDataHandler;
    private final PharmacyDataHandler pharmacyDataHandler;
    private final AddressDataHandler addressDataHandler;

    /**
     * Contructor to instanciate all the data handler that are needed
     * @param vehicleHandler
     * @param deliveryHandler
     * @param parkHandler
     * @param courierDataHandler
     * @param pharmacyDataHandler
     * @param addressDataHandler
     */
    public VehicleController(VehicleHandler vehicleHandler, DeliveryHandler deliveryHandler, ParkHandler parkHandler, CourierDataHandler courierDataHandler, PharmacyDataHandler pharmacyDataHandler, AddressDataHandler addressDataHandler) {
        this.vehicleHandler = vehicleHandler;
        this.deliveryHandler = deliveryHandler;
        this.parkHandler = parkHandler;
        this.courierDataHandler = courierDataHandler;
        this.pharmacyDataHandler = pharmacyDataHandler;
        this.addressDataHandler = addressDataHandler;
    }

    /**
     * method to manages the adding of new vehicles
     * @param licencePlate
     * @param maxBattery
     * @param enginePower
     * @param ahBattery
     * @param vBattery
     * @param idPharmacy
     * @param typeVehicle
     * @return boolean that confirms the operation was successful
     */
    public boolean addVehicle(String licencePlate, double maxBattery, double enginePower, double ahBattery, double vBattery, int idPharmacy, int typeVehicle) {
        boolean added;
        Vehicle vehicle = new Vehicle(licencePlate, maxBattery, enginePower, ahBattery, vBattery, idPharmacy, typeVehicle);
        added = vehicleHandler.addVehicle(vehicle);
        return added;
    }

    /**
     * method that permits the admin to remove vehicle
     * @param licencePlate
     * @return boolean that confirms the operation was successful
     */
    public boolean removeVehicle(String licencePlate) {
        boolean removed;
        removed = vehicleHandler.removeVehicle(licencePlate);
        return removed;
    }

    /**
     * @param licencePlate
     * @return boolean that confirms the operation was successful
     */
    public Vehicle getVehicle(String licencePlate) {
        return vehicleHandler.getVehicle(licencePlate);
    }

    /**
     * method to get available scooter
     * @param courierId
     * @param email
     * @return vehicle
     */
    public Vehicle getAvailableScooter(int courierId, String email) {
        Delivery d = deliveryHandler.getDeliveryByCourierId(courierId);
        double necessaryEnergy = d.getNecessaryEnergy();

        Courier c = courierDataHandler.getCourierByEmail(email);
        int pharmacyId = c.getPharmacyID();
        List<Vehicle> vehicleList = vehicleHandler.getAllScooterAvailables(pharmacyId, necessaryEnergy);
        for (Vehicle vehicle : vehicleList) {
            double actualBattery = vehicle.getActualBattery();
            if (necessaryEnergy < actualBattery) {
                String licensePlate = vehicle.getLicensePlate();
                Park park = parkHandler.getParkByPharmacyId(pharmacyId, 1);
                int parkId = park.getId();
                vehicleHandler.updateStatusToBusy(licensePlate);
                int isCharging = vehicle.getIsCharging();
                if (isCharging == 1) {
                    parkHandler.updateActualChargingPlacesA(parkId);
                    vehicleHandler.updateIsChargingN(licensePlate);
                } else {
                    parkHandler.updateActualCapacityA(parkId);
                }
                int deliveryId = d.getId();
                vehicleHandler.associateVehicleToDelivery(deliveryId, licensePlate);
                return vehicle;
            }
        }
        return null;
    }


    /**
     * method to manage the scooter parking
     * @param pharmacyId
     * @param scooter
     * @return
     * @throws IOException
     */
    public boolean parkScooter(int pharmacyId, Vehicle scooter) {
        int parkTypeID = 1;
        Park park = null;
        try {
                park = parkHandler.getParkByPharmacyId(pharmacyId, parkTypeID);
                int actualCapacity = park.getActualCapacity();
                int actualChargingPlaces = park.getActualChargingPlaces();

                if (actualChargingPlaces > 0) {
                    return parkVehicleInChargingPlaces(scooter, park, pharmacyId);

                } else {
                    if (scooter.getBatteryPercentage() < 10) {
                        getAnotherParkToCharge(parkTypeID, pharmacyId);
                        return false;
                    } else {
                        if (actualCapacity > 0) {
                            return parkVehicleInNormalPlaces(scooter, park, pharmacyId);
                        } else {
                            getAnotherParkToPark(parkTypeID, pharmacyId);
                            return false;
                        }
                    }
                }
        }catch (NullPointerException | InterruptedException n){
            simulateParking(park, scooter);
            return false;
        }
    }

    /**
     * method that simulates the parking
     * @param park
     * @param scooter
     * @return list of files
     */
    public List<String> simulateParking(Park park, Vehicle scooter) {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        List<String> listFiles = new LinkedList<>();

        try {

            String currentDir = System.getProperty("user.dir");

            File myObj = new File(String.format(currentDir + "/C_and_Assembly/lock_%4d_%2d_%2d_%2d_%2d_%2d.data", year, month, day, hour, minute, second));
            if (myObj.createNewFile()) {
                Logger.getLogger(VehicleController.class.getName()).log(Level.INFO, "File created: " + myObj.getName());
                listFiles.add(myObj.getName());

                try (FileWriter myWriter = new FileWriter(myObj)) {
                    writeInfo(myWriter, park, scooter, year, month, day, hour, minute, second);
                }

                int lines;
                try (BufferedReader reader = new BufferedReader(new FileReader(myObj.getPath()))) {
                    lines = 0;
                    while (reader.readLine() != null) lines++;
                }

                if (lines == 11) {
                    File flag = new File(String.format(currentDir + "/C_and_Assembly/lock_%4d_%2d_%2d_%2d_%2d_%2d.data.flag", year, month, day, hour, minute, second));
                    if (flag.createNewFile()) {
                        Logger.getLogger(VehicleController.class.getName()).log(Level.INFO, "Flag created: " + flag.getName());
                        listFiles.add(flag.getName());
                        return listFiles;

                    } else {
                        Logger.getLogger(VehicleController.class.getName()).log(Level.WARNING, "ERROR VehicleController");
                        return new LinkedList<>();
                    }
                }else {
                    Logger.getLogger(VehicleController.class.getName()).log(Level.WARNING, "The vehicle was badly parked");
                    if(myObj.delete()){
                        Logger.getLogger(VehicleController.class.getName()).log(Level.WARNING, "File of bad park removed");
                    }
                }
            } else {
                Logger.getLogger(VehicleController.class.getName()).log(Level.WARNING, "ERROR VehicleController");
                return new LinkedList<>();
            }
        } catch (IOException e) {
            Logger.getLogger(VehicleController.class.getName()).log(Level.WARNING, e.getMessage());
        }
        return new LinkedList<>();
    }

    /**
     * method to write the info to file
     * @param myWriter
     * @param park
     * @param scooter
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return boolean that confirms the operation was successful
     * @throws IOException
     */
    public boolean writeInfo(FileWriter myWriter,Park park,Vehicle scooter, int year, int month, int day, int hour, int minute, int second) throws IOException {
        if(park!=null && scooter!=null ) {
            myWriter.write(park.getId() + "\n");
            myWriter.write((int) scooter.getAhBattery() + "\n");
            myWriter.write((int)scooter.getMaxBattery() + "\n");
            myWriter.write((int) scooter.getActualBattery() + "\n");
            myWriter.write(year + "\n");
            myWriter.write(month + "\n");
            myWriter.write(day + "\n");
            myWriter.write(hour + "\n");
            myWriter.write(minute + "\n");
            myWriter.write(second + "\n");
            myWriter.write((park.getMaxChargingPlaces() - park.getActualChargingPlaces() + 1) + "\n");
        }else{
            myWriter.write("THE VEHICLE WAS BADLY PARKED");
            return false;
        }
        return true;
    }

    /**
     * method to get all vehicles
     * @return list of the vehicles
     */
    public List<Vehicle> getVehicles() {
        return vehicleHandler.getAllVehicles();
    }

    /**
     * method that returns the park that is closer
     * @param lista
     * @param pharmacyId
     * @return park
     */
    public Park getParkMoreClose(List<Park> lista, int pharmacyId) {
        Pharmacy pharmacy = pharmacyDataHandler.getPharmacyByID(pharmacyId);
        Address startPoint = addressDataHandler.getAddress(pharmacy.getLatitudePharmacy(), pharmacy.getLongitudePharmacy(), pharmacy.getAltitudePharmacy());

        Pharmacy pAux = pharmacyDataHandler.getPharmacyByID(lista.get(0).getPharmacyID());
        Address aAux = addressDataHandler.getAddress(pAux.getLatitudePharmacy(), pAux.getLongitudePharmacy(), pharmacy.getAltitudePharmacy());
        double menor = Physics.calculateDistanceWithElevation(startPoint.getLatitude(), aAux.getLatitude(), startPoint.getLongitude(), aAux.getLongitude(), startPoint.getAltitude(), aAux.getAltitude());
        Park parkMoreClose = null;

        for (int i = 1; i < lista.size(); i++) {
            Pharmacy p = pharmacyDataHandler.getPharmacyByID(lista.get(i).getPharmacyID());
            Address a = addressDataHandler.getAddress(p.getLatitudePharmacy(), p.getLongitudePharmacy(), pharmacy.getAltitudePharmacy());
            if (Physics.calculateDistanceWithElevation(startPoint.getLatitude(), a.getLatitude(), startPoint.getLongitude(), a.getLongitude(), startPoint.getAltitude(), a.getAltitude()) <= menor) {
                parkMoreClose = lista.get(i);
            }
        }
        return parkMoreClose;
    }

    /**
     * method that parks drone
     * @param pharmacyId
     * @param drone
     * @return boolean that confirms the operation was successful
     * @throws IOException
     */
    public boolean parkDrone(int pharmacyId, Vehicle drone) throws InterruptedException {
        int parkTypeId = 2;
        Park park = parkHandler.getParkByPharmacyId(pharmacyId, parkTypeId);

        if (park != null && drone != null) {
            double actualBattery = drone.getActualBattery();
            double actualCapacity = park.getActualCapacity();
            double actualChargingPlaces = park.getActualChargingPlaces();

            if (actualChargingPlaces > 0) {
                return parkVehicleInChargingPlaces(drone, park, pharmacyId);
            } else {
                if (actualBattery < 10) {
                    getAnotherParkToCharge(parkTypeId, pharmacyId);
                    return false;
                } else {
                    if (actualCapacity > 0) {
                        return parkVehicleInNormalPlaces(drone, park, pharmacyId);
                    } else {
                        getAnotherParkToPark(parkTypeId, pharmacyId);
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
    }

    /**
     * method to get another park to park the scooter or drone
     * @param parkTypeId
     * @param pharmacyId
     * @return boolean that confirms the operation was successful
     */
    public boolean getAnotherParkToPark(int parkTypeId, int pharmacyId) {
        List<Park> listNormalParksD = parkHandler.getParkWithNPlaces(parkTypeId);
        Park p = getParkMoreClose(listNormalParksD, pharmacyId);
        if (p != null) {
            Logger.getLogger(VehicleController.class.getName()).log(Level.INFO, "Go to park" + p.getId());
            return true;
        } else
            return false;

    }

    /**
     * method that parks the vehicle in normal place
     * @param vehicle
     * @param park
     * @param pharmacyId
     * @return boolean that confirms the operation was successful
     */
    public boolean parkVehicleInNormalPlaces(Vehicle vehicle, Park park, int pharmacyId) throws InterruptedException {
        List<String> listFiles = simulateParking(park,vehicle);
        boolean b = vehicleHandler.updateStatusToParked(vehicle.getLicensePlate());
        boolean b1 = parkHandler.updateActualCapacityR(park.getId());
        EmailAPI.sendEmailNotification(listFiles, pharmacyId, vehicle.getLicensePlate());
        return b && b1;
    }

    /**
     * method to get another park to charge the vehicle
     * @param parkTypeId
     * @param pharmacyId
     * @return boolean that confirms the operation was successful
     */
    public boolean getAnotherParkToCharge(int parkTypeId, int pharmacyId) {
        List<Park> listChargingParksD = parkHandler.getParkWithCPlaces(parkTypeId);
        Park p = getParkMoreClose(listChargingParksD, pharmacyId);
        if (p != null) {
            Logger.getLogger(VehicleController.class.getName()).log(Level.INFO, "Go to park" + p.getId());
            return true;
        } else {
            return false;
        }

    }

    /**
     * method that parks the vehicle in charging place
     * @param vehicle
     * @param park
     * @param pharmacyId
     * @return boolean that confirms the operation was successful
     */
    public boolean parkVehicleInChargingPlaces(Vehicle vehicle, Park park, int pharmacyId) throws InterruptedException {
        List<String> listFiles = simulateParking(park, vehicle);
        List<Pair<String, Vehicle>> listEmails = parkHandler.getChargingCourierList(park.getId());
        boolean bandeira = vehicleHandler.updateStatusToParked(vehicle.getLicensePlate());
        boolean bandeira1 = vehicleHandler.updateIsChargingY(vehicle.getLicensePlate());
        Logger.getLogger(CourierUI.class.getName()).log(Level.INFO, "The vehicle is charging...");
        boolean bandeira2 = parkHandler.updateChargingPlacesR(park.getId());

        for (Pair<String,Vehicle> p: listEmails) {
            List<String> listFilesToRemove = simulateParking(park,p.get2nd());
            EmailAPI.sendEmailNotification(listFilesToRemove,pharmacyId,p.get2nd().getLicensePlate());
        }

        EmailAPI.sendEmailNotification(listFiles, pharmacyId, vehicle.getLicensePlate());
        vehicleHandler.chargeVehicle(vehicle.getLicensePlate());    //Method to charge the vehicle
        return bandeira && bandeira1 && bandeira2;
    }

}
