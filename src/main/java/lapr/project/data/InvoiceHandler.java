package lapr.project.data;

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

    /**
     * Add the invoice specified to the table "Invoice"
     * @param finalPrice
     * @param clientId
     * @param orderId
     * @return true when added with sucess false otherwise
     */
    private int addInvoice(double finalPrice, int clientId, int orderId) {

        int invoiceId=0;
        try {
            openConnection();
            try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call fncAddInvoice(?,?,?) }")) {

                callStmt.registerOutParameter(1, OracleTypes.INTEGER);
                // Especifica o parâmetro de entrada da função "fncAddInvoice".
                callStmt.setDouble(2, finalPrice);
                callStmt.setInt(3, clientId);
                callStmt.setInt(4,orderId);

                // Executa a invocação da procedimento "fncAddCourier".
                callStmt.execute();

                invoiceId = callStmt.getInt(1);

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoiceId;
    }

    /**
     * Get the invoice with the id specified from the table "Invoice"
     * @param id
     * @return the invoice
     */
    public Invoice getInvoice(int id) {

        try {
            try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getInvoice(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getCourier".
                callStmt.setInt(2, id);

                // Executa a invocação da função "getCourier".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                    int invoiceId = rSet.getInt(1);
                    Date date = rSet.getDate(2);
                    double finalPrice = rSet.getDouble(3);
                    int clientId = rSet.getInt(4);
                    int orderId = rSet.getInt(5);


                    return new Invoice(invoiceId, date, finalPrice, clientId, orderId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No invoice with id" + id);
    }
}
