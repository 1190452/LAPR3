package lapr.project.data;

import lapr.project.model.Delivery;
import lapr.project.model.EletricScooter;
import lapr.project.model.Park;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ParkHandler extends DataHandler {
    private final DataHandler dataHandler;

    public ParkHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    Logger logger = Logger.getLogger(ScooterHandler.class.getName());


    public boolean checkParkByPharmacyId(String pharmacyId) {
        try {
            openConnection();
            /*
             *  Objeto "callStmt" para invocar a função "getParkByPharmacyId"
             *  armazenado na BD.
             *
             *  function getParkByPharmacyId()
             */
            try(CallableStatement callStmt = getConnection().prepareCall("{ call checkParkByPharmacyId(?) }")) {
                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.BIT);
                // Especifica o parâmetro de entrada da função "getParkByPharmacyId".
                callStmt.setInt(2, Integer.parseInt(pharmacyId));
                boolean r =  callStmt.execute();

                closeAll();
                return r;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Park getParkByPharmacyId(String pharmacyId) {
        /* Objeto "callStmt" para invocar a função "getScooter" armazenada na BD.
         *
         * FUNCTION getScooter(id INTEGER) RETURN pkgScooter.ref_cursor
         * PACKAGE pkgScooter AS TYPE ref_cursor IS REF CURSOR; END pkgClient;
         */
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getParkByPharmacyId(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getParkByPharmacyId".
                callStmt.setInt(2, Integer.parseInt(pharmacyId));

                // Executa a invocação da função "getClient".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                  String id = rSet.getString(1);
                  int maxCapacity = rSet.getInt(2);
                  int actualCapacity = rSet.getInt(3);
                  int maxChargingPlaces = rSet.getInt(4);
                  int actualChargingPlaces = rSet.getInt(5);


                  return new Park( id,  maxCapacity,  actualCapacity,  maxChargingPlaces,  actualChargingPlaces);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No pharmacy with id:" + pharmacyId);
    }

    public void updateActualCapacity(String id) {
        try {
            openConnection();
            /*
             *  Objeto "callStmt" para invocar a função "getParkByPharmacyId"
             *  armazenado na BD.
             *
             *  function getParkByPharmacyId()
             */
            try(CallableStatement callStmt = getConnection().prepareCall("{ call updateActualCapacity(?) }") ){
                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateChargingPlaces(String id) {
    }
}
