package lapr.project.data;

import lapr.project.model.ClientOrder;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientOrderHandler extends DataHandler {
    public int addClientOrder(ClientOrder order) {
        return addClientOrder(order.getClientId(), order.getFinalPrice(), order.getFinalWeight());
    }

    private int addClientOrder(int clientId, double finalPrice, double finalWeight) {
        int idOrder=0;

        try {
            openConnection();
            /*
             *  Objeto "callStmt" para invocar o procedimento "addClient" armazenado
             *  na BD.
             *
             *  PROCEDURE addClient(name VARCHAR, email VARCHAR, nif INT, latitude DOUBLE, longitude DOUBLE, creditCardNumber INT)
             *  PACKAGE pkgClient AS TYPE ref_cursor IS REF CURSOR; END pkgClient;
             */
            try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call fncAddClientOrder(?,?,?) }")) {

                callStmt.registerOutParameter(1, OracleTypes.INTEGER);

                callStmt.setDouble(2, finalPrice);
                callStmt.setDouble(3, finalWeight);
                callStmt.setInt(4, clientId);

                callStmt.execute();

                idOrder = callStmt.getInt(1);

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idOrder;
    }

    public ClientOrder getClientOrder(int clientOrderId) {
        /* Objeto "callStmt" para invocar a função "getClient" armazenada na BD.
         *
         * FUNCTION getClient(nif VARCHAR) RETURN pkgClient.ref_cursor
         * PACKAGE pkgClient AS TYPE ref_cursor IS REF CURSOR; END pkgClient;
         */
        try {
            try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getClientOrder(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getClientOrder".
                callStmt.setInt(2, clientOrderId);

                // Executa a invocação da função "getClient".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);


                if (rSet.next()) {
                    int idOrder = rSet.getInt(1);
                    Date dateOrder = rSet.getDate(2);
                    double finalPrice = rSet.getInt(3);
                    double finalWeight = rSet.getInt(4);
                    int status = rSet.getInt(5);
                    int clientId = rSet.getInt(6);


                    return new ClientOrder(idOrder, dateOrder, finalPrice, finalWeight, status, clientId);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No client order with this id");
    }


    public void addProductOrder(int idOrder, int idProduct, int quantity) {
        try {
            openConnection();
            try (CallableStatement callStmt = getConnection().prepareCall("{ call prcAddProductOrder(?,?,?) }")) {
                callStmt.setInt(1, idOrder);
                callStmt.setInt(2, idProduct);
                callStmt.setInt(3, quantity);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
