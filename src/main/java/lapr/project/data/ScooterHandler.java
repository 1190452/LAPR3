package lapr.project.data;

import lapr.project.model.EletricScooter;
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
        addScooter(scooter.getMaxBattery(), scooter.getActualBattery(), scooter.getEnginePower(), scooter.getAh_battery(), scooter.getV_battery(), scooter.getWeight(), scooter.getIdPharmacy());
    }

    public void addScooter(double maxBattery, double actualBattery, double enginePower, double ah_battery, double v_battery, double weight, int id_pharmacy) throws SQLException {
        try {
            /*
             *  Objeto "callStmt" para invocar o procedimento "addScooter" armazenado
             *  na BD.
             *
             *  PROCEDURE addScooter(maxBattery NUMBER, actualBattery NUMBER, status INTEGER, ah_battery NUMBER, v_battery NUMBER, enginePower NUMBER, weight NUMBER, id_Pharmacy INTEGER)
             *  PACKAGE pkgScooter AS TYPE ref_cursor IS REF CURSOR; END pkgScooter;
             */
            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcaddScooter(?,?,?,?,?,?,?) }")) {
                callStmt.setDouble(1, maxBattery);
                callStmt.setDouble(2, actualBattery);
                callStmt.setDouble(3, ah_battery);
                callStmt.setDouble(4, v_battery);
                callStmt.setDouble(5, enginePower);
                callStmt.setDouble(6, weight);
                callStmt.setInt(7, id_pharmacy);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public EletricScooter getScooter(int id) {
        /* Objeto "callStmt" para invocar a função "getScooter" armazenada na BD.
         *
         * FUNCTION getScooter(id INTEGER) RETURN pkgScooter.ref_cursor
         * PACKAGE pkgScooter AS TYPE ref_cursor IS REF CURSOR; END pkgClient;
         */
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getScooter(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getClient".
                callStmt.setInt(2, id);

                // Executa a invocação da função "getClient".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                    int idScooter = rSet.getInt(1);
                    double maxBattery = rSet.getInt(2);
                    double actualBattery = rSet.getDouble(3);
                    int status = rSet.getInt(4);
                    double ah_battery = rSet.getInt(5);
                    double v_battery = rSet.getDouble(6);
                    double enginePower = rSet.getDouble(7);
                    double weight = rSet.getDouble(8);
                    int pharmacyID = rSet.getInt(9);


                    return new EletricScooter(idScooter,maxBattery,actualBattery,status,ah_battery,v_battery,enginePower,weight, pharmacyID);
            }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Scooter with id:" + id);
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
                    int id = rSet.getInt(1);
                    double maxBattery = rSet.getDouble(2);
                    double actualBattery = rSet.getDouble(3);
                    int status = rSet.getInt(4);
                    double ah_battery = rSet.getDouble(5);
                    double v_battery = rSet.getDouble(6);
                    double enginePower = rSet.getDouble(7);
                    double weight = rSet.getDouble(8);
                    int pharmID = rSet.getInt(9);


                    scootersList.add(new EletricScooter(id, maxBattery, actualBattery, status, ah_battery, v_battery,enginePower, weight, pharmID));
                }

                return scootersList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Couriers found");
    }

    public void removeEletricScooter(int id) {
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
                callStmt.setInt(1, id);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<EletricScooter> getScooterList() {
        // implementar

        return null;
    }

    public boolean checkScooterId(String scooterId) {
        return true;
    }

    public boolean checkParkByPharmacyId(String pharmacyId) {
        return true;
    }

    public double getBatteryPercByScooterId(String scooterId) {
        return 0;
    }

}
