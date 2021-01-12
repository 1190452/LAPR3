package lapr.project.data;

import lapr.project.model.Product;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDataHandler extends DataHandler{

    public boolean addProduct(Product product) {
        return addProduct(product.getName(), product.getDescription(), product.getPrice(), product.getWeight(), product.getPharmacyID(), product.getQuantityStock());
    }

    private boolean addProduct(String name,  String description, double price, double weight, int pharmacyID, int stock) {
        boolean added = false;
        try {
            openConnection();
            /*
             *  Objeto "callStmt" para invocar o procedimento "addMedicine" armazenado
             *  na BD.
             *
             *  PROCEDURE addMedicine(sid NUMBER, sname VARCHAR, rating NUMBER, age NUMBER)
             *  PACKAGE pkgCreditCards AS TYPE ref_cursor IS REF CURSOR; END pkgCreditCards;
             */
            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcAddMedicine(?,?,?,?,?) }")) {
                callStmt.setString(1, name);
                callStmt.setString(2, description);
                callStmt.setDouble(3, price);
                callStmt.setDouble(4, weight);
                callStmt.setInt(5, pharmacyID);
                callStmt.setInt(6, stock);

                callStmt.execute();

                added  = true;

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return added;
    }

    public Product getProduct(String nameProduct) {
        /* Objeto "callStmt" para invocar a função "getCourier" armazenada na BD.
         *
         * FUNCTION getCourier(nif INT) RETURN pkgCourier.ref_cursor
         * PACKAGE pkgCourier AS TYPE ref_cursor IS REF CURSOR; END pkgCourier;
         */
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getProduct(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o parâmetro de entrada da função "getCourier".
                callStmt.setString(2, nameProduct);

                // Executa a invocação da função "getCourier".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                    int id = rSet.getInt(1);
                    String name = rSet.getString(2);
                    String description = rSet.getString(3);
                    double price = rSet.getDouble(4);
                    double weight = rSet.getDouble(5);
                    int idPharmacy = rSet.getInt(6);
                    int stock = rSet.getInt(7);


                    return new Product(id, name, description, price, weight, idPharmacy, stock);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Product with name:" + nameProduct);
    }

    public List<Product> getAllMedicines() {
        /* Objeto "callStmt" para invocar a função "getCourier" armazenada na BD.
         *
         * FUNCTION getCourier(nif INT) RETURN pkgCourier.ref_cursor
         * PACKAGE pkgCourier AS TYPE ref_cursor IS REF CURSOR; END pkgCourier;
         */
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getProductList() }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);

                // Executa a invocação da função "getCourier".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                List<Product> products = new ArrayList<>();
                while (rSet.next()) {
                    int id = rSet.getInt(1);
                    String name = rSet.getString(2);
                    String description = rSet.getString(3);
                    double price = rSet.getDouble(4);
                    double weight = rSet.getDouble(5);
                    int pharmID = rSet.getInt(6);
                    int stock = rSet.getInt(7);


                    products.add(new Product(id, name, description, price, weight, stock, pharmID));
                }
                return products;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("There are no products in the Pharmacy");
    }

    public boolean removeProduct(int id) {
        boolean removed = false;
        try {

            openConnection();
            /*
             *  Objeto "callStmt" para invocar o procedimento "removeSailor"
             *  armazenado na BD.
             *
             *  PROCEDURE removeSailor(sid NUMBER)
             *  PACKAGE pkgSailors AS TYPE ref_cursor IS REF CURSOR; END pkgSailors;
             */
            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcRemoveMedicine(?) }")) {
                callStmt.setInt(1, id);

                callStmt.execute();
                removed = true;

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return removed;
    }
}
