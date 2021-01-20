package lapr.project.data;

import lapr.project.model.RefillStock;

import java.sql.CallableStatement;
import java.sql.SQLException;

public class RefillStockDataHandler extends DataHandler {
    public int addRefillStock(RefillStock r) {
        return addRefillStock(r.getNecessaryEnergy(), r.getDistance(), r.getWeight(), r.getlicensePlate(), r.getCourierID());
    }

    private int addRefillStock(double necessaryEnergy, double distance, double weight, String licensePlate, int courierID) {
        try {
            openConnection();

            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call fncAddRefillStock(?,?,?,?,?) }")) {
                callStmt.setDouble(1, necessaryEnergy);
                callStmt.setDouble(2, distance);
                callStmt.setDouble(3, weight);
                callStmt.setString(4, licensePlate);
                callStmt.setInt(5, courierID);

                callStmt.execute();

                closeAll();

                return callStmt.getInt(1); //idRefillStock



            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean updateStatusToDone(int idRS) {
        try {
            openConnection();

            try(CallableStatement callStmt = getConnection().prepareCall("{ call updateStatusRefillStock(?) }")) {
                callStmt.setInt(1, idRS);

                callStmt.execute();

                closeAll();

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
