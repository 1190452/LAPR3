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
       return addCourier(courier.getEmail(), courier.getName(), courier.getWeight(), courier.getNIF(), courier.getNSS(), courier.getMaxWeightCapacity(), courier.getPharmacyID());
    }

    private boolean addCourier(String email, String name, double weight, int nif, BigDecimal nss, double maxWeightCapacity, int pharmacyID) {
        boolean added = false;
        try {
            openConnection();

            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcAddCourier(?,?,?,?,?,?,?) }")) {
                // Especifica o parâmetro de entrada da função "fncAddCourier".
                callStmt.setString(1, name);
                callStmt.setString(2, email);
                callStmt.setInt(3, nif);
                callStmt.setBigDecimal(4, nss);
                callStmt.setDouble(5, maxWeightCapacity);
                callStmt.setDouble(6, weight);
                callStmt.setInt(7, pharmacyID);

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

    public List<Courier> getCourierList() {

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
                    BigDecimal nss = rSet.getBigDecimal(5);
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

    public boolean removeCourier(int id) {
        boolean isRemoved = false;
        try {
            openConnection();

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
                    int pharmID = rSet.getInt(8);


                    return new Courier(id, name, emailC, nif, nss, maxWeight, weight, pharmID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Courier with email:" + email);
    }

    public List<Courier> getAvailableCouriers() {

        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getAvailableCouriers() }")) {
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
                    BigDecimal nss = rSet.getBigDecimal(5);
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
}
