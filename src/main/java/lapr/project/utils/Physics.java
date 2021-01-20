package lapr.project.utils;

public class Physics {

    private Physics() {
    }

    private static final double CONSTANT_AVERAGE_VELOCITY = 5; //m/s
    private static final double CONSTANT_DRONE_IMPULSE_SPEED = 2; //m/s
    private static final double AERODYNAMIC_COEFFICIENT_SCOOTER = 1.8;
    private static final double AERODYNAMIC_COEFFICIENT_DRONE = 0.04;
    private static final double DRONE_LIFT_TO_DRAG = 11.8;
    private static final double DRONE_POTENCY = 100; //W
    private static final double AIR_DENSITY = 1.2041; //to a temperature of 20 degrees
    private static final double DRONE_WEIGHT = 1.5; //Kg
    private static final double ELECTRIC_SCOOTER_WEIGHT = 80; //Kg
    private static final double GRAVITATIONAL_ACCELERATION = 9.80665;
    private static final double EARTH_RADIUS = 6371;


    public static double getNecessaryEnergy(double distanceWithElevation, double weight, int typeVehicle,double frontalArea,double elevationDifference, double windSpeed, double windDiretion, double roadRollingResistance){
        double totalWeight;
        double dragForce;
        double totalPower;
        double averageVelocity;
        if(typeVehicle == 1) {
            totalWeight = ELECTRIC_SCOOTER_WEIGHT + weight;
            averageVelocity = calculateAverageSpeedWithWindDirection(CONSTANT_AVERAGE_VELOCITY, windSpeed, windDiretion);
            double roadSlopeForce = getRoadSlope(totalWeight, distanceWithElevation, elevationDifference);
            double getFrictionalForce = getRoadLoad(totalWeight,distanceWithElevation, elevationDifference, roadRollingResistance);
            dragForce = getAerodynamicDragForce(frontalArea, typeVehicle, averageVelocity);
            totalPower = (roadSlopeForce + getFrictionalForce + dragForce) * averageVelocity;
            return (totalPower * getTimeSpent(distanceWithElevation, averageVelocity)) * 10E-3; //result in KWh
        }else {
            totalWeight = DRONE_WEIGHT + weight;
            double impulseForce = getDroneImpulse(totalWeight, frontalArea, CONSTANT_DRONE_IMPULSE_SPEED);
            double landingForce = getDroneImpulse(totalWeight, frontalArea, CONSTANT_DRONE_IMPULSE_SPEED);
            averageVelocity = calculateAverageSpeedWithWindDirection(CONSTANT_AVERAGE_VELOCITY, windSpeed, windDiretion);
            double horizontalForce = getHorizontalForce(distanceWithElevation, getHeadWindRatio(windSpeed, windDiretion, averageVelocity), weight, DRONE_LIFT_TO_DRAG, DRONE_POTENCY, averageVelocity);
            return (impulseForce + landingForce + horizontalForce)/3600000; //result in KWh
        }
    }

    public static double calculateAverageSpeedWithWindDirection(double averageVelocity, double windSpeed, double windDirection) {
        if (windDirection == 90 || windDirection == 270) {
            return averageVelocity;

        }else if( windDirection == 180) {
            return averageVelocity - windSpeed;

        }else if (windDirection == 360 || windDirection == 0) {
            return averageVelocity + windSpeed;

        } else if ((windDirection > 0 && windDirection < 90) || (windDirection > 270 && windDirection < 360)) {
            double windDirectionRad = Math.toRadians(windDirection);
            return averageVelocity + (windSpeed * Math.abs(Math.cos(windDirectionRad)));

        } else if ((windDirection > 90 && windDirection < 180) || (windDirection > 180 && windDirection < 270)){
            double windDirectionRad = Math.toRadians(windDirection);
            return averageVelocity - (windSpeed * Math.abs(Math.cos(windDirectionRad)));

        }else{
            return averageVelocity;
        }
    }

    public static double getTimeSpent(double distance, double averageVelocity){
        return distance/(averageVelocity * 3600);
    }

    public static double getAerodynamicDragForce(double frontalArea, int typeVehicle, double averageVelocity) {
        if(typeVehicle == 1)
            return 0.5 * AIR_DENSITY * AERODYNAMIC_COEFFICIENT_SCOOTER * frontalArea * Math.pow(averageVelocity, 2);
        else if (typeVehicle == 2)
            return 0.5 * AIR_DENSITY * AERODYNAMIC_COEFFICIENT_DRONE * frontalArea * Math.pow(averageVelocity, 2);
        else
            return 0;
    }

    public static double getRoadSlope(double totalWeight, double distanceWithElevation, double elevationDifference) {
        return totalWeight * GRAVITATIONAL_ACCELERATION * Math.sin(calculatePathInclination(distanceWithElevation, elevationDifference));
    }

    public static double getRoadLoad(double totalWeight, double distanceWithElevation, double elevationDifference, double roadRollingResistance) {
            return totalWeight * GRAVITATIONAL_ACCELERATION * roadRollingResistance * Math.cos(calculatePathInclination(distanceWithElevation, elevationDifference));
    }

    public static double calculatePathInclination(double distanceWithElevation, double elevationDifference) {
        double angle = (elevationDifference/distanceWithElevation);
       return Math.asin(angle);
    }

    public static double getDroneImpulse(double weight, double frontalArea, double averageVelocity) {
        return (weight * GRAVITATIONAL_ACCELERATION * (150-10)) +
                getAerodynamicDragForce(frontalArea, 2, averageVelocity);
    }

    public static double getHorizontalForce(double distance, double headwindRatio, double weight, double liftToDrag, double potency, double averageVelocity){
        double vratio = distance/(1-headwindRatio);
        double vmass = (weight + DRONE_WEIGHT)/(370 * liftToDrag);
        double vpot = potency/averageVelocity;
        return vratio * (vmass + vpot);
    }

    public static double getHeadWindRatio(double windSpeed, double windDirection, double averageVelocity){
        if((windDirection > 90 && windDirection <= 180) || (windDirection > 180 && windDirection < 270)){
            double windDirectionRad = Math.toRadians(windDirection);
            double wind = windSpeed * Math.abs(Math.cos(windDirectionRad));
            return wind/averageVelocity;
        }else
            return 0;
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
