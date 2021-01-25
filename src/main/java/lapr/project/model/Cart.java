package lapr.project.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {

    private double finalPrice;
    private double finalWeight;
    private List<AuxProduct> productsTobuy = new ArrayList<>();

    /***
     * Constructor Cart with parameters
     * @param finalPrice cart final price
     * @param finalWeight cart final weight
     * @param productsTobuy cart products to buy
     */
    public Cart(double finalPrice, double finalWeight, List<AuxProduct> productsTobuy) {
        this.finalPrice = finalPrice;
        this.finalWeight = finalWeight;
        this.productsTobuy = new ArrayList<>(productsTobuy);
    }

    public Cart(){}

    /***
     *
     * @return the final price
     */
    public double getFinalPrice() {
        return finalPrice;
    }

    /***
     *
     * @param finalPrice the final price to set
     */
    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    /***
     *
     * @return the final weight
     */
    public double getFinalWeight() {
        return finalWeight;
    }

    /***
     *
     * @param finalWeight the final weight to set
     */
    public void setFinalWeight(double finalWeight) {
        this.finalWeight = finalWeight;
    }

    /***
     *
     * @return the list of products to buy
     */
    public List<AuxProduct> getProductsTobuy() {
        return new ArrayList<>(productsTobuy);
    }

    /***
     *
     * @param productsTobuy the list of products to set
     */
    public void setProductsTobuy(List<AuxProduct> productsTobuy) {
        if(productsTobuy == null)
            throw new IllegalArgumentException("The list productsToBuy is null");
        this.productsTobuy.clear();
        this.productsTobuy.addAll(productsTobuy);
    }

    /***
     * Calculates and set the final price and final weight of the product
     * @param product the cart product
     * @param stock the quantity of that product
     */
    public void updateAddCart(Product product, int stock) {
        double finalP = this.getFinalPrice();
        finalP += product.getPrice() * stock;
        setFinalPrice(finalP);

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

    /***
     * Removes a product from the cart
     * @param u the cart product to be removed
     */
    public void updateRemoveCart(AuxProduct u) {
        int stockRemove = u.getStock();

        double finalP = this.getFinalPrice();
        finalP = finalP - (u.getProduct().getPrice() * stockRemove);
        setFinalPrice(finalP);

        double weight = this.getFinalWeight();
        weight = weight -  (u.getProduct().getWeight() * stockRemove);
        setFinalWeight(weight);
    }

    public static class AuxProduct{

        private Product product;
        private int stock;

        /***
         * Constructor AuxProduct with parameters
         * @param product the product
         * @param stock the quantity of that product
         */
        public AuxProduct(Product product, int stock){
            this.product = product;
            this.stock = stock;
        }

        /**
         *
         * @return the product
         */
        public Product getProduct() {
            return product;
        }

        /**
         *
         * @param product the product to set
         */
        public void setProduct(Product product) {
            this.product = product;
        }

        /**
         *
         * @return the stock
         */
        public int getStock() {
            return stock;
        }

        /**
         *
         * @param stock the stock to set
         */
        public void setStock(int stock) {
            this.stock = stock;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AuxProduct that = (AuxProduct) o;
            return Objects.equals(product, that.product);
        }

        @Override
        public int hashCode() {
            return Objects.hash(product);
        }

        @Override
        public String toString() {
            return "Cart:\n" +
                    product +
                    " | Quantity: " + stock +
                    '\n';
        }

    }

}
