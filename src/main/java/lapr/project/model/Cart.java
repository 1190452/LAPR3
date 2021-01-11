package lapr.project.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {
    private double finalPrice;
    private double finalWeight;
    private List<AuxProduct> productsTobuy = new ArrayList<>();

    public Cart(double finalPrice, double finalWeight, List<AuxProduct> productsTobuy) {
        this.finalPrice = finalPrice;
        this.finalWeight = finalWeight;
        this.productsTobuy = new ArrayList<>(productsTobuy);
    }

    public Cart(){}

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

    public List<AuxProduct> getProductsTobuy() {
        return new ArrayList<>(productsTobuy);
    }

    public void setProductsTobuy(List<AuxProduct> productsTobuy) {
        if(productsTobuy == null)
            throw new IllegalArgumentException("The list productsToBuy is null");
        this.productsTobuy.clear();
        this.productsTobuy.addAll(productsTobuy);
    }

    public void updateAddCart(Product product, int stock) {
        double finalPrice = this.getFinalPrice();
        finalPrice += product.getPrice() * stock;
        setFinalPrice(finalPrice);

        double weight = this.getFinalWeight();
        weight += product.getWeight() * stock;
        setFinalWeight(weight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;
        Cart cart = (Cart) o;
        return Double.compare(cart.finalPrice, finalPrice) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(finalPrice);
    }

    @Override
    public String toString() {
        return "Cart{" +
                ", finalPrice=" + finalPrice +
                ", finalWeight=" + finalWeight +
                ", productsTobuy=" + productsTobuy +
                '}';
    }

    public void updateRemoveCart(AuxProduct u) {
        int stockRemove = u.getStock();

        double finalPrice = this.getFinalPrice();
        finalPrice = finalPrice - (u.getProduct().getPrice() * stockRemove);
        setFinalPrice(finalPrice);

        double weight = this.getFinalWeight();
        weight = weight -  (u.getProduct().getWeight() * stockRemove);
        setFinalWeight(weight);
    }

    public static class AuxProduct{
        private Product product;
        private int stock;

        public AuxProduct(Product product, int stock){
            this.product = product;
            this.stock = stock;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }


        @Override
        public String toString() {
            return "AuxProduct{" +
                    "product=" + product +
                    ", stock=" + stock +
                    '}';
        }

    }
}
