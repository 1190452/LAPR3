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


    public Client(String name, String email, String pwd, int nif, double latitude, double longitude, BigDecimal creditCardNumber) {
        super(email, pwd, "CLIENT");
        this.name = name;
        this.email = email;
        this.nif = nif;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = 0;
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
        this.nif = nif;
        this.numCredits = 0;
        this.latitude = latitude;
        this.longitude = longitude;
        this.creditCardNumber = creditCardNumber;
        this.numCredits = numCredits;
        this.altitude = 0;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumCredits() {
        return numCredits;
    }

    public void setNumCredits(int numCredits) {
        this.numCredits = numCredits;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public BigDecimal getCreditCardNumber() {
        return creditCardNumber;
    }

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
