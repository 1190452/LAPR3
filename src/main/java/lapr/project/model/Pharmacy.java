package lapr.project.model;

import java.util.Objects;

public class Pharmacy {
    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private String emailAdministrator;
    private String email;

    public Pharmacy(String name, String email, double latitude, double longitude, String emailAdministrator) {
        this.name = name;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
        this.emailAdministrator = emailAdministrator;
    }

    public Pharmacy(int id, String name,String email, double latitude, double longitude, String emailAdministrator) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
        this.emailAdministrator = emailAdministrator;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEmailAdministrator() {
        return emailAdministrator;
    }

    public void setEmailAdministrator(String emailAdministrator) {
        this.emailAdministrator = emailAdministrator;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pharmacy)) return false;
        Pharmacy pharmacy = (Pharmacy) o;
        return id == pharmacy.id &&
                Double.compare(pharmacy.latitude, latitude) == 0 &&
                Double.compare(pharmacy.longitude, longitude) == 0 &&
                Objects.equals(name, pharmacy.name) &&
                Objects.equals(emailAdministrator, pharmacy.emailAdministrator) &&
                Objects.equals(email, pharmacy.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, latitude, longitude, emailAdministrator, email);
    }

    @Override
    public String toString() {
        return "Pharmacy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", emailAdministrator='" + emailAdministrator + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
