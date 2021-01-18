package lapr.project.model;

public class RefillStock {
    private int id;
    private double necessaryEnergy;
    private double distance;
    private double weight;
    private int status;
    private int courierID;
    private int vehicleID;


    public RefillStock(int id, double necessaryEnergy, double distance, double weight, int status, int courierID, int vehicleID) {
        this.id = id;
        this.necessaryEnergy = necessaryEnergy;
        this.distance = distance;
        this.weight = weight;
        this.status = status;
        this.courierID = courierID;
        this.vehicleID = vehicleID;
    }

    public RefillStock(double necessaryEnergy, double distance, double weight, int courierID, int vehicleID) {
        this.necessaryEnergy = necessaryEnergy;
        this.distance = distance;
        this.weight = weight;
        this.courierID = courierID;
        this.vehicleID = vehicleID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getNecessaryEnergy() {
        return necessaryEnergy;
    }

    public void setNecessaryEnergy(double necessaryEnergy) {
        this.necessaryEnergy = necessaryEnergy;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCourierID() {
        return courierID;
    }

    public void setCourierID(int courierID) {
        this.courierID = courierID;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    @Override
    public String toString() {
        return "RefillStock{" +
                "id=" + id +
                ", necessaryEnergy=" + necessaryEnergy +
                ", distance=" + distance +
                ", weight=" + weight +
                ", status=" + status +
                ", courierID=" + courierID +
                ", vehicleID=" + vehicleID +
                '}';
    }
}
