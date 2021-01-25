package lapr.project.data;

import lapr.project.model.Park;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ParkHandler extends DataHandler {

    /**
     * Get the park with the pharmacy id and type park id specified from the table "Park"
     * @param pharmacyId
     * @param parkTypeID
     * @return the park
     */
    public Park getParkByPharmacyId(int pharmacyId, int parkTypeID) {

        try {
            try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getParkByPharmacyId(?,?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getParkByPharmacyId".
                callStmt.setInt(2, pharmacyId);
                callStmt.setInt(3, parkTypeID);

                // Executa a invocação da função "getClient".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                    int id = rSet.getInt(1);
                    int maxCapacity = rSet.getInt(2);
                    int actualCapacity = rSet.getInt(3);
                    int maxChargingPlaces = rSet.getInt(4);
                    int actualChargingPlaces = rSet.getInt(5);
                    int power = rSet.getInt(6);
                    int pharmID = rSet.getInt(7);
                    int parkType = rSet.getInt(8);

                    return new Park(id, maxCapacity, actualCapacity, maxChargingPlaces, actualChargingPlaces, power, pharmID,parkType);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public int addPark(Park park) {
        return addPark(park.getMaxCapacity(), park.getMaxChargingPlaces(), park.getPower(), park.getPharmacyID(), park.getIdParktype());
    }

    /**
     * Add the park specified to the table "Park"
     * @param maxCapacity
     * @param maxChargingPlaces
     * @param power
     * @param pharmacyID
     * @param idParkType
     * @return true when added with sucess false otherwise
     */
    private int addPark(int maxCapacity, int maxChargingPlaces,double power, int pharmacyID, int idParkType) {
        int id = 0;
        try {
            openConnection();
            try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call fncAddPark(?,?,?,?,?) }")) {

                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.INTEGER);


                callStmt.setInt(2, maxCapacity);
                callStmt.setInt(3, maxChargingPlaces);
                callStmt.setDouble(4, power);
                callStmt.setInt(5, pharmacyID);
                callStmt.setInt(6, idParkType);

                callStmt.execute();
                id = callStmt.getInt(1);

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Update the actual changing places from park with the id specified from the table "Park"
     * @param parkId
     */
    public void updateActualChargingPlacesA(int parkId) {
        try {
            openConnection();

            try (CallableStatement callStmt = getConnection().prepareCall("{ call updateActualChargingPlacesA(?) }")) {
                callStmt.setInt(1, parkId);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the actual parking places from park with the park id specified from the table "Park"
     * @param parkId
     */
    public void updateActualCapacityA(int parkId) {
        try {
            openConnection();

            try (CallableStatement callStmt = getConnection().prepareCall("{ call updateActualCapacityA(?) }")) {
                callStmt.setInt(1, parkId);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the changing places from park with the park id specified from the table "Park"
     * @param parkId
     * @return
     */
    public boolean updateChargingPlacesR(int parkId) {
        try {
            openConnection();

            try (CallableStatement callStmt = getConnection().prepareCall("{ call updateActualChargingPlacesR(?) }")) {
                callStmt.setInt(1, parkId);

                callStmt.execute();

                closeAll();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Update the actual parking places from park with the park id specified from the table "Park"
     * @param parkid
     * @return
     */
    public boolean updateActualCapacityR(int parkid) {
        try {
            openConnection();

            try (CallableStatement callStmt = getConnection().prepareCall("{ call updateActualCapacityR(?) }")) {
                callStmt.setInt(1, parkid);

                callStmt.execute();

                closeAll();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get the parks with charging places available and the park type id specified from the table "Park"
     * @param tipo
     * @return the list of parks
     */
    public List<Park> getParkWithCPlaces(int tipo) {

        try {
            try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getParkWithCPlaces(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getParkByPharmacyId".
                callStmt.setInt(2, tipo);


                // Executa a invocação da função "getClient".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                List<Park> lista = new LinkedList<>();

                while (rSet.next()) {
                    int id = rSet.getInt(1);
                    int maxCapacity = rSet.getInt(2);
                    int actualCapacity = rSet.getInt(3);
                    int maxChargingPlaces = rSet.getInt(4);
                    int actualChargingPlaces = rSet.getInt(5);
                    int power = rSet.getInt(6);
                    int pharmID = rSet.getInt(7);
                    int parkType = rSet.getInt(8);

                    lista.add(new Park(id, maxCapacity, actualCapacity, maxChargingPlaces, actualChargingPlaces, power, pharmID,parkType));
                }
                return lista;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No parks availables");

    }

    /**
     * Get the parks with parking places available and with the park type id specified from the table "Park"
     * @param tipo
     * @return the list of parks
     */
    public List<Park> getParkWithNPlaces(int tipo) {

        try {
            try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getParkWithNPlaces(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getParkByPharmacyId".
                callStmt.setInt(2, tipo);


                // Executa a invocação da função "getClient".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                List<Park> lista = new LinkedList<>();

                while (rSet.next()) {
                    int id = rSet.getInt(1);
                    int maxCapacity = rSet.getInt(2);
                    int actualCapacity = rSet.getInt(3);
                    int maxChargingPlaces = rSet.getInt(4);
                    int actualChargingPlaces = rSet.getInt(5);
                    int power = rSet.getInt(6);
                    int pharmID = rSet.getInt(7);
                    int parkType = rSet.getInt(8);

                    lista.add(new Park(id, maxCapacity, actualCapacity, maxChargingPlaces, actualChargingPlaces, power, pharmID,parkType));
                }
                return lista;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No parks availables");

    }

    public List<String> getChargingCourierList(int parkId) {
        try {
            try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getChargingCourierList(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getParkByPharmacyId".
                callStmt.setInt(2, parkId);


                // Executa a invocação da função "getClient".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                List<String> listEmails = new LinkedList<>();

                while (rSet.next()) {
                    listEmails.add(rSet.getString(1));
                }
                return listEmails;

            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        throw new IllegalArgumentException("No emails registed");
    }
}