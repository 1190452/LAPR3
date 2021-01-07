package lapr.project.model;

import java.util.Objects;

public class Courier extends User{
    private int idCourier;
    private String name;
    private int NIF;
    private String NSS;
    private double maxWeightCapacity;
    private double weight;

    public Courier(String email, String password, int idCourier, String name, int NIF, String NSS, double maxWeightCapacity, double weight) {
        super(email, password, "COURIER");
        this.idCourier = idCourier;
        this.name = name;
        this.NIF = NIF;
        this.NSS = NSS;
        this.maxWeightCapacity = maxWeightCapacity;
        this.weight = weight;
    }

    public Courier(String email, String password, String name, int NIF, String NSS, double maxWeightCapacity, double weight) {
        super(email, password, "COURIER");
        this.name = name;
        this.NIF = NIF;
        this.NSS = NSS;
        this.maxWeightCapacity = maxWeightCapacity;
        this.weight = weight;
    }


    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getIdCourier() {
        return idCourier;
    }

    public double getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public int getNIF() {
        return NIF;
    }

    public String getNSS() {
        return NSS;
    }

    public double getMaxWeightCapacity() {
        return maxWeightCapacity;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setNIF(int NIF) {
        this.NIF = NIF;
    }

    public void setNSS(String NSS) {
        this.NSS = NSS;
    }

    public void setMaxWeightCapacity(double maxWeightCapacity) {
        this.maxWeightCapacity = maxWeightCapacity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Courier)) return false;
        Courier courier = (Courier) o;
        return NIF == courier.NIF &&
                Double.compare(courier.maxWeightCapacity, maxWeightCapacity) == 0 &&
                Objects.equals(name, courier.name) &&
                Objects.equals(NSS, courier.NSS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, NIF, NSS, maxWeightCapacity);
    }

    @Override
    public String toString() {
        return "Courier{" +
                "name='" + name + '\'' +
                ", NIF=" + NIF +
                ", socialSecurityNumber='" + NSS + '\'' +
                ", maxWeightCapacity=" + maxWeightCapacity +
                '}';
    }
}
