package lapr.project.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Client extends User{

    private int idClient;
    private String name;
    private String email;
    private int nif;
    private int numCredits;
    private double latitude;
    private double longitude;
    private double altitude;
    private BigDecimal creditCardNumber;

    /**
     * Constructor Client with parameters
     * @param idClient client id
     * @param name client name
     * @param email client email
     * @param pwd client password
     * @param nif client tax identification number
     * @param latitude client address latitude
     * @param longitude client address longitude
     * @param altitude client address altitude
     * @param creditCardNumber client credit card number
     */
    public Client(int idClient, String name, String email, String pwd, int nif, double latitude, double longitude, double altitude,BigDecimal creditCardNumber) {
        super(email,pwd, "CLIENT");
        this.idClient = idClient;
        this.name = name;
        this.email = email;
        this.nif = nif;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.creditCardNumber = creditCardNumber;
        this.numCredits = 0;
    }

    public Client(String name, String email, String pwd, int nif, double latitude, double longitude,double altitude ,BigDecimal creditCardNumber) {
        super(email, pwd, "CLIENT");
        this.name = name;
        this.email = email;
        this.nif = nif;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.creditCardNumber = creditCardNumber;
        this.numCredits = 0;
    }

    public Client(String name, String email, String pwd,double latitude, double longitude) {
        super(email, pwd, "");
        this.name = name;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = 0;
    }

    public Client(String email, String role, int idClient, String name, int nif, double latitude, double longitude,double altitude ,BigDecimal creditCardNumber, int numCredits) {
        super(email, role);
        this.idClient = idClient;
        this.name = name;
        this.email = email;
        this.nif = nif;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.creditCardNumber = creditCardNumber;
        this.numCredits = numCredits;
    }

    /**
     *
     * @return the client id
     */
    public int getIdClient() {
        return idClient;
    }

    /**
     *
     * @param idClient the client to set
     */
    public void setIdClient(int idClient) {
        this.idClient = idClient;
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
     * @return the email
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email the email to set
     */
    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return the number of credits
     */
    public int getNumCredits() {
        return numCredits;
    }

    /**
     *
     * @param numCredits the number of credits to set
     */
    public void setNumCredits(int numCredits) {
        this.numCredits = numCredits;
    }

    /**
     *
     * @return the address latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude the address latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return the address longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude the address longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return the address altitude
     */
    public double getAltitude() {
        return altitude;
    }

    /**
     *
     * @param altitude the address altitude to set
     */
    public void setAltitude(double altitude) {
        this.altitude = altitude;
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
     * @return the credit card number
     */
    public BigDecimal getCreditCardNumber() {
        return creditCardNumber;
    }

    /**
     *
     * @param creditCardNumber the credit card number to set
     */
    public void setCreditCardNumber(BigDecimal creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return idClient == client.idClient;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClient);
    }

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", nif=" + nif +
                ", numCredits=" + numCredits +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

}
