package lapr.project.model;

import lapr.project.data.ParkHandler;

import java.util.Objects;

public class Park {
    private int id;
    private int maxCapacity;
    private int actualCapacity;
    private int maxChargingPlaces;
    private int actualChargingPlaces;
    private int power;
    private int pharmacyID;
    private int idParktype;

    public Park(int id, int maxCapacity, int actualCapacity, int maxChargingPlaces, int actualChargingPlaces, int power, int pharmacyID, int idParktype) {
        this.id = id;
        this.maxCapacity = maxCapacity;
        this.actualCapacity = actualCapacity;
        this.maxChargingPlaces = maxChargingPlaces;
        this.actualChargingPlaces = actualChargingPlaces;
        this.power = power;
        this.pharmacyID = pharmacyID;
        this.idParktype = idParktype;
    }

    public Park(int maxCapacity, int maxChargingPlaces, int power, int pharmacyID, int idParktype) {
        this.maxCapacity = maxCapacity;
        this.maxChargingPlaces = maxChargingPlaces;
        this.power = power;
        this.pharmacyID = pharmacyID;
        this.idParktype = idParktype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Park)) return false;
        Park park = (Park) o;
        return id == park.id &&
                pharmacyID == park.pharmacyID;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, pharmacyID);
    }

    public int getPharmacyID() {
        return pharmacyID;
    }

    public void setPharmacyID(int pharmacyID) {
        this.pharmacyID = pharmacyID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getIdParktype() {
        return idParktype;
    }

    public void setIdParktype(int idParktype) {
        this.idParktype = idParktype;
    }

    @Override
    public String toString() {
        return "Park{" +
                "id=" + id +
                ", maxCapacity=" + maxCapacity +
                ", actualCapacity=" + actualCapacity +
                ", maxChargingPlaces=" + maxChargingPlaces +
                ", actualChargingPlaces=" + actualChargingPlaces +
                ", power=" + power +
                ", pharmacyID=" + pharmacyID +
                '}';
    }

}
