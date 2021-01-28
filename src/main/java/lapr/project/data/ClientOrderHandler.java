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

    /**
     * Add the client order specified to the table "ClientOrder"
     * @param clientId
     * @param finalPrice
     * @param finalWeight
     * @param isComplete
     * @return true when added with sucess false otherwise
     */
    private int addClientOrder(int clientId, double finalPrice, double finalWeight, int isComplete) {
        int idOrder = 0;

        try {
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

    /**
     * Add the product order specified to the table "ProductOrder"
     * @param idOrder
     * @param idProduct
     * @param quantity
     * @return true when added with sucess false otherwise
     */
    public boolean addProductOrder(int idOrder, int idProduct, int quantity) {
        try {
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

    /**
     * Get the undone orders from the pharmacy with the id specified from the table "ClientOrder"
     * @param pharID
     * @return a map with the order id and the client order
     */
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

                    orders.put(idOrder, new ClientOrder(idOrder, dateOrder, finalPrice, finalWeight, status, isComplete, clientId, deliveryId));
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    /**
     * Update the product stock after the order is paid in the table "Product"
     * Update the client credits after the order is paid in the table "Client"
     * @param orderId
     * @param stockMissing
     * @param productsPrice
     * @return true when updated with sucess false otherwise
     */
    public boolean updateStockAfterPayment(int orderId, int stockMissing, double productsPrice) {
        try {
            try (CallableStatement callStmt = getConnection().prepareCall("{ call prcUpdateStockAfterPayment(?,?,?) }")) {

                callStmt.setInt(1, orderId);
                callStmt.setInt(2, stockMissing);
                callStmt.setDouble(3, productsPrice);
                callStmt.execute();
                closeAll();

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Update the client credits with the client order id specified in the table "Client"
     * @param orderId
     * @return true when updated with sucess false otherwise
     */
    public boolean updateClientCredits(int orderId) {
        try {
            try (CallableStatement callStmt = getConnection().prepareCall("{ call prcUpdateCredits(?)}")) {

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

    /**
     * Get all clients emails associated with a delivery with the id specified from the table "Client"
     * @param id
     * @return list of clients emails
     */
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

    /**
     * Associate the order with the order id specified to a delivery with the delivery id specified in the table "ClientOrder"
     * Update the order status with the order id specified to done in the table "ClientOrder"
     * @param idDelivery
     * @param orderId
     * @return true when updated with sucess false otherwise
     */
    public boolean updateStatusOrder(int idDelivery, int orderId) {

        try {
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
