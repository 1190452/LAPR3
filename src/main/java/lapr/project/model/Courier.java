package lapr.project.model;

import lapr.project.data.CourierDataHandler;

import java.math.BigDecimal;
import java.util.Objects;

public class Courier extends User{
    private int idCourier;
    private String name;
    private int NIF;
    private BigDecimal NSS;
    private double maxWeightCapacity;
    private double weight;
    private int pharmacyID;
    private String email;

    public Courier(int idCourier, String email,String name, int NIF, BigDecimal NSS, double maxWeightCapacity, double weight, int pharmacyID) {
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

    public Courier(int idCourier, String name) {
        super("", "");
        this.idCourier = idCourier;
        this.email = email;
    }



    public Courier(String email, String name, int NIF, BigDecimal NSS, double maxWeightCapacity, double weight, int pharmacyID){
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

    public BigDecimal getNSS() {
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

    public void setNSS(BigDecimal NSS) {
        this.NSS = NSS;
    }

    public void setMaxWeightCapacity(double maxWeightCapacity) {
        this.maxWeightCapacity = maxWeightCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courier courier = (Courier) o;
        return idCourier == courier.idCourier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCourier);
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

    @Override
    public String toString() {
        return "Courier{" +
                "idCourier=" + idCourier +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", NIF=" + NIF +
                ", NSS=" + NSS +
                ", maxWeightCapacity=" + maxWeightCapacity +
                ", weight=" + weight +
                ", pharmacyID=" + pharmacyID +
                '}';
    }
}
