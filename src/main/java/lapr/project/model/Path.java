package lapr.project.model;

import java.util.Objects;

public class Path {
    private Address a1;
    private Address a2;
    private double roadRollingResistance;
    private double windspeed;
    private double windDirection;


    public Path(Address a1, Address a2, double roadRollingResistance, double windspeed, double windDirection) {
        this.a1 = a1;
        this.a2 = a2;
        this.roadRollingResistance = roadRollingResistance;
        this.windspeed = windspeed;
        this.windDirection = windDirection;
    }

    public Address getA1() {
        return a1;
    }

    public void setA1(Address a1) {
        this.a1 = a1;
    }

    public Address getA2() {
        return a2;
    }

    public void setA2(Address a2) {
        this.a2 = a2;
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
    public String toString() {
        return "Path{" +
                "a1=" + a1 +
                ", a2=" + a2 +
                ", road_rolling_resistance=" + roadRollingResistance +
                ", windspeed=" + windspeed +
                ", windDirection=" + windDirection +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Path)) return false;
        Path path = (Path) o;
        return Objects.equals(a1, path.a1) &&
                Objects.equals(a2, path.a2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a1, a2);
    }
}

