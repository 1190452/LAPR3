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
    private double ah_battery;
    private double v_battery;
    private double weight;
    private int idPharmacy;
    private int typeVehicle;

    public Vehicle(int id,String licensePlate, double maxBattery, double actualBattery, int status, int isCharging, double enginePower, double ah_battery, double v_battery, double weight, int idPharmacy, int typeVehicle) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.maxBattery = maxBattery;
        this.actualBattery = actualBattery;
        this.status = status;
        this.isCharging = isCharging;
        this.enginePower = enginePower;
        this.ah_battery = ah_battery;
        this.v_battery = v_battery;
        this.weight = weight;
        this.idPharmacy = idPharmacy;
        this.typeVehicle = typeVehicle;
    }

    public Vehicle(String licensePlate, double maxBattery, double actualBattery, double enginePower, double ah_battery, double v_battery, double weight, int idPharmacy, int typeVehicle) {
        this.licensePlate = licensePlate;
        this.maxBattery = maxBattery;
        this.actualBattery = actualBattery;
        this.enginePower = enginePower;
        this.ah_battery = ah_battery;
        this.v_battery = v_battery;
        this.weight = weight;
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

    public double getAh_battery() {
        return ah_battery;
    }

    public double getV_battery() {
        return v_battery;
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

    public void setAh_battery(double ah_battery) {
        this.ah_battery = ah_battery;
    }

    public void setV_battery(double v_battery) {
        this.v_battery = v_battery;
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
                ", licensePlate=" + licensePlate +
                ", maxBattery=" + maxBattery +
                ", actualBattery=" + actualBattery +
                ", status=" + status +
                ", isCharging=" + isCharging +
                ", enginePower=" + enginePower +
                ", ah_battery=" + ah_battery +
                ", v_battery=" + v_battery +
                ", weight=" + weight +
                ", idPharmacy=" + idPharmacy +
                ", typeVehicle=" + typeVehicle +
                '}';
    }


}
