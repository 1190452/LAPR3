package lapr.project.model;

import java.util.Objects;

public class Address {

    private double latitude;
    private double longitude;
    private String street;
    private int doorNumber;
    private String zipCode;
    private String locality;
    private double altitude;

    /***
     * Constructor Address with parameters
     * @param latitude address latitude
     * @param longitude address longitude
     * @param street address street name
     * @param doorNumber address door number
     * @param zipCode address zip code
     * @param locality address locality
     * @param altitude address altitude
     */
    public Address(double latitude, double longitude, String street, int doorNumber, String zipCode, String locality, double altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.street = street;
        this.doorNumber = doorNumber;
        this.zipCode = zipCode;
        this.locality = locality;
        this.altitude = altitude;
    }

    public Address(double latitude, double longitude, String street, int doorNumber, String zipCode, String locality) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.street = street;
        this.doorNumber = doorNumber;
        this.zipCode = zipCode;
        this.locality = locality;
    }

    /***
     *
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /***
     *
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /***
     *
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /***
     *
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /***
     *
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /***
     *
     * @param street the street to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /***
     *
     * @return the door number
     */
    public int getDoorNumber() {
        return doorNumber;
    }

    /***
     *
     * @param doorNumber the door number to set
     */
    public void setDoorNumber(int doorNumber) {
        this.doorNumber = doorNumber;
    }

    /***
     *
     * @return the zip code
     */
    public String getZipCode() {
        return zipCode;
    }

    /***
     *
     * @param zipCode the zip code to set
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /***
     *
     * @return the locality
     */
    public String getLocality() {
        return locality;
    }

    /***
     *
     * @param locality the locality to set
     */
    public void setLocality(String locality) {
        this.locality = locality;
    }

    /***
     *
     * @return the altitude
     */
    public double getAltitude() {
        return altitude;
    }

    /***
     *
     * @param altitude the altitude to set
     */
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Double.compare(address.latitude, latitude) == 0 &&
                Double.compare(address.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
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
                ", altitude='" + altitude + '\'' +
                '}';
    }

}
