package lapr.project.data;

import lapr.project.model.RefillStock;
import oracle.jdbc.internal.OracleTypes;

import java.sql.CallableStatement;
import java.sql.SQLException;

public class RefillStockDataHandler extends DataHandler {
    public int addRefillStock(RefillStock r) {
        return addRefillStock(r.getNecessaryEnergyRestock(), r.getDistanceRestock(), r.getWeightRestock(), r.getlicensePlate(), r.getCourierID());
    }

    /**
     * Add the refill stock specified to the table "RefillStock"
     * @param necessaryEnergy
     * @param distance
     * @param weight
     * @param licensePlate
     * @param courierID
     * @return true when added with sucess false otherwise
     */
    private int addRefillStock(double necessaryEnergy, double distance, double weight, String licensePlate, int courierID) {
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call fncAddRefill(?,?,?,?,?) }")) {
                callStmt.registerOutParameter(1, OracleTypes.INTEGER);
                callStmt.setDouble(2, necessaryEnergy);
                callStmt.setDouble(3, distance);
                callStmt.setDouble(4, weight);
                callStmt.setString(5, licensePlate);
                callStmt.setInt(6, courierID);

                callStmt.execute();

                int id = callStmt.getInt(1); //idRefillStock

                closeAll();

                return id;


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Update refill stock with the id specified in table "RefillStock"
     * @param idRS
     * @return true when updated with sucess false otherwise
     */
    public boolean updateStatusToDone(int idRS) {
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ call updateStatusRefill(?) }")) {
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
