package lapr.project.data;

import lapr.project.model.CreditCard;
import oracle.jdbc.OracleTypes;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class CreditCardDataHandler extends DataHandler{
    public boolean addCreditCard(CreditCard credcard) {
       return addCreditCard(credcard.getCardNumber(), credcard.getMonthExpiration(),credcard.getYearExpiration(),credcard.getCcv());
    }

    /**
     * Exemplo de invocação de uma "stored procedure".
     * <p>
     * Adiciona o marinheiro especificado à tabela "CreditCards".
     *
     * @param cardNumber
     * @param monthExpiration
     * @param yearExpiration
     * @param ccv
     */
    private boolean addCreditCard(BigDecimal cardNumber, int monthExpiration, int yearExpiration, int ccv) {
        boolean added = false;
        try {
            openConnection();

            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcAddCreditCard(?,?,?,?) }")) {
                callStmt.setBigDecimal(1, cardNumber);
                callStmt.setInt(2, monthExpiration);
                callStmt.setInt(3, yearExpiration);
                callStmt.setInt(4, ccv);

                callStmt.execute();

                added = true;

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return added;
    }



    public CreditCard getCreditCard(BigDecimal cardNumber) {

        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getCreditCard(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getCreditCard".
                callStmt.setBigDecimal(2, cardNumber);

                // Executa a invocação da função "getcreditCard".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                    BigDecimal credNumber = rSet.getBigDecimal(1);

                    return new CreditCard(credNumber);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Credit Card with number:" + cardNumber);
    }


}
