package lapr.project.data;

import lapr.project.model.EletricScooter;
import lapr.project.model.Park;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ScooterHandler extends DataHandler{

    Logger logger = Logger.getLogger(ScooterHandler.class.getName());


    public void addScooter(EletricScooter scooter) throws SQLException {
        addScooter(scooter.getLicensePlate(),scooter.getMaxBattery(), scooter.getActualBattery(), scooter.getEnginePower(), scooter.getAh_battery(), scooter.getV_battery(), scooter.getWeight(), scooter.getIdPharmacy());
    }

    public void addScooter(String licencePlate,double maxBattery, double actualBattery, double enginePower, double ah_battery, double v_battery, double weight, int id_pharmacy) throws SQLException {
        try {
            /*
             *  Objeto "callStmt" para invocar o procedimento "addScooter" armazenado
             *  na BD.
             *
             *  PROCEDURE addScooter(maxBattery NUMBER, actualBattery NUMBER, status INTEGER, ah_battery NUMBER, v_battery NUMBER, enginePower NUMBER, weight NUMBER, id_Pharmacy INTEGER)
             *  PACKAGE pkgScooter AS TYPE ref_cursor IS REF CURSOR; END pkgScooter;
             */
            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcaddScooter(?,?,?,?,?,?,?,?) }")) {
                callStmt.setString(1, licencePlate);
                callStmt.setDouble(2, maxBattery);
                callStmt.setDouble(3, actualBattery);
                callStmt.setDouble(4, ah_battery);
                callStmt.setDouble(5, v_battery);
                callStmt.setDouble(6, enginePower);
                callStmt.setDouble(7, weight);
                callStmt.setInt(8, id_pharmacy);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public EletricScooter getScooter(String licensePlate) {
        /* Objeto "callStmt" para invocar a função "getScooter" armazenada na BD.
         *
         * FUNCTION getScooter(id INTEGER) RETURN pkgScooter.ref_cursor
         * PACKAGE pkgScooter AS TYPE ref_cursor IS REF CURSOR; END pkgClient;
         */
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getScooter(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getScooter".
                callStmt.setString(2, licensePlate);

                // Executa a invocação da função "getClient".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                    String licencePlateScooter = rSet.getString(1);
                    double maxBattery = rSet.getInt(2);
                    double actualBattery = rSet.getDouble(3);
                    int status = rSet.getInt(4);
                    int isCharging = rSet.getInt(5);
                    double ah_battery = rSet.getInt(6);
                    double v_battery = rSet.getDouble(7);
                    double enginePower = rSet.getDouble(8);
                    double weight = rSet.getDouble(9);
                    int pharmacyID = rSet.getInt(10);


                    return new EletricScooter(licencePlateScooter,maxBattery,actualBattery,status,isCharging,ah_battery,v_battery,enginePower,weight, pharmacyID);
            }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Scooter with licence plate:" + licensePlate);
    }

    public List<EletricScooter> getAllScooters() {  //FALTAM MAIS PARÂMETROS
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getScooterList() }")) {
                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);


                // Executa a invocação da função "getCourierList".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                List<EletricScooter> scootersList = new ArrayList<>();


                while (rSet.next()) {
                    String licensePlate = rSet.getString(1);
                    double maxBattery = rSet.getDouble(2);
                    double actualBattery = rSet.getDouble(3);
                    int status = rSet.getInt(4);
                    int isCharging = rSet.getInt(5);
                    double ah_battery = rSet.getDouble(6);
                    double v_battery = rSet.getDouble(7);
                    double enginePower = rSet.getDouble(8);
                    double weight = rSet.getDouble(9);
                    int pharmID = rSet.getInt(10);


                    scootersList.add(new EletricScooter(licensePlate, maxBattery, actualBattery, status,isCharging, ah_battery, v_battery,enginePower, weight, pharmID));
                }

                return scootersList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Scooters found");
    }

    public void removeEletricScooter(String licencePlate) {
        try {
            openConnection();
            /*
             *  Objeto "callStmt" para invocar o procedimento "removeScooter"
             *  armazenado na BD.
             *
             *  PROCEDURE removeScooter(id INTEGER)
             *  PACKAGE pkgScooter AS TYPE ref_cursor IS REF CURSOR; END pkgScooter;
             */
            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcremoveScooter(?) }")) {
                callStmt.setString(1, licencePlate);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }






    public void updateStatusToParked(String scooterLicensePlate) {
        try {
            /*
             *  Objeto "callStmt" para invocar o procedimento "addScooter" armazenado
             *  na BD.
             *
             *  PROCEDURE addScooter(maxBattery NUMBER, actualBattery NUMBER, status INTEGER, ah_battery NUMBER, v_battery NUMBER, enginePower NUMBER, weight NUMBER, id_Pharmacy INTEGER)
             *  PACKAGE pkgScooter AS TYPE ref_cursor IS REF CURSOR; END pkgScooter;
             */
            try(CallableStatement callStmt = getConnection().prepareCall("{ call updateStatusToParked(?) }")) {
                callStmt.setString(1, scooterLicensePlate);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    public void updateIsChargingY(String scooterLicensePlate) {
        try {
            /*
             *  Objeto "callStmt" para invocar o procedimento "addScooter" armazenado
             *  na BD.
             *
             *  PROCEDURE addScooter(maxBattery NUMBER, actualBattery NUMBER, status INTEGER, ah_battery NUMBER, v_battery NUMBER, enginePower NUMBER, weight NUMBER, id_Pharmacy INTEGER)
             *  PACKAGE pkgScooter AS TYPE ref_cursor IS REF CURSOR; END pkgScooter;
             */
            try(CallableStatement callStmt = getConnection().prepareCall("{ call updateIsChargingY(?) }")) {
                callStmt.setString(1, scooterLicensePlate);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Park getParkByPharmacyId(int pharmacyId) {
        /* Objeto "callStmt" para invocar a função "getScooter" armazenada na BD.
         *
         * FUNCTION getScooter(id INTEGER) RETURN pkgScooter.ref_cursor
         * PACKAGE pkgScooter AS TYPE ref_cursor IS REF CURSOR; END pkgClient;
         */
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getParkByPharmacyId(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getScooter".
                callStmt.setInt(2, pharmacyId);

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
                     int pharmacyID=rSet.getInt(7);;



                    return new Park(id,maxCapacity,actualCapacity,maxChargingPlaces,actualChargingPlaces,power,pharmacyID);
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

    public void associateScooterToDelivery(int deliveryId, String licensePlate) {
        try {
            openConnection();

            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcAssociateScooterToDelivery(?,?) }") ){
                callStmt.setInt(1, deliveryId);
                callStmt.setString(2, licensePlate);

                callStmt.execute();
                System.out.println(String.format("Scooter with license: %s associated to the Delivery: %d",licensePlate,deliveryId));
                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
