package lapr.project.data;

import lapr.project.model.Delivery;
import lapr.project.model.Park;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DeliveryHandler extends DataHandler {
    private final DataHandler dataHandler;

    public DeliveryHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    Logger logger = Logger.getLogger(ScooterHandler.class.getName());


    public Delivery getDeliveryByCourierId(String courierId) {
        /* Objeto "callStmt" para invocar a função "getDeliveryByCourierId" armazenada na BD.
         *
         * FUNCTION getDeliveryByCourierId(String courierId) RETURN courierId
         */
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getDeliveryByCourierId(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getParkByPharmacyId".
                callStmt.setInt(2, Integer.parseInt(courierId));

                // Executa a invocação da função "getDeliveryByCourierId".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                    String id = rSet.getString(1);
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

}

