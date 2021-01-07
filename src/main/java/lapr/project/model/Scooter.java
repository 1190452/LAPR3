package lapr.project.model;

import java.util.Objects;

public class EletricScooter {

    private int id;
    private double maxBattery;
    private double actualBattery;
    private int status;
    private int idPharmacy;

    public EletricScooter(int id, double maxBattery, double actualBattery, int status, int idPharmacy) {
        this.id = id;
        this.maxBattery = maxBattery;
        this.actualBattery = actualBattery;
        this.status = status;
        this.idPharmacy = idPharmacy;
    }


    public EletricScooter(int id, double maxBattery, double actualBattery,int idPharmacy) {
        this.id = id;
        this.maxBattery = maxBattery;
        this.actualBattery = actualBattery;
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

    public long getBatteryPercentage() {
        return Math.round((this.getActualBattery() / this.getMaxBattery() * 100.0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EletricScooter scooter = (EletricScooter) o;
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
