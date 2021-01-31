package lapr.project.data;

import lapr.project.model.Pharmacy;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PharmacyDataHandler extends DataHandler {


    public boolean addPharmacy(Pharmacy pharmacy) {
        return addPharmacy(pharmacy.getName(), pharmacy.getLatitudePharmacy(), pharmacy.getLongitudePharmacy(), pharmacy.getEmailAdministrator(), pharmacy.getEmail(), pharmacy.getAltitudePharmacy());
    }

    /**
     * Add the Pharmacy specified to the table "Pharmacy"
     *
     * @param name
     * @param latitude
     * @param longitude
     * @param emailAdministrator
     * @param emailP
     * @param altitude
     * @return true when added with sucess false otherwise
     */
    public boolean addPharmacy(String name, double latitude, double longitude, String emailAdministrator, String emailP, double altitude) {
        boolean added = false;
        try {

            try (CallableStatement callStmt = getConnection().prepareCall("{ call prcAddPharmacy(?,?,?,?,?,?) }")) {
                callStmt.setString(1, name);
                callStmt.setDouble(2, latitude);
                callStmt.setDouble(3, longitude);
                callStmt.setDouble(4, altitude);
                callStmt.setString(5, emailP);
                callStmt.setString(6, emailAdministrator);
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
     * Get the pharmacy with the name specified from the table "Pharmacy"
     *
     * @param name
     * @return the pharmacy
     */
    public Pharmacy getPharmacyByName(String name) {

        try {
            try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getPharmacyByName(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getPharmacy".
                callStmt.setString(2, name);

                // Executa a invocação da função "getClient".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                    int idPharmacy = rSet.getInt(1);
                    String nameP = rSet.getString(2);
                    double latitude = rSet.getDouble(3);
                    double longitude = rSet.getDouble(4);
                    double altitude = rSet.getDouble(5);
                    String emailP = rSet.getString(6);
                    String emailAdmin = rSet.getString(7);


                    return new Pharmacy(idPharmacy, nameP, emailP, latitude, longitude, altitude, emailAdmin);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Pharmacy with name:" + name);
    }

    /**
     * Get the pharmacy with the id specified from the table "Pharmacy"
     *
     * @param id
     * @return the pharmacy
     */
    public Pharmacy getPharmacyByID(int id) {

        try {
            try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getPharmacyByID(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getPharmacy".
                callStmt.setInt(2, id);

                // Executa a invocação da função "getClient".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                    int idPharmacy = rSet.getInt(1);
                    String name = rSet.getString(2);
                    double latitude = rSet.getDouble(3);
                    double longitude = rSet.getDouble(4);
                    double altitude = rSet.getDouble(5);
                    String emailP = rSet.getString(6);
                    String emailAdmin = rSet.getString(7);


                    return new Pharmacy(idPharmacy, name, emailP, latitude, longitude, altitude, emailAdmin);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Pharmacy with id:" + id);
    }

    /**
     * Get all pharmacies from the table "Pharmacy"
     *
     * @return list of pharmacies
     */
    public List<Pharmacy> getAllPharmacies() {

        try {
            try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getPharmacy() }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);

                // Executa a invocação da função "getCourier".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                List<Pharmacy> pharmacyList = new ArrayList<>();


                while (rSet.next()) {
                    int id = rSet.getInt(1);
                    String name = rSet.getString(2);
                    double latitude = rSet.getDouble(3);
                    double longitude = rSet.getDouble(4);
                    double altitude = rSet.getDouble(5);
                    String emailP = rSet.getString(6);
                    String email = rSet.getString(7);

                    pharmacyList.add(new Pharmacy(id, name, emailP, latitude, longitude, altitude, email));
                }

                return pharmacyList;


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("There are no Pharmacies");
    }

    /**
     * Get the pharmacy id with the latitude, longitude and altitude specified from the table "Pharmacy"
     * @param latitudeAddress
     * @param longitudeAddress
     * @param altitudeAddress
     * @return the pharmacy id
     */
    public int getPharmacyByCoordinates(double latitudeAddress, double longitudeAddress, double altitudeAddress) {
        try {
            try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getPharmacyByAddress(?,?,?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.INTEGER);
                // Especifica o parâmetro de entrada da função "getClient".
                callStmt.setDouble(2, latitudeAddress);
                callStmt.setDouble(3, longitudeAddress);
                callStmt.setDouble(4, altitudeAddress);


                // Executa a invocação da função "getClient".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
               return callStmt.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Client with this address");
    }
}

