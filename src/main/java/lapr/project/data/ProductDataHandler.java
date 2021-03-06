package lapr.project.data;

import lapr.project.model.Pharmacy;
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

    /**
     * Add the product specified to the table "Product"
     * @param name
     * @param description
     * @param price
     * @param weight
     * @param pharmacyID
     * @param stock
     * @return true when added with sucess false otherwise
     */
    private boolean addProduct(String name,  String description, double price, double weight, int pharmacyID, int stock) {
        boolean added = false;
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ call prcAddMedicine(?,?,?,?,?,?) }")) {
                callStmt.setString(1, name);
                callStmt.setString(2, description);
                callStmt.setDouble(3, price);
                callStmt.setDouble(4, weight);
                callStmt.setInt(5, stock);
                callStmt.setInt(6, pharmacyID);

                callStmt.execute();

                added  = true;

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return added;
    }

    /**
     * Get the product with the name specified from the table "Product"
     * @param nameProduct
     * @return the product
     */
    public Product getProduct(String nameProduct) {

        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getProduct(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o par??metro de entrada da fun????o "getCourier".
                callStmt.setString(2, nameProduct);

                // Executa a invoca????o da fun????o "getCourier".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                    int idProduct = rSet.getInt(1);
                    String productN = rSet.getString(2);
                    String productDescription = rSet.getString(3);
                    double productPrice = rSet.getDouble(4);
                    double productWeight = rSet.getDouble(5);
                    int pharmacyID = rSet.getInt(6);
                    int stockProduct = rSet.getInt(7);


                    return new Product(idProduct, productN, productDescription, productPrice, productWeight, pharmacyID, stockProduct);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No Product with name:" + nameProduct);
    }

    /**
     * Get all products from a pharmacy with the id specified from the table "Product"
     * @param pharmID
     * @return list of products
     */
    public List<Product> getAllMedicines(int pharmID) {

        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getProductListByPharmacy(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setInt(2, pharmID);

                // Executa a invoca????o da fun????o "getCourier".
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
                    int pharmacyID = rSet.getInt(6);
                    int stock = rSet.getInt(7);


                    products.add(new Product(id, name, description, price, weight, stock, pharmacyID));
                }
                return products;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("There are no products in the Pharmacy");
    }

    /**
     * Remove the product with the id specified from the table "Product"
     * @param id
     * @return true when removed with sucess false otherwise
     */
    public boolean removeProduct(int id) {
        boolean removed = false;
        try {
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

    /**
     * Get all pharmacys with the product and the stock specified from the table "Pharmacy"
     * @param nameMedicine
     * @param stockMissing
     * @param pharmID
     * @return list of pharmacys
     */
    public List<Pharmacy> getAllMedicinesOfOthersPharmacy(String nameMedicine, int stockMissing, int pharmID) {

        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getProductByStock(?,?,?) }")) {

                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setString(2, nameMedicine);
                callStmt.setInt(3, stockMissing);
                callStmt.setInt(4, pharmID);

                // Executa a invoca????o da fun????o "getCourier".
                callStmt.execute();

                // Guarda o cursor retornado num objeto "ResultSet".
                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                List<Pharmacy> pharmsID = new ArrayList();
                while (rSet.next()) {
                    int id = rSet.getInt(1);
                    String name = rSet.getString(2);
                    double latitude = rSet.getDouble(3);
                    double longitude = rSet.getDouble(4);
                    double altitude = rSet.getDouble(5);
                    String emailP = rSet.getString(6);
                    String email = rSet.getString(7);


                    pharmsID.add(new Pharmacy(id,name, emailP,latitude, longitude, altitude,email));
                }

                return pharmsID;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("There are no products in the others pharmacy");
    }

    /**
     * Update product stock from the pharmacies specified in table "Product"
     * @param idReceiver
     * @param idSender
     * @param productID
     * @param stockMissing
     * @return true when updated with sucess false otherwise
     */
    public boolean updateStock(int idReceiver, int idSender, int productID, int stockMissing) {
        boolean removed = false;
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{  call prcUpdateStockA(?,?,?,?) }")) {
                callStmt.setInt(1, idReceiver);
                callStmt.setInt(2, idSender);
                callStmt.setInt(3, productID);
                callStmt.setInt(4, stockMissing);

                callStmt.execute();
                removed = true;

                closeAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return removed;
    }

    /**
     * Get the product with the id specified from the table "Product"
     * @param productID
     * @return the product
     */
    public Product getProductByID(int productID) {
        try {
            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call getProductByID(?) }")) {


                // Regista o tipo de dados SQL para interpretar o resultado obtido.
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                // Especifica o par??metro de entrada da fun????o "getCourier".
                callStmt.setInt(2, productID);

                // Executa a invoca????o da fun????o "getCourier".
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
        throw new IllegalArgumentException("No Product with id:" + productID);
    }
}
