package lapr.project.data;

import lapr.project.model.Pharmacy;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PharmacyDataHandler extends DataHandler{


    public void addPharmacy(Pharmacy pharmacy) {
        addPharmacy(pharmacy.getName(), pharmacy.getLatitude(), pharmacy.getLongitude(), pharmacy.getEmailAdministrator());
    }

    public void addPharmacy(String name, double latitude, double longitude, String emailAdministrator) {
        try {
            openConnection();
            /*
             *  Objeto "callStmt" para invocar o procedimento "addPharmacy" armazenado
             *  na BD.
             *
             *  PROCEDURE addPharmacy(name VARCHAR, latitude NUMBER, longitude NUMBER, emailAdministrator)
             *  PACKAGE pkgCourier AS TYPE ref_cursor IS REF CURSOR; END pkgCourier;
             */
            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcAddPharmacy(?,?,?,?) }")) {
                callStmt.setString(1, name);
                callStmt.setDouble(2, latitude);
                callStmt.setDouble(3, longitude);
                callStmt.setString(4, emailAdministrator);
                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Pharmacy getPharmacy(String name) {
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
                    String emailAdmin = rSet.getString(5);


                    return new Pharmacy(idPharmacy, nameP, latitude, longitude, emailAdmin);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Pharmacy with name:" + name);
    }

    public Pharmacy getPharmacy(int id) {
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
                    String emailAdmin = rSet.getString(5);


                    return new Pharmacy(idPharmacy, name, latitude, longitude, emailAdmin);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Pharmacy with id:" + id);
    }


    public void removePharmacy(int id) {
        try {
            openConnection();
            /*
             *  Objeto "callStmt" para invocar o procedimento "removePharmacy"
             *  armazenado na BD.
             *
             *  PROCEDURE removePharmacy(id INTEGER)
             *  PACKAGE pkgPharmacy AS TYPE ref_cursor IS REF CURSOR; END pkgPharmacy;
             */
            try(CallableStatement callStmt = getConnection().prepareCall("{ call removePharmacy(?) }")) {
                callStmt.setInt(1, id);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }



}
