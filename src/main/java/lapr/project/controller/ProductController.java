package lapr.project.controller;

import lapr.project.data.ProductDataHandler;
import lapr.project.model.Pharmacy;
import lapr.project.model.Product;

import java.util.List;

public class ProductController {
    private final ProductDataHandler productDataHandler;


    public ProductController(ProductDataHandler productDataHandler){
        this.productDataHandler = productDataHandler;

    }

    public boolean addProduct(String name, String description, double price, double weight, int pharmacyID, int stock) {
        boolean added = false;
        Product product = new Product(name, description, price, weight, pharmacyID, stock);
        added = productDataHandler.addProduct(product);
        return added;
    }

    public Product getProduct(String nameProduct) {
        return productDataHandler.getProduct(nameProduct);
    }

    public List<Product> getMedicines(int pharmID) {
        return productDataHandler.getAllMedicines(pharmID);
    }

    /*public void removeProduct(int productID) {
        Product p = new Product(productID, "");
        p.delete();
    }*/

    public boolean removeProduct(int id){
        boolean removed = false;
        removed = productDataHandler.removeProduct(id);
        return removed;
    }

    public List<Pharmacy> getPharmaciesStcok(String nameMedicine, int stockMissing) {
        return productDataHandler.getAllMedicinesOfOthersPharmacy(nameMedicine, stockMissing);
    }
}
