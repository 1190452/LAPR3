package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.utils.Physics;

import java.io.*;
import java.time.LocalDateTime;
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

    public VehicleController(VehicleHandler vehicleHandler, DeliveryHandler deliveryHandler, ParkHandler parkHandler,CourierDataHandler courierDataHandler,PharmacyDataHandler pharmacyDataHandler, AddressDataHandler addressDataHandler) {
        this.vehicleHandler = vehicleHandler;
        this.deliveryHandler = deliveryHandler;
        this.parkHandler = parkHandler;
        this.courierDataHandler = courierDataHandler;
        this.pharmacyDataHandler=pharmacyDataHandler;
        this.addressDataHandler = addressDataHandler;
    }

    public boolean addVehicle(String licencePlate, double maxBattery, double enginePower, double ahBattery, double vBattery, int idPharmacy, int typeVehicle) {
        boolean added;
        Vehicle vehicle = new Vehicle(licencePlate, maxBattery, enginePower, ahBattery, vBattery, idPharmacy, typeVehicle);
        added = vehicleHandler.addVehicle(vehicle);
        return added;
    }


    public boolean removeVehicle(String licencePlate) {
        boolean removed;
        removed = vehicleHandler.removeVehicle(licencePlate);
        return removed;
    }

    public Vehicle getVehicle(String licencePlate) {
        return vehicleHandler.getVehicle(licencePlate);
    }

    public Vehicle getAvailableScooter(int courierId, String email){
        Delivery d = deliveryHandler.getDeliveryByCourierId(courierId);
        double necessaryEnergy = d.getNecessaryEnergy();

        Courier c = courierDataHandler.getCourierByEmail(email);
        int pharmacyId = c.getPharmacyID();
        List<Vehicle> vehicleList = vehicleHandler.getAllScooterAvailables(pharmacyId);
        for (Vehicle vehicle : vehicleList) {
            double actualBattery = vehicle.getActualBattery();
            if (necessaryEnergy < actualBattery) {
                String licensePlate = vehicle.getLicensePlate();
                Park park = vehicleHandler.getParkByPharmacyId(pharmacyId, 1);
                int parkId = park.getId();
                vehicleHandler.updateStatusToParked(licensePlate);
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


    public boolean parkScooter(int pharmacyId,Vehicle scooter) throws IOException {
        int parkTypeID=1;
        Park park = parkHandler.getParkByPharmacyId(pharmacyId,parkTypeID);
           if( park!=null && scooter!=null){
              double actualBattery = scooter.getActualBattery();
              int actualCapacity = park.getActualCapacity();
              int actualChargingPlaces = park.getActualChargingPlaces();
              int parkId = park.getId();
              double ahBattery = scooter.getAhBattery();
              double maxBattery = scooter.getMaxBattery();

                  if(actualChargingPlaces>0){
                      parkVehicleInChargingPlaces(scooter,parkId,pharmacyId,ahBattery,maxBattery,actualBattery);
                      return true;
                  }else {
                      if (actualBattery < 10) {
                          getAnotherParkToCharge(parkTypeID,pharmacyId);
                          return false;
                      }else {
                             if(actualCapacity>0){
                             parkVehicleInNormalPlaces(scooter,parkId,pharmacyId,ahBattery,maxBattery,actualBattery);
                             return true;
                             }else {
                                 getAnotherParkToPark(parkTypeID,pharmacyId);
                             return false;
                             }
                      }
           }
           }else {
               //simulateParking(0, scooter.getAhBattery(), scooter.getMaxBattery(), scooter.getActualBattery()); TODO Alterar porque pode dar nullPointer
               return false;
           }
    }

    public boolean simulateParking(int parkId,double ahBattery,double maxBattery,double actualBattery)  {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();

        try {
            File myObj = new File(String.format("\\lock_%4d_%2d_%2d_%2d_%2d_%2d.data",year,month,day,hour,minute,second));    //TODO Verificar a pasta de criação
            if (myObj.createNewFile()) {
                Logger.getLogger(VehicleController.class.getName()).log(Level.INFO, "File created: " + myObj.getName());

                try(FileWriter myWriter = new FileWriter(myObj)) {
                    writeInfo(myWriter,parkId,ahBattery,maxBattery,actualBattery,year,month,day,hour,minute,second);
                }
                
                int lines;
                try( BufferedReader reader = new BufferedReader(new FileReader(myObj.getPath()))) {
                    lines = 0;
                    while (reader.readLine() != null) lines++;
                }

                if(lines == 10) {
                        File flag = new File(String.format("\\lock_%4d_%2d_%2d_%2d_%2d_%2d.data.flag", year, month, day, hour, minute, second));
                        if (flag.createNewFile()) {
                            Logger.getLogger(VehicleController.class.getName()).log(Level.INFO, "Flag created: " + flag.getName());
                            return true;

                        } else {
                            Logger.getLogger(VehicleController.class.getName()).log(Level.WARNING, "ERROR VehicleController");
                            return false;
                        }
                }
            } else {
                Logger.getLogger(VehicleController.class.getName()).log(Level.WARNING, "ERROR VehicleController");
                return false;
            }
        } catch (IOException e) {
            Logger.getLogger(VehicleController.class.getName()).log(Level.WARNING, e.getMessage());
        }
        return false;
    }

    private boolean writeInfo(FileWriter myWriter,int parkId, double ahBattery, double maxBattery, double actualBattery, int year, int month, int day, int hour, int minute, int second) throws IOException {
        myWriter.write(parkId+"\n");
        myWriter.write((int)ahBattery+"\n");
        myWriter.write((int)maxBattery+"\n");
        myWriter.write((int)actualBattery+"\n");
        myWriter.write(year+"\n");
        myWriter.write(month+"\n");
        myWriter.write(day+"\n");
        myWriter.write(hour+"\n");
        myWriter.write(minute+"\n");
        myWriter.write(second+"\n");
        return true;
    }

    public List<Vehicle> getVehicles() {
        return vehicleHandler.getAllVehicles();
    }

    public Park getParkMoreClose(List<Park> lista,int pharmacyId){
        Pharmacy pharmacy = pharmacyDataHandler.getPharmacyByID(pharmacyId);
        Address startPoint = addressDataHandler.getAddress(pharmacy.getLatitude(), pharmacy.getLongitude(),pharmacy.getAltitude());

        Pharmacy pAux = pharmacyDataHandler.getPharmacyByID(lista.get(0).getPharmacyID());
        Address aAux = addressDataHandler.getAddress(pAux.getLatitude(), pAux.getLongitude(),pharmacy.getAltitude());
        double menor= Physics.calculateDistanceWithElevation(startPoint.getLatitude(),aAux.getLatitude(),startPoint.getLongitude(),aAux.getLongitude(), startPoint.getAltitude(), aAux.getAltitude());
        Park parkMoreClose=null;

        for (int i = 1; i <lista.size() ; i++) {
            Pharmacy p = pharmacyDataHandler.getPharmacyByID(lista.get(i).getPharmacyID());
            Address a = addressDataHandler.getAddress(p.getLatitude(), p.getLongitude(),pharmacy.getAltitude());
            if(Physics.calculateDistanceWithElevation(startPoint.getLatitude(),a.getLatitude(),startPoint.getLongitude(),a.getLongitude(), startPoint.getAltitude(), a.getAltitude())<=menor){
                parkMoreClose=lista.get(i);
            }
        }
        return parkMoreClose;
    }

    public boolean parkDrone(int pharmacyId,Vehicle drone)  throws IOException{
        int parkTypeId = 2;
        Park park = parkHandler.getParkByPharmacyId(pharmacyId,parkTypeId);
        
        if( park!=null && drone!=null ){
            double actualBattery = drone.getActualBattery();
            double actualCapacity = park.getActualCapacity();
            double actualChargingPlaces = park.getActualChargingPlaces();
            int parkId = park.getId();
            double ahBattery = drone.getAhBattery();
            double maxBattery = drone.getMaxBattery();

        if(actualChargingPlaces>0){
            parkVehicleInChargingPlaces(drone,parkId,pharmacyId,ahBattery,maxBattery,actualBattery);
            return true;
        }else {
            if (actualBattery < 10) {
                getAnotherParkToCharge(parkTypeId,pharmacyId);
                return false;
            } else {
                if (actualCapacity > 0) {
                    parkVehicleInNormalPlaces(drone,parkId,pharmacyId,ahBattery,maxBattery,actualBattery);
                    return true;
                } else {
                    getAnotherParkToPark(parkTypeId,pharmacyId);
                    return false;
                }
            }
        }
        }else {
            //simulateParking(park.getId(), drone.getAhBattery(), drone.getMaxBattery(), drone.getActualBattery());
            return false;
        }
    }

    public boolean getAnotherParkToPark(int parkTypeId,int pharmacyId) {
        List<Park> listNormalParksD = parkHandler.getParkWithNPlaces(parkTypeId);
        Park p = getParkMoreClose(listNormalParksD, pharmacyId);
        if(p!= null) {
            Logger.getLogger(VehicleController.class.getName()).log(Level.INFO, "Go to park" + p.getId());
            return true;
        }else
            return false;

    }

    public boolean parkVehicleInNormalPlaces(Vehicle vehicle, int parkId, int pharmacyId,double ahBattery,double maxBattery, double actualBattery) throws IOException {
        simulateParking(parkId, ahBattery, maxBattery, actualBattery);
        boolean b = vehicleHandler.updateStatusToParked(vehicle.getLicensePlate());
        boolean b1 = parkHandler.updateActualCapacityR(parkId);
        EmailAPI.sendEmailNotification(pharmacyId,vehicle.getLicensePlate());
        return b && b1;
    }

    public boolean getAnotherParkToCharge(int parkTypeId,int pharmacyId) {
        List<Park> listChargingParksD = parkHandler.getParkWithCPlaces(parkTypeId);
        Park p = getParkMoreClose(listChargingParksD, pharmacyId);
        if(p!=null){
            Logger.getLogger(VehicleController.class.getName()).log(Level.INFO, "Go to park" + p.getId());
            return true;
        }else {
            return false;
        }

    }

    public boolean parkVehicleInChargingPlaces(Vehicle vehicle,int parkId,int pharmacyId,double ahBattery,double maxBattery, double actualBattery) throws IOException {
        simulateParking(parkId,ahBattery,maxBattery,actualBattery);
        boolean bandeira = vehicleHandler.updateStatusToParked(vehicle.getLicensePlate());
        boolean bandeira1 = vehicleHandler.updateIsChargingY(vehicle.getLicensePlate());
        boolean bandeira2 = parkHandler.updateChargingPlacesR(parkId);
        EmailAPI.sendEmailNotification(pharmacyId,vehicle.getLicensePlate());
        return bandeira && bandeira1 && bandeira2;

    }

    public boolean sendEmailNotification(int pharmacyId,Vehicle drone) throws IOException {
        EmailAPI.sendEmailNotification(pharmacyId, drone.getLicensePlate());
        return true;
    }
}
