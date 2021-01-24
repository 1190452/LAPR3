package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    private final Product product;
    private final Product product2;
    private final Product product3;


    ProductTest() {
        product = new Product(1,"xarope","xarope para a tosse",6,0.5,1,2);
        product2 = new Product("xarope","xarope para a tosse",6,0.5,1,2);
        product3 = new Product(3,"benuron");
    }

    @Test
    void getId() {
        int expResult = 1;
        assertEquals(expResult,product.getId());
    }

    @Test
    void getName() {
        String expResult = "xarope";
        assertEquals(expResult,product.getName());

    }

    @Test
    void setName() {
        product.setName("benuron");
        String result = product.getName();
        String expResult = "benuron";
        assertEquals(expResult,result);
    }

    @Test
    void getDescription() {
        String expResult = "xarope para a tosse";
        assertEquals(expResult,product.getDescription());
    }

    @Test
    void setDescription() {
        product.setDescription("comprimidos");
        String result = product.getDescription();
        String expResult = "comprimidos";
        assertEquals(expResult,result);
    }

    @Test
    void getPrice() {
        double expResult = 6;
        assertEquals(expResult, product.getPrice());
    }

    @Test
    void setPrice() {
        product.setPrice(4);
        double result = product.getPrice();
        double expResult = 4;
        assertEquals(expResult,result);
    }

    @Test
    void getWeight() {
        double expResult = 0.5;
        assertEquals(expResult,product.getWeight());
    }

    @Test
    void setWeight() {
        product.setWeight(0.2);
        double result = product.getWeight();
        double expResult = 0.2;
        assertEquals(expResult,result);
    }

    @Test
    void getPharmacyID() {
        int expResult = 1;
        assertEquals(expResult, product.getPharmacyID());
    }

    @Test
    void setPharmacyID() {
        product.setPharmacyID(2);
        int result = product.getPharmacyID();
        int expResult = 2;
        assertEquals(expResult,result);
    }

    @Test
    void setId() {
        product.setId(4);
        int result = product.getId();
        int expResult = 4;
        assertEquals(expResult,result);
    }

    @Test
    void getQuantityStock() {
        int expResult = 2;
        assertEquals(expResult,product.getQuantityStock());
    }

    @Test
    void setQuantityStock() {
        product.setQuantityStock(10);
        int result = product.getQuantityStock();
        int expResult = 10;
        assertEquals(expResult,result);
    }

    @Test
    public void test1Equals() {
        Product obj = null;
        Product instance = new Product(1,"xarope","xarope para a tosse",6,0.5,1,2);
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test2Equals() {
        Object obj = null;
        Product instance = new Product(1,"xarope","xarope para a tosse",6,0.5,1,2);
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test3Equals() {
        Product instance = new Product(1,"xarope","xarope para a tosse",6,0.5,1,2);
        boolean expected = true;
        boolean result = instance.equals(instance);
        assertEquals(expected, result);
    }

    @Test
    public void test4Equals() {
        Product p = new Product(1,"xarope","xarope para a tosse",6,0.5,1,2);
        Product instance = new Product(1,"xarope","xarope para a tosse",6,0.5,1,2);
        boolean expected = true;
        boolean result = instance.equals(p);
        assertEquals(expected, result);
    }

    @Test
    public void test5Equals() {
        Product p = new Product(1,"xarope","xarope para a tosse",6,0.5,1,2);
        Product instance = new Product(2,"comprimidos","comprimidos para a tosse",5,1,1,2);
        boolean expected = false;
        boolean result = instance.equals(p);
        assertEquals(expected, result);
    }

    @Test
    void testHashCode() {
        int result = product.hashCode();
        int expected = 32;
        assertEquals(expected, result);
    }

    @Test
    void testToString() {
        String result = product.toString();
        String expResult = "Product: " +
                product.getName()  +
                ", Description='" + product.getDescription() + '\'' +
                ", Price=" + product.getPrice() +
                ", Weight=" + product.getWeight() +
                ", ID=" + product.getId() +
                ", Stock quantity=" + product.getQuantityStock();
        assertEquals(expResult,result);
    }

    @Test
    public void test6Equals() {
        Pharmacy p = new Pharmacy(1,"phar", "Farmácia Tirori", 2313.12, 41.1111, -8.9999, "admin@isep.ipp.pt");
        Product instance =  new Product(1,"xarope","xarope para a tosse",6,0.5,1,2);
        boolean expected = false;
        boolean result = instance.equals(p);
        assertEquals(expected, result);
    }
}