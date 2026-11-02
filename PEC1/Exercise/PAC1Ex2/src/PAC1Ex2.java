    public class PAC1Ex2 {

        public static String[] STATION_NAMES = {"Central Station", "University", "Market Square",
                "North District", "Old Town", "Sports Centre"};
        public static int[] LOCKER_CAPACITIES = {40, 24, 60, 32, 48, 20};
        public static double COLLECTION_THRESHOLD = 0.80;

        public static int getLockerCapacity(int stationIndex) {
            // TODO: implementar según el enunciado.

            return LOCKER_CAPACITIES[stationIndex];
        }

        public static double calculateOccupancyRate(int packages, int stationIndex) {
            // TODO: implementar según el enunciado.

            int capacities = getLockerCapacity(stationIndex);

            return packages * 1.0 / capacities;
        }

        public static boolean needsPackageCollection(int packages, int stationIndex) {
            // TODO: implementar según el enunciado.

            double occupancyRate = calculateOccupancyRate(packages, stationIndex);

            if (occupancyRate >= COLLECTION_THRESHOLD) {
                return true;

            } else {
                return false;
            }
        }

        public static int calculateTotalPackages(int[] packagesPerStation) {
            // TODO: implementar según el enunciado.

            int total = 0;

            for (int i = 0; i < packagesPerStation.length; i++) {
                total = total + packagesPerStation[i];
            }

            return total;
        }

        public static int getBusiestStation(int[] packagesPerStation) {
            // TODO: implementar según el enunciado.

            int busiestStation = 0;
            double highestRate = calculateOccupancyRate(packagesPerStation[0], 0);

            for (int i = 1; i < packagesPerStation.length; i++) {
                double currentRate = calculateOccupancyRate(packagesPerStation[i], i);

                if (currentRate > highestRate) {
                    highestRate = currentRate;
                    busiestStation = i;
                }
            }

            return busiestStation;
        }

        public static void printLockerReport(int[] packagesPerStation) {
            // TODO: implementar según el enunciado.

            int totalPackages = calculateTotalPackages(packagesPerStation);
            int busiestStation = getBusiestStation(packagesPerStation);

            System.out.println("Total packages: " + totalPackages);
            System.out.println("Busiest station: " + STATION_NAMES[busiestStation]);
        }

        public static void main(String[] args) {
//            System.out.println(getLockerCapacity(0));
//            System.out.println(calculateOccupancyRate(0,0));
//            System.out.println(needsPackageCollection(1,1));
//
//            System.out.println("////////////////");
//
//            System.out.println(getLockerCapacity(2));
//            System.out.println(calculateOccupancyRate(48,2));
//            System.out.println(needsPackageCollection(48,1));

//            int[] packages = {1, 15, 20, 5, 8, 12};
//
//            System.out.println(calculateTotalPackages(packages));
//
//            System.out.println(getBusiestStation(packages));
//
//            printLockerReport(packages);
        }
    }
