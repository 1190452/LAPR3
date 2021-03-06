package lapr.project.data;


import lapr.project.model.Courier;
import oracle.jdbc.OracleTypes;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourierDataHandler extends DataHandler {

    public boolean addCourier(Courier courier) {
       return addCourier(courier.getEmail(), courier.getName(), courier.getWeight(), courier.getNif(), courier.getNss(), courier.getPharmacyID());
    }

    /**
     * Add the courier specified to the table "Courier"
     * @param email
     * @param name
     * @param weight
     * @param nif
     * @param nss
     * @param pharmacyID
     * @return true when added with sucess false otherwise
     */
    private boolean addCourier(String email, String name, double weight, int nif, BigDecimal nss, int pharmacyID) {
        boolean added = false;
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcAddCourier(?,?,?,?,?,?) }")) {
                // Especifica o parâmetro de entrada da função "fncAddCourier".
                callStmt.setString(1, name);
                callStmt.setString(2, email);
                callStmt.setInt(3, nif);
                callStmt.setBigDecimal(4, nss);
                callStmt.setDouble(5, weight);
                callStmt.setInt(6, pharmacyID);

                // Executa a invocação da procedimento "fncAddCourier".


                callStmt.execute();

                added=true;

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return added;
    }

    /**
     * Get the courier with the nif specified from the table "Courier"
     * @param nif
     * @return the courier
     */
    public Courier getCourier(double nif) {

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

    /**
     * Get all couriers from the table "Courier"
     * @return the list of couriers
     */
    public List<Courier> getCourierList() {

        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getCourierList() }")) {
                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);


                // Executa a invocação da função "getCourierList".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                List<Courier> listCourier = new ArrayList<>();


                while (rSet.next()) {
                    int idCourier = rSet.getInt(1);
                    String nameCourier = rSet.getString(2);
                    String emailCourier = rSet.getString(3);
                    int nifCourier = rSet.getInt(4);
                    BigDecimal nssCourier = rSet.getBigDecimal(5);
                    double maxweightCourier = rSet.getDouble(6);
                    double weight = rSet.getDouble(7);
                    int pharmID = rSet.getInt(8);


                    listCourier.add(new Courier(idCourier, emailCourier, nameCourier, nifCourier, nssCourier, maxweightCourier, weight, pharmID));
                }

                return listCourier;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Couriers found");

    }

    /**
     * Update the courier status with the id specified in the table "Courier"
     * @param id
     * @return true when updated with sucess false otherwise
     */
    public boolean updateSatusCourier(int id) {
        boolean isUpdated = false;
        try {
            try (CallableStatement callStmt = getConnection().prepareCall("{ call updateStatusCourier(?) }")) {
                callStmt.setInt(1, id);

                callStmt.execute();

                isUpdated = true;
                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    /**
     * Remove the courier with the id specified from the table "Courier"
     * @param id
     * @return true when removed with sucess false otherwise
     */
    public boolean removeCourier(int id) {
        boolean isRemoved = false;
        try {
            try (CallableStatement callStmt = getConnection().prepareCall("{ call prcRemoveCourier(?) }")) {
                callStmt.setInt(1, id);

                callStmt.execute();

                isRemoved = true;
                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isRemoved;
    }

    /**
     * Get the courier with the email specified from the table "Courier"
     * @param email
     * @return the courier
     */
    public Courier getCourierByEmail(String email) {

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
                    BigDecimal nss = rSet.getBigDecimal(5);
                    double maxWeight = rSet.getDouble(6);
                    double weight = rSet.getDouble(7);
                    int pharmID = rSet.getInt(9);


                    return new Courier(id, name, emailC, nif, nss, maxWeight, weight, pharmID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Courier with email:" + email);
    }

    /**
     * Get all couriers available from the pharmacy with the pharmacy id specified from the table "Courier"
     * @param idPhar
     * @return the list of couriers
     */
    public List<Courier> getAvailableCouriers(int idPhar) {

        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getCourierAvailable(?) }")) {
                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setInt(2, idPhar);

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
                    BigDecimal nss = rSet.getBigDecimal(5);
                    double maxWeight = rSet.getDouble(6);
                    double weightC = rSet.getDouble(7);
                    int pharmID = rSet.getInt(8);


                    courierList.add(new Courier(id, email, name, nif, nss, maxWeight, weightC, pharmID));
                }

                return courierList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Couriers found");
    }
}
