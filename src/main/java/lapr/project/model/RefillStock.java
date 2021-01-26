package lapr.project.model;

public class RefillStock {

    private int idRefillStock;
    private double necessaryEnergyRestock;
    private double distanceRestock;
    private double weightRestock;
    private int status;
    private int courierID;
    private String licensePlate;

    /**
     * Constructor RefillStock with parameters
     * @param id refill stock id
     * @param necessaryEnergyRestock the necessary energy to do the restock delivery
     * @param distanceRestock refill stock delivery distance
     * @param weightRestock refill stock delivery weight (all orders weight)
     * @param status refill stock delivery status (1(done) or 0(undone))
     * @param courierID couriers id that will make the refill stock delivery
     * @param licensePlate license plate of the vehicle that will make the refill stock delivery
     */
    public RefillStock(int id, double necessaryEnergyRestock, double distanceRestock, double weightRestock, int status, int courierID, String licensePlate) {
        this.idRefillStock = id;
        this.necessaryEnergyRestock = necessaryEnergyRestock;
        this.distanceRestock = distanceRestock;
        this.weightRestock = weightRestock;
        this.status = status;
        this.courierID = courierID;
        this.licensePlate = licensePlate;
    }

    public RefillStock(double necessaryEnergyRestock, double distanceRestock, double weightRestock, int courierID, String licensePlate) {
        this.necessaryEnergyRestock = necessaryEnergyRestock;
        this.distanceRestock = distanceRestock;
        this.weightRestock = weightRestock;
        this.courierID = courierID;
        this.licensePlate = licensePlate;
    }

    /**
     *
     * @return the refill stock id
     */
    public int getIdRefillStock() {
        return idRefillStock;
    }

    /**
     *
     * @param idRefillStock the refill stock id to set
     */
    public void setIdRefillStock(int idRefillStock) {
        this.idRefillStock = idRefillStock;
    }

    /**
     *
     * @return the necessary energy
     */
    public double getNecessaryEnergyRestock() {
        return necessaryEnergyRestock;
    }

    /**
     *
     * @param necessaryEnergyRestock the necessary energy to set
     */
    public void setNecessaryEnergyRestock(double necessaryEnergyRestock) {
        this.necessaryEnergyRestock = necessaryEnergyRestock;
    }

    /**
     *
     * @return the distance
     */
    public double getDistanceRestock() {
        return distanceRestock;
    }

    /**
     *
     * @param distanceRestock the distance to set
     */
    public void setDistanceRestock(double distanceRestock) {
        this.distanceRestock = distanceRestock;
    }

    /**
     *
     * @return the weight
     */
    public double getWeightRestock() {
        return weightRestock;
    }

    /**
     *
     * @param weightRestock the weight to set
     */
    public void setWeightRestock(double weightRestock) {
        this.weightRestock = weightRestock;
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
                "id=" + idRefillStock +
                ", necessaryEnergy=" + necessaryEnergyRestock +
                ", distance=" + distanceRestock +
                ", weight=" + weightRestock +
                ", status=" + status +
                ", courierID=" + courierID +
                ", License Plate=" + licensePlate +
                '}';
    }

}
