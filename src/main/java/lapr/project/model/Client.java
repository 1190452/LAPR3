package lapr.project.model;

import java.util.Objects;

public class Client extends User{

    private String name;
    private String email;
    private int NIF;
    private String password;
    private int numCredits;
    private Address address;
    private String role;

    public Client(String name, String email, int NIF, Address address, String password, String role) {
        this.name = name;
        this.email = email;
        this.NIF = NIF;
        this.address = address;
        this.password = password;
        this.numCredits = 0;
        this.role = "CLIENT";
    }

    public Client(String name, String email, String street, double latitude, double longitude, String password) {
        this.name = name;
        this.email = email;
        this.address = new Address(latitude,longitude,street);
        this.password = password;
    }

    public Client(String name, String email, String street, double latitude, double longitude, int NIF, String password) {
        this.name = name;
        this.email = email;
        this.address = new Address(latitude,longitude,street);
        this.password = password;
        this.NIF = NIF;
    }

    public Client(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.numCredits = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return email.equals(client.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNIF() {
        return NIF;
    }

    public void setNIF(int NIF) {
        this.NIF = NIF;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setNumCredits(int numCredits) {
        this.numCredits = numCredits;
    }

    public int getNumCredits() {
        return numCredits;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", NIF=" + NIF +
                ", numCredits=" + numCredits +
                ", address=" + address +
                '}';
    }
}
