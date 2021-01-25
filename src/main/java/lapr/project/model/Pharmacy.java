package lapr.project.model;

import java.util.Objects;

public class Pharmacy {

    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private double altitude;
    private String emailAdministrator;
    private String email;

    /**
     * Constructor Pharmacy with parameters
     * @param id pharmacy id
     * @param name pharmacy name
     * @param email pharmacy email
     * @param latitude address latitude
     * @param longitude address longitude
     * @param altitude address altitude
     * @param emailAdministrator pharmacy administrator email
     */
    public Pharmacy(int id, String name,String email, double latitude, double longitude,double altitude, String emailAdministrator) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
        this.emailAdministrator = emailAdministrator;
        this.altitude = altitude;
    }

    public Pharmacy(String name, String email, double latitude, double longitude,double altitude,String emailAdministrator) {
        this.name = name;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
        this.emailAdministrator = emailAdministrator;
        this.altitude = altitude;
    }

    /**
     *
     * @return the pharamcy id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id the pharmacy id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the address latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude the address latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return the address longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude the address longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return the address altitude
     */
    public double getAltitude() {
        return altitude;
    }

    /**
     *
     * @param altitude the address altitude to set
     */
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    /**
     *
     * @return the aministrator email
     */
    public String getEmailAdministrator() {
        return emailAdministrator;
    }

    /**
     *
     * @param emailAdministrator the administrator email to set
     */
    public void setEmailAdministrator(String emailAdministrator) {
        this.emailAdministrator = emailAdministrator;
    }

    /**
     *
     * @return the pharmacy email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email the pharamcy email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pharmacy)) return false;
        Pharmacy pharmacy = (Pharmacy) o;
        return id == pharmacy.id ;
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
                ", altitude=" + altitude +
                ", emailAdministrator='" + emailAdministrator + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
