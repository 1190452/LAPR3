package lapr.project.model;

import java.util.Objects;

public class Delivery {
    private int id;
    private double necessaryEnergy;
    private double distance;
    private double weight;
    private int courierID;
    private int vehicleID;


    public Delivery(int id, double necessaryEnergy, double distance, double weight) {
        this.id = id;
        this.necessaryEnergy = necessaryEnergy;
        this.distance = distance;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", necessaryEnergy=" + necessaryEnergy +
                ", distance=" + distance +
                ", weight=" + weight +
                ", courierID=" + courierID +
                ", vehicleID=" + vehicleID +
                '}';
    }

    public Delivery(double necessaryEnergy, double distance, double weight, int courierID, int vehicleID){
        this.necessaryEnergy = necessaryEnergy;
        this.distance = distance;
        this.weight = weight;
        this.courierID = courierID;
        this.vehicleID = vehicleID;
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

    public int getCourierID() {
        return courierID;
    }

    public void setCourierID(int courierID) {
        this.courierID = courierID;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }
}
