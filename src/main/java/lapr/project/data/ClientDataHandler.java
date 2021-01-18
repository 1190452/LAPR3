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
        return addClient(client.getName(), client.getEmail(), client.getnif(), client.getLatitude(), client.getLongitude(), client.getCreditCardNumber(),client.getAltitude());
    }

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
                callStmt.setBigDecimal(6, creditCardNumber);
                callStmt.setDouble(7, altitude);

                callStmt.execute();

                added = true;

                closeAll();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return added;
    }

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
                    int idClient = rSet.getInt(1);
                    String name = rSet.getString(2);
                    String email = rSet.getString(3);
                    int nifClient = rSet.getInt(4);
                    int credits = rSet.getInt(5);
                    double latitude = rSet.getDouble(6);
                    double longitude = rSet.getDouble(7);
                    BigDecimal numberCC = rSet.getBigDecimal(8);
                    double altitude = rSet.getDouble(9);

                    return new Client(email, CLIENT, idClient, name, nifClient, latitude, longitude,altitude, numberCC, credits);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Client with nif:" + nif);
    }

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
                    int nifClient = rSet.getInt(4);
                    int credits = rSet.getInt(5);
                    double latitude = rSet.getDouble(6);
                    double longitude = rSet.getDouble(7);
                    BigDecimal numberCC = rSet.getBigDecimal(8);
                    double altitude = rSet.getDouble(9);

                    return new Client(email, CLIENT, idClient, name, nifClient, latitude, longitude, altitude,numberCC, credits);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Client with email:" + email);
    }

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
                    int idClient = rSet.getInt(1);
                    String name = rSet.getString(2);
                    String email = rSet.getString(3);
                    int nifClient = rSet.getInt(4);
                    int credits = rSet.getInt(4);
                    double latitude = rSet.getDouble(6);
                    double longitude = rSet.getDouble(7);
                    BigDecimal numberCC = rSet.getBigDecimal(8);
                    double altitude = rSet.getDouble(9);

                    return new Client(email,CLIENT, idClient, name, nifClient, latitude, longitude, altitude,numberCC, credits);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Client with id:" + clientId);
    }

    public Client getClientByClientOrderID(int clientOrderID) {
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getClientbByClientOrder(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getClient".
                callStmt.setInt(2, clientOrderID);

                // Executa a invocação da função "getClient".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);


                if (rSet.next()) {
                    if (rSet.next()) {
                        int idClient = rSet.getInt(1);
                        String name = rSet.getString(2);
                        String email = rSet.getString(3);
                        int nifClient = rSet.getInt(4);
                        int credits = rSet.getInt(4);
                        double latitude = rSet.getDouble(6);
                        double longitude = rSet.getDouble(7);
                        BigDecimal numberCC = rSet.getBigDecimal(8);
                        double altitude = rSet.getDouble(9);

                        return new Client(email,CLIENT, idClient, name, nifClient, latitude, longitude, altitude,numberCC, credits);
                    }
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Client order id:" + clientOrderID);
    }
}
