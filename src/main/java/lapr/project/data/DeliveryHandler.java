package lapr.project.data;

import lapr.project.model.Delivery;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class DeliveryHandler extends DataHandler {

    Logger logger = Logger.getLogger(VehicleHandler.class.getName());

    public void addDelivery(Delivery delivery) {
        addDelivery(delivery.getNecessaryEnergy(), delivery.getDistance(), delivery.getWeight(), delivery.getCourierID(), delivery.getVehicleID());
    }


    private void addDelivery(double necessaryEnergy, double distance, double weight, int courID, int vehicleID) {
        try {
            openConnection();
            /*
             *  Objeto "callStmt" para invocar o procedimento "addCreditCard" armazenado
             *  na BD.
             *
             *  PROCEDURE addSailor(sid NUMBER, sname VARCHAR, rating NUMBER, age NUMBER)
             *  PACKAGE pkgCreditCards AS TYPE ref_cursor IS REF CURSOR; END pkgCreditCards;
             */
            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcAddDelivery(?,?,?,?,?) }")) {
                callStmt.setDouble(1, necessaryEnergy);
                callStmt.setDouble(2, distance);
                callStmt.setDouble(3, weight);
                callStmt.setInt(4, courID);
                callStmt.setInt(5, vehicleID);


                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Delivery getDeliveryByCourierId(int courierId) {
        /* Objeto "callStmt" para invocar a função "getDeliveryByCourierId" armazenada na BD.
         *
         * FUNCTION getDeliveryByCourierId(String courierId) RETURN courierId
         */
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getDeliveryByCourierId(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getParkByPharmacyId".
                callStmt.setInt(2, courierId);

                // Executa a invocação da função "getDeliveryByCourierId".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                    int id = rSet.getInt(1);
                    double necessaryEnergy = rSet.getInt(2);
                    double distance = rSet.getInt(3);
                    double weight = rSet.getInt(4);

                    return new Delivery( id,  necessaryEnergy,  distance,  weight );
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Courier with id:" + courierId);
    }

    public Delivery getDelivery(int id) {
        /* Objeto "callStmt" para invocar a função "getDeliveryByCourierId" armazenada na BD.
         *
         * FUNCTION getDeliveryByCourierId(String courierId) RETURN courierId
         */
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getDelivery(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getParkByPharmacyId".
                callStmt.setInt(2, id);

                // Executa a invocação da função "getDeliveryByCourierId".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                    int idD = rSet.getInt(1);
                    double necessaryEnergy = rSet.getInt(2);
                    double distance = rSet.getInt(3);
                    double weight = rSet.getInt(4);

                    return new Delivery( idD,  necessaryEnergy,  distance,  weight );
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Delivery with id:" + id);
    }

    public List<Delivery> getDeliverysByCourierId(int idCourier) {
        /* Objeto "callStmt" para invocar a função "getDeliveryByCourierId" armazenada na BD.
         *
         * FUNCTION getDeliveryByCourierId(String courierId) RETURN courierId
         */

        List<Delivery> undoneDeliverys = null;
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getCourierDeliveries(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getParkByPharmacyId".
                callStmt.setInt(2, idCourier);

                // Executa a invocação da função "getDeliveryByCourierId".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                while (rSet.next()) {
                    int id = rSet.getInt(1);
                    double necessaryEnergy = rSet.getInt(2);
                    double distance = rSet.getInt(3);
                    double weight = rSet.getInt(4);

                    undoneDeliverys.add(new Delivery( id,  necessaryEnergy,  distance,  weight ));
                }
                return undoneDeliverys;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Deliverys associated with this courier");

    }
}

