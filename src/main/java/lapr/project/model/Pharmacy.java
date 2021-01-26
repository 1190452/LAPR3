package lapr.project.model;

import java.util.Objects;

public class Pharmacy {

    private int id;
    private String name;
    private double latitudePharmacy;
    private double longitudePharmacy;
    private double altitudePharmacy;
    private String emailAdministrator;
    private String email;

    /**
     * Constructor Pharmacy with parameters
     * @param id pharmacy id
     * @param name pharmacy name
     * @param email pharmacy email
     * @param latitudePharmacy address latitude
     * @param longitude address longitude
     * @param altitude address altitude
     * @param emailAdministrator pharmacy administrator email
     */
    public Pharmacy(int id, String name, String email, double latitudePharmacy, double longitude, double altitude, String emailAdministrator) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.latitudePharmacy = latitudePharmacy;
        this.longitudePharmacy = longitude;
        this.emailAdministrator = emailAdministrator;
        this.altitudePharmacy = altitude;
    }

    public Pharmacy(String name, String email, double latitudePharmacy, double longitude, double altitude, String emailAdministrator) {
        this.name = name;
        this.email = email;
        this.latitudePharmacy = latitudePharmacy;
        this.longitudePharmacy = longitude;
        this.emailAdministrator = emailAdministrator;
        this.altitudePharmacy = altitude;
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
    public double getLatitudePharmacy() {
        return latitudePharmacy;
    }

    /**
     *
     * @param latitudePharmacy the address latitude to set
     */
    public void setLatitudePharmacy(double latitudePharmacy) {
        this.latitudePharmacy = latitudePharmacy;
    }

    /**
     *
     * @return the address longitude
     */
    public double getLongitudePharmacy() {
        return longitudePharmacy;
    }

    /**
     *
     * @param longitudePharmacy the address longitude to set
     */
    public void setLongitudePharmacy(double longitudePharmacy) {
        this.longitudePharmacy = longitudePharmacy;
    }

    /**
     *
     * @return the address altitude
     */
    public double getAltitudePharmacy() {
        return altitudePharmacy;
    }

    /**
     *
     * @param altitudePharmacy the address altitude to set
     */
    public void setAltitudePharmacy(double altitudePharmacy) {
        this.altitudePharmacy = altitudePharmacy;
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
        return Objects.hash(id, name, latitudePharmacy, longitudePharmacy, emailAdministrator, email);
    }

    @Override
    public String toString() {
        return "Pharmacy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitudePharmacy +
                ", longitude=" + longitudePharmacy +
                ", altitude=" + altitudePharmacy +
                ", emailAdministrator='" + emailAdministrator + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
