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
        if(delivery.getVehicleID() == 0){
            addDeliveryByScooter(delivery.getNecessaryEnergy(), delivery.getDistance(), delivery.getWeight(), delivery.getCourierID());
        } else {
            addDeliveryByDrone(delivery.getNecessaryEnergy(), delivery.getDistance(), delivery.getWeight(), delivery.getVehicleID());
        }
    }

    private void addDeliveryByScooter(double necessaryEnergy, double distance, double weight, int courierID) {
        try {
            openConnection();

            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcAddDeliveryByScooter(?,?,?,?) }")) {
                callStmt.setDouble(1, necessaryEnergy);
                callStmt.setDouble(2, distance);
                callStmt.setDouble(3, weight);
                callStmt.setInt(4, courierID);


                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addDeliveryByDrone(double necessaryEnergy, double distance, double weight, int vehicleID) {
        try {
            openConnection();

            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcAddDeliveryByDrone(?,?,?,?) }")) {
                callStmt.setDouble(1, necessaryEnergy);
                callStmt.setDouble(2, distance);
                callStmt.setDouble(3, weight);
                callStmt.setInt(4, vehicleID);


                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

