public class PAC1Ex3 {
    public static final int AVAILABLE = 0;
    public static final int ASSIGNED = 1;
    public static final int RESTING = 2;
    public static final double BASE_PRICE = 25.0;
    public static final int IRRIGATED_ROWS = 2;
    public static final double IRRIGATION_SURCHARGE = 7.50;
    public static final int DISCOUNT_MIN_PLOTS = 4;
    public static final double GROUP_DISCOUNT = 0.15;

    public static boolean isValidPlot(int[][] garden, int row, int column) {
        // El cortocircuito evita acceder a una fila inexistente.
        return row >= 0 && row < garden.length
                && column >= 0 && column < garden[row].length;
    }

    public static int countAvailablePlots(int[][] garden, int row) {
        if (!isValidPlot(garden, row, 0)) {
            return -1;
        }
        int count = 0;
        for (int plot : garden[row]) {
            if (plot == AVAILABLE) {
                count++;
            }

        }
        return count;
    }

    public static boolean canAssignPlots(int[][] garden, int row, int startColumn, int plotCount) {
        // Restar el inicio al ancho evita sumar índices antes de validar el tamaño.
        if (!isValidPlot(garden, row, startColumn) || plotCount <= 0
                || plotCount > garden[row].length - startColumn) {
            return false;
        }
        for (int column = startColumn; column < startColumn + plotCount; column++) {
            if (garden[row][column] != AVAILABLE) {
                return false;
            }
        }
        return true;
    }

    public static int[] findPlotBlock(int[][] garden, int plotCount) {
        if (plotCount <= 0 || plotCount > garden[0].length) {
            return new int[]{-1, -1};
        }
        for (int row = 0; row < garden.length; row++) {
            // El recuento solo permite descartar filas, no confirma la continuidad.
            if (countAvailablePlots(garden, row) < plotCount) {
                continue;
            }
            for (int column = 0; column <= garden[row].length - plotCount; column++) {
                if (canAssignPlots(garden, row, column, plotCount)) {
                    return new int[]{row, column};
                }
            }
        }
        return new int[]{-1, -1};
    }

    public static double calculateLeasePrice(int[][] garden, int plotCount) {
        int[] location = findPlotBlock(garden, plotCount);
        if (location[0] == -1) {
            return -1.0;
        }
        double plotPrice = BASE_PRICE;
        if (location[0] < IRRIGATED_ROWS) {
            plotPrice += IRRIGATION_SURCHARGE;
        }
        double total = plotPrice * plotCount;
        if (plotCount >= DISCOUNT_MIN_PLOTS) {
            total *= 1.0 - GROUP_DISCOUNT;
        }
        return Math.round(total * 100.0) / 100.0;
    }
}
