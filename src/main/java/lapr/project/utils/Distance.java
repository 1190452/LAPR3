package lapr.project.utils;

public class Distance {

    public Distance() {
        throw new IllegalStateException("Utility class");
    }

    public static double distanceBetweenTwoAddresses(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371e3;
        double phi1 = lat1 * Math.PI / 180;
        double phi2 = lat2 * Math.PI / 180;
        double deltaPhi = (lat2 - lat1) * Math.PI / 180;
        double deltaLambda = (lon2 - lon1) * Math.PI / 180;

        double a = Math.sin(deltaPhi / 2) * Math.sin(deltaPhi / 2) + Math.cos(phi1) * Math.cos(phi2) *
                Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double d = (R * c) / 100;

        return Math.round(d * 10.0) / 10.0; //faz-se a conversão de metros para km
    }
}
