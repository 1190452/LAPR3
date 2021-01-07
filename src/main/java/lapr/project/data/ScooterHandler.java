package lapr.project.data;

import lapr.project.model.Scooter;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScooterHandler {

    private final DataHandler dataHandler;

    public ScooterHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    Logger logger = Logger.getLogger(ScooterHandler.class.getName());

    public int addScooter(Scooter scooter, int idPharmacy) throws SQLException {
        CallableStatement callableStatement = null;

        callableStatement = dataHandler.getConnection().prepareCall(" { ? = call funcAddScooter(?,?,?,?,?)"); //FALTA CONTINUAR
        return 0;
    }

    public Scooter getScooter(int id) {
        String query = "SELECT * FROM ElectricScooter " + " WHERE id = " + id;

        Statement callStmt = null;
        ResultSet rst = null;
        try {
            callStmt = dataHandler.getConnection().createStatement();
            rst = callStmt.executeQuery(query);
            if(rst.next()) {
                double maxBattery = rst.getDouble(2);
                double actualBattery = rst.getDouble(3);
                int status = rst.getInt(4);
                int idPharmacy = rst.getInt(5);


                return new Scooter(id,maxBattery,actualBattery,status, idPharmacy);
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

    public ArrayList<Scooter> getAllScooters(Integer id) {  //FALTAM MAIS PARÂMETROS
        ArrayList<Scooter> scooters = new ArrayList<>();
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
}
