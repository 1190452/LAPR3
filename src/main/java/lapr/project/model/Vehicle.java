package lapr.project.model;

import java.util.Objects;

public class Vehicle {

    private int id;
    private final String licensePlate;
    private double maxBattery;
    private double actualBattery;
    private int status;
    private int isCharging;
    private double enginePower;
    private double ahBattery;
    private double vBattery;
    private double weight;
    private int idPharmacy;
    private int typeVehicle;
    private double maxWeightCapacity;
    private double frontalArea;

    /**
     * Constructor Vehicle with parameters
     * @param id vehicle id
     * @param licensePlate vehicle license plate
     * @param maxBattery vehicle maximum battery capacity
     * @param actualBattery vehicle current battery
     * @param status vehicle status (1(being used) or 0(parked))
     * @param isCharging vechicle charging status (1(being charged) or 0(not being charged))
     * @param enginePower vechicle engine power
     * @param ahBattery vehicle ampere/hour battery
     * @param vBattery vehicle voltage battery
     * @param weight vehicle weight
     * @param idPharmacy pharmacy id to which the vehicle belongs
     * @param typeVehicle vehicle type id (1(electric scooter) or 2(drone))
     * @param maxWeightCapacity drone maximum weight capacity to suport
     * @param frontalArea vehicle frontal area
     */
    public Vehicle(int id, String licensePlate, double maxBattery, double actualBattery, int status, int isCharging, double enginePower, double ahBattery, double vBattery, double weight, int idPharmacy, int typeVehicle, double maxWeightCapacity, double frontalArea) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.maxBattery = maxBattery;
        this.actualBattery = actualBattery;
        this.status = status;
        this.isCharging = isCharging;
        this.enginePower = enginePower;
        this.ahBattery = ahBattery;
        this.vBattery = vBattery;
        this.weight = weight;
        this.idPharmacy = idPharmacy;
        this.typeVehicle = typeVehicle;
        this.maxWeightCapacity = maxWeightCapacity;
        this.frontalArea = frontalArea;
    }

    public Vehicle(int id, String licensePlate, double maxBattery, double enginePower, double ahBattery, double vBattery, double weight, int idPharmacy, int typeVehicle) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.maxBattery = maxBattery;
        this.enginePower = enginePower;
        this.ahBattery = ahBattery;
        this.vBattery = vBattery;
        this.weight = weight;
        this.idPharmacy = idPharmacy;
        this.typeVehicle = typeVehicle;
    }

    public Vehicle(String licensePlate, double maxBattery, double actualBattery, double enginePower, double ahBattery, double vBattery, double weight, int idPharmacy, int typeVehicle, double maxWeightCapacity) {
        this.licensePlate = licensePlate;
        this.maxBattery = maxBattery;
        this.actualBattery = actualBattery;
        this.enginePower = enginePower;
        this.ahBattery = ahBattery;
        this.vBattery = vBattery;
        this.weight = weight;
        this.idPharmacy = idPharmacy;
        this.typeVehicle = typeVehicle;
        this.maxWeightCapacity = maxWeightCapacity;
    }

    public Vehicle(String licensePlate, double maxBattery, double enginePower, double ahBattery, double vBattery, int idPharmacy, int typeVehicle) {
        this.licensePlate = licensePlate;
        this.maxBattery = maxBattery;
        this.enginePower = enginePower;
        this.ahBattery = ahBattery;
        this.vBattery = vBattery;
        this.idPharmacy = idPharmacy;
        this.typeVehicle = typeVehicle;
    }

    public Vehicle(String licensePlate) {
        this.licensePlate = licensePlate;
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
     * @return the max battery
     */
    public double getMaxBattery() {
        return maxBattery;
    }

    /**
     *
     * @return the current battery
     */
    public double getActualBattery() {
        return actualBattery;
    }

    /**
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     *
     * @return the charging status
     */
    public int getIsCharging() {
        return isCharging;
    }

    /**
     *
     * @return the engine power
     */
    public double getEnginePower() {
        return enginePower;
    }

    /**
     *
     * @return the ah battery
     */
    public double getAhBattery() {
        return ahBattery;
    }

    /**
     *
     * @return the v battery
     */
    public double getvBattery() {
        return vBattery;
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
     * @return the pharmacy id
     */
    public int getIdPharmacy() {
        return idPharmacy;
    }

    /**
     *
     * @return the type vehicle id
     */
    public int getTypeVehicle() {
        return typeVehicle;
    }

    /**
     *
     * @param maxBattery the max battery to set
     */
    public void setMaxBattery(double maxBattery) {
        this.maxBattery = maxBattery;
    }

    /**
     *
     * @param actualBattery the actual battery to set
     */
    public void setActualBattery(double actualBattery) {
        this.actualBattery = actualBattery;
    }

    /**
     *
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     *
     * @param isCharging the charging status to set
     */
    public void setIsCharging(int isCharging) {
        this.isCharging = isCharging;
    }

    /**
     *
     * @param enginePower the engine power to set
     */
    public void setEnginePower(double enginePower) {
        this.enginePower = enginePower;
    }

    /**
     *
     * @param ahBattery the ah battery to set
     */
    public void setAhBattery(double ahBattery) {
        this.ahBattery = ahBattery;
    }

    /**
     *
     * @param vBattery the v battery to set
     */
    public void setvBattery(double vBattery) {
        this.vBattery = vBattery;
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
     * @param idPharmacy the pharmacy id to set
     */
    public void setIdPharmacy(int idPharmacy) {
        this.idPharmacy = idPharmacy;
    }

    /**
     *
     * @param typeVehicle the type vehicle id to set
     */
    public void setTypeVehicle(int typeVehicle) {
        this.typeVehicle = typeVehicle;
    }

    /**
     *
     * @return the vehicle id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id the vehicle id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the battery percentage
     */
    public long getBatteryPercentage() {
        return Math.round((this.getActualBattery() / this.getMaxBattery() * 100.0));
    }

    /**
     *
     * @return vehicle frontal area
     */
    public double getFrontalArea() {
        return frontalArea;
    }

    /**
     *
     * @param frontalArea the frontal area to set
     */
    public void setFrontalArea(double frontalArea) {
        this.frontalArea = frontalArea;
    }

    /**
     *
     * @return the max weight capacity
     */
    public double getMaxWeightCapacity() {
        return maxWeightCapacity;
    }

    /**
     *
     * @param maxWeightCapacity the max weight capacity to set
     */
    public void setMaxWeightCapacity(double maxWeightCapacity) {
        this.maxWeightCapacity = maxWeightCapacity;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return licensePlate.equals((vehicle.licensePlate));
    }

    @Override
    public int hashCode() {
        return Objects.hash(licensePlate);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", licensePlate='" + licensePlate + '\'' +
                ", maxBattery=" + maxBattery +
                ", actualBattery=" + actualBattery +
                ", status=" + status +
                ", isCharging=" + isCharging +
                ", enginePower=" + enginePower +
                ", ah_battery=" + ahBattery +
                ", v_battery=" + vBattery +
                ", weight=" + weight +
                ", idPharmacy=" + idPharmacy +
                ", typeVehicle=" + typeVehicle +
                ", maxWeightCapacity=" + maxWeightCapacity +
                ", frontalArea=" + frontalArea +
                '}';
    }

}
