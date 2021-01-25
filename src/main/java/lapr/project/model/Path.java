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

    public double getLatitude_a1() {
        return latitude_a1;
    }

    public void setLatitude_a1(double latitude_a1) {
        this.latitude_a1 = latitude_a1;
    }

    public double getLongitude_a1() {
        return longitude_a1;
    }

    public void setLongitude_a1(double longitude_a1) {
        this.longitude_a1 = longitude_a1;
    }

    public double getAltitude_a1() {
        return altitude_a1;
    }

    public void setAltitude_a1(double altitude_a1) {
        this.altitude_a1 = altitude_a1;
    }

    public double getLatitude_a2() {
        return latitude_a2;
    }

    public void setLatitude_a2(double latitude_a2) {
        this.latitude_a2 = latitude_a2;
    }

    public double getLongitude_a2() {
        return longitude_a2;
    }

    public void setLongitude_a2(double longitude_a2) {
        this.longitude_a2 = longitude_a2;
    }

    public double getAltitude_a2() {
        return altitude_a2;
    }

    public void setAltitude_a2(double altitude_a2) {
        this.altitude_a2 = altitude_a2;
    }

    public double getRoadRollingResistance() {
        return roadRollingResistance;
    }

    public void setRoadRollingResistance(double roadRollingResistance) {
        this.roadRollingResistance = roadRollingResistance;
    }

    public double getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(double windspeed) {
        this.windspeed = windspeed;
    }

    public double getWindDirection() {
        return windDirection;
    }

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

