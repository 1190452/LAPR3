package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.utils.Distance;

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
        List<Vehicle> vehicleList = vehicleHandler.getAllScooterAvaiables(pharmacyId);
        for (Vehicle vehicle : vehicleList) {
            double actualBattery = vehicle.getActualBattery();
            if (necessaryEnergy < actualBattery) {
                String licensePlate = vehicle.getLicensePlate();
                Park park = vehicleHandler.getParkByPharmacyId(pharmacyId, 1);
                int parkId = park.getId();
                vehicleHandler.updateStatusToFree(licensePlate);
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


    public boolean parkScooter(int pharmacyId,String scooterLicensePlate) throws IOException {

        Park park = parkHandler.getParkByPharmacyId(pharmacyId,1);
        Vehicle vehicle = vehicleHandler.getVehicle(scooterLicensePlate);
           if( park!=null && vehicle!=null && vehicle.getTypeVehicle() == 1){
              double actualBattery = vehicle.getActualBattery();

              int actualCapacity = park.getActualCapacity();
              int actualChargingPlaces = park.getActualChargingPlaces();
              int parkId = park.getId();
              double power = park.getPower();
              double ahBattery = vehicle.getAhBattery();
              double maxBattery = vehicle.getMaxBattery();

              if(actualBattery < 10){
                  if(actualChargingPlaces>0){
                      simulateParking(scooterLicensePlate,parkId,power,ahBattery,maxBattery,actualBattery);
                      vehicleHandler.updateStatusToParked(scooterLicensePlate);
                      vehicleHandler.updateIsChargingY(scooterLicensePlate);
                      parkHandler.updateChargingPlacesR(parkId);
                      return true;
                  }else {
                      List<Park> listChargingParks = parkHandler.getParkWithCPlaces(1);
                      Park parkMoreClose = getParkMoreClose(listChargingParks,pharmacyId);
                      Logger.getLogger(VehicleController.class.getName()).log(Level.INFO, "No places avaiable\nGo to park"+parkMoreClose);
                      return false;
                  }
              }else {
                  if(actualCapacity>0){
                      simulateParking(scooterLicensePlate,parkId,power,ahBattery,maxBattery,actualBattery);
                      vehicleHandler.updateStatusToParked(scooterLicensePlate);
                      parkHandler.updateActualCapacityR(parkId);
                      return true;
                  }else {
                      List<Park> listNormalParks = parkHandler.getParkWithNPlaces(1);
                      Park parkMoreClose = getParkMoreClose(listNormalParks,pharmacyId);
                      Logger.getLogger(VehicleController.class.getName()).log(Level.INFO, "No places avaiable\nGo to park"+parkMoreClose);
                      return false;
                  }
              }
           }else {
               simulateParking(scooterLicensePlate,park.getId(),park.getPower(),vehicle.getAhBattery(),vehicle.getMaxBattery(),vehicle.getActualBattery());
               return false;
           }
    }

    public void simulateParking(String licensePlate,int parkId,double power,double ahBattery, double maxBattery, double actualBattery) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();

        try {
            File myObj = new File(String.format(/*C_and_Assembly\\*/"lock_%4d_%2d_%2d_%2d_%2d_%2d.data",year,month,day,hour,minute,second));    //TODO Verificar a pasta de criação
            if (myObj.createNewFile()) {
                Logger.getLogger(VehicleController.class.getName()).log(Level.INFO, "File created: " + myObj.getName());

                try(FileWriter myWriter = new FileWriter(myObj)) {
                    myWriter.write(licensePlate+"\n");
                    myWriter.write(parkId+"\n");
                    myWriter.write(power+"\n");
                    myWriter.write((int) ahBattery+"\n");
                    myWriter.write((int)maxBattery+"\n");
                    myWriter.write((int)actualBattery+"\n");
                    myWriter.write(year+"\n");
                    myWriter.write(month+"\n");
                    myWriter.write(day+"\n");
                    myWriter.write(hour+"\n");
                    myWriter.write(minute+"\n");
                    myWriter.write(second+"\n");
                }
                
                int lines;
                try( BufferedReader reader = new BufferedReader(new FileReader(myObj.getPath()))) {
                    lines = 0;
                    while (reader.readLine() != null) lines++;
                }

                if(lines == 12) {
                        File flag = new File(String.format(/*C_and_Assembly\\*/"lock_%4d_%2d_%2d_%2d_%2d_%2d.data.flag", year, month, day, hour, minute, second));
                        if (flag.createNewFile()) {
                            Logger.getLogger(VehicleController.class.getName()).log(Level.INFO, "Flag created: " + flag.getName());

                        } else {
                            Logger.getLogger(VehicleController.class.getName()).log(Level.WARNING, "ERROR VehicleController");
                        }
                }
            } else {
                Logger.getLogger(VehicleController.class.getName()).log(Level.WARNING, "ERROR VehicleController");
            }
        } catch (IOException e) {
            Logger.getLogger(VehicleController.class.getName()).log(Level.WARNING, e.getMessage());
        }
    }

    public List<Vehicle> getVehicles() {
        return vehicleHandler.getAllVehicles();
    }

    public Park getParkMoreClose(List<Park> lista,int pharmacyId){
        Pharmacy pharmacy = pharmacyDataHandler.getPharmacyByID(pharmacyId);
        Address startPoint = addressDataHandler.getAddress(pharmacy.getLatitude(), pharmacy.getLongitude());

        Pharmacy pAux = pharmacyDataHandler.getPharmacyByID(lista.get(0).getPharmacyID());
        Address aAux = addressDataHandler.getAddress(pAux.getLatitude(), pAux.getLongitude());
        double menor=Distance.distanceBetweenTwoAddresses(startPoint.getLatitude(),startPoint.getLongitude(),aAux.getLatitude(),aAux.getLongitude());
        Park parkMoreClose=null;

        for (int i = 1; i <lista.size() ; i++) {
            Pharmacy p = pharmacyDataHandler.getPharmacyByID(lista.get(i).getPharmacyID());
            Address a = addressDataHandler.getAddress(p.getLatitude(), p.getLongitude());
            if(Distance.distanceBetweenTwoAddresses(startPoint.getLatitude(),startPoint.getLongitude(),a.getLatitude(),a.getLongitude())<=menor){
                parkMoreClose=lista.get(i);
            }
        }
        return parkMoreClose;
    }

    public void parkDrone(int pharmacyId,String licenseplate){
        Park park = parkHandler.getParkByPharmacyId(pharmacyId,2);
        Vehicle drone = vehicleHandler.getVehicle(licenseplate);
        double actualBattery
    }
}
