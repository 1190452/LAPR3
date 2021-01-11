package lapr.project.model;

import lapr.project.data.DataHandler;
import lapr.project.data.DeliveryHandler;
import lapr.project.data.InvoiceHandler;

import java.util.Objects;

public class Delivery {
    private int id;
    private double necessaryEnergy;
    private double distance;
    private double weight;

    public Delivery(int id, double necessaryEnergy, double distance, double weight) {
        this.id = id;
        this.necessaryEnergy = necessaryEnergy;
        this.distance = distance;
        this.weight = weight;
    }

    public Delivery(double necessaryEnergy, double distance, double weight){
        this.necessaryEnergy = necessaryEnergy;
        this.distance = distance;
        this.weight = weight;
    }


    @Override
    public String toString() {
        return "Delivery{" +
                "id='" + id + '\'' +
                ", necessaryEnergy=" + necessaryEnergy +
                ", distance=" + distance +
                ", weight=" + weight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return Double.compare(delivery.necessaryEnergy, necessaryEnergy) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, necessaryEnergy, distance, weight);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getNecessaryEnergy() {
        return necessaryEnergy;
    }

    public void setNecessaryEnergy(double necessaryEnergy) {
        this.necessaryEnergy = necessaryEnergy;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void save() {
        try {
            getDelivery(this.id);
        } catch (IllegalArgumentException ex) {
            //Of the record does not exist, save it
            new DeliveryHandler().addDelivery(this);
        }
    }

    public static Delivery getDelivery(int id) {
        return new DeliveryHandler().getDelivery(id);
    }
}
