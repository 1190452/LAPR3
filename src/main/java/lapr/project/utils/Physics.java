package lapr.project.utils;

public class Physics {
    private static final double AVERAGE_COURIER_WEIGHT = 70; //Kg
    private static final double CONSTANT_AVERAGE_VELOCITY = 5; //m/s
    private static final double CONSTANT_DRONE_IMPULSE_SPEED = 2; //m/s
    private static final double AERODYNAMIC_COEFFICIENT_SCOOTER = 1.8;
    private static final double AERODYNAMIC_COEFFICIENT_DRONE = 0.04;
    private static final double AIR_DENSITY = 1.2041; //to a temperature of 20 degrees
    private static final double DRONE_WEIGHT = 10; //Kg
    private static final double DRONE_WIDTH = 1; //m
    private static final double ELECTRIC_SCOOTER_WEIGHT = 126; //Kg
    private static final double GRAVITATIONAL_ACCELERATION = 9.80665;
    private static final double EARTH_RADIUS = 6371;


    public static double getNecessaryEnergy(double distanceWithElevation, double weight, int typeVehicle,double frontalArea,double elevationDifference, double windSpeed, double windDiretion, double roadRollingResistance, double linearDistance){
        double totalWeight;
        double dragForce;
        double totalPower;
        if(typeVehicle == 1) {
            totalWeight = ELECTRIC_SCOOTER_WEIGHT + AVERAGE_COURIER_WEIGHT + weight;
            double roadSlopeForce = getRoadSlope(totalWeight, distanceWithElevation, elevationDifference);
            double getFrictionalForce = getRoadLoad(totalWeight,distanceWithElevation, elevationDifference, roadRollingResistance);
            dragForce = getAerodynamicDragForce(typeVehicle, frontalArea, CONSTANT_AVERAGE_VELOCITY, windSpeed, windDiretion, 2);
            totalPower = (roadSlopeForce + getFrictionalForce + dragForce) * CONSTANT_AVERAGE_VELOCITY;
            return (totalPower * getTimeSpent(distanceWithElevation, CONSTANT_AVERAGE_VELOCITY))/3600000; //result in KWh
        }else {
            totalWeight = DRONE_WEIGHT + AVERAGE_COURIER_WEIGHT + weight;
            double landingAndImpulse = 2 * getDroneImpulse(totalWeight, frontalArea);
            double totalEnergyVertical = landingAndImpulse * getTimeSpent(140, CONSTANT_DRONE_IMPULSE_SPEED);
            double parasitePotency = getAerodynamicDragForce(typeVehicle, frontalArea, CONSTANT_AVERAGE_VELOCITY, windSpeed, windDiretion, 3);
            double liftForce = getLiftPotency(totalWeight, CONSTANT_AVERAGE_VELOCITY);
            double totalEnergyHorizontal = (parasitePotency + liftForce) * getTimeSpent(linearDistance, CONSTANT_AVERAGE_VELOCITY);
            return (totalEnergyVertical + totalEnergyHorizontal) / 3600000; //result in KWh
        }
    }

    public static double getLiftPotency(double totalWeight, double calculateSpeedWithWind) {
        double numerator = Math.pow(totalWeight, 2);
        double denominator = AIR_DENSITY * Math.pow(DRONE_WIDTH, 2) * calculateSpeedWithWind;
        return numerator/denominator;
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
        return distance/(averageVelocity);
    }

    public static double getAerodynamicDragForce(int typeVehicle, double frontalArea, double averageVelocity, double windSpeed, double windDirection, double exponent) {
        double speedWithWind;
        if (typeVehicle == 1){
            speedWithWind = calculateSpeedWithWind(averageVelocity, windSpeed, windDirection);
            return 0.5 * AIR_DENSITY * AERODYNAMIC_COEFFICIENT_SCOOTER * frontalArea * Math.pow(speedWithWind, exponent);
        }else{
            speedWithWind = calculateSpeedWithWind(averageVelocity, windSpeed, windDirection);
            return 0.5 * AIR_DENSITY * AERODYNAMIC_COEFFICIENT_DRONE * frontalArea * Math.pow(speedWithWind, exponent);
        }

    }

    private static double calculateSpeedWithWind(double averageVelocity, double windSpeed, double windDirection) {
        double windX = calculateWindX(windDirection, windSpeed);
        double windY = calculateWindY(windDirection, windSpeed);
        return Math.sqrt(Math.pow(averageVelocity-windX, 2) + Math.pow(windY, 2));
    }

    private static double calculateWindY(double windDirection, double windSpeed) {
        return windSpeed * Math.sin(Math.toRadians(windDirection));
    }

    private static double calculateWindX(double windDirection, double windSpeed) {
        return windSpeed * Math.cos(Math.toRadians(windDirection));
    }

    public static double getRoadSlope(double totalWeight, double distanceWithElevation, double elevationDifference) {
        return totalWeight * GRAVITATIONAL_ACCELERATION * Math.sin(calculatePathInclination(distanceWithElevation, elevationDifference));
    }

    public static double getRoadLoad(double totalWeight, double distanceWithElevation, double elevationDifference, double roadRollingResistance) {
            return totalWeight * GRAVITATIONAL_ACCELERATION * roadRollingResistance * Math.cos(calculatePathInclination(distanceWithElevation, elevationDifference));
    }

    public static double calculatePathInclination(double distanceWithElevation, double elevationDifference) {
        double angle = (elevationDifference/distanceWithElevation);
        return Math.asin(Math.toRadians(angle));
    }

    public static double getDroneImpulse(double weight, double frontalArea) {
        double thrust =Math.pow( weight * GRAVITATIONAL_ACCELERATION, 1.5);
        double denominator = Math.sqrt(2 * AIR_DENSITY * frontalArea);
        return thrust/denominator;
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
