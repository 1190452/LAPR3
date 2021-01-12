package lapr.project.utils;

public class Physics {
    private static final double CONSTANT_GRAVITY=9.80665;
    private static final double CONSTANT_DYNAMIC_FRICTION_COEFFICIENT = 0.80;
    private static final double CONSTANT_AVERAGE_VELOCITY = 5; //m/s


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

}