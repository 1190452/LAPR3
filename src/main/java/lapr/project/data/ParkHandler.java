package lapr.project.data;

import lapr.project.model.Park;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ParkHandler extends DataHandler {


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
        throw new IllegalArgumentException("No park with pahrmacy id:" + pharmacyId);
    }





    public boolean addPark(Park park) {
        return addPark(park.getMaxCapacity(), park.getMaxChargingPlaces(), park.getPower(), park.getPharmacyID(), park.getIdParktype());
    }

    private boolean addPark(int maxCapacity, int maxChargingPlaces,double power, int pharmacyID, int idParkType) {
        boolean isAdded = false;
        try {

            openConnection();

            try (CallableStatement callStmt = getConnection().prepareCall("{ call prcAddPark(?,?,?,?,?) }")) {
                callStmt.setInt(1, maxCapacity);
                callStmt.setInt(2, maxChargingPlaces);
                callStmt.setDouble(3, power);
                callStmt.setInt(4, pharmacyID);
                callStmt.setInt(5, idParkType);

                callStmt.execute();

                isAdded = true;
                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAdded;
    }


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

    public void updateChargingPlacesR(int parkId) {
        try {
            openConnection();

            try (CallableStatement callStmt = getConnection().prepareCall("{ call updateChargingPlacesR(?) }")) {
                callStmt.setInt(1, parkId);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateActualCapacityR(int parkid) {
        try {
            openConnection();

            try (CallableStatement callStmt = getConnection().prepareCall("{ call updateActualCapacityR(?) }")) {
                callStmt.setInt(1, parkid);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No parks availables");

    }

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

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No parks availables");

    }
}