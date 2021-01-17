package lapr.project.data;

import lapr.project.model.Pharmacy;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PharmacyDataHandler extends DataHandler{


    public boolean addPharmacy(Pharmacy pharmacy) {
        return addPharmacy(pharmacy.getName(), pharmacy.getLatitude(), pharmacy.getLongitude(), pharmacy.getEmailAdministrator(), pharmacy.getEmail(), pharmacy.getAltitude());
    }

    public boolean addPharmacy(String name, double latitude, double longitude, String emailAdministrator, String emailP,double altitude) {
        boolean added =  false;
        try {
            openConnection();

            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcAddPharmacy(?,?,?,?,?,?) }")) {
                callStmt.setString(1, name);
                callStmt.setDouble(2, latitude);
                callStmt.setDouble(3, longitude);
                callStmt.setString(4, emailP);
                callStmt.setString(5, emailAdministrator);
                callStmt.setDouble(6, altitude);
                callStmt.execute();
                added = true;
                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return added;

    }

    public Pharmacy getPharmacyByName(String name) {

        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getPharmacyByName(?) }")) {


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
                    String emailP = rSet.getString(5);
                    String emailAdmin = rSet.getString(6);
                    double altitude = rSet.getDouble(7);


                    return new Pharmacy(idPharmacy, nameP, emailP, latitude, longitude, altitude, emailAdmin);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Pharmacy with name:" + name);
    }

    public Pharmacy getPharmacyByID(int id) {

        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getPharmacyByID(?) }")) {


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
                    String emailP = rSet.getString(5);
                    String emailAdmin = rSet.getString(6);
                    double altitude = rSet.getDouble(7);


                    return new Pharmacy(idPharmacy, name, emailP, latitude, longitude,altitude, emailAdmin);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Pharmacy with id:" + id);
    }


    public List<Pharmacy> getAllPharmacies() {

        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getPharmacy() }")) {


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
                    String emailP = rSet.getString(5);
                    String email = rSet.getString(6);
                    double altitude = rSet.getDouble(7);

                    pharmacyList.add(new Pharmacy(id,emailP, name, latitude, longitude, altitude,email));
                }

                return pharmacyList;


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("There are no Pharmacies");
    }
}
