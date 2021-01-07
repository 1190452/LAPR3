package lapr.project.model;


import lapr.project.data.ClientDataHandler;
import java.util.Objects;

public class Client extends User{
    private int idClient;
    private String name;
    private String email;
    private int nif;
    private int numCredits;
    private double latitude;
    private double longitude;
    private int creditCardNumber;

    public Client(int idClient, String name, String email, int nif, double latitude, double longitude, int creditCardNumber) {
        this.idClient = idClient;
        this.name = name;
        this.email = email;
        this.nif = nif;
        this.latitude = latitude;
        this.longitude = longitude;
        this.creditCardNumber = creditCardNumber;
        this.numCredits = 0;
    }

    public Client(String name, String email, int nif, double latitude, double longitude, int creditCardNumber) {
        this.name = name;
        this.email = email;
        this.nif = nif;
        this.latitude = latitude;
        this.longitude = longitude;
        this.creditCardNumber = creditCardNumber;
        this.numCredits = 0;
    }

    public Client(String name, String email, double latitude, double longitude) {
        this.name = name;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public int getnif() {
        return nif;
    }

    public void setnif(int nif) {
        this.nif = nif;
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

    public int getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(int creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return idClient == client.idClient &&
                nif == client.nif &&
                numCredits == client.numCredits &&
                Double.compare(client.latitude, latitude) == 0 &&
                Double.compare(client.longitude, longitude) == 0 &&
                creditCardNumber == client.creditCardNumber &&
                Objects.equals(name, client.name) &&
                Objects.equals(email, client.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClient, name, email, nif, numCredits, latitude, longitude, creditCardNumber);
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
                ", creditCardNumber=" + creditCardNumber +
                '}';
    }

    public void save() {
        try {
            getClient(this.nif);
        } catch (IllegalArgumentException ex) {
            //Of the record does not exist, save it
            new ClientDataHandler().addClient(this);
        }
    }

    public static Client getClient(int nif) {
        return new ClientDataHandler().getClient(nif);
    }
}
