package lapr.project.data;

import lapr.project.model.Park;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParkHandler extends DataHandler {
    

<<<<<<< HEAD
    public Park getParkByPharmacyId(int pharmacyId) {
=======
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
                callStmt.setInt(1, Integer.parseInt(pharmacyId));
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
>>>>>>> c8dc38359360d9b09d26cc2b39a67bbaa0014079
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
                callStmt.setInt(2, pharmacyId);

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

                  return new Park(id,  maxCapacity,  actualCapacity,  maxChargingPlaces,  actualChargingPlaces, power, pharmID);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No park with pahrmacy id:" + pharmacyId);
    }



    //MELHORAR
    public void updateActualCapacity(int id) {
        try {
            openConnection();
            /*
             *  Objeto "callStmt" para invocar a função "getParkByPharmacyId"
             *  armazenado na BD.
             *
             *  function getParkByPharmacyId()
             */
            try(CallableStatement callStmt = getConnection().prepareCall("{ call updateActualCapacity(?) }") ){
                callStmt.setInt(1, id);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //MELHORAR
    public void updateChargingPlaces(int id) {
    }

    public void addPark(Park park) {
        addPark(park.getMaxCapacity(),  park.getMaxChargingPlaces(), park.getActualChargingPlaces(), park.getPower(), park.getPharmacyID());
    }

    private void addPark(int maxCapacity, int maxChargingPlaces, int actualChargingPlaces, int power, int pharmacyID){
        try {
            openConnection();
            /*
             *  Objeto "callStmt" para invocar o procedimento "addClient" armazenado
             *  na BD.
             *
             *  PROCEDURE addClient(name VARCHAR, email VARCHAR, nif INT, latitude DOUBLE, longitude DOUBLE, creditCardNumber INT)
             *  PACKAGE pkgClient AS TYPE ref_cursor IS REF CURSOR; END pkgClient;
             */
            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcaddPark(?,?,?,?,?,?) }")) {
                callStmt.setInt(1, maxCapacity);
                callStmt.setInt(2, maxChargingPlaces);
                callStmt.setInt(3, actualChargingPlaces);
                callStmt.setInt(4, power);
                callStmt.setInt(5, pharmacyID);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
