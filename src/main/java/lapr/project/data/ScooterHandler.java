package lapr.project.data;

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

public class ScooterHandler {

    private final DataHandler dataHandler;

    public ScooterHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }
    Logger logger = Logger.getLogger(ScooterHandler.class.getName());


    public int addScooter(EletricScooter scooter, int idPharmacy) throws SQLException {
        CallableStatement callableStatement = null;
        callableStatement = DataHandler.getConnection().prepareCall(" { ? = call funcAddScooter(?,?,?,?,?)"); //FALTA CONTINUAR

        callableStatement.registerOutParameter(1, OracleTypes.INTEGER);
        callableStatement.setDouble(2,scooter.getMaxBattery());


        return 0;
    }

    public EletricScooter getScooter(int id) {
        String query = "SELECT * FROM ElectricScooter " + " WHERE id = " + id;

        Statement callStmt = null;
        ResultSet rst = null;
        try {
            callStmt = DataHandler.getConnection().createStatement();
            rst = callStmt.executeQuery(query);
            if(rst.next()) {
                double maxBattery = rst.getDouble(2);
                double actualBattery = rst.getDouble(3);
                int status = rst.getInt(4);
                double ah_battery = rst.getInt(5);
                double v_battery = rst.getDouble(6);
                double enginePower = rst.getDouble(7);
                double weight = rst.getDouble(8);
                int idPharmacy = rst.getInt(9);


                return new EletricScooter(id,maxBattery,actualBattery,status,enginePower,ah_battery,v_battery,weight,idPharmacy);
            }

        } catch (SQLException exception) {
            logger.log(Level.WARNING, exception.getMessage());
        } finally {
            try {
                if(rst != null)
                    rst.close();
                if(callStmt != null)
                    callStmt.close();
            }catch (SQLException exception) {
                logger.log(Level.WARNING, exception.getMessage());
            }
        }
        return null;
    }

    public ArrayList<EletricScooter> getAllScooters(Integer id) {  //FALTAM MAIS PARÂMETROS
        ArrayList<EletricScooter> scooters = new ArrayList<>();
        String query = "Select * FROM ElectricScooters "; //FALTA IMPLEMENTAR AQUI
        return scooters;
    }

    public boolean removeScooter(int id) throws SQLException {
        CallableStatement callableStatement = null;
        boolean isRemoved = false;
        try {
            callableStatement = dataHandler.getConnection().prepareCall("{ call removeScooter(?) }");

            callableStatement.setInt(1,id);

            callableStatement.execute();

            isRemoved = true;

            callableStatement.close();

        }catch (SQLException | NullPointerException e) {
            logger.log(Level.WARNING, e.getMessage());
        }

        return isRemoved;


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
