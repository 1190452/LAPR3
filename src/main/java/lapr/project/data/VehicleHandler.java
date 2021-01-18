package lapr.project.data;

import lapr.project.model.Park;
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
        return addVehicle(vehicle.getLicensePlate(),vehicle.getMaxBattery(), vehicle.getEnginePower(), vehicle.getAhBattery(), vehicle.getvBattery(), vehicle.getIdPharmacy(), vehicle.getTypeVehicle());
    }

    public boolean addVehicle(String licencePlate,double maxBattery, double enginePower, double ahBattery, double vBattery, int idPharmacy, int typeVehicle) {

        boolean isAdded = false;
        try {

            openConnection();

            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcAddVehicle(?,?,?,?,?,?,?) }")) {
                callStmt.setString(1, licencePlate);
                callStmt.setDouble(2, maxBattery);
                callStmt.setDouble(3, ahBattery);
                callStmt.setDouble(4, vBattery);
                callStmt.setDouble(5, enginePower);
                callStmt.setInt(6, idPharmacy);
                callStmt.setInt(7, typeVehicle);


                callStmt.execute();
                isAdded = true;

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAdded;
    }


    public Vehicle getVehicle(String licencePlate) {

        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getVehicle(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getScooter".
                callStmt.setString(2, licencePlate);

                // Executa a invocação da função "getClient".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
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
                    double maxWeight = rSet.getDouble(11);
                    int pharmacyID = rSet.getInt(12);
                    int typeVehicle = rSet.getInt(13);
                    double frontalArea = rSet.getDouble(14);



                    return new Vehicle(id,licencePlateScooter,maxBattery,actualBattery,status,isCharging,ahBattery,vBattery,enginePower,weight, pharmacyID, typeVehicle, maxWeight, frontalArea);
            }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Vehicle with licence plate:" + licencePlate);
    }

    public List<Vehicle> getAllVehicles(){
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getVehicleList() }")) {
                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);


                // Executa a invocação da função "getVehicleList".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                List<Vehicle> vehiclesList = new ArrayList<>();


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
                    double maxWeight = rSet.getDouble(11);
                    int pharmacyID = rSet.getInt(12);
                    int typeVehicle = rSet.getInt(13);
                    double frontalArea = rSet.getDouble(14);



                    vehiclesList.add(new Vehicle(id,licencePlateScooter,maxBattery,actualBattery,status,isCharging,ahBattery,vBattery,enginePower,weight, pharmacyID, typeVehicle, maxWeight, frontalArea));
                }

                return vehiclesList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Vehicles found");
    }

    public boolean removeVehicle(String licencePlate) {
        boolean removed = false;
        try {
            openConnection();

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


    public void updateStatusToParked(String vehicleLicencePlate) {
        try {

            try(CallableStatement callStmt = getConnection().prepareCall("{ call updateStatusToParked(?) }")) {
                callStmt.setString(1, vehicleLicencePlate);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    public void updateIsChargingY(String vehicleLicencePlate) {
        try {

            try(CallableStatement callStmt = getConnection().prepareCall("{ call updateIsChargingY(?) }")) {
                callStmt.setString(1, vehicleLicencePlate);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Park getParkByPharmacyId(int pharmacyId, int parkType) {

        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getParkByPharmacyId(?,?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getPharmacy".
                callStmt.setInt(2, pharmacyId);
                callStmt.setInt(3, parkType);

                // Executa a invocação da função "getClient".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                     int id=rSet.getInt(1);
                     int maxCapacity=rSet.getInt(2);
                     int actualCapacity=rSet.getInt(3);
                     int maxChargingPlaces=rSet.getInt(4);
                     int actualChargingPlaces=rSet.getInt(5);
                     int power=rSet.getInt(6);
                     int pharmacyID=rSet.getInt(7);
                     int parkTypeID=rSet.getInt(8);



                    return new Park(id,maxCapacity,actualCapacity,maxChargingPlaces,actualChargingPlaces,power,pharmacyID,parkTypeID);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Park with pharmacyId " + pharmacyId);
    }

    public void updateStatusToBusy(String licensePlate) {
        try {
            openConnection();

            try(CallableStatement callStmt = getConnection().prepareCall("{ call updateStatusToFree(?) }") ){
                callStmt.setString(1, licensePlate);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateIsChargingN(String licensePlate) {
        try {
            openConnection();

            try(CallableStatement callStmt = getConnection().prepareCall("{ call updateIsChargingN(?) }") ){
                callStmt.setString(1, licensePlate);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void associateVehicleToDelivery(int deliveryId, String licensePlate) {
        try {
            openConnection();

            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcAssociateVehicleToDelivery(?,?) }") ){
                callStmt.setInt(1, deliveryId);
                callStmt.setString(2, licensePlate);

                callStmt.execute();
                Logger.getLogger(VehicleHandler.class.getName()).log(Level.INFO, "Vehicle with license: " + licensePlate +  "associated to the Delivery: " + deliveryId);
                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Vehicle> getAllScooterAvaiables(int pharmacyId) {
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getScooterAvailable(?) }")) {
                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setInt(2, pharmacyId);


                // Executa a invocação da função "getVehicleList".
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
                    double maxWeight = rSet.getDouble(11);
                    int pharmacyID = rSet.getInt(12);
                    int typeVehicle = rSet.getInt(13);
                    double frontalArea = rSet.getDouble(14);



                    vehiclesList.add(new Vehicle(id,licencePlateScooter,maxBattery,actualBattery,status,isCharging,ahBattery,vBattery,enginePower,weight, pharmacyID, typeVehicle, maxWeight, frontalArea));
                }

                return vehiclesList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Scooters found");
    }

    public List<Vehicle> getDronesAvailable(int idP, double necessaryEnergy) {
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getDroneAvailable(?,?,?) }")) {
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
                    String licencePlateScooter = rSet.getString(2);
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



                    dronesList.add(new Vehicle(id,licencePlateScooter,maxBattery,actualBattery,status,isCharging,ahBattery,vBattery,enginePower,weight, pharmacyID, typeVehicle, maxWeight, frontalArea));
                }

                return dronesList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Drones found");
    }

}




