package lapr.project.controller;

import lapr.project.data.ProductDataHandler;
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
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        when(productDataHandler.getProduct(any(String.class))).thenReturn(product);
        when(productDataHandler.getAllMedicines()).thenReturn(products);
        when(productDataHandler.getProduct(any(String.class))).thenReturn(product);
        when(productDataHandler.removeProduct(any(Integer.class))).thenReturn(Boolean.TRUE);
        when(productDataHandler.addProduct(any(Product.class))).thenReturn(Boolean.TRUE);
        instance = new ProductController(productDataHandler);
    }

    @Test
    void getMedicines() {
        Product product = new Product(1,"xarope","xarope para a tosse",6,0.5,1,2);
        List<Product> expResult = new ArrayList<>();
        expResult.add(product);
        List<Product> result = instance.getMedicines();
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

}