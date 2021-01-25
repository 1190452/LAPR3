package lapr.project.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Courier extends User{

    private int idCourier;
    private String name;
    private int nif;
    private BigDecimal nss;
    private double maxWeightCapacity;
    private double weight;
    private int pharmacyID;
    private String email;

    private static final String ROLE = "COURIER";

    /**
     * Constructor Courier with parameters
     * @param idCourier courier id
     * @param email courier email
     * @param name courier name
     * @param nif courier tax identification number
     * @param nss courier social security number
     * @param maxWeightCapacity maximum weight capacity of the courier backpack
     * @param weight courier weight
     * @param pharmacyID pharmacy id for which the courier works
     */
    public Courier(int idCourier, String email,String name, int nif, BigDecimal nss, double maxWeightCapacity, double weight, int pharmacyID) {
        super(email, ROLE);
        this.idCourier = idCourier;
        this.email = email;
        this.name = name;
        this.nif = nif;
        this.nss = nss;
        this.maxWeightCapacity = maxWeightCapacity;
        this.weight = weight;
        this.pharmacyID = pharmacyID;
    }

    public Courier(int idCourier, String name) {
        super("", "");
        this.idCourier = idCourier;
        this.name = name;
    }


    public Courier(String email, String password, String role, String name, int nif, BigDecimal nss, double weight, int pharmacyID) {
        super(email, password, role);
        this.name = name;
        this.nif = nif;
        this.nss = nss;
        this.weight = weight;
        this.pharmacyID = pharmacyID;
    }


    public Courier(String email, String name, int nif, BigDecimal nss, double maxWeightCapacity, double weight, int pharmacyID){
        super(email, ROLE);
        this.email = email;
        this.name = name;
        this.nif = nif;
        this.nss = nss;
        this.maxWeightCapacity = maxWeightCapacity;
        this.weight = weight;
        this.pharmacyID = pharmacyID;
    }

    public Courier(String email, String name, int idPharmacy){
        super(email, ROLE);
        this.email = email;
        this.name = name;
        this.pharmacyID = idPharmacy;
    }

    public Courier(String email, String name, int nif, BigDecimal nss, double weight, int pharmacyID) {
        super(email, ROLE);
        this.email = email;
        this.name = name;
        this.nif = nif;
        this.nss = nss;
        this.weight = weight;
        this.pharmacyID = pharmacyID;

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
     * @param weight the weight to set
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     *
     * @return the courier id
     */
    public int getIdCourier() {
        return idCourier;
    }

    /**
     *
     * @param idCourier the courier id to set
     */
    public void setIdCourier(int idCourier) {
        this.idCourier = idCourier;
    }

    /**
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the nif
     */
    public int getNif() {
        return nif;
    }

    /**
     *
     * @param nif the nif to set
     */
    public void setNif(int nif) {
        this.nif = nif;
    }

    /**
     *
     * @return the nss
     */
    public BigDecimal getNss() {
        return nss;
    }

    /**
     *
     * @param nss the nss to set
     */
    public void setNss(BigDecimal nss) {
        this.nss = nss;
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
     * @return the courier email
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email the courier email to set
     */
    @Override
    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public String toString() {
        return "Courier{" +
                "idCourier=" + idCourier +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", NIF=" + nif +
                ", NSS=" + nss +
                ", maxWeightCapacity=" + maxWeightCapacity +
                ", weight=" + weight +
                ", pharmacyID=" + pharmacyID +
                '}';
    }

}
