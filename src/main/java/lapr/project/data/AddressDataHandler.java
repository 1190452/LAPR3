package lapr.project.data;

import lapr.project.model.Address;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    private boolean addAddress(double latitude, double longitude, String street, int doorNum, String zipcode, String locality) {
        boolean added = false;
        try {
            openConnection();
            /*
             *  Objeto "callStmt" para invocar o procedimento "addSailor" armazenado
             *  na BD.
             *
             *  PROCEDURE addSailor(sid NUMBER, sname VARCHAR, rating NUMBER, age NUMBER)
             *  PACKAGE pkgSailors AS TYPE ref_cursor IS REF CURSOR; END pkgSailors;
             */
            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcaddAddress(?,?,?,?,?,?) }")) {
                callStmt.setDouble(1, latitude);
                callStmt.setDouble(2, longitude);
                callStmt.setString(3, street);
                callStmt.setInt(4, doorNum);
                callStmt.setString(5, zipcode);
                callStmt.setString(6, locality);

                callStmt.execute();

                added = true;

                closeAll();

            }


        } catch (SQLException e) {
            e.printStackTrace();

        }
        return added;
    }

    public List<Address> getAllAddresses() {
        /* Objeto "callStmt" para invocar a função "getCourier" armazenada na BD.
         *
         * FUNCTION getCourier(nif INT) RETURN pkgCourier.ref_cursor
         * PACKAGE pkgCourier AS TYPE ref_cursor IS REF CURSOR; END pkgCourier;
         */
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getAddressList() }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);

                // Executa a invocação da função "getCourier".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                List<Address> addresses = new ArrayList<>();
                while (rSet.next()) {
                    double latitude = rSet.getDouble(1);
                    double longitude = rSet.getDouble(2);
                    String street = rSet.getString(3);
                    int doorNum = rSet.getInt(4);
                    String zipCode = rSet.getString(5);
                    String locality = rSet.getString(6);

                    addresses.add(new Address(latitude, longitude, street, doorNum, zipCode, locality));
                }
                return addresses;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("There are no products in the Pharmacy");
    }

    public Address getAddress(double latitude, double longitude) {
        /* Objeto "callStmt" para invocar a função "getCreditCard" armazenada na BD.
         *
         * FUNCTION getCreditCard(cardNumber int) RETURN pkgCreditCards.ref_cursor
         * PACKAGE pkgCreditCards AS TYPE ref_cursor IS REF CURSOR; END pkgCreditCards;
         */
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getAddress(?,?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getCreditCard".
                callStmt.setDouble(2, latitude);
                callStmt.setDouble(3, longitude);


                // Executa a invocação da função "getcreditCard".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                    double latitudeA = rSet.getDouble(1);
                    double longitudeA = rSet.getDouble(2);
                    String street = rSet.getString(3);
                    int doorNumber = rSet.getInt(4);
                    String zipCode = rSet.getString(5);
                    String locality = rSet.getString(6);

                    return new Address(latitudeA,longitudeA,street,doorNumber, zipCode,locality);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Address with coordinates: " + latitude + " & " + longitude);
    }
}
