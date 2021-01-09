package lapr.project.controller;

import lapr.project.data.DeliveryHandler;
import lapr.project.data.ParkHandler;
import lapr.project.data.ScooterHandler;
import lapr.project.model.Delivery;
import lapr.project.model.EletricScooter;
import lapr.project.model.Park;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

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
        List<EletricScooter> scooterList = scooterHandler.getScooterList();
        for (int i = 0; i < scooterList.size() ; i++) {
            EletricScooter e = scooterList.get(i);
            double actualBattery = e.getActualBattery();
            if(necessaryEnergy < actualBattery){
                return e;
            }
        }
        return null;
    }


    public boolean parkScooter(int pharmacyId,String scooterLicensePlate){
        Park park = parkHandler.getParkByPharmacyId(pharmacyId);
        EletricScooter eletricScooter = scooterHandler.getScooter(scooterLicensePlate);
           if( park!=null && eletricScooter!=null){
              double actualBattery = scooterHandler.getBatteryPercByScooterId(scooterLicensePlate);

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
                      parkHandler.updateChargingPlaces(park.getId());
                      return true;
                  }else {
                      return false;
                  }
              }else {
                  if(actualCapacity>0){
                      simulateParking(scooterLicensePlate,parkId,power,ahBattery,maxBattery,actualBattery);
                      scooterHandler.updateStatusToParked(scooterLicensePlate);
                      parkHandler.updateActualCapacity(park.getId());
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
            File myObj = new File("Parking/lock"+""+year+""+month+""+day+""+hour+""+minute+""+second+".data");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());

                FileWriter myWriter = new FileWriter(myObj);
                myWriter.write(licensePlate);
                myWriter.write(parkId);
                myWriter.write(power);
                myWriter.write((int) ahBattery);
                myWriter.write((int)maxBattery);
                myWriter.write((int)actualBattery);

                if(licensePlate== null  || parkId==0 || power==0  || ahBattery==0  || maxBattery==0 ||  actualBattery==0){

                }else{
                    new File("Parking/lock"+""+year+""+month+""+day+""+hour+""+minute+""+second+".data.flag");
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
