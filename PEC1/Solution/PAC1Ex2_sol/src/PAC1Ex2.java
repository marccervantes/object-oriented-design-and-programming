public class PAC1Ex2 {

    private static final String[] STATION_NAMES = {"Central Station", "University", "Market Square",
            "North District", "Old Town", "Sports Centre"};
    private static final int[] LOCKER_CAPACITIES = {40, 24, 60, 32, 48, 20};
    private static final double COLLECTION_THRESHOLD = 0.80;

    public static int getLockerCapacity(int stationIndex) {
        return LOCKER_CAPACITIES[stationIndex];
    }

    public static double calculateOccupancyRate(int packages, int stationIndex) {
        return (double) packages / getLockerCapacity(stationIndex);
    }

    public static boolean needsPackageCollection(int packages, int stationIndex) {
        return calculateOccupancyRate(packages, stationIndex) >= COLLECTION_THRESHOLD;
    }

    public static int calculateTotalPackages(int[] packagesPerStation) {
        int total = 0;
        for (int packages : packagesPerStation) {
            total += packages;
        }
        return total;
    }

    public static int getBusiestStation(int[] packagesPerStation) {
        int busiestStation = 0;
        double highestOccupancy = calculateOccupancyRate(packagesPerStation[0], 0);

        for (int i = 1; i < packagesPerStation.length; i++) {
            double occupancy = calculateOccupancyRate(packagesPerStation[i], i);
            if (occupancy > highestOccupancy) {
                highestOccupancy = occupancy;
                busiestStation = i;
            }
        }
        return busiestStation;
    }

    public static void printLockerReport(int[] packagesPerStation) {
        System.out.println("Total packages: " + calculateTotalPackages(packagesPerStation));
        System.out.println("Busiest station: " + STATION_NAMES[getBusiestStation(packagesPerStation)]);
    }
}
