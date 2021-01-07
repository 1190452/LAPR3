package lapr.project.model;

import lapr.project.data.AddressDB;

import java.util.Objects;

public class Address {

    private double latitude;
    private double longitude;
    private String street;

    public Address(double latitude, double longitude, String street) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.street = street;
    }

    public Address(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Double.compare(address.latitude, latitude) == 0 && Double.compare(address.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public String toString() {
        if(street == null) {
            return "Address{" +
                    "latitude=" + latitude +
                    ", longitude=" + longitude +
                    ", street='" + "NOT SPECIFIED" + '\'' +
                    '}';
        }else {
            return "Address{" +
                    "latitude=" + latitude +
                    ", longitude=" + longitude +
                    ", street='" + street + '\'' +
                    '}';
        }

    }

    public void save() {
        new AddressDB().addAddress(this);
    }
}
