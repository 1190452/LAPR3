package lapr.project.model;

import java.util.Objects;

public class Delivery {

    private int id;
    private double necessaryEnergy;
    private double distance;
    private double weight;
    private int courierID;
    private String licensePlate;

    /**
     * Constructor Delivery with parameters
     * @param necessaryEnergy the necessary energy to do the delivery
     * @param distance delivery distance
     * @param weight delivery weight (all orders weight)
     * @param courierID couriers id that will make the delivery
     * @param licensePlate license plate of the vehicle that will make the delivery
     */
    public Delivery(double necessaryEnergy, double distance, double weight, int courierID,String licensePlate){
        this.necessaryEnergy = necessaryEnergy;
        this.distance = distance;
        this.weight = weight;
        this.courierID = courierID;
        this.licensePlate = licensePlate;
    }

    public Delivery(int id, double necessaryEnergy, double distance, double weight) {
        this.id = id;
        this.necessaryEnergy = necessaryEnergy;
        this.distance = distance;
        this.weight = weight;
    }


    /**
     *
     * @return the delivery id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id the delivery id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the necessary energy
     */
    public double getNecessaryEnergy() {
        return necessaryEnergy;
    }

    /**
     *
     * @param necessaryEnergy the necessary energy to set
     */
    public void setNecessaryEnergy(double necessaryEnergy) {
        this.necessaryEnergy = necessaryEnergy;
    }

    /**
     *
     * @return the distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     *
     * @param distance the distance to set
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     *
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     *
     * @param weight the weight to set
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     *
     * @return the courier id
     */
    public int getCourierID() {
        return courierID;
    }

    /**
     *
     * @param courierID the courier id to set
     */
    public void setCourierID(int courierID) {
        this.courierID = courierID;
    }

    /**
     *
     * @return the license plate
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     *
     * @param licensePlate the license plate to set
     */
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
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

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", necessaryEnergy=" + necessaryEnergy +
                ", distance=" + distance +
                ", weight=" + weight +
                ", courierID=" + courierID +
                ", licensePlate=" + licensePlate +
                '}';
    }

}
