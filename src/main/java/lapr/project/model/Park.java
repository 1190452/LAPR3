package lapr.project.model;

import java.util.Objects;

public class Park {

    private int id;
    private int maxCapacity;
    private int actualCapacity;
    private int maxChargingPlaces;
    private int actualChargingPlaces;
    private double power;
    private int pharmacyID;
    private int idParktype;

    /**
     * Constructor Park with parameters
     * @param id park id
     * @param maxCapacity maximum parking places
     * @param actualCapacity occupied parking spaces
     * @param maxChargingPlaces maximum charging parking places
     * @param actualChargingPlaces occupied charging parking spaces
     * @param power park power charging capacity
     * @param pharmacyID pharmacy id to which the park belongs
     * @param idParktype park type id (1(electric scooters) or 2(drones))
     */
    public Park(int id, int maxCapacity, int actualCapacity, int maxChargingPlaces, int actualChargingPlaces, double power, int pharmacyID, int idParktype) {
        this.id = id;
        this.maxCapacity = maxCapacity;
        this.actualCapacity = actualCapacity;
        this.maxChargingPlaces = maxChargingPlaces;
        this.actualChargingPlaces = actualChargingPlaces;
        this.power = power;
        this.pharmacyID = pharmacyID;
        this.idParktype = idParktype;
    }

    public Park(int maxCapacity, int maxChargingPlaces, double power, int pharmacyID, int idParktype) {
        this.maxCapacity = maxCapacity;
        this.maxChargingPlaces = maxChargingPlaces;
        this.power = power;
        this.pharmacyID = pharmacyID;
        this.idParktype = idParktype;
    }

    /**
     *
     * @return the pharmacy id
     */
    public int getPharmacyID() {
        return pharmacyID;
    }

    /**
     *
     * @param pharmacyID the pharmacy id to set
     */
    public void setPharmacyID(int pharmacyID) {
        this.pharmacyID = pharmacyID;
    }

    /**
     *
     * @return the park id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id the park id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the max capacity
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     *
     * @param maxCapacity the max capacity to set
     */
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    /**
     *
     * @return the actual capacity
     */
    public int getActualCapacity() {
        return actualCapacity;
    }

    /**
     *
     * @param actualCapacity the actual capacity to set
     */
    public void setActualCapacity(int actualCapacity) {
        this.actualCapacity = actualCapacity;
    }

    /**
     *
     * @return the max charging places
     */
    public int getMaxChargingPlaces() {
        return maxChargingPlaces;
    }

    /**
     *
     * @param maxChargingPlaces the max charging places to set
     */
    public void setMaxChargingPlaces(int maxChargingPlaces) {
        this.maxChargingPlaces = maxChargingPlaces;
    }

    /**
     *
     * @return the actual changing places
     */
    public int getActualChargingPlaces() {
        return actualChargingPlaces;
    }

    /**
     *
     * @param actualChargingPlaces the actual charging places to set
     */
    public void setActualChargingPlaces(int actualChargingPlaces) {
        this.actualChargingPlaces = actualChargingPlaces;
    }

    /**
     *
     * @return the power
     */
    public double getPower() {
        return power;
    }

    /**
     *
     * @param power the power to set
     */
    public void setPower(double power) {
        this.power = power;
    }

    /**
     *
     * @return the park type id
     */
    public int getIdParktype() {
        return idParktype;
    }

    /**
     *
     * @param idParktype the park type id to set
     */
    public void setIdParktype(int idParktype) {
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
                ", ParktypeID=" + idParktype+
                '}';
    }

}
