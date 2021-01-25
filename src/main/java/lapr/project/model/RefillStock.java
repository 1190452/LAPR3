package lapr.project.model;

public class RefillStock {

    private int id;
    private double necessaryEnergy;
    private double distance;
    private double weight;
    private int status;
    private int courierID;
    private String licensePlate;

    /**
     * Constructor RefillStock with parameters
     * @param id refill stock id
     * @param necessaryEnergy the necessary energy to do the restock delivery
     * @param distance refill stock delivery distance
     * @param weight refill stock delivery weight (all orders weight)
     * @param status refill stock delivery status (1(done) or 0(undone))
     * @param courierID couriers id that will make the refill stock delivery
     * @param licensePlate license plate of the vehicle that will make the refill stock delivery
     */
    public RefillStock(int id, double necessaryEnergy, double distance, double weight, int status, int courierID, String licensePlate) {
        this.id = id;
        this.necessaryEnergy = necessaryEnergy;
        this.distance = distance;
        this.weight = weight;
        this.status = status;
        this.courierID = courierID;
        this.licensePlate = licensePlate;
    }

    public RefillStock(double necessaryEnergy, double distance, double weight, int courierID, String licensePlate) {
        this.necessaryEnergy = necessaryEnergy;
        this.distance = distance;
        this.weight = weight;
        this.courierID = courierID;
        this.licensePlate = licensePlate;
    }

    /**
     *
     * @return the refill stock id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id the refill stock id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the necessary energy
     */
    public double getNecessaryEnergy() {
        return necessaryEnergy;
    }

    /**
     *
     * @param necessaryEnergy the necessary energy to set
     */
    public void setNecessaryEnergy(double necessaryEnergy) {
        this.necessaryEnergy = necessaryEnergy;
    }

    /**
     *
     * @return the distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     *
     * @param distance the distance to set
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     *
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     *
     * @param weight the weight to set
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     *
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     *
     * @return the courier id
     */
    public int getCourierID() {
        return courierID;
    }

    /**
     *
     * @param courierID the courier id to set
     */
    public void setCourierID(int courierID) {
        this.courierID = courierID;
    }

    /**
     *
     * @return the license plate
     */
    public String getlicensePlate() {
        return licensePlate;
    }

    /**
     *
     * @param licensePlate the license plate to set
     */
    public void setlicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
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
                ", License Plate=" + licensePlate +
                '}';
    }

}
