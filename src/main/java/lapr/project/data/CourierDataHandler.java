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

    private void addCourier(String email, String name, double weight, int nif, double nss, double maxWeightCapacity, int pharmacyID) {
        try {
            openConnection();
            /*
             *  Objeto "callStmt" para invocar o procedimento "addCourier" armazenado
             *  na BD.
             *
             *  PROCEDURE addCourier(email VARCHAR, name VARCHAR, weight DOUBLE, nif INT, nss VARCHAR, maxWeightCapacity DOUBLE,  pharmacyID INT)
             *  PACKAGE pkgCourier AS TYPE ref_cursor IS REF CURSOR; END pkgCourier;
             */

            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcAddCourier(?,?,?,?,?,?,?) }")) {
                // Especifica o parâmetro de entrada da função "fncAddCourier".
                callStmt.setString(1, name);
                callStmt.setString(2, email);
                callStmt.setInt(3, nif);
                callStmt.setDouble(4, nss);
                callStmt.setDouble(5, maxWeightCapacity);
                callStmt.setDouble(6, weight);
                callStmt.setInt(7, pharmacyID);

                // Executa a invocação da procedimento "fncAddCourier".


                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Courier getCourier(double nif) {
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
                callStmt.setDouble(2, nif);

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
                    String name = rSet.getString(2);
                    String email = rSet.getString(3);
                    int nif = rSet.getInt(4);
                    double nss = rSet.getDouble(5);
                    double maxWeight = rSet.getDouble(6);
                    double weight = rSet.getDouble(7);
                    int pharmID = rSet.getInt(8);


                    courierList.add(new Courier(id, email, name, nif, nss, maxWeight, weight, pharmID));
                }

                return courierList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Couriers found");

    }

    public void removeCourier(int id) {
        try {
            openConnection();
            /*
             *  Objeto "callStmt" para invocar o procedimento "removeCourier"
             *  armazenado na BD.
             *
             *  PROCEDURE removeCourier(id INTEGER)
             */
            try (CallableStatement callStmt = getConnection().prepareCall("{ call prcRemoveCourier(?) }")) {
                callStmt.setInt(1, id);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Courier getCourierByEmail(String email) {
        /* Objeto "callStmt" para invocar a função "getCourier" armazenada na BD.
         *
         * FUNCTION getCourier(nif INT) RETURN pkgCourier.ref_cursor
         * PACKAGE pkgCourier AS TYPE ref_cursor IS REF CURSOR; END pkgCourier;
         */
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getCourierByEmail(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getCourier".
                callStmt.setString(2, email);

                // Executa a invocação da função "getCourier".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                    int id = rSet.getInt(1);
                    String name = rSet.getString(2);
                    String emailC = rSet.getString(3);
                    int nif = rSet.getInt(4);
                    double nss = rSet.getDouble(5);
                    double maxWeight = rSet.getDouble(6);
                    double weight = rSet.getDouble(7);
                    int pharmID = rSet.getInt(8);


                    return new Courier(id, name, emailC, nif, nss, maxWeight, weight, pharmID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Courier with email:" + email);
    }
}
