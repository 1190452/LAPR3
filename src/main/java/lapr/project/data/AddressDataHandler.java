package lapr.project.data;

import lapr.project.model.Address;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressDataHandler extends DataHandler {

    public boolean addAddress(Address add) {
        return addAddress(add.getLatitude(), add.getLongitude(), add.getStreet(), add.getDoorNumber(), add.getZipCode(), add.getLocality(), add.getAltitude());
    }

    /**
     * Add the address specified to the table "Address"
     *
     * @param latitude
     * @param longitude
     * @param street
     * @param doorNum
     * @param zipcode
     * @param locality
     * @param altitude
     *
     * @return true when added with sucess and false otherwise
     */
    private boolean addAddress(double latitude, double longitude, String street, int doorNum, String zipcode, String locality, double altitude) {
        boolean added = false;
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcaddAddress(?,?,?,?,?,?,?) }")) {
                callStmt.setDouble(1, latitude);
                callStmt.setDouble(2, longitude);
                callStmt.setDouble(3, altitude);
                callStmt.setString(4, street);
                callStmt.setInt(5, doorNum);
                callStmt.setString(6, zipcode);
                callStmt.setString(7, locality);


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
     * Get all addresses in the table "Address"
     * @return list of addresses
     */
    public List<Address> getAllAddresses() {
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
                    double altitude = rSet.getDouble(3);
                    String street = rSet.getString(4);
                    int doorNum = rSet.getInt(5);
                    String zipCode = rSet.getString(6);
                    String locality = rSet.getString(7);

                    addresses.add(new Address(latitude, longitude, street, doorNum, zipCode, locality, altitude));
                }
                return addresses;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("There are no products in the Pharmacy");
    }

    /**
     * Get the address specified from the table "Address"
     * @param latitude
     * @param longitude
     * @param altitude
     * @return the address
     */
    public Address getAddress(double latitude, double longitude, double altitude) {
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getAddress(?,?,?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getCreditCard".
                callStmt.setDouble(2, latitude);
                callStmt.setDouble(3, longitude);
                callStmt.setDouble(4, altitude);


                // Executa a invocação da função "getcreditCard".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                    double latitudeA = rSet.getDouble(1);
                    double longitudeA = rSet.getDouble(2);
                    double altitudeA = rSet.getDouble(3);
                    String street = rSet.getString(4);
                    int doorNumber = rSet.getInt(5);
                    String zipCode = rSet.getString(6);
                    String locality = rSet.getString(7);

                    return new Address(latitudeA,longitudeA,street,doorNumber, zipCode,locality,altitudeA);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Address with coordinates: " + latitude + " & " + longitude);
    }
}
