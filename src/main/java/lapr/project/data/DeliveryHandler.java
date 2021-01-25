package lapr.project.data;

import lapr.project.model.Delivery;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryHandler extends DataHandler {

    public int addDelivery(Delivery delivery) {
        if(delivery.getLicensePlate().isEmpty()){
            return addDeliveryByScooter(delivery.getNecessaryEnergy(), delivery.getDistance(), delivery.getWeight(), delivery.getCourierID());
        } else {
            return addDeliveryByDrone(delivery.getNecessaryEnergy(), delivery.getDistance(), delivery.getWeight(), delivery.getLicensePlate());
        }
    }

    /**
     * Add the delivery specified to the table "Delivery"
     * @param necessaryEnergy
     * @param distance
     * @param weight
     * @param courierID
     * @return the delivery id
     */
    private int addDeliveryByScooter(double necessaryEnergy, double distance, double weight, int courierID) {
        int id=0;
        try {
            openConnection();

            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call fncAddDeliveryByScooter(?,?,?,?) }")) {
                callStmt.registerOutParameter(1, OracleTypes.INTEGER);
                callStmt.setDouble(2, necessaryEnergy);
                callStmt.setDouble(3, distance);
                callStmt.setDouble(4, weight);
                callStmt.setInt(5, courierID);


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
     * Add the delivery specified to the table "Delivery"
     * @param necessaryEnergy
     * @param distance
     * @param weight
     * @param licensePlate
     * @return the delivery id
     */
    private int addDeliveryByDrone(double necessaryEnergy, double distance, double weight, String licensePlate) {
        int id=0;
        try {
            openConnection();

            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call  fncAddDeliveryByDrone(?,?,?,?) }")) {
                callStmt.registerOutParameter(1, OracleTypes.INTEGER);

                callStmt.setDouble(2, necessaryEnergy);
                callStmt.setDouble(3, distance);
                callStmt.setDouble(4, weight);
                callStmt.setString(5, licensePlate);


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
     * Get the delivery with the courier id specified from the table "Delivery"
     * @param courierId
     * @return the delivery
     */
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

    /**
     * Get the all deliveries with the courier id specified from the table "Delivery"
     * @param idCourier
     * @return the list of deliveries
     */
    public List<Delivery> getDeliverysByCourierId(int idCourier) {

        List<Delivery> undoneDeliveries = new ArrayList<>();
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

                    undoneDeliveries.add(new Delivery( id,  necessaryEnergy,  distance,  weight ));
                }
                return undoneDeliveries;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Deliverys associated with this courier");

    }

    /**
     * Update the delivery status with the id specified from the table "Delivery"
     * @param delId
     * @return true when updated with sucess false otherwise
     */
    public boolean updateStatusDelivery(int delId) {
        try {
            openConnection();

            try(CallableStatement callStmt = getConnection().prepareCall("{ call updateStatusDelivery(?) }")) {
                callStmt.setInt(1, delId);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Get the delivery with the drone id specified from the table "Delivery"
     * @param idDroneDelivery
     * @return the delivery
     */
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

