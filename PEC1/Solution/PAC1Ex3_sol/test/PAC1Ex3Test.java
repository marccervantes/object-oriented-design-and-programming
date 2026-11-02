import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PAC1Ex3Test {
    private int[][] garden() {
        return new int[][]{
                {1, 0, 0, 2, 0, 0, 0, 1},
                {0, 0, 1, 0, 0, 0, 0, 2},
                {0, 0, 0, 0, 0, 1, 0, 0},
                {2, 0, 0, 0, 0, 0, 0, 0}
        };
    }

    @Test
    @Order(1)
    @DisplayName("Validar la posición de una parcela")
    public void testIsValidPlot() {
        int[][] plots = garden();
        assertTrue(PAC1Ex3.isValidPlot(plots, 0, 0)); // Asignada, pero válida.
        assertTrue(PAC1Ex3.isValidPlot(plots, 0, 3)); // En descanso, pero válida.
        assertTrue(PAC1Ex3.isValidPlot(plots, 3, 7));
        assertFalse(PAC1Ex3.isValidPlot(plots, -1, 0));
        assertFalse(PAC1Ex3.isValidPlot(plots, 0, -1));
        assertFalse(PAC1Ex3.isValidPlot(plots, 4, 0));
        assertFalse(PAC1Ex3.isValidPlot(plots, 0, 8));
        // La consulta debe conservar el contenido del huerto.
        int[][] originalGarden = garden();
        for (int row = 0; row < plots.length; row++) {
            assertArrayEquals(originalGarden[row], plots[row]);
        }
    }

    @Test
    @Order(2)
    @DisplayName("Contar las parcelas disponibles de una fila")
    public void testCountAvailablePlots() {
        int[][] plots = garden();
        assertEquals(5, PAC1Ex3.countAvailablePlots(plots, 0));
        assertEquals(6, PAC1Ex3.countAvailablePlots(plots, 1));
        assertEquals(7, PAC1Ex3.countAvailablePlots(plots, 2));
        assertEquals(0, PAC1Ex3.countAvailablePlots(new int[][]{{1, 2, 1}}, 0));
        assertEquals(2, PAC1Ex3.countAvailablePlots(new int[][]{{0, 2, 0}}, 0));
        assertEquals(-1, PAC1Ex3.countAvailablePlots(plots, -1));
        assertEquals(-1, PAC1Ex3.countAvailablePlots(plots, 4));
        // La consulta debe conservar el contenido del huerto.
        int[][] originalGarden = garden();
        for (int row = 0; row < plots.length; row++) {
            assertArrayEquals(originalGarden[row], plots[row]);
        }
    }

    @Test
    @Order(3)
    @DisplayName("Comprobar si se puede asignar un bloque de parcelas")
    public void testCanAssignPlots() {
        int[][] plots = garden();
        assertTrue(PAC1Ex3.canAssignPlots(plots, 0, 4, 3));
        assertTrue(PAC1Ex3.canAssignPlots(plots, 3, 1, 7)); // Hasta la última columna.
        assertFalse(PAC1Ex3.canAssignPlots(plots, 0, 1, 3)); // Descanso intermedio.
        assertFalse(PAC1Ex3.canAssignPlots(plots, 1, 1, 3)); // Parcela asignada.
        assertFalse(PAC1Ex3.canAssignPlots(plots, 3, 1, 8)); // Rebasa la fila.
        assertFalse(PAC1Ex3.canAssignPlots(plots, 0, 1, 0));
        assertFalse(PAC1Ex3.canAssignPlots(plots, 0, 1, -1));
        assertFalse(PAC1Ex3.canAssignPlots(plots, -1, 0, 1));
        assertFalse(PAC1Ex3.canAssignPlots(plots, 4, 0, 1));
        assertFalse(PAC1Ex3.canAssignPlots(plots, 0, -1, 1));
        assertFalse(PAC1Ex3.canAssignPlots(plots, 0, 8, 1));
        assertTrue(PAC1Ex3.canAssignPlots(plots, 1, 3, 4));
        // La consulta debe conservar el contenido del huerto.
        int[][] originalGarden = garden();
        for (int row = 0; row < plots.length; row++) {
            assertArrayEquals(originalGarden[row], plots[row]);
        }
    }

    @Test
    @Order(4)
    @DisplayName("Localizar el primer bloque de parcelas disponibles")
    public void testFindPlotBlock() {
        int[][] expected = {{0, 1}, {0, 1}, {0, 4}, {1, 3}, {2, 0}, {3, 1}, {3, 1}};
        for (int count = 1; count <= expected.length; count++) {
            assertArrayEquals(expected[count - 1], PAC1Ex3.findPlotBlock(garden(), count));
        }

        // Prioridad por fila y columna, y continuidad del bloque.
        int[][] plots = {{1, 1, 0, 0}, {0, 0, 0, 0}};
        assertArrayEquals(new int[]{0, 2}, PAC1Ex3.findPlotBlock(plots, 2));
        assertArrayEquals(new int[]{1, 0}, PAC1Ex3.findPlotBlock(plots, 3));
        assertArrayEquals(new int[]{-1, -1},
                PAC1Ex3.findPlotBlock(new int[][]{{0, 2, 0}, {1, 0, 1}}, 2));
        assertArrayEquals(new int[]{-1, -1},
                PAC1Ex3.findPlotBlock(new int[][]{{1, 0}, {0, 1}}, 2));

        // Solicitudes inválidas o sin espacio disponible.
        for (int count : new int[]{-1, 0, 8, 9}) {
            assertArrayEquals(new int[]{-1, -1}, PAC1Ex3.findPlotBlock(garden(), count));
        }
        assertArrayEquals(new int[]{-1, -1},
                PAC1Ex3.findPlotBlock(new int[][]{{1, 2}, {2, 1}}, 1));

        // Huertos con distintas dimensiones.
        assertArrayEquals(new int[]{0, 0}, PAC1Ex3.findPlotBlock(new int[][]{{0}}, 1));
        assertArrayEquals(new int[]{-1, -1}, PAC1Ex3.findPlotBlock(new int[][]{{2}}, 1));
        assertArrayEquals(new int[]{2, 0},
                PAC1Ex3.findPlotBlock(new int[][]{{1}, {2}, {0}}, 1));
        assertArrayEquals(new int[]{-1, -1},
                PAC1Ex3.findPlotBlock(new int[][]{{0}, {0}}, 2));
        assertArrayEquals(new int[]{0, 1},
                PAC1Ex3.findPlotBlock(new int[][]{{2, 0, 0}}, 2));

        // Las consultas repetidas no asignan parcelas.
        plots = garden();
        assertArrayEquals(new int[]{1, 3}, PAC1Ex3.findPlotBlock(plots, 4));
        assertArrayEquals(new int[]{1, 3}, PAC1Ex3.findPlotBlock(plots, 4));
        // La consulta debe conservar el contenido del huerto.
        int[][] originalGarden = garden();
        for (int row = 0; row < plots.length; row++) {
            assertArrayEquals(originalGarden[row], plots[row]);
        }
    }

    @Test
    @Order(5)
    @DisplayName("Calcular la cuota de temporada de las parcelas")
    public void testCalculateLeasePrice() {
        double[] expected = {32.50, 65.00, 97.50, 110.50, 106.25, 127.50, 148.75};
        for (int count = 1; count <= expected.length; count++) {
            assertEquals(expected[count - 1],
                    PAC1Ex3.calculateLeasePrice(garden(), count), 0.000001);
        }

        // Tarifas con y sin riego, descuento y redondeo final.
        assertEquals(138.13,
                PAC1Ex3.calculateLeasePrice(new int[][]{{0, 0, 0, 0, 0}}, 5), 0.000001);
        int[][] plots = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        // La primera fila tiene prioridad aunque la tercera sea más barata.
        assertEquals(110.50, PAC1Ex3.calculateLeasePrice(plots, 4), 0.000001);
        int[][] noIrrigation = {{1, 1, 1, 1}, {2, 2, 2, 2}, {0, 0, 0, 0}};
        assertEquals(75.00, PAC1Ex3.calculateLeasePrice(noIrrigation, 3), 0.000001);
        assertEquals(85.00, PAC1Ex3.calculateLeasePrice(noIrrigation, 4), 0.000001);

        // Solicitudes inválidas o sin espacio disponible.
        for (int count : new int[]{-1, 0, 8, 9}) {
            assertEquals(-1.0, PAC1Ex3.calculateLeasePrice(garden(), count));
        }

        // Las consultas repetidas no asignan parcelas.
        plots = garden();
        assertEquals(110.50, PAC1Ex3.calculateLeasePrice(plots, 4), 0.000001);
        assertEquals(110.50, PAC1Ex3.calculateLeasePrice(plots, 4), 0.000001);
        // La consulta debe conservar el contenido del huerto.
        int[][] originalGarden = garden();
        for (int row = 0; row < plots.length; row++) {
            assertArrayEquals(originalGarden[row], plots[row]);
        }
    }
}
