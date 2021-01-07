package lapr.project.model;

public class Product {
    private String name;
    private String description;
    private double price;
    private double weight;
    private String pharmacyID;
    private int id;

    public Product(int id, String name, String description, double price, double weight, String pharmacyID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.weight = weight;
        this.pharmacyID = pharmacyID;
    }

    public Product(String name, String description, double price, double weight, String pharmacyID) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.weight = weight;
        this.pharmacyID = pharmacyID;
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

    public String getPharmacyID() {
        return pharmacyID;
    }

    public void setPharmacyID(String pharmacyID) {
        this.pharmacyID = pharmacyID;
    }
}
