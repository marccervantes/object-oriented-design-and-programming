import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        // TODO

        // Comprobamos si la columna esta fuera de los limites
        if (column < 0 || column >= garden[0].length) {
            return false;
        }

        // Comprobamos si la fila esta fuera de los limites
        if (row < 0 || row >= garden.length) {
            return false;
        }

        // Si la fila y la columna son validas
        return true;
    }

    public static int countAvailablePlots(int[][] garden, int row) {
        // TODO

        // Inicializamos el contador a 0
        int count = 0;

        // comprobamos si la fila es valida
        if (!isValidPlot(garden, row, 0)) {
            return -1;
        }

        // Recorremos las columnas y comprobamos si la parcela está disponible
        for (int column = 0; column < garden[row].length; column++) {
            if (garden[row][column] == AVAILABLE) {
                count++;
            }
        }

        //Return del total de parcelas disponibles
        return count;
    }

    public static boolean canAssignPlots(int[][] garden, int row, int startColumn, int plotCount) {
        // TODO

        int sumTotal = startColumn + plotCount;

        // compruebo si la posición inicial es inválida -> return false
        if (!isValidPlot(garden, row, startColumn)) {
            return false;
        }

        // compruebo si plotCount es cero o negativo -> return false
        if (plotCount <= 0) {
            return false;
        }

        // compruebo si el número de parcelas consecutivas que se solicita rebasa la longitud de la fila -> return false
        if (sumTotal > garden[row].length) {
            return false;
        }

        // compruebo si no hay plotCount parcelas consecutivas disponibles. -> return false
        for (int i = startColumn; i < sumTotal; i++) {
            if (garden[row][i] != AVAILABLE) {
                return false;
            }
        }

        // Si llega hasta aqui, todo bien -> return true
        return true;
    }

    public static int[] findPlotBlock(int[][] garden, int plotCount) {
        // TODO

        // creamos el array a devolver
        int[] result = new int[2];

        // inicializamos la array a -1
        result[0] = -1;
        result[1] = -1;

        // si el numero de parcelas es 0 o negativo, hacemos return de la aray creada arriba
        if (plotCount <= 0) {
            return result;
        }

        // Vamos a recorrer las filas y las columnas
        for (int i = 0; i < garden.length; i++) {
            for (int x = 0; x < garden[i].length; x++) {

                // comprobamos si se puede asignar
                boolean canAssign = canAssignPlots(garden,i,x,plotCount);

                if (canAssign) {
                    result[0] = i;
                    result[1] = x;

                    return result;
                }
            }
        }

        // si todo sigue igual, retorna el valor inicial
        return result;
    }

    public static double calculateLeasePrice(int[][] garden, int plotCount) {
        // TODO

        int[] position; // Creamos la array
        double pricePerPlot = BASE_PRICE; // Inicializamos la variable con los datos base.


        //Buscamos la posición del primer bloque
        position = findPlotBlock(garden, plotCount);

        // Si findPlotBlock == -1, no existe un bloque válido.
        if (position[0] == -1) {
            return -1.0;
        }

        // Si la fila tiene riego, añadimos el suplemento de riego
        if (position[0] < IRRIGATED_ROWS) {
            pricePerPlot = pricePerPlot + IRRIGATION_SURCHARGE;
        }

        double totalPrice = pricePerPlot * plotCount; //Calculamos el precio total

        // Si se alquilan 4 parcelas o más, aplicamos 15% de dto
        if (plotCount >= DISCOUNT_MIN_PLOTS) {
            totalPrice = totalPrice - (totalPrice * GROUP_DISCOUNT);
        }

        // Redondeamos a 2 decimales.
        totalPrice = Math.round(totalPrice * 100.0) / 100.0;

        return totalPrice;
    }

//    public static void main(String[] args) {
//        int[][] garden = {
//                {1, 0, 0, 2, 0, 0, 0, 1},
//                {0, 0, 1, 0, 0, 0, 0, 2},
//                {0, 0, 0, 0, 0, 1, 0, 0},
//                {2, 0, 0, 0, 0, 0, 0, 0}
//        };
//
////        Ex1
//        System.out.println("Parte 1");
//        System.out.println(isValidPlot(garden, 0, 0)); // true
//        System.out.println(isValidPlot(garden, 4, 0)); // false
//
////        Ex2
//        System.out.println();
//        System.out.println("Parte 2");
//        System.out.println(countAvailablePlots(garden, 0)); // 5
//        System.out.println(countAvailablePlots(garden, 4)); // -1
//
////        Ex3
//        System.out.println();
//        System.out.println("Parte 3");
//
//        System.out.println("1 - " + canAssignPlots(garden, 0, 4, 3));
//        System.out.println("2 - " + canAssignPlots(garden, 3, 1, 7)); // Hasta la última columna.
//        System.out.println("3 - " + canAssignPlots(garden, 0, 1, 3)); // Descanso intermedio.
//        System.out.println("4 - " + canAssignPlots(garden, 1, 1, 3)); // Parcela asignada.
//        System.out.println("5 - " + canAssignPlots(garden, 3, 1, 8)); // Rebasa la fila.
//        System.out.println("6 - " + canAssignPlots(garden, 0, 1, 0));
//
//        // Ex4
//        System.out.println();
//        System.out.println("Parte 4");
//
//        System.out.println("1 - " + findPlotBlock(garden, 1)[0] + ", " + findPlotBlock(garden, 1)[1]);
//        System.out.println("2 - " + findPlotBlock(garden, -1)[0] + ", " + findPlotBlock(garden, -1)[1]);
//        System.out.println("3 - " + findPlotBlock(garden, 0)[0] + ", " + findPlotBlock(garden, 0)[1]);
//
//        // Ex5
//        System.out.println();
//        System.out.println("Parte 5");
//
//        System.out.println("1 - " + findPlotBlock(garden, 1)[0] + ", " + findPlotBlock(garden, 1)[1]);
//        System.out.println("2 - " + findPlotBlock(garden, -1)[0] + ", " + findPlotBlock(garden, -1)[1]);
//    }
}
