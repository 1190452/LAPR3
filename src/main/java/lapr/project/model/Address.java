package lapr.project.model;

public class Address {

    private double latitude;
    private double longitude;
    private String street;

    public Address(double latitude, double longitude, String street) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.street = street;
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
}
