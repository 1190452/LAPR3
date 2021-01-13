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
        return addPharmacy(pharmacy.getName(), pharmacy.getLatitude(), pharmacy.getLongitude(), pharmacy.getEmailAdministrator(), pharmacy.getEmail());
    }

    public boolean addPharmacy(String name, double latitude, double longitude, String emailAdministrator, String emailP) {
        boolean added =  false;
        try {
            openConnection();
            /*
             *  Objeto "callStmt" para invocar o procedimento "addPharmacy" armazenado
             *  na BD.
             *
             *  PROCEDURE addPharmacy(name VARCHAR, latitude NUMBER, longitude NUMBER, emailAdministrator)
             *  PACKAGE pkgCourier AS TYPE ref_cursor IS REF CURSOR; END pkgCourier;
             */
            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcAddPharmacy(?,?,?,?,?) }")) {
                callStmt.setString(1, name);
                callStmt.setDouble(2, latitude);
                callStmt.setDouble(3, longitude);
                callStmt.setString(4, emailP);
                callStmt.setString(5, emailAdministrator);
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
        /* Objeto "callStmt" para invocar a função "getPharmacy" armazenada na BD.
         *
         * FUNCTION getPharmacy(id INTEGER) RETURN pkgPharmacy.ref_cursor
         * PACKAGE pkgPharmacy AS TYPE ref_cursor IS REF CURSOR; END pkgPharmacy;
         */
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


                    return new Pharmacy(idPharmacy, nameP, emailP, latitude, longitude, emailAdmin);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Pharmacy with name:" + name);
    }

    public Pharmacy getPharmacyByID(int id) {
        /* Objeto "callStmt" para invocar a função "getPharmacy" armazenada na BD.
         *
         * FUNCTION getPharmacy(id INTEGER) RETURN pkgPharmacy.ref_cursor
         * PACKAGE pkgPharmacy AS TYPE ref_cursor IS REF CURSOR; END pkgPharmacy;
         */
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


                    return new Pharmacy(idPharmacy, name, emailP, latitude, longitude, emailAdmin);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Pharmacy with id:" + id);
    }


    public List<Pharmacy> getAllPharmacies() {
        /* Objeto "callStmt" para invocar a função "getCourier" armazenada na BD.
         *
         * FUNCTION getCourierList(nif INT) RETURN pkgCourier.ref_cursor
         * PACKAGE pkgCourier AS TYPE ref_cursor IS REF CURSOR; END pkgCourier;
         */
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

                    pharmacyList.add(new Pharmacy(id,emailP, name, latitude, longitude, email));
                }

                return pharmacyList;


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("There are no Pharmacies");
    }
}
