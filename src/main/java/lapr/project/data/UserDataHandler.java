package lapr.project.data;

import lapr.project.model.User;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDataHandler extends DataHandler{

    public boolean addUser(User user) {
        return addUser(user.getEmail(), user.getPassword(), user.getRole());
    }

    /**
     * Add the User specified to the table "AppUser"
     * @param email
     * @param password
     * @param role
     * @return true when added with sucess false otherwise
     */
    public boolean addUser(String email, String password, String role) {
        boolean isAdded = false;
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcAddUser(?,?,?) }")) {
                callStmt.setString(1, email);
                callStmt.setString(2, password);
                callStmt.setString(3, role);

                isAdded = true;
                callStmt.execute();

                closeAll();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAdded;
    }

    /**
     * Validate if the user exists in the table "AppUser"
     * @param email
     * @param password
     * @return the email
     */
    public String validateLogin(String email, String password) {
        try {
            try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call fncLogin(?,?) }")){
                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.INTEGER);
                // Especifica o parâmetro de entrada da função "fncLogin".
                callStmt.setString(2, email);
                callStmt.setString(3, password);

                // Executa a invocação da função "fncLogin".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                int rSet = callStmt.getInt(1);

                if (rSet == 1 ) {
                    return email;
                }
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
            throw new IllegalArgumentException("No User with email:" + email);
    }

    /**
     * Get the user with the email specified from the table "AppUser"
     * @param email
     * @return the user
     */
    public User getByEmail(String email) {
        try {
            try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getUser(?) }")){
                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "fncLogin".
                callStmt.setString(2, email);


                // Executa a invocação da função "getSailor".
                callStmt.execute();

                ResultSet rSet = (ResultSet) callStmt.getObject(1);


                if (rSet.next()) {
                    String emailU = rSet.getString(1);
                    String passwordU = rSet.getString(2);
                    String roleU = rSet.getString(3);
                    return new User(emailU, passwordU, roleU);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No User with email:" + email);
    }
}
