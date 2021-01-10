package lapr.project.controller;

import lapr.project.data.ProductDataHandler;
import lapr.project.model.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    private static ProductController instance;

    @BeforeAll
    static void setUp() {
        ProductDataHandler productDataHandler = mock(ProductDataHandler.class);
        Product product = new Product(1,"xarope","xarope para a tosse",6,0.5,1,2);
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        when(productDataHandler.getAllMedicines()).thenReturn(products);


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
}