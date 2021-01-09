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

    public void addScooter(double maxBattery, double actualBattery, double ah_battery, double v_battery, double enginePower, double weight, int idPharmacy) throws SQLException {
        EletricScooter scooter = new EletricScooter(maxBattery,actualBattery,ah_battery,v_battery,enginePower,weight,idPharmacy);
        scooter.save();
    }

    public void removeScooter(int idScooter) throws SQLException {
        EletricScooter es = new EletricScooter(idScooter);
        es.delete();
    }

    public EletricScooter getAvailableScooter(String courierId){
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


    public boolean parkScooter(String pharmacyId,String scooterId){
           if( scooterHandler.checkScooterId(scooterId) && parkHandler.checkParkByPharmacyId(pharmacyId)){
              double actualBattery = scooterHandler.getBatteryPercByScooterId(scooterId);
              Park park = parkHandler.getParkByPharmacyId(pharmacyId);
              int actualCapacity = park.getActualCapacity();
              int actualChargingPlaces = park.getActualChargingPlaces();
              if(actualBattery < 10){
                  if(actualChargingPlaces>0){
                      parkHandler.updateChargingPlaces(park.getId());
                      simulateParking();
                      return true;
                  }else {
                      return false;
                  }
              }else {
                  if(actualCapacity>0){
                      parkHandler.updateActualCapacity(park.getId());
                      simulateParking();
                      return true;
                  }else {
                      return false;
                  }
              }
           }else {
               return false;
           }
    }

    public void simulateParking() {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();

        try {
            File myObj = new File("Parking/lock"+"_"+year+"_"+month+"_"+day+"_"+hour+"_"+minute+"_"+second+".data");
            File myObj2 = new File("Parking/lock"+"_"+year+"_"+month+"_"+day+"_"+hour+"_"+minute+"_"+second+".data.flag");
            if (myObj.createNewFile() && myObj2.createNewFile() ) {
                System.out.println("File created: " + myObj.getName());
                System.out.println("File created: " + myObj2.getName());
                File resultingFile = new File("Parking/estimate"+"_"+year+"_"+month+"_"+day+"_"+hour+"_"+minute+"_"+second+".data");
                File resultingFileFlag = new File("Parking/estimate"+"_"+year+"_"+month+"_"+day+"_"+hour+"_"+minute+"_"+second+".data.flag");
                FileWriter myWriter = new FileWriter(resultingFile.getName());
                FileWriter myWriterFlag = new FileWriter(resultingFileFlag.getName());
                myWriter.write(myObj.getName());
                myWriterFlag.write(myObj2.getName());
                myWriter.close();
                myWriterFlag.close();
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
