package lapr.project.data;

import lapr.project.model.Delivery;

import java.util.logging.Logger;

public class DeliveryHandler {
    private final DataHandler dataHandler;

    public DeliveryHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    Logger logger = Logger.getLogger(ScooterHandler.class.getName());


    public Delivery getDeliveryByCourierId(String courierId) {
        return null;
    }
}
