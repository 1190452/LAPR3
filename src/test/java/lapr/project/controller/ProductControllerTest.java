package lapr.project.controller;

import lapr.project.data.PharmacyDataHandler;
import lapr.project.data.ProductDataHandler;
import lapr.project.model.Pharmacy;
import lapr.project.model.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    private static ProductController instance;

    @BeforeAll
    static void setUp() {
        ProductDataHandler productDataHandler = mock(ProductDataHandler.class);
        Product product = new Product(1,"xarope","xarope para a tosse",6,0.5,1,2);
        Product product2 = new Product(2,"vacina","vacina",6,0.5,1,2);
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        when(productDataHandler.getProduct(any(String.class))).thenReturn(product);
        when(productDataHandler.getAllMedicines(any(Integer.class))).thenReturn(products);
        when(productDataHandler.getProduct(any(String.class))).thenReturn(product);

        when(productDataHandler.removeProduct(any(Integer.class))).thenReturn(Boolean.TRUE);
        when(productDataHandler.addProduct(any(Product.class))).thenReturn(Boolean.TRUE);

        PharmacyDataHandler pharmacyDataHandler = mock(PharmacyDataHandler.class);
        Pharmacy phar = new Pharmacy(5, "ISEP","phar1@isep.ipp.pt", 2323, 23323, "isep@isep.ipp.pt");
        List<Pharmacy> pharmacies = new ArrayList<>();
        pharmacies.add(phar);
        when(productDataHandler.getAllMedicinesOfOthersPharmacy(any(String.class),any(Integer.class))).thenReturn(pharmacies);
        when(pharmacyDataHandler.getAllPharmacies()).thenReturn(pharmacies);

        instance = new ProductController(productDataHandler, pharmacyDataHandler);
    }

    @Test
    void getMedicines() {
        Product product = new Product(1,"xarope","xarope para a tosse",6,0.5,1,2);
        List<Product> expResult = new ArrayList<>();
        expResult.add(product);
        List<Product> result = instance.getMedicines(1);
        assertEquals(expResult, result);
    }


    @Test
    void addProducts() {
        boolean expResult = true;
        Product product = new Product(1,"xarope","xarope para a tosse",6,0.5,1,2);
        boolean result = instance.addProduct(product.getName(),product.getDescription(),product.getPrice(),product.getWeight(),product.getPharmacyID(),product.getQuantityStock());
        assertEquals(expResult, result);
    }


    @Test
    void removeProducts() {
        Product product = new Product(1, "xarope", "xarope para a tosse", 6, 0.5, 1, 2);
        boolean result = instance.removeProduct(product.getId());
        boolean expResult = true;
        assertEquals(expResult,result);

    }

    @Test
    void getProduct() {
        Product expResult = new Product(1,"xarope","xarope para a tosse",6,0.5,1,2);
        String name = "xarope";
        Product result = instance.getProduct(name);
        assertEquals(expResult, result);
    }

    @Test
    void getPharmaciesStock() {
        Pharmacy phar = new Pharmacy(5, "ISEP","phar1@isep.ipp.pt", 2323, 23323, "isep@isep.ipp.pt");
        List<Pharmacy> expResult = new ArrayList<>();
        expResult.add(phar);
        List<Pharmacy> result = instance.getPharmaciesStock(phar.getName(),4);
        assertEquals(expResult,result);
    }

    @Test
    void getPharmacies() {
        Pharmacy phar = new Pharmacy(5, "ISEP","phar1@isep.ipp.pt", 2323, 23323, "isep@isep.ipp.pt");
        List<Pharmacy> expResult = new ArrayList<>();
        expResult.add(phar);
        List<Pharmacy> result = instance.getPharmacies();
        assertEquals(expResult,result);
    }
}