package lapr.project.model;

import java.util.Objects;

public class Scooter {

    private int id;
    private double maxBattery;
    private double actualBattery;
    private int status;
    private double enginePower;
    private double ah_battery;
    private double v_battery;
    private double weight;
    private int idPharmacy;


    public Scooter(int id, double maxBattery, double actualBattery, int status, double enginePower, double ah_battery, double v_battery, double weight, int idPharmacy) {
        this.id = id;
        this.maxBattery = maxBattery;
        this.actualBattery = actualBattery;
        this.status = status;
        this.enginePower = enginePower;
        this.ah_battery = ah_battery;
        this.v_battery = v_battery;
        this.weight = weight;
        this.idPharmacy = idPharmacy;
    }

    public Scooter(int id, double maxBattery, double actualBattery, double enginePower, double ah_battery, double v_battery, double weight, int idPharmacy) {
        this.id = id;
        this.maxBattery = maxBattery;
        this.actualBattery = actualBattery;
        this.enginePower = enginePower;
        this.ah_battery = ah_battery;
        this.v_battery = v_battery;
        this.weight = weight;
        this.idPharmacy = idPharmacy;
        this.status = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getStatus() {
        return status;
    }

    public int getIdPharmacy() {
        return idPharmacy;
    }

    public void setIdPharmacy(int idPharmacy) {
        this.idPharmacy = idPharmacy;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(double enginePower) {
        this.enginePower = enginePower;
    }

    public double getAh_battery() {
        return ah_battery;
    }

    public void setAh_battery(double ah_battery) {
        this.ah_battery = ah_battery;
    }

    public double getV_battery() {
        return v_battery;
    }

    public void setV_battery(double v_battery) {
        this.v_battery = v_battery;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public long getBatteryPercentage() {
        return Math.round((this.getActualBattery() / this.getMaxBattery() * 100.0));
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
