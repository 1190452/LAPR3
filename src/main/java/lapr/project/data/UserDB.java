package lapr.project.data;

import lapr.project.model.User;
import oracle.jdbc.OracleTypes;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB extends DataHandler{

    public void addUser(User user) {
        addUser(user.getEmail(), user.getPassword(), user.getRole());
    }

    private void addUser(String email, String password, String role) {
        try {
            openConnection();
            /*
             *  Objeto "callStmt" para invocar o procedimento "addClient" armazenado
             *  na BD.
             *
             *  PROCEDURE addUser(email VARCHAR, password VARCHAR, role VARCHAR)
             *  PACKAGE pkgUser AS TYPE ref_cursor IS REF CURSOR; END pkgUser;
             */
            CallableStatement callStmt = getConnection().prepareCall("{ call addUser(?,?,?,?) }");

            callStmt.setString(1, email);
            callStmt.setString(2, password);
            callStmt.setString(3, role);

            callStmt.execute();

            closeAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String email) {
        /* Objeto "callStmt" para invocar a função "getUser" armazenada na BD.
         *
         * FUNCTION getUser(email varchar) RETURN pkgUser.ref_cursor
         * PACKAGE pkgUser AS TYPE ref_cursor IS REF CURSOR; END pkgUser;
         */
        CallableStatement callStmt = null;
        try {
            callStmt = getConnection().prepareCall("{ ? = call getUser(?) }");


            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Especifica o parâmetro de entrada da função "getUser".
            callStmt.setString(2, email);

            // Executa a invocação da função "getUser".
            callStmt.execute();

            // Guarda o cursor retornado num objeto "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                String emailU = rSet.getString(2);
                String password = rSet.getString(3);
                String role = rSet.getString(4);


                return new User(emailU, password, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No User with email:" + email);
    }
}
