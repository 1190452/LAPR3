package lapr.project.model;

import lapr.project.data.ScooterHandler;

import java.sql.SQLException;
import java.util.Objects;

public class EletricScooter {

    private int id;
    private double maxBattery;
    private double actualBattery;
    private int status;
    private double enginePower;
    private double ah_battery;
    private double v_battery;
    private double weight;
    private int idPharmacy;

    public EletricScooter(int id) {
        this.id = id;
    }

    public EletricScooter(int id, double maxBattery, double actualBattery, int status, double enginePower, double ah_battery, double v_battery, double weight, int idPharmacy) {
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

    public EletricScooter(double maxBattery, double actualBattery, double ah_battery, double v_battery, double enginePower, double weight, int idPharmacy) {
        this.maxBattery = maxBattery;
        this.actualBattery = actualBattery;
        this.enginePower = enginePower;
        this.ah_battery = ah_battery;
        this.v_battery = v_battery;
        this.weight = weight;
        this.idPharmacy = idPharmacy;
    }

    public int getId() {
        return id;
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




    public void save() throws SQLException {
            new ScooterHandler().addScooter(this);
    }

    public void delete(){
        new ScooterHandler().removeEletricScooter(this.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EletricScooter)) return false;
        EletricScooter that = (EletricScooter) o;
        return id == that.id &&
                Double.compare(that.maxBattery, maxBattery) == 0 &&
                Double.compare(that.actualBattery, actualBattery) == 0 &&
                status == that.status &&
                Double.compare(that.enginePower, enginePower) == 0 &&
                Double.compare(that.ah_battery, ah_battery) == 0 &&
                Double.compare(that.v_battery, v_battery) == 0 &&
                Double.compare(that.weight, weight) == 0 &&
                idPharmacy == that.idPharmacy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, maxBattery, actualBattery, status, enginePower, ah_battery, v_battery, weight, idPharmacy);
    }

    @Override
    public String toString() {
        return "EletricScooter{" +
                "id=" + id +
                ", maxBattery=" + maxBattery +
                ", actualBattery=" + actualBattery +
                ", status=" + status +
                ", enginePower=" + enginePower +
                ", ah_battery=" + ah_battery +
                ", v_battery=" + v_battery +
                ", weight=" + weight +
                ", idPharmacy=" + idPharmacy +
                '}';
    }
}
