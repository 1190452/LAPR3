package lapr.project.model;

import java.util.Objects;

public class Courier extends User{
    private String name;
    private String email;
    private int NIF;
    private String socialSecurityNumber;
    private double maxWeightCapacity;
    private String password;
    private String role;

    public Courier(String name, String email, int NIF, String socialSecurityNumber, double maxWeightCapacity, String password) {
        this.name = name;
        this.email = email;
        this.NIF = NIF;
        this.socialSecurityNumber = socialSecurityNumber;
        this.maxWeightCapacity = maxWeightCapacity;
        this.password = password;
        this.role = "COURIER";
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getNIF() {
        return NIF;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public double getMaxWeightCapacity() {
        return maxWeightCapacity;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNIF(int NIF) {
        this.NIF = NIF;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public void setMaxWeightCapacity(double maxWeightCapacity) {
        this.maxWeightCapacity = maxWeightCapacity;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Courier)) return false;
        Courier courier = (Courier) o;
        return NIF == courier.NIF &&
                Double.compare(courier.maxWeightCapacity, maxWeightCapacity) == 0 &&
                Objects.equals(name, courier.name) &&
                Objects.equals(email, courier.email) &&
                Objects.equals(socialSecurityNumber, courier.socialSecurityNumber) &&
                Objects.equals(password, courier.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, NIF, socialSecurityNumber, maxWeightCapacity, password);
    }

    @Override
    public String toString() {
        return "Courier{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", NIF=" + NIF +
                ", socialSecurityNumber='" + socialSecurityNumber + '\'' +
                ", maxWeightCapacity=" + maxWeightCapacity +
                ", password='" + password + '\'' +
                '}';
    }
}
