package lapr.project.data;

import lapr.project.model.User;
import oracle.jdbc.OracleTypes;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDataHandler extends DataHandler{

    public  void addUser(User user) {
        addUser(user.getEmail(), user.getPassword(), user.getRole());
    }

    public void addUser(String email, String password, String role) {
        try {
            /*
             *  Objeto "callStmt" para invocar o procedimento "addClient" armazenado
             *  na BD.
             *
             *  PROCEDURE addUser(email VARCHAR, password VARCHAR, role VARCHAR)
             *  PACKAGE pkgUser AS TYPE ref_cursor IS REF CURSOR; END pkgUser;
             */
            try(CallableStatement callStmt = getConnection().prepareCall("{ call addUser(?,?,?,?) }")) {
                callStmt.setString(1, email);
                callStmt.setString(2, password);
                callStmt.setString(3, role);

                callStmt.execute();

                closeAll();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int validateLogin(String email, String password) {
        CallableStatement callStmt = null;
        int result = 0;
        try {
            callStmt = getConnection().prepareCall("{ ? = call funcValidateLogin(?,?) }");
            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmt.setString(2, email);
            callStmt.setString(3, password);
            callStmt.execute();

            result = callStmt.getInt(1);

        } catch (SQLException e) {
            Logger.getLogger(UserDataHandler.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            try {
                if (callStmt != null) {
                    callStmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return result;
    }

    public User getById(int id) {
        String query = "SELECT * FROM user WHERE id_user= " + id;
        ResultSet rst = null;
        Statement stm = null;
        User user = null;
        try {
            try {
                Connection con = getConnection();
                stm = con.createStatement();
                rst = stm.executeQuery(query);

                if (rst.next()) {
                    String email = rst.getString(2);
                    String username = rst.getString(3);
                    String role = rst.getString(4);
                    user = new User(email, username, role);
                }
            }finally {
                try {
                    if (stm != null)
                        stm.close();
                    if (rst != null)
                        rst.close();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        } catch (SQLException exception) {
            Logger.getLogger(UserDataHandler.class.getName()).log(Level.WARNING, exception.getMessage());
        }
        return user;
    }

    public User getUser(String email) {
        /* Objeto "callStmt" para invocar a função "getUser" armazenada na BD.
         *
         * FUNCTION getUser(email varchar) RETURN pkgUser.ref_cursor
         * PACKAGE pkgUser AS TYPE ref_cursor IS REF CURSOR; END pkgUser;
         */
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getUser(?) }")) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No User with email:" + email);
    }
}
