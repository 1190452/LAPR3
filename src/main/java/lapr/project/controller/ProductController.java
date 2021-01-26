package lapr.project.controller;

import lapr.project.data.PharmacyDataHandler;
import lapr.project.data.ProductDataHandler;
import lapr.project.model.Pharmacy;
import lapr.project.model.Product;
import lapr.project.utils.Physics;

import java.util.List;

public class ProductController {
    private final ProductDataHandler productDataHandler;
    private final PharmacyDataHandler pharmacyDataHandler;


    /**
     * constructor to iniciate the data handlers that are needed in this controller
     * @param productDataHandler
     * @param pharmacyDataHandler
     */
    public ProductController(ProductDataHandler productDataHandler, PharmacyDataHandler pharmacyDataHandler){
        this.productDataHandler = productDataHandler;
        this.pharmacyDataHandler = pharmacyDataHandler;
    }

    /**
     * methodo to manage the adding of products
     * @param name
     * @param description
     * @param price
     * @param weight
     * @param pharmacyID
     * @param stock
     * @return boolean to inform the success of the operation
     */
    public boolean addProduct(String name, String description, double price, double weight, int pharmacyID, int stock) {
        boolean added = false;
        Product product = new Product(name, description, price, weight, pharmacyID, stock);
        added = productDataHandler.addProduct(product);
        return added;
    }

    /**
     * method to get the product from db by its name
     * @param nameProduct
     * @return product
     */
    public Product getProduct(String nameProduct) {
        return productDataHandler.getProduct(nameProduct);
    }

    /**
     * method to return all products from a certain pharmacy
     * @param pharmID
     * @return listProducts
     */
    public List<Product> getMedicines(int pharmID) {
        return productDataHandler.getAllMedicines(pharmID);
    }

    /**
     * method to remove product by its id
     * @param id
     * @return boolean to inform the success of the operation
     */
    public boolean removeProduct(int id){
        boolean removed = false;
        removed = productDataHandler.removeProduct(id);
        return removed;
    }

    /**
     * method to get pharmacies that have a certain product in stock
     * @param nameMedicine
     * @param stockMissing
     * @param pharmID
     * @return listPharmacies
     */
    public List<Pharmacy> getPharmaciesStock(String nameMedicine, int stockMissing, int pharmID) {
        return productDataHandler.getAllMedicinesOfOthersPharmacy(nameMedicine, stockMissing, pharmID);
    }

    /**
     * method to get all pharmacies registred in the system
     * @return listPharmacies
     */
    public List<Pharmacy> getPharmacies() {
        return pharmacyDataHandler.getAllPharmacies();
    }

    /**
     * method to update the stock of a pharmacy
     * @param idReceiver
     * @param idSender
     * @param productID
     * @param stockMissing
     * @return boolean to inform the success of the operation
     */
    public boolean updateStockPharmacy(int idReceiver, int idSender, int productID, int stockMissing) {
        return productDataHandler.updateStock(idReceiver, idSender, productID, stockMissing);
    }

    /**
     * return pharmacy that is clooser
     * @param list
     * @param receiver
     * @return pharmacy
     */
    public Pharmacy getPharmacyCloser(List<Pharmacy> list,Pharmacy receiver) {
        Pharmacy paux = list.get(0);
        double menor= Physics.calculateDistanceWithElevation(receiver.getLatitudePharmacy() ,paux.getLatitudePharmacy(), receiver.getLongitudePharmacy(), paux.getLongitudePharmacy(), receiver.getAltitudePharmacy(), paux.getAltitudePharmacy());
        Pharmacy pharmacyCloser=paux;

        for (int i = 1; i <list.size() ; i++) {
            Pharmacy p = list.get(i);
            double dist = Physics.calculateDistanceWithElevation(receiver.getLatitudePharmacy() ,p.getLatitudePharmacy(), receiver.getLongitudePharmacy(), p.getLongitudePharmacy(), receiver.getAltitudePharmacy(), p.getAltitudePharmacy());
            if(dist < menor){
                menor = dist;
                pharmacyCloser=p;
            }

        }
        return pharmacyCloser;
    }

    /**
     * method to get product by its id
     * @param productID
     * @return product
     */
    public Product getProductByID(int productID) {
        return productDataHandler.getProductByID(productID);
    }
}
