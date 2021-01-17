package lapr.project.model;

import java.util.ArrayList;
import java.util.List;

public class DeliveryRestock {
    private final List<Restock> restock;
    private final int courierID;
    private final int vehicleid;

    public DeliveryRestock(int courierID, int vehicleid) {
        this.restock = new ArrayList<>();
        this.courierID = courierID;
        this.vehicleid = vehicleid;
    }

    public List<Restock> getRestock() {
        return restock;
    }

    public int getCourierID() {
        return courierID;
    }

    public int getVehicleid() {
        return vehicleid;
    }


}
