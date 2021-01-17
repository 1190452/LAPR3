package lapr.project.data;

import lapr.project.model.Restock;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestockDataHandler extends DataHandler{

    public List<Restock> getRestockList(int pharmID) {
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getRestockRequestList(?) }")) {
                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setInt(2, pharmID);


                // Executa a invocação da função "getCourierList".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                List<Restock> restocklist = new ArrayList<>();

                while (rSet.next()) {
                    int id = rSet.getInt(1);
                    int pharmIDReceiver = rSet.getInt(2);
                    int pharmIDSender = rSet.getInt(2);
                    String productName = rSet.getString(3);
                    int clientID = rSet.getInt(4);
                    int productQuantity = rSet.getInt(5);


                    restocklist.add(new Restock(id, pharmIDReceiver, pharmIDSender, productName, clientID, productQuantity));
                }

                return restocklist;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Restocks found");
    }

    public void updateStatusRestock(int id) {
        try {
            openConnection();

            try (CallableStatement callStmt = getConnection().prepareCall("{ call updateStatusRestockRequest(?) }")) {
                callStmt.setInt(1, id);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
