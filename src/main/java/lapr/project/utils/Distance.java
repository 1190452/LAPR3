package lapr.project.utils;

public class Distance {

    public static final int EARTH_RADIUS = 6371;// Radius of the earth
    private Distance() {
        //Not called
    }

    public static double distanceBetweenTwoAddressesWithElevation(double lat1, double lon1, double lat2, double lon2, double h1, double h2) {
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c * 1000; // convert to meters

        double height = h1 - h2;

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
    public double linearDistanceTo(double lat1, double lat2, double long1, double long2) {
        return distanceBetweenTwoAddressesWithElevation(lat1, lat2, long1, long2, 0, 0); //m
    }
}
