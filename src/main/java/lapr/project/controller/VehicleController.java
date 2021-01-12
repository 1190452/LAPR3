package lapr.project.controller;

import lapr.project.data.DeliveryHandler;
import lapr.project.data.ParkHandler;
import lapr.project.data.VehicleHandler;
import lapr.project.model.Delivery;
import lapr.project.model.Park;
import lapr.project.model.Vehicle;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VehicleController {

    private final VehicleHandler vehicleHandler;
    private DeliveryHandler deliveryHandler;
    private ParkHandler parkHandler;
    private static final Logger WARNING = Logger.getLogger(VehicleController.class.getName());

    public VehicleController(VehicleHandler vehicleHandler, DeliveryHandler deliveryHandler, ParkHandler parkHandler) {
        this.vehicleHandler = vehicleHandler;
        this.deliveryHandler = deliveryHandler;
        this.parkHandler = parkHandler;
    }

    public VehicleController(VehicleHandler vehicleHandler) {
        this.vehicleHandler = vehicleHandler;
    }

    public boolean addVehicle(String licensePlate, double maxBattery, double actualBattery, double enginePower, double ah_battery, double v_battery, double weight, int idPharmacy, int typeVehicle) throws SQLException {

        try{
            if(licensePlate!=null) {
                Vehicle vehicle = new Vehicle(licensePlate, maxBattery, actualBattery, enginePower, ah_battery, v_battery, weight, idPharmacy, typeVehicle);
               // return vehicle.save();
                vehicleHandler.addVehicle(vehicle);
                return true;
            }
        }catch (Exception e){
            WARNING.log(Level.WARNING, e.getMessage());
        }
        return false;
    }

    public boolean removeVehicle(String licencePlate) {
        try{
             //vehicle.delete();
            // return true;
            vehicleHandler.removeVehicle(licencePlate);
        }catch (Exception e){
            WARNING.log(Level.WARNING, e.getMessage());
        }
        return false;

    }

    public Vehicle getAvailableScooter(int courierId){
        Delivery d = deliveryHandler.getDeliveryByCourierId(courierId);
        double necessaryEnergy = d.getNecessaryEnergy();
        List<Vehicle> vehicleList = vehicleHandler.getAllVehicles();
        for (Vehicle vehicle : vehicleList) {
            double actualBattery = vehicle.getActualBattery();
            if (necessaryEnergy < actualBattery) {
                String licensePlate = vehicle.getLicensePlate();
                int pharmacyId = vehicle.getIdPharmacy();
                Park park = vehicleHandler.getParkByPharmacyId(pharmacyId);
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

        Park park = parkHandler.getParkByPharmacyId(pharmacyId);
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
                      return false;
                  }
              }else {
                  if(actualCapacity>0){
                      simulateParking(scooterLicensePlate,parkId,power,ahBattery,maxBattery,actualBattery);
                      vehicleHandler.updateStatusToParked(scooterLicensePlate);
                      parkHandler.updateActualCapacityR(parkId);
                      return true;
                  }else {
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
            File myObj = new File(String.format("C_and_Assembly\\lock_%4d_%2d_%2d_%2d_%2d_%2d.data",year,month,day,hour,minute,second));
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
                    Logger.getLogger(VehicleController.class.getName()).log(Level.WARNING, ioException.getMessage());
                }

                    int lines;
                   try( BufferedReader reader = new BufferedReader(new FileReader(myObj.getPath()))) {
                        lines = 0;
                       while (reader.readLine() != null) lines++;
                   }

                    if (lines != 6) {

                    } else {
                        try {
                            File flag = new File(String.format("C_and_Assembly\\lock_%4d_%2d_%2d_%2d_%2d_%2d.data.flag", year, month, day, hour, minute, second));
                            if (flag.createNewFile()) {
                                System.out.println("Flag created: " + flag.getName());
                            } else {
                                System.out.println("File already exists.");
                            }
                        } catch (IOException e) {
                            System.out.println("An error occurred.");
                            e.printStackTrace();
                        }
                    }

            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }



    public ArrayList<Vehicle> getVehicles() {
        return vehicleHandler.getAllVehicles();
    }
}
