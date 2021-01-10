package lapr.project.controller;

import lapr.project.data.DeliveryHandler;
import lapr.project.data.EmailAPI;
import lapr.project.data.ParkHandler;
import lapr.project.data.ScooterHandler;
import lapr.project.model.Delivery;
import lapr.project.model.EletricScooter;
import lapr.project.model.Park;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VehicleController {

    private ScooterHandler scooterHandler;
    private DeliveryHandler deliveryHandler;
    private ParkHandler parkHandler;

    public VehicleController(ScooterHandler scooterHandler, DeliveryHandler deliveryHandler, ParkHandler parkHandler) {
        this.scooterHandler = scooterHandler;
        this.deliveryHandler = deliveryHandler;
        this.parkHandler = parkHandler;
    }

    public VehicleController(ScooterHandler scooterHandler) {
        this.scooterHandler = scooterHandler;
    }

    public void addScooter(String licencePlate, double maxBattery, double actualBattery, double ah_battery, double v_battery, double enginePower, double weight, int idPharmacy) throws SQLException {
        EletricScooter scooter = new EletricScooter(licencePlate,maxBattery,actualBattery,enginePower,ah_battery,v_battery,weight,idPharmacy);
        scooter.save();
    }

    public void removeScooter(String licencePlate) throws SQLException {
        EletricScooter es = new EletricScooter(licencePlate);
        es.delete();
    }

    public EletricScooter getAvailableScooter(int courierId){
        Delivery d = deliveryHandler.getDeliveryByCourierId(courierId);
        double necessaryEnergy = d.getNecessaryEnergy();
        List<EletricScooter> scooterList = scooterHandler.getAllScooters();
        for (int i = 0; i < scooterList.size() ; i++) {
            EletricScooter e = scooterList.get(i);
            double actualBattery = e.getActualBattery();
            if(necessaryEnergy < actualBattery){
                String licensePlate = e.getLicensePlate();
                int pharmacyId = e.getIdPharmacy();
                Park park = scooterHandler.getParkByPharmacyId(pharmacyId);
                int parkId = park.getId();
                scooterHandler.updateStatusToFree(licensePlate);
                int isCharging= e.getIsCharging();
                if(isCharging==1){
                    parkHandler.updateActualChargingPlacesA(parkId);
                    scooterHandler.updateIsChargingN(licensePlate);
                }else {
                    parkHandler.updateActualCapacityA(parkId);
                }
                int deliveryId = d.getId();
                scooterHandler.associateScooterToDelivery(deliveryId,licensePlate);
                return e;
            }
        }
        return null;
    }


    public boolean parkScooter(int pharmacyId,String scooterLicensePlate){
        Park park = parkHandler.getParkByPharmacyId(pharmacyId);
        EletricScooter eletricScooter = scooterHandler.getScooter(scooterLicensePlate);
           if( park!=null && eletricScooter!=null){
              double actualBattery = eletricScooter.getActualBattery();

              int actualCapacity = park.getActualCapacity();
              int actualChargingPlaces = park.getActualChargingPlaces();
              int parkId = park.getId();
              int power = park.getPower();
              double ahBattery = eletricScooter.getAh_battery();
              double maxBattery = eletricScooter.getMaxBattery();

              if(actualBattery < 10){
                  if(actualChargingPlaces>0){
                      simulateParking(scooterLicensePlate,parkId,power,ahBattery,maxBattery,actualBattery);
                      scooterHandler.updateStatusToParked(scooterLicensePlate);
                      scooterHandler.updateIsChargingY(scooterLicensePlate);
                      parkHandler.updateChargingPlacesR(parkId);
                      return true;
                  }else {
                      return false;
                  }
              }else {
                  if(actualCapacity>0){
                      simulateParking(scooterLicensePlate,parkId,power,ahBattery,maxBattery,actualBattery);
                      scooterHandler.updateStatusToParked(scooterLicensePlate);
                      parkHandler.updateActualCapacityR(parkId);
                      return true;
                  }else {
                      return false;
                  }
              }
           }else {
               simulateParking(scooterLicensePlate,park.getId(),park.getPower(),eletricScooter.getAh_battery(),eletricScooter.getMaxBattery(),eletricScooter.getActualBattery());
               return false;
           }
    }


    public void simulateParking(String licensePlate,int parkId,int power,double ahBattery, double maxBattery, double actualBattery) {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();

        try {
            File myObj = new File(String.format("C:\\Users\\User\\Documents\\lapr3-2020-g033\\Parking\\lock_%4d_%2d_%2d_%2d_%2d_%2d.data",year,month,day,hour,minute,second));
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

                BufferedReader reader = new BufferedReader(new FileReader(myObj.getPath()));
                int lines = 0;
                while (reader.readLine() != null) lines++;
                reader.close();

                if(lines!=6){

                }else{
                    try {
                        File flag = new File(String.format("C:\\Users\\User\\Documents\\lapr3-2020-g033\\Parking\\lock_%4d_%2d_%2d_%2d_%2d_%2d.data.flag", year, month, day, hour, minute, second));
                        if (flag.createNewFile()) {
                            System.out.println("Flag created: " + flag.getName());
                        } else {
                            System.out.println("File already exists.");
                        }
                    }
                     catch (IOException e) {
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

    public List<EletricScooter> getEletricScooters() {
        return scooterHandler.getAllScooters();
    }
}
