package lapr.project.model;

import lapr.project.data.ProductDataHandler;

import java.util.Objects;

public class Product {
    private String name;
    private String description;
    private double price;
    private double weight;
    private int pharmacyID;
    private int id;
    private int quantityStock;

    public Product(int id, String name, String description, double price, double weight, int pharmacyID, int quantityStock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.weight = weight;
        this.pharmacyID = pharmacyID;
        this.quantityStock = quantityStock;
    }

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Product(String name, String description, double price, double weight, int pharmacyID, int quantityStock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.weight = weight;
        this.pharmacyID = pharmacyID;
        this.quantityStock = quantityStock;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getPharmacyID() {
        return pharmacyID;
    }

    public void setPharmacyID(int pharmacyID) {
        this.pharmacyID = pharmacyID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantityStock() {
        return quantityStock;
    }

    public void setQuantityStock(int quantityStock) {
        this.quantityStock = quantityStock;
    }

    public void save() {
        try {
            getProduct(this.name);
        } catch (IllegalArgumentException ex) {
            //Of the record does not exist, save it
            new ProductDataHandler().addProduct(this);
        }
    }

    public static Product getProduct(String nameProduct) {
        return new ProductDataHandler().getProduct(nameProduct);
    }

    public void delete(){
        new ProductDataHandler().removeProduct(this.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
                Double.compare(product.weight, weight) == 0 &&
                pharmacyID == product.pharmacyID &&
                id == product.id &&
                quantityStock == product.quantityStock &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, weight, pharmacyID, id, quantityStock);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", pharmacyID=" + pharmacyID +
                ", id=" + id +
                ", quantityStock=" + quantityStock +
                '}';
    }
}
