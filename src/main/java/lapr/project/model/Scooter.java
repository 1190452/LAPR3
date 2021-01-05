package lapr.project.model;

import java.util.Objects;

public class Scooter {

    private String id;
    private double maxBattery;
    private double actualBattery;

    public Scooter(String id, double maxBattery, double actualBattery) {
        this.id = id;
        this.maxBattery = maxBattery;
        this.actualBattery = actualBattery;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getMaxBattery() {
        return maxBattery;
    }

    public void setMaxBattery(double maxBattery) {
        this.maxBattery = maxBattery;
    }

    public double getActualBattery() {
        return actualBattery;
    }

    public void setActualBattery(double actualBattery) {
        this.actualBattery = actualBattery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scooter scooter = (Scooter) o;
        return Objects.equals(id, scooter.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Scooter{" +
                "id='" + id + '\'' +
                ", maxBattery=" + maxBattery +
                ", actualBattery=" + actualBattery +
                '}';
    }
}
