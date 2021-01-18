package lapr.project.controller;

import lapr.project.data.EmailAPI;
import lapr.project.data.PharmacyDataHandler;
import lapr.project.data.ProductDataHandler;
import lapr.project.data.RestockDataHandler;
import lapr.project.model.Pharmacy;
import lapr.project.model.Product;
import lapr.project.model.RestockOrder;
import lapr.project.utils.Physics;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductController {
    private final ProductDataHandler productDataHandler;
    private final PharmacyDataHandler pharmacyDataHandler;
    private final RestockDataHandler restockDataHandler;


    public ProductController(ProductDataHandler productDataHandler, PharmacyDataHandler pharmacyDataHandler, RestockDataHandler restockDataHandler){
        this.productDataHandler = productDataHandler;
        this.pharmacyDataHandler = pharmacyDataHandler;
        this.restockDataHandler = restockDataHandler;
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

    public boolean removeProduct(int id){
        boolean removed = false;
        removed = productDataHandler.removeProduct(id);
        return removed;
    }

    public List<Pharmacy> getPharmaciesStock(String nameMedicine, int stockMissing) {
        return productDataHandler.getAllMedicinesOfOthersPharmacy(nameMedicine, stockMissing);
    }

    public List<Pharmacy> getPharmacies() {
        return pharmacyDataHandler.getAllPharmacies();
    }

    public boolean updateStockPharmacy(int idReceiver, int idSender, int productID, int stockMissing) {
        return productDataHandler.updateStock(idReceiver, idSender, productID, stockMissing);
    }

    public Pharmacy getPharmacyCloser(List<Pharmacy> list,Pharmacy receiver) {
        Pharmacy paux = list.get(0);
        double menor= Physics.calculateDistanceWithElevation(receiver.getLatitude() ,paux.getLatitude(), receiver.getLongitude(), paux.getLongitude(), receiver.getAltitude(), paux.getAltitude());
        Pharmacy pharmacyCloser=null;

        for (int i = 1; i <list.size() ; i++) {
            Pharmacy p = list.get(i);
            if(Physics.calculateDistanceWithElevation(receiver.getLatitude() ,paux.getLatitude(), receiver.getLongitude(), paux.getLongitude(), receiver.getAltitude(), paux.getAltitude())<=menor){
                pharmacyCloser=p;
            }

        }
        return pharmacyCloser;
    }

    public boolean sendEmail(Pharmacy pharmacy,Product product,int stockMissing) {
        return EmailAPI.sendEmailToSendingProduct(pharmacy.getEmail(), product ,stockMissing) ;//TODO Verificar se funciona
    }

    public RestockOrder createRestock(int prodID, int pharmSenderID, int pharmReceiverID, int stockMissing, int clientOrderID) {
        RestockOrder r = new RestockOrder(pharmReceiverID, pharmSenderID, prodID, clientOrderID, stockMissing, 0, 0);
        restockDataHandler.addRestock(r);
        return r;
    }

    public Product getProductByID(int productID) {
        return productDataHandler.getProductByID(productID);
    }
}
