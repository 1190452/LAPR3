package lapr.project.data;

import lapr.project.model.CreditCard;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class CreditCardDataHandler extends DataHandler{
    public void addCreditCard(CreditCard credcard) {
        addCreditCard(credcard.getCardNumber(), credcard.getMonthExpiration(),credcard.getYearExpiration(),credcard.getCcv());
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
    private void addCreditCard(long cardNumber, int monthExpiration, int yearExpiration, int ccv) {
        try {
            openConnection();
            /*
             *  Objeto "callStmt" para invocar o procedimento "addCreditCard" armazenado
             *  na BD.
             *
             *  PROCEDURE addSailor(sid NUMBER, sname VARCHAR, rating NUMBER, age NUMBER)
             *  PACKAGE pkgCreditCards AS TYPE ref_cursor IS REF CURSOR; END pkgCreditCards;
             */
            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcAddCreditCard(?,?,?,?) }")) {
                callStmt.setLong(1, cardNumber);
                callStmt.setInt(2, monthExpiration);
                callStmt.setInt(3, yearExpiration);
                callStmt.setInt(4, ccv);

                callStmt.execute();

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public CreditCard getCreditCard(long cardNumber) {
        /* Objeto "callStmt" para invocar a função "getCreditCard" armazenada na BD.
         *
         * FUNCTION getCreditCard(cardNumber int) RETURN pkgCreditCards.ref_cursor
         * PACKAGE pkgCreditCards AS TYPE ref_cursor IS REF CURSOR; END pkgCreditCards;
         */
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getCreditCard(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getCreditCard".
                callStmt.setLong(2, cardNumber);

                // Executa a invocação da função "getcreditCard".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                    int credNumber = rSet.getInt(1);

                    return new CreditCard(credNumber);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Credit Card with number:" + cardNumber);
    }


}
