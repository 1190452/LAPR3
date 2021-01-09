package lapr.project.model;

import lapr.project.data.AddressDataHandler;

import java.util.Objects;

public class Address {

    private double latitude;
    private double longitude;
    private String street;
    private int doorNumber;
    private String zipCode;
    private String locality;


    public Address(double latitude, double longitude, String street, int doorNumber, String zipCode, String locality) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.street = street;
        this.doorNumber = doorNumber;
        this.zipCode = zipCode;
        this.locality = locality;
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



    public void save() {
        new AddressDataHandler().addAddress(this);
    }

    public int getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(int doorNumber) {
        this.doorNumber = doorNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Double.compare(address.latitude, latitude) == 0 &&
                Double.compare(address.longitude, longitude) == 0 &&
                doorNumber == address.doorNumber &&
                Objects.equals(street, address.street) &&
                Objects.equals(zipCode, address.zipCode) &&
                Objects.equals(locality, address.locality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, street, doorNumber, zipCode, locality);
    }

    @Override
    public String toString() {
        return "Address{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", street='" + street + '\'' +
                ", doorNumber=" + doorNumber +
                ", zipCode='" + zipCode + '\'' +
                ", locality='" + locality + '\'' +
                '}';
    }
}
