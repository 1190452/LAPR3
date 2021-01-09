package lapr.project.data;

import lapr.project.model.Client;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDataHandler extends DataHandler {
    public void addClient(Client client) {
        addClient(client.getName(), client.getEmail(), client.getnif(), client.getLatitude(), client.getLongitude(), client.getCreditCardNumber());
    }

    private void addClient(String name, String email, double nif, double latitude, double longitude, long creditCardNumber) {
        try {
            openConnection();
            /*
             *  Objeto "callStmt" para invocar o procedimento "addClient" armazenado
             *  na BD.
             *
             *  PROCEDURE addClient(name VARCHAR, email VARCHAR, nif INT, latitude DOUBLE, longitude DOUBLE, creditCardNumber INT)
             *  PACKAGE pkgClient AS TYPE ref_cursor IS REF CURSOR; END pkgClient;
             */
            try(CallableStatement callStmt = getConnection().prepareCall("{ call addClient(?,?,?,?) }")) {
                callStmt.setString(1, name);
                callStmt.setString(2, email);
                callStmt.setDouble(3, nif);
                callStmt.setDouble(4, latitude);
                callStmt.setDouble(5, longitude);
                callStmt.setLong(6, creditCardNumber);



                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Client getClient(double nif) {
        /* Objeto "callStmt" para invocar a função "getClient" armazenada na BD.
         *
         * FUNCTION getClient(nif VARCHAR) RETURN pkgClient.ref_cursor
         * PACKAGE pkgClient AS TYPE ref_cursor IS REF CURSOR; END pkgClient;
         */
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
                    double nifClient = rSet.getInt(4);
                    int credits = rSet.getInt(5);
                    double latitude = rSet.getDouble(6);
                    double longitude = rSet.getDouble(7);
                    long numberCC = rSet.getInt(8);

                    return new Client(email, "CLIENT", idClient, name, nifClient, latitude, longitude, numberCC, credits);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Client with nif:" + nif);
    }

    public Client getClientByEmail(String email) {
        /* Objeto "callStmt" para invocar a função "getClient" armazenada na BD.
         *
         * FUNCTION getClient(nif VARCHAR) RETURN pkgClient.ref_cursor
         * PACKAGE pkgClient AS TYPE ref_cursor IS REF CURSOR; END pkgClient;
         */
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
                    double nifClient = rSet.getInt(4);
                    int credits = rSet.getInt(5);
                    double latitude = rSet.getDouble(6);
                    double longitude = rSet.getDouble(7);
                    long numberCC = rSet.getLong(8);

                    return new Client(email, "CLIENT", idClient, name, nifClient, latitude, longitude, numberCC, credits);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Client with email:" + email);
    }
}
