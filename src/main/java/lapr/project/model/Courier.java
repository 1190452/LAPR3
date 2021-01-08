package lapr.project.model;

import lapr.project.data.CourierDataHandler;

import java.util.Objects;

public class Courier extends User{
    private int idCourier;
    private String name;
    private int NIF;
    private double NSS;
    private double maxWeightCapacity;
    private double weight;
    private int pharmacyID;
    private String email;

    public Courier(int idCourier, String email,String name, int NIF, double NSS, double maxWeightCapacity, double weight, int pharmacyID) {
        super(email, "COURIER");
        this.idCourier = idCourier;
        this.email = email;
        this.name = name;
        this.NIF = NIF;
        this.NSS = NSS;
        this.maxWeightCapacity = maxWeightCapacity;
        this.weight = weight;
        this.pharmacyID = pharmacyID;
    }


    public Courier(String email, String name, int NIF, double NSS, double maxWeightCapacity, double weight, int pharmacyID){
        super(email, "COURIER");
        this.email = email;
        this.name = name;
        this.NIF = NIF;
        this.NSS = NSS;
        this.maxWeightCapacity = maxWeightCapacity;
        this.weight = weight;
        this.pharmacyID = pharmacyID;
    }

    public Courier(String email, String name, int idPharmacy) {
        super(email, "COURIER");
        this.email = email;
        this.name = name;
        this.pharmacyID = idPharmacy;
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

    public double getNSS() {
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

    public void setNSS(double NSS) {
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
                "id=" + idCourier +
                "name='" + name + '\'' +
                ", NIF=" + NIF +
                ", socialSecurityNumber='" + NSS + '\'' +
                ", maxWeightCapacity=" + maxWeightCapacity +
                '}';
    }

    public void save() {
        try {
            getCourier(this.NIF);
        } catch (IllegalArgumentException ex) {
            //Of the record does not exist, save it
            new CourierDataHandler().addCourier(this);
        }
    }

    public static Courier getCourier(int nif) {
        return new CourierDataHandler().getCourier(nif);
    }

    public void setIdCourier(int idCourier) {
        this.idCourier = idCourier;
    }

    public int getPharmacyID() {
        return pharmacyID;
    }

    public void setPharmacyID(int pharmacyID) {
        this.pharmacyID = pharmacyID;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }
}
