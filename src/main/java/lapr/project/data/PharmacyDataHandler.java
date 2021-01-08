package lapr.project.data;

import lapr.project.model.Pharmacy;

import java.sql.CallableStatement;
import java.sql.SQLException;

public class PharmacyDataHandler extends DataHandler{

    private final DataHandler dataHandler;


    public PharmacyDataHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

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
            try(CallableStatement callStmt = getConnection().prepareCall("{ call addPharmacy(?,?,?,?) }")) {
                callStmt.setString(2, name);
                callStmt.setDouble(3, latitude);
                callStmt.setDouble(4, longitude);
                callStmt.setString(5, emailAdministrator);
                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }




}
