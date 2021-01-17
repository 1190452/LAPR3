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

    public int addDelivery(Delivery delivery) {
        if(delivery.getVehicleID() == 0){
            return addDeliveryByScooter(delivery.getNecessaryEnergy(), delivery.getDistance(), delivery.getWeight(), delivery.getCourierID());
        } else {
            return addDeliveryByDrone(delivery.getNecessaryEnergy(), delivery.getDistance(), delivery.getWeight(), delivery.getVehicleID());
        }
    }

    private int addDeliveryByScooter(double necessaryEnergy, double distance, double weight, int courierID) {
        int id=0;
        try {
            openConnection();

            try(CallableStatement callStmt = getConnection().prepareCall("{ call ? = addDeliveryByScooter(?,?,?,?) }")) {
                callStmt.registerOutParameter(1, OracleTypes.INTEGER);
                callStmt.setDouble(2, necessaryEnergy);
                callStmt.setDouble(3, distance);
                callStmt.setDouble(4, weight);
                callStmt.setInt(5, courierID);


                callStmt.execute();

                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                    id = rSet.getInt(1);

                }

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private int addDeliveryByDrone(double necessaryEnergy, double distance, double weight, int vehicleID) {
        int id=0;
        try {
            openConnection();

            try(CallableStatement callStmt = getConnection().prepareCall("{ call ? = addDeliveryByDrone(?,?,?,?) }")) {
                callStmt.registerOutParameter(1, OracleTypes.INTEGER);
                callStmt.setDouble(2, necessaryEnergy);
                callStmt.setDouble(3, distance);
                callStmt.setDouble(4, weight);
                callStmt.setInt(5, vehicleID);


                callStmt.execute();
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                    id = rSet.getInt(1);
                }

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public Delivery getDeliveryByCourierId(int courierId) {

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

    public List<Delivery> getDeliverysByCourierId(int idCourier) {

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

    public void updateStatusDelivery(int delId) {
        try {
            openConnection();

            try(CallableStatement callStmt = getConnection().prepareCall("{ call updateStatusDelivery(?) }")) {
                callStmt.setInt(1, delId);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Delivery getDeliveryByDroneId(int idDroneDelivery) {

        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getDeliveryByDroneId(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getParkByPharmacyId".
                callStmt.setInt(2, idDroneDelivery);

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
        throw new IllegalArgumentException("No Drone with this id");
    }
}

