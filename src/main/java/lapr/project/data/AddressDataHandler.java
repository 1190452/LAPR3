package lapr.project.data;

import lapr.project.model.Address;
import java.sql.CallableStatement;
import java.sql.SQLException;

public class AddressDataHandler extends DataHandler {
    public void addAddress(Address add) {
        addAddress(add.getLatitude(), add.getLongitude(), add.getStreet());
    }

    /**
     * Exemplo de invocação de uma "stored procedure".
     * <p>
     * Adiciona o marinheiro especificado à tabela "Address".
     *
     * @param latitude
     * @param longitude
     * @param street
     */
    private void addAddress(double latitude, double longitude, String street) {
        try {
            /*
             *  Objeto "callStmt" para invocar o procedimento "addSailor" armazenado
             *  na BD.
             *
             *  PROCEDURE addSailor(sid NUMBER, sname VARCHAR, rating NUMBER, age NUMBER)
             *  PACKAGE pkgSailors AS TYPE ref_cursor IS REF CURSOR; END pkgSailors;
             */
            CallableStatement callStmt = getConnection().prepareCall("{ call addAddress(?,?,?,?) }");

            callStmt.setDouble(1, latitude);
            callStmt.setDouble(2, longitude);
            callStmt.setString(3, street);

            callStmt.execute();

            closeAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
