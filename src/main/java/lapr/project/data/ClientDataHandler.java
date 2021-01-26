package lapr.project.data;

import lapr.project.model.Client;
import oracle.jdbc.OracleTypes;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDataHandler extends DataHandler {

    private static final String CLIENT = "CLIENT";

    public boolean addClient(Client client) {
        return addClient(client.getName(), client.getEmail(), client.getNif(), client.getLatitude(), client.getLongitude(), client.getCreditCardNumber(),client.getAltitude());
    }

    /**
     * Add the client specified to the table "Client"
     * @param name
     * @param email
     * @param nif
     * @param latitude
     * @param longitude
     * @param creditCardNumber
     * @param altitude
     * @return true when added with sucess false otherwise
     */
    private boolean addClient(String name, String email, double nif, double latitude, double longitude, BigDecimal creditCardNumber,double altitude) {
        boolean added = false;
        try {
            openConnection();

            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcAddClient(?,?,?,?,?,?,?) }")) {
                callStmt.setString(1, email);
                callStmt.setString(2, name);
                callStmt.setDouble(3, nif);
                callStmt.setDouble(4, latitude);
                callStmt.setDouble(5, longitude);
                callStmt.setDouble(6, altitude);
                callStmt.setBigDecimal(7, creditCardNumber);

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
     * Get the client with the nif specified from the table "Client"
     * @param nif
     * @return the client
     */
    public Client getClient(double nif) {

        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getClient(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getClient".
                callStmt.setDouble(2, nif);

                // Executa a invocação da função "getClient".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);


                if (rSet.next()) {
                    int clientID = rSet.getInt(1);
                    String clientName = rSet.getString(2);
                    String clientEmail = rSet.getString(3);
                    int clientNif = rSet.getInt(4);
                    int clientCredits = rSet.getInt(5);
                    double clientLatitude = rSet.getDouble(6);
                    double clientLongitude = rSet.getDouble(7);
                    double clientAltitude = rSet.getDouble(8);
                    BigDecimal clientCC = rSet.getBigDecimal(9);

                    return new Client(clientEmail, CLIENT, clientID, clientName, clientNif, clientLatitude, clientLongitude, clientAltitude, clientCC, clientCredits);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Client with nif:" + nif);
    }

    /**
     * Get the client with the email specified from the table "Client"
     * @param email
     * @return the client
     */
    public Client getClientByEmail(String email) {

        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getClientByEmail(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getClient".
                callStmt.setString(2, email);

                // Executa a invocação da função "getClient".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);


                if (rSet.next()) {
                    int idClient = rSet.getInt(1);
                    String name = rSet.getString(2);
                    String emailC = rSet.getString(3);
                    int nifClient = rSet.getInt(4);
                    int credits = rSet.getInt(5);
                    double latitude = rSet.getDouble(6);
                    double longitude = rSet.getDouble(7);
                    double altitude = rSet.getDouble(8);
                    BigDecimal numberCC = rSet.getBigDecimal(9);

                    return new Client(emailC, CLIENT, idClient, name, nifClient, latitude, longitude,altitude, numberCC, credits);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Client with email:" + email);
    }

    /**
     * Get the client with the id specified from the table "Client"
     * @param clientId
     * @return the client
     */
    public Client getClientByID(int clientId) {

        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getClientByID(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getClient".
                callStmt.setInt(2, clientId);

                // Executa a invocação da função "getClient".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);


                if (rSet.next()) {
                    int cID = rSet.getInt(1);
                    String cName = rSet.getString(2);
                    String cEmail = rSet.getString(3);
                    int cNif = rSet.getInt(4);
                    int cCredits = rSet.getInt(5);
                    double cLatitude = rSet.getDouble(6);
                    double cLongitude = rSet.getDouble(7);
                    double cAltitude = rSet.getDouble(8);
                    BigDecimal cNumberCC = rSet.getBigDecimal(9);

                    return new Client(cEmail,CLIENT, cID, cName, cNif, cLatitude, cLongitude, cAltitude,cNumberCC, cCredits);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Client with id:" + clientId);
    }

    /**
     * Get the client with the client order id specified from the table "Client"
     * @param clientOrderID
     * @return the client
     */
    public Client getClientByClientOrderID(int clientOrderID) {
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getClientByClientOrder(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getClient".
                callStmt.setInt(2, clientOrderID);

                // Executa a invocação da função "getClient".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);


                    if (rSet.next()) {
                        int client1ID = rSet.getInt(1);
                        String client1Name = rSet.getString(2);
                        String client1Email = rSet.getString(3);
                        int client1Nif = rSet.getInt(4);
                        int client1Credits = rSet.getInt(5);
                        double client1Latitude = rSet.getDouble(6);
                        double client1Longitude = rSet.getDouble(7);
                        double client1Altitude = rSet.getDouble(8);
                        BigDecimal client1CC = rSet.getBigDecimal(9);

                        return new Client(client1Email,CLIENT, client1ID, client1Name, client1Nif, client1Latitude, client1Longitude, client1Altitude,client1CC, client1Credits);
                    }
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Client order id:" + clientOrderID);
    }
}
