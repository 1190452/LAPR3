package lapr.project.model;

import java.util.Objects;

public class Path {

    private double latitudeFrom;
    private double longitudeFrom;
    private double altitudeFrom;
    private double latitudeTo;
    private double longitudeTo;
    private double altitudeTo;
    private double roadRollingResistance;
    private double windspeed;
    private double windDirection;

    /**
     * Constructor Path with parameters
     * @param latitudeFrom departure address latitude
     * @param longitudeFrom departure address longitude
     * @param altitudeFrom departure address altitude
     * @param latitudeTo arrival address latitude
     * @param longitudeTo arrival address longitude
     * @param altitudeTo arrival address altitude
     * @param roadRollingResistance road rolling resistance
     * @param windspeed wind speed in m/s
     * @param windDirection wind direction in degrees
     */
    public Path(double latitudeFrom, double longitudeFrom, double altitudeFrom, double latitudeTo, double longitudeTo, double altitudeTo, double roadRollingResistance, double windspeed, double windDirection) {
        this.latitudeFrom = latitudeFrom;
        this.longitudeFrom = longitudeFrom;
        this.altitudeFrom = altitudeFrom;
        this.latitudeTo = latitudeTo;
        this.longitudeTo = longitudeTo;
        this.altitudeTo = altitudeTo;
        this.roadRollingResistance = roadRollingResistance;
        this.windspeed = windspeed;
        this.windDirection = windDirection;
    }

    /**
     *
     * @return the address latitude
     */
    public double getLatitudeFrom() {
        return latitudeFrom;
    }

    /**
     *
     * @param latitudeFrom the address latitude to set
     */
    public void setLatitudeFrom(double latitudeFrom) {
        this.latitudeFrom = latitudeFrom;
    }

    /**
     *
     * @return the address longitude
     */
    public double getLongitudeFrom() {
        return longitudeFrom;
    }

    /**
     *
     * @param longitudeFrom the address longitude to set
     */
    public void setLongitudeFrom(double longitudeFrom) {
        this.longitudeFrom = longitudeFrom;
    }

    /**
     *
     * @return the address altitude
     */
    public double getAltitudeFrom() {
        return altitudeFrom;
    }

    /**
     *
     * @param altitudeFrom the address altitude to set
     */
    public void setAltitudeFrom(double altitudeFrom) {
        this.altitudeFrom = altitudeFrom;
    }

    /**
     *
     * @return the address latitude
     */
    public double getLatitudeTo() {
        return latitudeTo;
    }

    /**
     *
     * @param latitudeTo the address latitude to set
     */
    public void setLatitudeTo(double latitudeTo) {
        this.latitudeTo = latitudeTo;
    }

    /**
     *
     * @return the address longitude
     */
    public double getLongitudeTo() {
        return longitudeTo;
    }

    /**
     *
     * @param longitudeTo the address longitude to set
     */
    public void setLongitudeTo(double longitudeTo) {
        this.longitudeTo = longitudeTo;
    }

    /**
     *
     * @return the address altitude
     */
    public double getAltitudeTo() {
        return altitudeTo;
    }

    /**
     *
     * @param altitudeTo the address altitude to set
     */
    public void setAltitudeTo(double altitudeTo) {
        this.altitudeTo = altitudeTo;
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
        return Double.compare(path.latitudeFrom, latitudeFrom) == 0 &&
                Double.compare(path.longitudeFrom, longitudeFrom) == 0 &&
                Double.compare(path.altitudeFrom, altitudeFrom) == 0 &&
                Double.compare(path.latitudeTo, latitudeTo) == 0 &&
                Double.compare(path.longitudeTo, longitudeTo) == 0 &&
                Double.compare(path.altitudeTo, altitudeTo) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitudeFrom, longitudeFrom, altitudeFrom, latitudeTo, longitudeTo, altitudeTo, roadRollingResistance, windspeed, windDirection);
    }

    @Override
    public String toString() {
        return "Path{" +
                "latitude_a1=" + latitudeFrom +
                ", longitude_a1=" + longitudeFrom +
                ", altitude_a1=" + altitudeFrom +
                ", latitude_a2=" + latitudeTo +
                ", longitude_a2=" + longitudeTo +
                ", altitude_a2=" + altitudeTo +
                ", roadRollingResistance=" + roadRollingResistance +
                ", windspeed=" + windspeed +
                ", windDirection=" + windDirection +
                '}';
    }

}

