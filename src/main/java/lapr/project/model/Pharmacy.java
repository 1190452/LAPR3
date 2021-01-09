package lapr.project.model;

import lapr.project.data.PharmacyDataHandler;

import java.util.Objects;

public class Pharmacy {
    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private String emailAdministrator;

    public Pharmacy(String name, double latitude, double longitude, String emailAdministrator) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.emailAdministrator = emailAdministrator;
    }

    public Pharmacy(int id, String name, double latitude, double longitude, String emailAdministrator) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "Pharmacy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", emailAdministrator='" + emailAdministrator + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pharmacy pharmacy = (Pharmacy) o;
        return id == pharmacy.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void save() {
        try {
            getPharmacy(this.name);
        } catch (IllegalArgumentException ex) {
            //Of the record does not exist, save it
            new PharmacyDataHandler().addPharmacy(this);
        }
    }

    public static Pharmacy getPharmacy(String name) {
        return new PharmacyDataHandler().getPharmacy(name);
    }
}
