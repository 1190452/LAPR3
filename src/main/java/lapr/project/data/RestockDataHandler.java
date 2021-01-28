package lapr.project.data;

import lapr.project.model.RestockOrder;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestockDataHandler extends DataHandler{

    public boolean addRestock(RestockOrder r) {
        return addRestock(r.getPharmReceiverID(), r.getPharmSenderID(), r.getProductQuantity(), r.getClientOrderID(), r.getProductID());
    }

    /**
     * Add the restock order specified to the table "RestockOrder"
     * @param pharmReceiverID
     * @param pharmSenderID
     * @param productQuantity
     * @param clientOrderID
     * @param productID
     * @return true when added with sucess false otherwise
     */
    private boolean addRestock(int pharmReceiverID, int pharmSenderID, int productQuantity, int clientOrderID, int productID) {
        boolean added = false;
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcAddRestockOrder(?,?,?,?,?) }")) {
                callStmt.setInt(1, productID);
                callStmt.setInt(2, productQuantity);
                callStmt.setInt(3, pharmReceiverID);
                callStmt.setInt(4, pharmSenderID);
                callStmt.setInt(5, clientOrderID);

                callStmt.execute();

                added = true;

                closeAll();
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return added;
    }

    /**
     * Get all restock orders from a pharmacy with the id specified from the table "RestockOrder"
     * @param pharmID
     * @return list of restock orders
     */
    public List<RestockOrder> getRestockList(int pharmID) {
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getRestockList(?) }")) {
                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setInt(2, pharmID);

                // Executa a invocação da função "getCourierList".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                List<RestockOrder> restocklist = new ArrayList<>();

                while (rSet.next()) {
                    int id = rSet.getInt(1);
                    int prodID = rSet.getInt(2);
                    int productQuantity = rSet.getInt(3);
                    int status = rSet.getInt(4);
                    int pharmIDReceiver = rSet.getInt(5);
                    int pharmIDSender = rSet.getInt(6);
                    int clientOrderID = rSet.getInt(7);
                    int idRefillStock = rSet.getInt(8);

                    restocklist.add(new RestockOrder(id, pharmIDReceiver, pharmIDSender,prodID, clientOrderID, productQuantity, status, idRefillStock));
                }
                return restocklist;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Restocks found");
    }

    /**
     * Associate the restock order with id specified to a refill stock with the id specified in the table "RestockOrder"
     * Update the restock order status with the id specified to done in the table "RestockOrder"
     * @param idRO
     * @param idRefillR
     */
    public void updateStatusRestock(int idRO, int idRefillR) {
        try {
            try (CallableStatement callStmt = getConnection().prepareCall("{ call updateStatusRestock(?,?) }")) {
                callStmt.setInt(1, idRO);
                callStmt.setInt(2, idRefillR);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
