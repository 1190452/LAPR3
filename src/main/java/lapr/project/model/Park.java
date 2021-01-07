package lapr.project.model;

import java.util.Objects;

public class Park {
    private String id;
    private int maxCapacity;
    private int actualCapacity;
    private int maxChargingPlaces;
    private int actualChargingPlaces;

    public Park(String id, int maxCapacity, int actualCapacity, int maxChargingPlaces, int actualChargingPlaces) {
        this.id = id;
        this.maxCapacity = maxCapacity;
        this.actualCapacity = actualCapacity;
        this.maxChargingPlaces = maxChargingPlaces;
        this.actualChargingPlaces = actualChargingPlaces;
    }

    @Override
    public String toString() {
        return "Park{" +
                "id='" + id + '\'' +
                ", maxCapacity=" + maxCapacity +
                ", actualCapacity=" + actualCapacity +
                ", maxChargingPlaces=" + maxChargingPlaces +
                ", actualChargingPlaces=" + actualChargingPlaces +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Park park = (Park) o;
        return maxCapacity == park.maxCapacity &&
                actualCapacity == park.actualCapacity &&
                maxChargingPlaces == park.maxChargingPlaces &&
                actualChargingPlaces == park.actualChargingPlaces &&
                Objects.equals(id, park.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, maxCapacity, actualCapacity, maxChargingPlaces, actualChargingPlaces);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getActualCapacity() {
        return actualCapacity;
    }

    public void setActualCapacity(int actualCapacity) {
        this.actualCapacity = actualCapacity;
    }

    public int getMaxChargingPlaces() {
        return maxChargingPlaces;
    }

    public void setMaxChargingPlaces(int maxChargingPlaces) {
        this.maxChargingPlaces = maxChargingPlaces;
    }

    public int getActualChargingPlaces() {
        return actualChargingPlaces;
    }

    public void setActualChargingPlaces(int actualChargingPlaces) {
        this.actualChargingPlaces = actualChargingPlaces;
    }
}
