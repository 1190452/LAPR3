package lapr.project.data;

import lapr.project.model.Client;
import oracle.jdbc.OracleTypes;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDB extends DataHandler {
    public static void addClient(Client client) {
        addClient(client.getName(), client.getEmail(), client.getnif(), client.getLatitude(), client.getLongitude(), client.getCreditCardNumber());
    }

    private static void addClient(String name, String email, int nif, double latitude, double longitude, int creditCardNumber) {
        try {
            openConnection();
            /*
             *  Objeto "callStmt" para invocar o procedimento "addClient" armazenado
             *  na BD.
             *
             *  PROCEDURE addClient(name VARCHAR, email VARCHAR, nif INT, latitude DOUBLE, longitude DOUBLE, creditCardNumber INT)
             *  PACKAGE pkgClient AS TYPE ref_cursor IS REF CURSOR; END pkgClient;
             */
            CallableStatement callStmt = getConnection().prepareCall("{ call addClient(?,?,?,?) }");

            callStmt.setString(1, name);
            callStmt.setString(2, email);
            callStmt.setInt(3, nif);
            callStmt.setDouble(4, latitude);
            callStmt.setDouble(5, longitude);
            callStmt.setInt(6, creditCardNumber);



            callStmt.execute();

            closeAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Client getClient(int nif) {
        /* Objeto "callStmt" para invocar a função "getClient" armazenada na BD.
         *
         * FUNCTION getClient(nif VARCHAR) RETURN pkgClient.ref_cursor
         * PACKAGE pkgClient AS TYPE ref_cursor IS REF CURSOR; END pkgClient;
         */
        CallableStatement callStmt = null;
        try {
            callStmt = getConnection().prepareCall("{ ? = call getClient(?) }");


            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Especifica o parâmetro de entrada da função "getClient".
            callStmt.setInt(2, nif);

            // Executa a invocação da função "getClient".
            callStmt.execute();

            // Guarda o cursor retornado num objeto "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                int nifClient = rSet.getInt(5);
                String name = rSet.getString(3);
                String email = rSet.getString(4);
                int credits = rSet.getInt(6);
                double latitude = rSet.getDouble(7);
                double longitude = rSet.getDouble(8);


                return new Client(name, email, nifClient, latitude, longitude, credits);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Client with nif:" + nif);
    }
}
