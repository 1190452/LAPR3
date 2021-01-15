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
        this.actualBattery = actualBattery;
        this.enginePower = enginePower;
        this.ahBattery = ahBattery;
        this.vBattery = vBattery;
        this.idPharmacy = idPharmacy;
        this.typeVehicle = typeVehicle;
    }

    public Vehicle(String licensePlate) {
        this.licensePlate = licensePlate;
    }


    public String getLicensePlate() {
        return licensePlate;
    }

    public double getMaxBattery() {
        return maxBattery;
    }

    public double getActualBattery() {
        return actualBattery;
    }

    public int getStatus() {
        return status;
    }

    public int getIsCharging() {
        return isCharging;
    }

    public double getEnginePower() {
        return enginePower;
    }

    public double getAhBattery() {
        return ahBattery;
    }

    public double getvBattery() {
        return vBattery;
    }

    public double getWeight() {
        return weight;
    }

    public int getIdPharmacy() {
        return idPharmacy;
    }

    public int getTypeVehicle() {
        return typeVehicle;
    }

    public void setMaxBattery(double maxBattery) {
        this.maxBattery = maxBattery;
    }

    public void setActualBattery(double actualBattery) {
        this.actualBattery = actualBattery;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setIsCharging(int isCharging) {
        this.isCharging = isCharging;
    }

    public void setEnginePower(double enginePower) {
        this.enginePower = enginePower;
    }

    public void setAhBattery(double ahBattery) {
        this.ahBattery = ahBattery;
    }

    public void setvBattery(double vBattery) {
        this.vBattery = vBattery;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setIdPharmacy(int idPharmacy) {
        this.idPharmacy = idPharmacy;
    }

    public void setTypeVehicle(int typeVehicle) {
        this.typeVehicle = typeVehicle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getBatteryPercentage() {
        return Math.round((this.getActualBattery() / this.getMaxBattery() * 100.0));
    }

    public double getFrontalArea() {
        return frontalArea;
    }

    public void setFrontalArea(double frontalArea) {
        this.frontalArea = frontalArea;
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




    public double getMaxWeightCapacity() {
        return maxWeightCapacity;
    }

    public void setMaxWeightCapacity(double maxWeightCapacity) {
        this.maxWeightCapacity = maxWeightCapacity;
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
