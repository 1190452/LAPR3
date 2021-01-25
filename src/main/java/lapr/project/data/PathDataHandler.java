package lapr.project.data;

import lapr.project.model.Path;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PathDataHandler extends DataHandler{

    public boolean addPath(Path path) {
        return addPath(path.getLongitude_a1(), path.getLatitude_a1(), path.getAltitude_a1(), path.getLongitude_a2(), path.getLatitude_a2(), path.getAltitude_a2(), path.getRoadRollingResistance(), path.getWindspeed(), path.getWindDirection());
    }

    private boolean addPath(double longitudeA1, double latitudeA1, double altitudeA1, double longitudeA2, double latitudeA2, double altitudeA2, double roadRolling, double windspeed, double windDirection) {
        boolean isAdded = false;
        try {
            openConnection();
            try (CallableStatement callStmt = getConnection().prepareCall("{  call prcAddPath(?,?,?,?,?,?,?,?,?) }")) {


                callStmt.setDouble(1, latitudeA1);
                callStmt.setDouble(2, longitudeA1);
                callStmt.setDouble(3, altitudeA1);
                callStmt.setDouble(4, latitudeA2);
                callStmt.setDouble(5, longitudeA2);
                callStmt.setDouble(6, altitudeA2);
                callStmt.setDouble(7, roadRolling);
                callStmt.setDouble(8, windspeed);
                callStmt.setDouble(9, windDirection);

                callStmt.execute();

                isAdded = true;

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAdded;
    }

    public List<Path> getAllPaths() {

        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getAllPaths() }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);

                // Executa a invocação da função "getCourier".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                List<Path> pathsList = new ArrayList<>();


                while (rSet.next()) {
                    double latitudeA1 = rSet.getDouble(1);
                    double longitudeA1 = rSet.getDouble(2);
                    double altitudeA1 = rSet.getDouble(3);
                    double latitudeA2 = rSet.getDouble(4);
                    double longitudeA2 = rSet.getDouble(5);
                    double altitudeA2 = rSet.getDouble(6);
                    double roadRolling = rSet.getDouble(7);
                    double windSpeed = rSet.getDouble(8);
                    double windDirection = rSet.getDouble(9);

                    pathsList.add(new Path(latitudeA1,longitudeA1,altitudeA1, latitudeA2, longitudeA2, altitudeA2, roadRolling, windSpeed, windDirection));
                }
                return pathsList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("There are no Paths");
    }
}
