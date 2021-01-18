package lapr.project.controller;

import lapr.project.data.PharmacyDataHandler;
import lapr.project.data.ProductDataHandler;
import lapr.project.data.RestockDataHandler;
import lapr.project.model.Pharmacy;
import lapr.project.model.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    private static ProductController instance;

    @BeforeAll
    static void setUp() {
        ProductDataHandler productDataHandler = mock(ProductDataHandler.class);
        Product product = new Product(1, "xarope", "xarope para a tosse", 6, 0.5, 1, 2);
        Product product2 = new Product(2, "vacina", "vacina", 6, 0.5, 1, 2);
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        when(productDataHandler.getProduct(any(String.class))).thenReturn(product);
        when(productDataHandler.getAllMedicines(any(Integer.class))).thenReturn(products);
        when(productDataHandler.getProduct(any(String.class))).thenReturn(product);
        when(productDataHandler.updateStock(any(Integer.class), any(Integer.class), any(Integer.class), any(Integer.class))).thenReturn(Boolean.TRUE);

        when(productDataHandler.removeProduct(any(Integer.class))).thenReturn(Boolean.TRUE);
        when(productDataHandler.addProduct(any(Product.class))).thenReturn(Boolean.TRUE);

        PharmacyDataHandler pharmacyDataHandler = mock(PharmacyDataHandler.class);
        Pharmacy phar = new Pharmacy(4, "farmacia", "Farmácia Tirori", 232.019, 41.1111, -8.9999, "admin@isep.ipp.pt");
        List<Pharmacy> pharmacies = new ArrayList<>();
        pharmacies.add(phar);
        when(productDataHandler.getAllMedicinesOfOthersPharmacy(any(String.class), any(Integer.class))).thenReturn(pharmacies);
        when(pharmacyDataHandler.getAllPharmacies()).thenReturn(pharmacies);
        RestockDataHandler restockDataHandlerMock = mock(RestockDataHandler.class);

        instance = new ProductController(productDataHandler, pharmacyDataHandler, restockDataHandlerMock);
    }

    @Test
    void getMedicines() {
        Product product = new Product(1, "xarope", "xarope para a tosse", 6, 0.5, 1, 2);
        List<Product> expResult = new ArrayList<>();
        expResult.add(product);
        List<Product> result = instance.getMedicines(1);
        assertEquals(expResult, result);
    }


    @Test
    void addProducts() {
        boolean expResult = true;
        Product product = new Product(1, "xarope", "xarope para a tosse", 6, 0.5, 1, 2);
        boolean result = instance.addProduct(product.getName(), product.getDescription(), product.getPrice(), product.getWeight(), product.getPharmacyID(), product.getQuantityStock());
        assertEquals(expResult, result);
    }

    @Test
    void addProducts2() {
        Product product = new Product(1, "xarope", "xarope para a tosse", 6, 0.5, 1, 2);

        ProductDataHandler productDataHandler = mock(ProductDataHandler.class);
        RestockDataHandler restockDataHandler = mock(RestockDataHandler.class);
        when(productDataHandler.addProduct(any(Product.class))).thenReturn(Boolean.FALSE);
        ProductController productController = new ProductController(productDataHandler, new PharmacyDataHandler(), restockDataHandler);

        boolean expResult = false;
        boolean result = productController.addProduct(product.getName(), product.getDescription(), product.getPrice(), product.getWeight(), product.getPharmacyID(), product.getQuantityStock());
        assertEquals(expResult, result);
    }


    @Test
    void removeProducts() {
        Product product = new Product(1, "xarope", "xarope para a tosse", 6, 0.5, 1, 2);
        boolean result = instance.removeProduct(product.getId());
        boolean expResult = true;
        assertEquals(expResult, result);

    }

    @Test
    void removeProducts2() {
        Product product = new Product(1, "xarope", "xarope para a tosse", 6, 0.5, 1, 2);

        ProductDataHandler productDataHandler = mock(ProductDataHandler.class);
        RestockDataHandler restockDataHandler = mock(RestockDataHandler.class);
        when(productDataHandler.removeProduct(any(Integer.class))).thenReturn(Boolean.FALSE);
        ProductController productController = new ProductController(productDataHandler, new PharmacyDataHandler(), restockDataHandler);

        boolean result = productController.removeProduct(product.getId());
        boolean expResult = false;
        assertEquals(expResult, result);

    }

    @Test
    void getProduct() {
        Product expResult = new Product(1, "xarope", "xarope para a tosse", 6, 0.5, 1, 2);
        String name = "xarope";
        Product result = instance.getProduct(name);
        assertEquals(expResult, result);
    }

    @Test
    void getPharmaciesStock() {
        Pharmacy phar = new Pharmacy(4, "farmacia", "Farmácia Tirori", 232.019, 41.1111, -8.9999, "admin@isep.ipp.pt");
        List<Pharmacy> expResult = new ArrayList<>();
        expResult.add(phar);
        List<Pharmacy> result = instance.getPharmaciesStock(phar.getName(), 4);
        assertEquals(expResult, result);
    }

    @Test
    void getPharmacies() {
        Pharmacy phar = new Pharmacy(4, "farmacia", "Farmácia Tirori", 232.019, 41.1111, -8.9999, "admin@isep.ipp.pt");
        List<Pharmacy> expResult = new ArrayList<>();
        expResult.add(phar);
        List<Pharmacy> result = instance.getPharmacies();
        assertEquals(expResult, result);
    }

    @Test
    void updateStockPharmacy() {
        boolean result = instance.updateStockPharmacy(2, 4, 1, 5);
        assertTrue(result);
    }

    @Test
    void updateStockPharmacy2() {
        ProductDataHandler productDataHandler = mock(ProductDataHandler.class);
        RestockDataHandler restockDataHandler = mock(RestockDataHandler.class);
        when(productDataHandler.updateStock(any(Integer.class), any(Integer.class), any(Integer.class), any(Integer.class))).thenReturn(Boolean.FALSE);
        ProductController productController = new ProductController(productDataHandler, new PharmacyDataHandler(), restockDataHandler);

        boolean result = productController.updateStockPharmacy(2, 4, 1, 5);
        boolean expResult = false;
        assertEquals(expResult, result);
    }

    @Test
    void sendEmail() {
        boolean result = instance.sendEmail(new Pharmacy(4, "farmacia", "jskadjkasdl9387219kds@gmail.com", 232.019, 41.1111, -8.9999, "admin@isep.ipp.pt"), new Product(1, "xarope", "xarope para a tosse", 6, 0.5, 1, 2), 5);
        assertTrue(result);
    }

    @Test
    void sendEmai2l() {
        boolean result = instance.sendEmail(new Pharmacy(4, "farmacia", "", 232.019, 41.1111, -8.9999, "admin@isep.ipp.pt"), new Product(1, "xarope", "xarope para a tosse", 6, 0.5, 1, 2), 5);
        assertFalse(result);
    }

    @Test
    void getPharmacyCloser() {
        Pharmacy receiver = new Pharmacy(4, "farmacia", "Farmácia Tirori", 232.019, 41.1111, -8.9999, "admin@isep.ipp.pt");

        Pharmacy p1 = new Pharmacy(1, "p1", "p1@gmail.com", 232.0, 41.6, -8.99, "admin@gmail.com");
        Pharmacy p2 = new Pharmacy(2, "p2", "p2@gmail.com", 500.0, 23.6, 10, "admin@gmail.com");
        Pharmacy p3 = new Pharmacy(3, "p3", "p3@gmail.com", 600.0, 13.6, 10, "admin@gmail.com");
        Pharmacy p4 = new Pharmacy(5, "p5", "p5@gmail.com", 700.0, 416.6, 10, "admin@gmail.com");

        List<Pharmacy> list = new ArrayList<>();

        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);

        Pharmacy result = instance.getPharmacyCloser(list, receiver);

        Pharmacy expResult = new Pharmacy(1, "p1", "p1@gmail.com", 232.0, 41.6, 10, "admin@gmail.com");

        assertEquals(expResult, result);

    }
}