package lapr.project.data;

import lapr.project.model.Park;
import lapr.project.model.Vehicle;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleHandler extends DataHandler{


    public boolean addDrone(Vehicle vehicle) {
        return addDrone(vehicle.getLicensePlate(),vehicle.getMaxBattery(), vehicle.getActualBattery(), vehicle.getEnginePower(), vehicle.getAh_battery(), vehicle.getV_battery(), vehicle.getWeight(), vehicle.getIdPharmacy(), vehicle.getTypeVehicle(), vehicle.getMaxWeightCapacity());
    }

    public boolean addDrone(String licencePlate,double maxBattery, double actualBattery, double enginePower, double ah_battery, double v_battery, double weight, int id_pharmacy, int typeVehicle, double maxWeight) {
        boolean isAdded = false;
        try {

            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcaddDrone(?,?,?,?,?,?,?,?,?) }")) {
                callStmt.setString(1, licencePlate);
                callStmt.setDouble(2, maxBattery);
                callStmt.setDouble(3, actualBattery);
                callStmt.setDouble(4, ah_battery);
                callStmt.setDouble(5, v_battery);
                callStmt.setDouble(6, enginePower);
                callStmt.setDouble(7, weight);
                callStmt.setInt(8, id_pharmacy);
                callStmt.setInt(9, typeVehicle);
                callStmt.setDouble(10, maxWeight);

                callStmt.execute();
                isAdded = true;

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAdded;
    }

    public boolean addScooter(Vehicle vehicle) {
        return addScooter(vehicle.getLicensePlate(),vehicle.getMaxBattery(), vehicle.getActualBattery(), vehicle.getEnginePower(), vehicle.getAh_battery(), vehicle.getV_battery(), vehicle.getWeight(), vehicle.getIdPharmacy(), vehicle.getTypeVehicle());
    }

    public boolean addScooter(String licencePlate,double maxBattery, double actualBattery, double enginePower, double ah_battery, double v_battery, double weight, int id_pharmacy, int typeVehicle) {
        boolean isAdded = false;
        try {

            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcaddScooter(?,?,?,?,?,?,?,?) }")) {
                callStmt.setString(1, licencePlate);
                callStmt.setDouble(2, maxBattery);
                callStmt.setDouble(3, actualBattery);
                callStmt.setDouble(4, ah_battery);
                callStmt.setDouble(5, v_battery);
                callStmt.setDouble(6, enginePower);
                callStmt.setDouble(7, weight);
                callStmt.setInt(8, id_pharmacy);
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

    public Vehicle getVehicle(String licencePlate) {

        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getScooter(?) }")) {


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
                    double ah_battery = rSet.getInt(7);
                    double v_battery = rSet.getDouble(8);
                    double enginePower = rSet.getDouble(9);
                    double weight = rSet.getDouble(10);
                    int pharmacyID = rSet.getInt(11);
                    int typeVehicle = rSet.getInt(12);
                    double maxWeight = rSet.getDouble(13);


                    return new Vehicle(id,licencePlateScooter,maxBattery,actualBattery,status,isCharging,ah_battery,v_battery,enginePower,weight, pharmacyID, typeVehicle, maxWeight);
            }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Vehicle with licence plate:" + licencePlate);
    }

    public ArrayList<Vehicle> getAllVehiclesAvaiables() {
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getScooterList() }")) {
                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);


                // Executa a invocação da função "getVehicleList".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                ArrayList<Vehicle> vehiclesList = new ArrayList<>();


                while (rSet.next()) {
                    int id = rSet.getInt(1);
                    String licensePlate = rSet.getString(2);
                    double maxBattery = rSet.getDouble(3);
                    double actualBattery = rSet.getDouble(4);
                    int status = rSet.getInt(5);
                    int isCharging = rSet.getInt(6);
                    double ah_battery = rSet.getDouble(7);
                    double v_battery = rSet.getDouble(8);
                    double enginePower = rSet.getDouble(9);
                    double weight = rSet.getDouble(10);
                    int pharmID = rSet.getInt(11);
                    int type = rSet.getInt(12);
                    double maxWeight = rSet.getDouble(13);


                    vehiclesList.add(new Vehicle(id,licensePlate, maxBattery, actualBattery, status,isCharging, ah_battery, v_battery,enginePower, weight, pharmID, type, maxWeight));
                }

                return vehiclesList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Scooters found");
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
                     int id=rSet.getInt(1);;
                     int maxCapacity=rSet.getInt(2);;
                     int actualCapacity=rSet.getInt(3);;
                     int maxChargingPlaces=rSet.getInt(4);;
                     int actualChargingPlaces=rSet.getInt(5);;
                     int power=rSet.getInt(6);;
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

    public void updateStatusToFree(String licensePlate) {
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
                System.out.println(String.format("Vehicle with license: %s associated to the Delivery: %d",licensePlate,deliveryId));
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
                    String licensePlate = rSet.getString(2);
                    double maxBattery = rSet.getDouble(3);
                    double actualBattery = rSet.getDouble(4);
                    int status = rSet.getInt(5);
                    int isCharging = rSet.getInt(6);
                    double ah_battery = rSet.getDouble(7);
                    double v_battery = rSet.getDouble(8);
                    double enginePower = rSet.getDouble(9);
                    double weight = rSet.getDouble(10);
                    int pharmID = rSet.getInt(11);
                    int type = rSet.getInt(12);
                    double maxWeight = rSet.getDouble(13);



                    vehiclesList.add(new Vehicle(id,licensePlate, maxBattery, actualBattery, status,isCharging, ah_battery, v_battery,enginePower, weight, pharmID, type, maxWeight));
                }

                return vehiclesList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Scooters found");
    }

    public List<Vehicle> getDronesAvailable(int idP, double necessaryEnergy, double weight) {
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getDroneAvailable(?,?,?) }")) {
                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);

                callStmt.setInt(2, idP);
                callStmt.setDouble(3, necessaryEnergy);
                callStmt.setDouble(4, weight);

                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                ArrayList<Vehicle> dronesList = new ArrayList<>();


                while (rSet.next()) {
                    int id = rSet.getInt(1);
                    String licensePlate = rSet.getString(2);
                    double maxBattery = rSet.getDouble(3);
                    double actualBattery = rSet.getDouble(4);
                    int status = rSet.getInt(5);
                    int isCharging = rSet.getInt(6);
                    double ah_battery = rSet.getDouble(7);
                    double v_battery = rSet.getDouble(8);
                    double enginePower = rSet.getDouble(9);
                    double weightD = rSet.getDouble(10);
                    int pharmID = rSet.getInt(11);
                    int type = rSet.getInt(12);
                    double maxWeight = rSet.getDouble(13);


                    dronesList.add(new Vehicle(id,licensePlate, maxBattery, actualBattery, status,isCharging, ah_battery, v_battery,enginePower, weightD, pharmID, type, maxWeight));
                }

                return dronesList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Drones found");
    }
}




