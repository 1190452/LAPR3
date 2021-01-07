package lapr.project.model;

import java.util.List;
import java.util.Objects;

public class Cart {
    private int productQuantity;
    private double finalPrice;
    private double finalWeight;
    private List<Product> productsTobuy;

    public Cart(int productQuantity, double finalPrice, double finalWeight, List<Product> productsTobuy) {
        this.productQuantity = productQuantity;
        this.finalPrice = finalPrice;
        this.finalWeight = finalWeight;
        this.productsTobuy = productsTobuy;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public double getFinalWeight() {
        return finalWeight;
    }

    public void setFinalWeight(double finalWeight) {
        this.finalWeight = finalWeight;
    }

    public List<Product> getProductsTobuy() {
        return productsTobuy;
    }

    public void setProductsTobuy(List<Product> productsTobuy) {
        this.productsTobuy = productsTobuy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;
        Cart cart = (Cart) o;
        return productQuantity == cart.productQuantity &&
                Double.compare(cart.finalPrice, finalPrice) == 0 &&
                Double.compare(cart.finalWeight, finalWeight) == 0 &&
                Objects.equals(productsTobuy, cart.productsTobuy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productQuantity, finalPrice, finalWeight, productsTobuy);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "productQuantity=" + productQuantity +
                ", finalPrice=" + finalPrice +
                ", finalWeight=" + finalWeight +
                ", productsTobuy=" + productsTobuy +
                '}';
    }
}
