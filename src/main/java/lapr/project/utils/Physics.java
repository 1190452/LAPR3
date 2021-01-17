package lapr.project.utils;

public class Physics {
    private static final double CONSTANT_AVERAGE_VELOCITY = 5; //m/s
    private static final double AERODYNAMIC_COEFFICIENT_SCOOTER = 1.8;
    private static final double AERODYNAMIC_COEFFICIENT_DRONE = 0.04;
    private static final double AIR_DENSITY = 1.2041; //to a temperature of 20 degrees
    private static final double ROAD_ROLLING_RESISTANCE = 0.0020;
    private static final double DRONE_WEIGHT = 1.5;
    private static final double ELECTRIC_SCOOTER_WEIGHT = 80;
    private static final double GRAVITATIONAL_ACCELERATION = 9.80665;
    private static final double EARTH_RADIUS = 6371;



    /*public static double getNecessaryEnergy(double distance, double totalWeight) {
        double frictionalForce = getFrictionalForce(totalWeight);
        double totalPower = getTotalPower(frictionalForce);
        double timeSpent = getTimeSpent(distance);
        return totalPower * timeSpent;
    }*/

    public double getNecessaryEnergy(double distance, double weight, int typeVehicle,double frontalArea,double elevationInitial, double elevationFinal, double latitude1, double latitude2, double longitude1, double longitude2){
        double totalWeight;
        double dragForce;
        double totalPower;
        if(typeVehicle == 1) {
            totalWeight = ELECTRIC_SCOOTER_WEIGHT + weight;
            double roadSlopeForce = getRoadSlope(totalWeight,elevationInitial, elevationFinal, latitude1, latitude2, longitude1, longitude2);
            double getFrictionalForce = getRoadLoad(totalWeight,elevationInitial,elevationFinal, latitude1, latitude2, longitude1, longitude2);
            dragForce = getAerodynamicDragForce(frontalArea, typeVehicle);
            totalPower = (roadSlopeForce + getFrictionalForce + dragForce) * CONSTANT_AVERAGE_VELOCITY;
        }else {
            double impulseForce = getDroneImpulse(weight);
            dragForce = getAerodynamicDragForce(frontalArea, typeVehicle);
            totalPower = (dragForce + impulseForce) * CONSTANT_AVERAGE_VELOCITY;
        }
        return totalPower * getTimeSpent(distance);
    }

    public double getTimeSpent(double distance){
        return distance/(CONSTANT_AVERAGE_VELOCITY*3600);
    }

    public double getAerodynamicDragForce(double frontalArea, int typeVehicle) {
        if(typeVehicle == 1)
            return 0.5 * AIR_DENSITY * AERODYNAMIC_COEFFICIENT_SCOOTER * frontalArea * Math.pow(CONSTANT_AVERAGE_VELOCITY, 2);
        else
            return 0.5 * AIR_DENSITY * AERODYNAMIC_COEFFICIENT_DRONE * frontalArea * Math.pow(CONSTANT_AVERAGE_VELOCITY, 2);

    }

    public double getRoadSlope(double totalWeight,double elevationInitial, double elevationFinal, double latitude1, double latitude2, double longitude1, double longitude2) {
        return totalWeight * GRAVITATIONAL_ACCELERATION * Math.sin(calculatePathInclination(elevationInitial, elevationFinal, latitude1, latitude2, longitude1, longitude2));
    }

    public double getRoadLoad(double totalWeight, double elevationInitial, double elevationFinal, double latitude1, double latitude2, double longitude1, double longitude2) {
            return totalWeight * GRAVITATIONAL_ACCELERATION * ROAD_ROLLING_RESISTANCE * Math.cos(calculatePathInclination(elevationInitial, elevationFinal, latitude1, latitude2, longitude1, longitude2));
    }

    public double calculatePathInclination(double elevationInicial, double elevationFinal, double latitude1, double latitude2, double longitude1, double longitude2) {
        double distance = linearDistanceTo(Math.toRadians(latitude1), Math.toRadians(latitude2), Math.toRadians(longitude1), Math.toRadians(longitude2));
        return (elevationFinal - elevationInicial) / distance;
    }

    public double getDroneImpulse(double weight) {
        return (DRONE_WEIGHT + weight) * GRAVITATIONAL_ACCELERATION * (150-10);
    }

    /**
     * Calculate calculateDistanceWithElevation between two points in latitude
     * and longitude taking into account height difference. If you are not
     * interested in height difference pass 0.0. Uses Haversine method as its
     * base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     *
     * @returns Distance in Meters
     */
    public static double calculateDistanceWithElevation(double lat1, double lat2, double lon1,
                                                        double lon2, double el1, double el2) {

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    /**
     * Get the linear calculateDistanceWithElevation from one location to
     * another.
     *
     * @param lat1 Origin latitude in Decimal Degrees.
     * @param lat2 Origin longitude in Decimal Degrees.
     * @param long1 Destiny latitude in Decimal Degrees.
     * @param long2 Destiny longitude in Decimal Degrees.
     * @return Returns the calculateDistanceWithElevation in meters from one
     * location to another.
     */
    public static double linearDistanceTo(double lat1, double lat2, double long1, double long2) {
        return calculateDistanceWithElevation(lat1, lat2, long1, long2, 0, 0); //m
    }

}
