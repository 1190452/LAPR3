package lapr.project.controller;

import lapr.project.data.EmailAPI;
import lapr.project.data.PharmacyDataHandler;
import lapr.project.data.ProductDataHandler;
import lapr.project.model.Pharmacy;
import lapr.project.model.Product;
import lapr.project.utils.Distance;

import java.util.List;

public class ProductController {
    private final ProductDataHandler productDataHandler;
    private final PharmacyDataHandler pharmacyDataHandler;


    public ProductController(ProductDataHandler productDataHandler, PharmacyDataHandler pharmacyDataHandler){
        this.productDataHandler = productDataHandler;
        this.pharmacyDataHandler = pharmacyDataHandler;
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
        double menor= Distance.distanceBetweenTwoAddresses(receiver.getLatitude(),receiver.getLongitude(),paux.getLatitude(),paux.getLongitude());
        Pharmacy pharmacyCloser=null;

        for (int i = 1; i <list.size() ; i++) {
            Pharmacy p = list.get(i);
            if(Distance.distanceBetweenTwoAddresses(receiver.getLatitude(),receiver.getLongitude(),p.getLatitude(),p.getLongitude())<=menor){
                pharmacyCloser=p;
            }

        }
        return pharmacyCloser;
    }

    public boolean sendEmail(Pharmacy pharmacy,Product product,int stockMissing) {
        return EmailAPI.sendEmailToSendingProduct(pharmacy.getEmail(), product ,stockMissing) ;//TODO Verificar se funciona
    }

    public void restock(Pharmacy receiver, Pharmacy pharmacyCloser,Product product,int stockMissing) {
        System.out.println("Getting stock");
        sendEmail(receiver,product,stockMissing);
        updateStockPharmacy(receiver.getId(),pharmacyCloser.getId(),product.getId(),stockMissing);
        System.out.println("Restocked");
    }


}
