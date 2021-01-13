package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.utils.Distance;

import javax.swing.text.Utilities;
import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class VehicleController {

    private final VehicleHandler vehicleHandler;
    private DeliveryHandler deliveryHandler;
    private ParkHandler parkHandler;
    private CourierDataHandler courierDataHandler;
    private PharmacyDataHandler pharmacyDataHandler;
    private static final Logger WARNING = Logger.getLogger(VehicleController.class.getName());

    public VehicleController(VehicleHandler vehicleHandler, DeliveryHandler deliveryHandler, ParkHandler parkHandler,CourierDataHandler courierDataHandler,PharmacyDataHandler pharmacyDataHandler) {
        this.vehicleHandler = vehicleHandler;
        this.deliveryHandler = deliveryHandler;
        this.parkHandler = parkHandler;
        this.courierDataHandler = courierDataHandler;
        this.pharmacyDataHandler=pharmacyDataHandler;
    }

    public VehicleController(VehicleHandler vehicleHandler) {
        this.vehicleHandler = vehicleHandler;
    }

    /*
    public boolean addScooter(String licensePlate, double maxBattery, double actualBattery, double enginePower, double ah_battery, double v_battery, double weight, int idPharmacy, int typeVehicle) throws SQLException {
        boolean added;
        Vehicle vehicle = new Vehicle(licensePlate, maxBattery, actualBattery, enginePower, ah_battery, v_battery, weight, idPharmacy, typeVehicle);
        added =  vehicleHandler.addScooter(vehicle);
        return added;
    }

    public boolean addDrone(double maxCapacity,String licensePlate, double maxBattery, double actualBattery, double enginePower, double ah_battery, double v_battery, double weight, int idPharmacy, int typeVehicle) throws SQLException {
        boolean added;
        Vehicle vehicle = new Vehicle(licensePlate, maxBattery, actualBattery, enginePower, ah_battery, v_battery, weight, idPharmacy, typeVehicle,maxCapacity);
        added =  vehicleHandler.addDrone(vehicle);
        return added;
    }*/


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
        //Courier c = courierDataHandler.getCourierByEmail(UserSession.getInstance().getUser().getEmail());
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
              int power = park.getPower();
              double ahBattery = vehicle.getAh_battery();
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
                      System.out.println("No places avaiable\nGo to park"+parkMoreClose);
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
                      System.out.println("No places avaiable\nGo to park"+parkMoreClose);
                      return false;
                  }
              }
           }else {
               simulateParking(scooterLicensePlate,park.getId(),park.getPower(),vehicle.getAh_battery(),vehicle.getMaxBattery(),vehicle.getActualBattery());
               return false;
           }
    }

    public void simulateParking(String licensePlate,int parkId,int power,double ahBattery, double maxBattery, double actualBattery) throws IOException {
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
                System.out.println("File created: " + myObj.getName());

                try (FileWriter myWriter = new FileWriter(myObj)) {
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

                } catch (IOException ioException) {
                    System.out.println(ioException.getMessage());
                }

                int lines;
                try( BufferedReader reader = new BufferedReader(new FileReader(myObj.getPath()))) {
                    lines = 0;
                    while (reader.readLine() != null) lines++;
                }

                if (lines != 12) {

                } else {
                    try {
                        File flag = new File(String.format(/*C_and_Assembly\\*/"lock_%4d_%2d_%2d_%2d_%2d_%2d.data.flag", year, month, day, hour, minute, second));
                        if (flag.createNewFile()) {
                            System.out.println("Flag created: " + flag.getName());
                        } else {
                            System.out.println("ERROR VehicleController");
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } else {
                System.out.println("ERROR VehicleController");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicleHandler.getAllVehiclesAvaiables();
    }

    public Park getParkMoreClose(List<Park> lista,int pharmacyId){
        Pharmacy pharmacy = pharmacyDataHandler.getPharmacyByID(pharmacyId);
        Address startPoint = new AddressDataHandler().getAddress(pharmacy.getLatitude(), pharmacy.getLongitude());

        Pharmacy pAux = pharmacyDataHandler.getPharmacyByID(lista.get(0).getPharmacyID());
        Address aAux = new AddressDataHandler().getAddress(pAux.getLatitude(), pAux.getLongitude());
        double menor=Distance.distanceBetweenTwoAddresses(startPoint.getLatitude(),startPoint.getLongitude(),aAux.getLatitude(),aAux.getLongitude());
        Park parkMoreClose=null;

        for (int i = 1; i <lista.size() ; i++) {
            Pharmacy p = pharmacyDataHandler.getPharmacyByID(lista.get(i).getPharmacyID());
            Address a = new AddressDataHandler().getAddress(p.getLatitude(), p.getLongitude());
            if(Distance.distanceBetweenTwoAddresses(startPoint.getLatitude(),startPoint.getLongitude(),a.getLatitude(),a.getLongitude())<=menor){
                parkMoreClose=lista.get(i);
            }
        }
        return parkMoreClose;
    }
}
