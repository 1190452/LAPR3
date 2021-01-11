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
        doNothing().when(productDataHandler).addProduct(product);
        doNothing().when(productDataHandler).removeProduct(product.getId());
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
        Product product = new Product(1,"xarope","xarope para a tosse",6,0.5,1,2);
        instance.addProduct(product.getName(),product.getDescription(),product.getPrice(),product.getWeight(),product.getPharmacyID(),product.getQuantityStock());
        /*
        ProductDataHandler productDataHandler = mock(ProductDataHandler.class);
        ProductController productController = new ProductController(productDataHandler);
        Product product = new Product(1,"xarope","xarope para a tosse",6,0.5,1,2);
        doNothing().when(productDataHandler).addProduct(product);
        productController.addProduct(product.getName(), product.getDescription(), product.getPrice(), product.getWeight(), product.getPharmacyID(), product.getQuantityStock());
        verify(productDataHandler, times(1)).addProduct(product);*/
        //assertEquals(instance.addProduct("xarope","xarope para a tosse",6,0.5,1,2));
    }

    @Test
    void removeProducts() {
        assertEquals(1, instance.getMedicines().size());
        Product product = new Product(1, "xarope", "xarope para a tosse", 6, 0.5, 1, 2);
        instance.removeProduct(product.getId());
       // assertEquals(0, instance.getMedicines().size());

    }
}