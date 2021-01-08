package lapr.project.controller;

import lapr.project.data.ProductDataHandler;
import lapr.project.model.Product;

import java.util.List;

public class ProductController {
    private final ProductDataHandler productDataHandler;

    public ProductController(ProductDataHandler productDataHandler){
        this.productDataHandler = productDataHandler;

    }

    public void addProduct(String name, String description, double price, double weight, int pharmacyID, int stock) {
        Product product = new Product(name, description, price, weight, pharmacyID, stock);
        product.save();
    }

    public List<Product> getMedicines() {
        return productDataHandler.getAllMedicines();
    }

    public void removeProduct(int productID) {
        Product p = new Product(productID, "");
        p.delete();
    }
}
