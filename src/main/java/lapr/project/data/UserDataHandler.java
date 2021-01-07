package lapr.project.data;

import lapr.project.model.User;
import oracle.jdbc.OracleTypes;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDataHandler extends DataHandler{

    public static void addUser(User user) {
        addUser(user.getEmail(), user.getPassword(), user.getRole());
    }

    private static void addUser(String email, String password, String role) {
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

    public static int validateLogin(String email, String password) {
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

    public static User getById(int id) {
        String query = "SELECT * FROM user WHERE id_user= " + id;
        ResultSet rst = null;
        User user = null;
        try {
            Connection con = getConnection();
            Statement stm = con.createStatement();
            rst = stm.executeQuery(query);

            if (rst.next()) {
                String email = rst.getString(2);
                String username = rst.getString(3);
                String creditCard = rst.getString(4);
                Float cyclingAverageSpeed = rst.getFloat(5);
                int height = rst.getInt(6);
                Float weight = rst.getFloat(7);
                String gender = rst.getString(8);
                String pwd = rst.getString(9);
                int points = rst.getInt(10);
                user = new User(id, email, username, creditCard, cyclingAverageSpeed,
                        height, weight, gender, pwd);
                user.setPoints(points);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return user;
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
