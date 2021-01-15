package lapr.project.utils;

public class Physics {
    private static final double CONSTANT_GRAVITY=9.80665;
    private static final double CONSTANT_DYNAMIC_FRICTION_COEFFICIENT = 0.80;
    private static final double CONSTANT_AVERAGE_VELOCITY = 5; //m/s
    private static final double AERODYNAMIC_COEFFICIENT = 0.065;
    private static final double AIR_DENSITY = 1.225; //to a temperature of 15 degrees
    //private static final double DRONE_WEIGHT = 1.5;
    //private static final double ELECTRIC_SCOOTER_WEIGHT = 80;


    public double getNecessaryEnergy(double distance, double totalWeight){
        double frictionalForce = getFrictionalForce(totalWeight);
        double totalPower = getTotalPower(frictionalForce);
        double timeSpent = getTimeSpent(distance);
        return totalPower*timeSpent;
    }

    public double getTimeSpent(double distance){
        return distance/(CONSTANT_AVERAGE_VELOCITY*3600);
    }

    public double getTotalPower(double frictionalForce) {
        return frictionalForce*CONSTANT_AVERAGE_VELOCITY;
    }

    public double getFrictionalForce(double totalWeight){
        return CONSTANT_DYNAMIC_FRICTION_COEFFICIENT*totalWeight*CONSTANT_GRAVITY;
    }

    public double getAerodynamicDragForce() {
        return 0;
    }


}
