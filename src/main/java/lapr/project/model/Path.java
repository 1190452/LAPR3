package lapr.project.model;

import java.util.Objects;

public class Path {

    private double latitude_a1;
    private double longitude_a1;
    private double altitude_a1;
    private double latitude_a2;
    private double longitude_a2;
    private double altitude_a2;
    private double roadRollingResistance;
    private double windspeed;
    private double windDirection;

    /**
     * Constructor Path with parameters
     * @param latitude_a1 departure address latitude
     * @param longitude_a1 departure address longitude
     * @param altitude_a1 departure address altitude
     * @param latitude_a2 arrival address latitude
     * @param longitude_a2 arrival address longitude
     * @param altitude_a2 arrival address altitude
     * @param roadRollingResistance road rolling resistance
     * @param windspeed wind speed in m/s
     * @param windDirection wind direction in degrees
     */
    public Path(double latitude_a1, double longitude_a1, double altitude_a1, double latitude_a2, double longitude_a2, double altitude_a2, double roadRollingResistance, double windspeed, double windDirection) {
        this.latitude_a1 = latitude_a1;
        this.longitude_a1 = longitude_a1;
        this.altitude_a1 = altitude_a1;
        this.latitude_a2 = latitude_a2;
        this.longitude_a2 = longitude_a2;
        this.altitude_a2 = altitude_a2;
        this.roadRollingResistance = roadRollingResistance;
        this.windspeed = windspeed;
        this.windDirection = windDirection;
    }

    /**
     *
     * @return the address latitude
     */
    public double getLatitude_a1() {
        return latitude_a1;
    }

    /**
     *
     * @param latitude_a1 the address latitude to set
     */
    public void setLatitude_a1(double latitude_a1) {
        this.latitude_a1 = latitude_a1;
    }

    /**
     *
     * @return the address longitude
     */
    public double getLongitude_a1() {
        return longitude_a1;
    }

    /**
     *
     * @param longitude_a1 the address longitude to set
     */
    public void setLongitude_a1(double longitude_a1) {
        this.longitude_a1 = longitude_a1;
    }

    /**
     *
     * @return the address altitude
     */
    public double getAltitude_a1() {
        return altitude_a1;
    }

    /**
     *
     * @param altitude_a1 the address altitude to set
     */
    public void setAltitude_a1(double altitude_a1) {
        this.altitude_a1 = altitude_a1;
    }

    /**
     *
     * @return the address latitude
     */
    public double getLatitude_a2() {
        return latitude_a2;
    }

    /**
     *
     * @param latitude_a2 the address latitude to set
     */
    public void setLatitude_a2(double latitude_a2) {
        this.latitude_a2 = latitude_a2;
    }

    /**
     *
     * @return the address longitude
     */
    public double getLongitude_a2() {
        return longitude_a2;
    }

    /**
     *
     * @param longitude_a2 the address longitude to set
     */
    public void setLongitude_a2(double longitude_a2) {
        this.longitude_a2 = longitude_a2;
    }

    /**
     *
     * @return the address altitude
     */
    public double getAltitude_a2() {
        return altitude_a2;
    }

    /**
     *
     * @param altitude_a2 the address altitude to set
     */
    public void setAltitude_a2(double altitude_a2) {
        this.altitude_a2 = altitude_a2;
    }

    /**
     *
     * @return the road rolling resistance
     */
    public double getRoadRollingResistance() {
        return roadRollingResistance;
    }

    /**
     *
     * @param roadRollingResistance the road rolling resistance to set
     */
    public void setRoadRollingResistance(double roadRollingResistance) {
        this.roadRollingResistance = roadRollingResistance;
    }

    /**
     *
     * @return the wind speed
     */
    public double getWindspeed() {
        return windspeed;
    }

    /**
     *
     * @param windspeed the wind speed to set
     */
    public void setWindspeed(double windspeed) {
        this.windspeed = windspeed;
    }

    /**
     *
     * @return the wind direction
     */
    public double getWindDirection() {
        return windDirection;
    }

    /**
     *
     * @param windDirection the wind direction to set
     */
    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Path)) return false;
        Path path = (Path) o;
        return Double.compare(path.latitude_a1, latitude_a1) == 0 &&
                Double.compare(path.longitude_a1, longitude_a1) == 0 &&
                Double.compare(path.altitude_a1, altitude_a1) == 0 &&
                Double.compare(path.latitude_a2, latitude_a2) == 0 &&
                Double.compare(path.longitude_a2, longitude_a2) == 0 &&
                Double.compare(path.altitude_a2, altitude_a2) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude_a1, longitude_a1, altitude_a1, latitude_a2, longitude_a2, altitude_a2, roadRollingResistance, windspeed, windDirection);
    }

    @Override
    public String toString() {
        return "Path{" +
                "latitude_a1=" + latitude_a1 +
                ", longitude_a1=" + longitude_a1 +
                ", altitude_a1=" + altitude_a1 +
                ", latitude_a2=" + latitude_a2 +
                ", longitude_a2=" + longitude_a2 +
                ", altitude_a2=" + altitude_a2 +
                ", roadRollingResistance=" + roadRollingResistance +
                ", windspeed=" + windspeed +
                ", windDirection=" + windDirection +
                '}';
    }

}

