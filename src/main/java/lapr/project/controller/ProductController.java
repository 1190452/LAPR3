package lapr.project.controller;

import lapr.project.data.ProductDataHandler;
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

    public List<Product> getMedicines() {
        return productDataHandler.getAllMedicines();
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
}
