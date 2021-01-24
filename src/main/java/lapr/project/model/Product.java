package lapr.project.model;


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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product: " +
                name  +
                ", Description='" + description + '\'' +
                ", Price=" + price +
                ", Weight=" + weight +
                ", ID=" + id +
                ", Stock quantity=" + quantityStock;
    }
}
