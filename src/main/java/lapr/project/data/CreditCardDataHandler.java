package lapr.project.data;

import lapr.project.model.CreditCard;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;

public class CreditCardDataHandler extends DataHandler{
    public boolean addCreditCard(CreditCard credcard) {
       return addCreditCard(credcard.getCardNumber(), credcard.getMonthExpiration(),credcard.getYearExpiration(),credcard.getCcv());
    }

    /**
     * Add the credit card specified to the table "CreditCard"
     * @param cardNumber
     * @param monthExpiration
     * @param yearExpiration
     * @param ccv
     * @return true when added with sucess false otherwise
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

}
