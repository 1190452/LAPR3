package lapr.project.controller;

import lapr.project.data.ScooterHandler;
import lapr.project.model.Scooter;

import java.sql.SQLException;

public class VehicleController {

    private ScooterHandler scooterHandler;

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
}
