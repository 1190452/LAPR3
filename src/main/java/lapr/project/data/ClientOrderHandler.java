package lapr.project.data;

import lapr.project.model.ClientOrder;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class ClientOrderHandler extends DataHandler {
    public int addClientOrder(ClientOrder order) {
        return addClientOrder(order.getClientId(), order.getFinalPrice(), order.getFinalWeight(), order.getIsComplete());
    }

    private int addClientOrder(int clientId, double finalPrice, double finalWeight, int isComplete) {
        int idOrder = 0;

        try {
            openConnection();

            try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call fncAddClientOrder(?,?,?,?) }")) {

                callStmt.registerOutParameter(1, OracleTypes.INTEGER);

                callStmt.setDouble(2, finalPrice);
                callStmt.setDouble(3, finalWeight);
                callStmt.setInt(4, isComplete);
                callStmt.setInt(5, clientId);

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
                    int isComplete = rSet.getInt(6);
                    int clientId = rSet.getInt(7);
                    int deliveryId = rSet.getInt(8);


                    return new ClientOrder(idOrder, dateOrder, finalPrice, finalWeight, status, isComplete,clientId, deliveryId);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No client order with this id");
    }

    public boolean addProductOrder(int idOrder, int idProduct, int quantity) {
        try {
            openConnection();
            try (CallableStatement callStmt = getConnection().prepareCall("{ call prcAddProductOrder(?,?,?) }")) {
                if (quantity < 0) {
                    return false;
                }
                callStmt.setInt(1, idOrder);
                callStmt.setInt(2, idProduct);
                callStmt.setInt(3, quantity);

                callStmt.execute();

                closeAll();

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Map<Integer, ClientOrder> getUndoneOrders(int pharID) {

        Map<Integer, ClientOrder> orders = new LinkedHashMap<>();

        try {
            try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getUndoneOrdersByPharmacy(?) }")) {

                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getClientOrder".
                callStmt.setInt(2, pharID);

                // Executa a invocação da função "getClient".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);


                while (rSet.next()) {
                    int idOrder = rSet.getInt(1);
                    Date dateOrder = rSet.getDate(2);
                    double finalPrice = rSet.getDouble(3);
                    double finalWeight = rSet.getDouble(4);
                    int status = rSet.getInt(5);
                    int isComplete = rSet.getInt(6);
                    int clientId = rSet.getInt(7);
                    int deliveryId = rSet.getInt(8);

                    orders.put(idOrder, new ClientOrder(idOrder, dateOrder, finalPrice, finalWeight, status, isComplete,clientId, deliveryId));
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public boolean updateStockAfterPayment(int orderId) {
        try {
            openConnection();
            try (CallableStatement callStmt = getConnection().prepareCall("{ call prcUpdateStockAfterPayment(?) }")) {

                callStmt.setInt(1, orderId);
                callStmt.execute();
                closeAll();

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateClientCredits(int orderId) {
        try {
            openConnection();
            try (CallableStatement callStmt = getConnection().prepareCall("{ call prcUpdateCredits(?) }")) {

                callStmt.setInt(1, orderId);
                callStmt.execute();
                closeAll();

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<String> getClientEmailByDelivery(int id) {

        List<String> clientMails = new ArrayList<>();

        try {
            try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getClientEmailByDelivery(?) }")) {

                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getClientOrder".
                callStmt.setInt(2, id);

                // Executa a invocação da função "getClient".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);


                while (rSet.next()) {
                    String mail = rSet.getString(1);
                    clientMails.add(mail);
                }


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientMails;

    }

    public void updateStatusStock(int idClient) {
        try {
            openConnection();

            try (CallableStatement callStmt = getConnection().prepareCall("{ call updateStatusStockClientOrder(?) }")) {
                callStmt.setInt(1, idClient);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateStatusOrder(int idDelivery, int orderId) {

        try {
            openConnection();

            try (CallableStatement callStmt = getConnection().prepareCall("{ call updateStatusOrder(?,?) }")) {
                callStmt.setInt(1, idDelivery);
                callStmt.setInt(2, orderId);


                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
