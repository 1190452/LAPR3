package lapr.project.data;


import lapr.project.model.Courier;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourierDataHandler extends DataHandler {

    public void addCourier(Courier courier) {
        addCourier(courier.getEmail(), courier.getName(), courier.getWeight(), courier.getNIF(), courier.getNSS(), courier.getMaxWeightCapacity(), courier.getPharmacyID());
    }

    private void addCourier(String email, String name, double weight, int nif, String nss, double maxWeightCapacity, int pharmacyID) {
        try {
            openConnection();
            /*
             *  Objeto "callStmt" para invocar o procedimento "addCourier" armazenado
             *  na BD.
             *
             *  PROCEDURE addCourier(email VARCHAR, name VARCHAR, weight DOUBLE, nif INT, nss VARCHAR, maxWeightCapacity DOUBLE,  pharmacyID INT)
             *  PACKAGE pkgCourier AS TYPE ref_cursor IS REF CURSOR; END pkgCourier;
             */
            try(CallableStatement callStmt = getConnection().prepareCall("{ call addCourier(?,?,?,?) }")) {
                callStmt.setString(1, email);
                callStmt.setString(2, name);
                callStmt.setDouble(4, weight);
                callStmt.setInt(5, nif);
                callStmt.setString(6, nss);
                callStmt.setDouble(7, maxWeightCapacity);
                callStmt.setInt(8, pharmacyID);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Courier getCourier(int nif) {
        /* Objeto "callStmt" para invocar a função "getCourier" armazenada na BD.
         *
         * FUNCTION getCourier(nif INT) RETURN pkgCourier.ref_cursor
         * PACKAGE pkgCourier AS TYPE ref_cursor IS REF CURSOR; END pkgCourier;
         */
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getCourier(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getCourier".
                callStmt.setInt(2, nif);

                // Executa a invocação da função "getCourier".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                    String email = rSet.getString(2);
                    String name = rSet.getString(3);
                    int idPharmacy = rSet.getInt(8);


                    return new Courier(email, name, idPharmacy);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Courier with nif:" + nif);
    }

    public List<Courier> getCourierList() {
        /* Objeto "callStmt" para invocar a função "getCourier" armazenada na BD.
         *
         * FUNCTION getCourierList(nif INT) RETURN pkgCourier.ref_cursor
         * PACKAGE pkgCourier AS TYPE ref_cursor IS REF CURSOR; END pkgCourier;
         */
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getCourierList() }")) {
                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);


                // Executa a invocação da função "getCourierList".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                List<Courier> courierList = new ArrayList<>();


                while (rSet.next()) {
                    int id = rSet.getInt(1);
                    String email = rSet.getString(2);
                    String name = rSet.getString(3);
                    double weight = rSet.getDouble(4);
                    int NIF = rSet.getInt(5);
                    String NSS = rSet.getString(6);
                    double maxCapacity = rSet.getDouble(7);
                    int idPharmacy = rSet.getInt(8);


                    courierList.add(new Courier(id, email, name, NIF, NSS, maxCapacity, weight, idPharmacy));
                }

                return courierList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Couriers found");

    }
}
