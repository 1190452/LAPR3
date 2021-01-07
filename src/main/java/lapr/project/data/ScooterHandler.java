package lapr.project.data;

import lapr.project.model.Client;
import lapr.project.model.Courier;
import lapr.project.model.EletricScooter;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScooterHandler extends DataHandler{

    private final DataHandler dataHandler;

    public ScooterHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }
    Logger logger = Logger.getLogger(ScooterHandler.class.getName());


    public void addScooter(EletricScooter scooter) throws SQLException {
        addScooter(scooter.getMaxBattery(), scooter.getActualBattery(), scooter.getStatus(), scooter.getEnginePower(), scooter.getAh_battery(), scooter.getV_battery(), scooter.getWeight(), scooter.getIdPharmacy());
    }

    public void addScooter(double maxBattery, double actualBattery, int status, double enginePower, double ah_battery, double v_battery, double weight, int id_pharmacy) throws SQLException {
        try {
            /*
             *  Objeto "callStmt" para invocar o procedimento "addScooter" armazenado
             *  na BD.
             *
             *  PROCEDURE addScooter(maxBattery NUMBER, actualBattery NUMBER, status INTEGER, ah_battery NUMBER, v_battery NUMBER, enginePower NUMBER, weight NUMBER, id_Pharmacy INTEGER)
             *  PACKAGE pkgScooter AS TYPE ref_cursor IS REF CURSOR; END pkgScooter;
             */
            CallableStatement callStmt = getConnection().prepareCall("{ call addClient(?,?,?,?,?,?,?,?) }");

            callStmt.setDouble(2, maxBattery);
            callStmt.setDouble(3, actualBattery);
            callStmt.setInt(4, status);
            callStmt.setDouble(5, ah_battery);
            callStmt.setDouble(6, v_battery);
            callStmt.setDouble(7, enginePower);
            callStmt.setDouble(8, weight);
            callStmt.setInt(9, id_pharmacy);



            callStmt.execute();

            closeAll();
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
        CallableStatement callStmt;
        try {
            callStmt = getConnection().prepareCall("{ ? = call getScooter(?) }");


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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Scooter with id:" + id);
    }

    public ArrayList<EletricScooter> getAllScooters(Integer id) {  //FALTAM MAIS PARÂMETROS
        ArrayList<EletricScooter> scooters = new ArrayList<>();
        String query = "Select * FROM ElectricScooters "; //FALTA IMPLEMENTAR AQUI
        return scooters;
    }

    public void removeScooter(int id) {
        try {
            openConnection();
            /*
             *  Objeto "callStmt" para invocar o procedimento "removeScooter"
             *  armazenado na BD.
             *
             *  PROCEDURE removeScooter(id INTEGER)
             *  PACKAGE pkgScooter AS TYPE ref_cursor IS REF CURSOR; END pkgScooter;
             */
            CallableStatement callStmt = getConnection().prepareCall("{ call removeScooter(?) }");

            callStmt.setInt(1, id);

            callStmt.execute();

            closeAll();
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
