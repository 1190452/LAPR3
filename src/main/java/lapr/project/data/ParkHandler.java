package lapr.project.data;

import lapr.project.model.Delivery;
import lapr.project.model.Park;

import java.util.logging.Logger;

public class ParkHandler {
    private final DataHandler dataHandler;

    public ParkHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    Logger logger = Logger.getLogger(ScooterHandler.class.getName());


    public boolean checkParkByPharmacyId(String pharmacyId) {
        return true;
    }

    public Park getParkByPharmacyId() {
        return null;
    }
}
