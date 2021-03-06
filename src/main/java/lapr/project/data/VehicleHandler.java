package lapr.project.data;

import lapr.project.model.Vehicle;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VehicleHandler extends DataHandler{

    public boolean addVehicle(Vehicle vehicle) {
        return addVehicle(vehicle.getLicensePlate(),vehicle.getMaxBattery(), vehicle.getEnginePower(), vehicle.getAhBattery(), vehicle.getvBattery(), vehicle.getIdPharmacy(), vehicle.getTypeVehicle(), vehicle.getFrontalArea(), vehicle.getWeight());
    }

    /**
     * Add the vehicle specified to the table "Vehicle"
     * @param licencePlate
     * @param maxBattery
     * @param enginePower
     * @param ahBattery
     * @param vBattery
     * @param idPharmacy
     * @param typeVehicle
     * @return true when added with sucess false otherwise
     */
    public boolean addVehicle(String licencePlate,double maxBattery, double enginePower, double ahBattery, double vBattery, int idPharmacy, int typeVehicle, double frontalArea, double weight) {

        boolean isAdded = false;
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcAddVehicle(?,?,?,?,?,?,?,?,?) }")) {
                callStmt.setString(1, licencePlate);
                callStmt.setDouble(2, maxBattery);
                callStmt.setDouble(3, ahBattery);
                callStmt.setDouble(4, vBattery);
                callStmt.setDouble(5, enginePower);
                callStmt.setDouble(6, weight);
                callStmt.setDouble(7, frontalArea);
                callStmt.setInt(8, idPharmacy);
                callStmt.setInt(9, typeVehicle);


                callStmt.execute();
                isAdded = true;

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAdded;
    }

    /**
     * Get the vehicle with the license plate specified from the table "Vehicle"
     * @param licencePlate
     * @return the vehicle
     */
    public Vehicle getVehicle(String licencePlate) {

        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getVehicle(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o par??metro de entrada da fun????o "getScooter".
                callStmt.setString(2, licencePlate);

                // Executa a invoca????o da fun????o "getClient".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                    int vehicleID = rSet.getInt(1);
                    String licencePlateVehicle = rSet.getString(2);
                    double maxBatteryVehicle = rSet.getInt(3);
                    double actualBatteryVehicle = rSet.getDouble(4);
                    int statusVehicle = rSet.getInt(5);
                    int isVehicleCharging = rSet.getInt(6);
                    double ahBatteryVehicle = rSet.getInt(7);
                    double vBatteryVehicle = rSet.getDouble(8);
                    double enginepowerVehicle = rSet.getDouble(9);
                    double weightVehicle = rSet.getDouble(10);
                    double maxweightVehicle = rSet.getDouble(11);
                    double frontalareaVehicle = rSet.getDouble(12);
                    int pharmacyID = rSet.getInt(13);
                    int typeVehicle = rSet.getInt(14);

                    return new Vehicle(vehicleID,licencePlateVehicle,maxBatteryVehicle,actualBatteryVehicle,statusVehicle,isVehicleCharging,enginepowerVehicle,ahBatteryVehicle,vBatteryVehicle,weightVehicle, pharmacyID, typeVehicle, maxweightVehicle, frontalareaVehicle);
            }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Vehicle with licence plate:" + licencePlate);
    }

    /**
     * Get all vehicles from the table "Vehicle"
     * @return list of vehicles
     */
    public List<Vehicle> getAllVehicles(){
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getVehicleList() }")) {
                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);


                // Executa a invoca????o da fun????o "getVehicleList".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                List<Vehicle> vehiclesList = new ArrayList<>();


                while (rSet.next()) {
                    int id = rSet.getInt(1);
                    String licencePlate = rSet.getString(2);
                    double maxBattery = rSet.getInt(3);
                    double actualBattery = rSet.getDouble(4);
                    int status = rSet.getInt(5);
                    int isCharging = rSet.getInt(6);
                    double ahBattery = rSet.getInt(7);
                    double vBattery = rSet.getDouble(8);
                    double enginePower = rSet.getDouble(9);
                    double weight = rSet.getDouble(10);
                    double maxWeight = rSet.getDouble(11);
                    int pharmacyID = rSet.getInt(12);
                    int typeVehicle = rSet.getInt(13);
                    double frontalArea = rSet.getDouble(14);

                    vehiclesList.add(new Vehicle(id,licencePlate,maxBattery,actualBattery,status,isCharging,enginePower,ahBattery,vBattery,weight, pharmacyID, typeVehicle, maxWeight, frontalArea));
                }

                return vehiclesList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Vehicles found");
    }

    /**
     * Remove the vehicle with the license plate specified from the table "Vehicle"
     * @param licencePlate
     * @return true when removed with sucess false otherwise
     */
    public boolean removeVehicle(String licencePlate) {
        boolean removed = false;
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcremoveVehicle(?) }")) {
                callStmt.setString(1, licencePlate);

                callStmt.execute();

                closeAll();
                removed =  true;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return removed;
    }

    /**
     * Update the vehicle status with the license plate specified in the table "Vehicle"
     * @param vehicleLicencePlate
     * @return true when updated with sucess false otherwise
     */
    public boolean updateStatusToParked(String vehicleLicencePlate) {
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ call updateStatusToParked(?) }")) {
                callStmt.setString(1, vehicleLicencePlate);

                callStmt.execute();
                closeAll();
                return true;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
        
    }

    /**
     * Update the vehicle status with the license plate specified in the table "Vehicle"
     * @param vehicleLicencePlate
     * @return true when updated with sucess false otherwise
     */
    public boolean chargeVehicle(String vehicleLicencePlate) {
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ call chargeVehicle(?) }")) {
                callStmt.setString(1, vehicleLicencePlate);

                callStmt.execute();
                closeAll();
                return true;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * Update the vehicle charging status with the license plate specified in the table "Vehicle"
     * @param vehicleLicencePlate
     * @return true when updated with sucess false otherwise
     */
    public boolean updateIsChargingY(String vehicleLicencePlate) {
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ call updateIsChargingY(?) }")) {
                callStmt.setString(1, vehicleLicencePlate);

                callStmt.execute();

                closeAll();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Update the vehicle status with the license plate specified in the table "Vehicle"
     * @param licensePlate
     */
    public void updateStatusToBusy(String licensePlate) {
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ call updateStatusToBusy(?) }") ){
                callStmt.setString(1, licensePlate);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the vehicle charging status with the license plate specified in the table "Vehicle"
     * @param licensePlate
     * @return true when updated with sucess false otherwise
     */
    public boolean updateIsChargingN(String licensePlate) {
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ call updateIsChargingN(?) }") ){
                callStmt.setString(1, licensePlate);

                callStmt.execute();

                closeAll();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Associate the vehicle with the license plate specified to a delivery with the id specified in the table "ClientOrder"
     * @param deliveryId
     * @param licensePlate
     * @return true when updated with sucess false otherwise
     */
    public boolean associateVehicleToDelivery(int deliveryId, String licensePlate) {
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcAssociateVehicleToDelivery(?,?) }") ){
                callStmt.setInt(1, deliveryId);
                callStmt.setString(2, licensePlate);

                callStmt.execute();
                Logger.getLogger(VehicleHandler.class.getName()).log(Level.INFO, () -> "Vehicle with license: " + licensePlate +  " associated to the Delivery: " + deliveryId);
                closeAll();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get all scooters available from the pharmacy id and with the energy specified from the table "Vehicle"
     * @param pharmacyId
     * @param necessaryEnergy
     * @return list of scooters
     */
    public List<Vehicle> getAllScooterAvailables(int pharmacyId, double necessaryEnergy) {
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getScooterAvailable(?,?) }")) {
                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setInt(2, pharmacyId);
                callStmt.setDouble(3,necessaryEnergy);


                // Executa a invoca????o da fun????o "getVehicleList".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                ArrayList<Vehicle> vehiclesList = new ArrayList<>();


                while (rSet.next()) {
                    int id = rSet.getInt(1);
                    String licencePlateScooter = rSet.getString(2);
                    double maxBattery = rSet.getInt(3);
                    double actualBattery = rSet.getDouble(4);
                    int status = rSet.getInt(5);
                    int isCharging = rSet.getInt(6);
                    double ahBattery = rSet.getInt(7);
                    double vBattery = rSet.getDouble(8);
                    double enginePower = rSet.getDouble(9);
                    double weight = rSet.getDouble(10);
                    int pharmacyID = rSet.getInt(11);
                    int typeVehicle = rSet.getInt(12);
                    double maxWeight = rSet.getDouble(13);
                    double frontalArea = rSet.getDouble(14);

                    vehiclesList.add(new Vehicle(id,licencePlateScooter,maxBattery,actualBattery,status,isCharging,enginePower,ahBattery,vBattery,weight, pharmacyID, typeVehicle, maxWeight, frontalArea));
                }

                return vehiclesList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Scooters found");
    }

    /**
     * Get all drones available from the pharmacy id and with the energy specified from the table "Vehicle"
     * @param idP
     * @param necessaryEnergy
     * @return list of drones
     */
    public List<Vehicle> getDronesAvailable(int idP, double necessaryEnergy) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getDroneAvailable(?,?) }")) {
                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);

                callStmt.setInt(2, idP);
                callStmt.setDouble(3, necessaryEnergy);

                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                ArrayList<Vehicle> dronesList = new ArrayList<>();

                while (rSet.next()) {
                    int id = rSet.getInt(1);
                    String licencePlateDrone = rSet.getString(2);
                    double droneMaxBattery = rSet.getInt(3);
                    double droneActualBattery = rSet.getDouble(4);
                    int droneStatus = rSet.getInt(5);
                    int droneChargingStatus = rSet.getInt(6);
                    double droneAhbattery = rSet.getInt(7);
                    double droneVbattery = rSet.getDouble(8);
                    double droneEngine = rSet.getDouble(9);
                    double droneWeight = rSet.getDouble(10);
                    double maxWeightCapacity = rSet.getDouble(11);
                    int pId = rSet.getInt(12);
                    int typeOfVehicle = rSet.getInt(13);
                    double droneFrontalArea = rSet.getDouble(14);

                    dronesList.add(new Vehicle(id,licencePlateDrone,droneMaxBattery,droneActualBattery,droneStatus,droneChargingStatus,droneEngine,droneAhbattery,droneVbattery,droneWeight, pId, typeOfVehicle, maxWeightCapacity, droneFrontalArea));
                }

                return dronesList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Drones found");
    }

}




