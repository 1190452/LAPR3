package lapr.project.controller;

import lapr.project.data.DeliveryHandler;
import lapr.project.data.ParkHandler;
import lapr.project.data.ScooterHandler;
import lapr.project.model.Delivery;
import lapr.project.model.EletricScooter;
import lapr.project.model.Park;

import java.sql.SQLException;
import java.util.List;

public class VehicleController {

    private ScooterHandler scooterHandler;
    private DeliveryHandler deliveryHandler;
    private ParkHandler parkHandler;

    public VehicleController(ScooterHandler scooterHandler) {
        this.scooterHandler = scooterHandler;
    }

    public boolean addScooter() throws SQLException {
        //Scooter scooter = new Scooter();

        return true;
    }

    public boolean removeScooter(int idScooter) throws SQLException {
        boolean isRemoved = false;

        isRemoved = scooterHandler.removeScooter(idScooter);

        return isRemoved;
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


    public boolean validateData(String pharmacyId,String scooterId) {
        if (scooterHandler.checkScooterId(scooterId) && scooterHandler.checkParkByPharmacyId(pharmacyId)) {
            return true;
        }
        return false;
    }

    public boolean parkScooter(String pharmacyId,String scooterId){
           if( scooterHandler.checkScooterId(scooterId) && parkHandler.checkParkByPharmacyId(pharmacyId)){
              double actualBattery = scooterHandler.getBatteryPercByScooterId(scooterId);
              Park park = parkHandler.getParkByPharmacyId();
              int actualCapacity = park.getActualCapacity();
              int actualChargingPlaces = park.getActualChargingPlaces();
              if(actualBattery < 10){
                  if(actualChargingPlaces>0){
                      //simulateParking();
                      return true;
                  }else {
                      return false;
                  }
              }else {
                  if(actualCapacity>0){
                      //simulateParking();
                      return true;
                  }else {
                      return false;
                  }
              }

           }else {
               return false;
           }
    }

    private void simulateParking() {
        //simular parqueamento
    }
}
