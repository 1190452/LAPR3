package lapr.project.data;

import lapr.project.model.Courier;
import lapr.project.model.Invoice;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InvoiceHandler extends DataHandler {
    public int addInvoice(Invoice invoice) {
        return addInvoice(invoice.getFinalPrice(), invoice.getClientID(), invoice.getIdOrder());
    }

    private int addInvoice(double finalPrice, int clientId, int orderId) {

        int invoiceId=0;
        try {
            openConnection();


            try (CallableStatement callStmt = getConnection().prepareCall("{ call ? = fncAddInvoice(?,?,?) }")) {

                callStmt.registerOutParameter(1, OracleTypes.INTEGER);
                // Especifica o parâmetro de entrada da função "fncAddInvoice".
                callStmt.setDouble(1, finalPrice);
                callStmt.setInt(2, clientId);
                callStmt.setInt(3,orderId);


                // Executa a invocação da procedimento "fncAddCourier".


                callStmt.execute();

                ResultSet rSet = (ResultSet) callStmt.getObject(1);


                if(rSet.next()){
                    invoiceId = rSet.getInt(1);
                }

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoiceId;
    }

    public Invoice getInvoice(int id) {
        /* Objeto "callStmt" para invocar a função "getCourier" armazenada na BD.
         *
         * FUNCTION getCourier(nif INT) RETURN pkgCourier.ref_cursor
         * PACKAGE pkgCourier AS TYPE ref_cursor IS REF CURSOR; END pkgCourier;
         */
        try {
            try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getInvoice(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getCourier".
                callStmt.setDouble(2, id);

                // Executa a invocação da função "getCourier".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                    int invoiceId = rSet.getInt(2);
                    Date date = rSet.getDate(3);
                    double finalPrice = rSet.getDouble(4);
                    int clientId = rSet.getInt(5);
                    int orderId = rSet.getInt(6);


                    return new Invoice(invoiceId, date, finalPrice, clientId, orderId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No invoice with id" + id);
    }
}
