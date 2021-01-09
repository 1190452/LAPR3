package lapr.project.data;

import lapr.project.model.Address;
import java.sql.CallableStatement;
import java.sql.SQLException;

public class AddressDataHandler extends DataHandler {
    public void addAddress(Address add) {
        addAddress(add.getLatitude(), add.getLongitude(), add.getStreet(), add.getDoorNumber(), add.getZipCode(), add.getLocality());
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
    private void addAddress(double latitude, double longitude, String street, int doorNum, String zipcode, String locality) {
        try {
            openConnection();
            /*
             *  Objeto "callStmt" para invocar o procedimento "addSailor" armazenado
             *  na BD.
             *
             *  PROCEDURE addSailor(sid NUMBER, sname VARCHAR, rating NUMBER, age NUMBER)
             *  PACKAGE pkgSailors AS TYPE ref_cursor IS REF CURSOR; END pkgSailors;
             */
            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcaddAddress(?,?,?) }")) {
                callStmt.setDouble(1, latitude);
                callStmt.setDouble(2, longitude);
                callStmt.setString(3, street);
                callStmt.setInt(4, doorNum);
                callStmt.setString(5, zipcode);
                callStmt.setString(6, locality);

                callStmt.execute();

                closeAll();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
