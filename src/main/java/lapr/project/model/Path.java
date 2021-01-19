package lapr.project.model;

public class Path {
    private Address a1;
    private Address a2;
    private double road_rolling_resistance;
    private double windspeed;
    private double windDirection;


    public Path(Address a1, Address a2, double road_rolling_resistance, double windspeed, double windDirection) {
        this.a1 = a1;
        this.a2 = a2;
        this.road_rolling_resistance = road_rolling_resistance;
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

    public double getRoad_rolling_resistance() {
        return road_rolling_resistance;
    }

    public void setRoad_rolling_resistance(double road_rolling_resistance) {
        this.road_rolling_resistance = road_rolling_resistance;
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
                ", road_rolling_resistance=" + road_rolling_resistance +
                ", windspeed=" + windspeed +
                ", windDirection=" + windDirection +
                '}';
    }
}

