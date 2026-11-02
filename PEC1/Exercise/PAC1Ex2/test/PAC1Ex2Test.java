import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class PAC1Ex2Test {

    private ByteArrayOutputStream outContent;
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testGetLockerCapacity() {
        assertEquals(40, PAC1Ex2.getLockerCapacity(0));
        assertEquals(24, PAC1Ex2.getLockerCapacity(1));
        assertEquals(60, PAC1Ex2.getLockerCapacity(2));
        assertEquals(32, PAC1Ex2.getLockerCapacity(3));
        assertEquals(48, PAC1Ex2.getLockerCapacity(4));
        assertEquals(20, PAC1Ex2.getLockerCapacity(5));
    }

    @Test
    public void testCalculateOccupancyRate() {
        assertEquals(0.0, PAC1Ex2.calculateOccupancyRate(0, 0));
        assertEquals(0.5, PAC1Ex2.calculateOccupancyRate(12, 1));
        assertEquals(0.8, PAC1Ex2.calculateOccupancyRate(48, 2));
        assertEquals(0.875, PAC1Ex2.calculateOccupancyRate(28, 3));
        assertEquals(1.25, PAC1Ex2.calculateOccupancyRate(60, 4));
    }

    @Test
    public void testNeedsPackageCollection() {
        assertFalse(PAC1Ex2.needsPackageCollection(31, 0));
        assertTrue(PAC1Ex2.needsPackageCollection(32, 0));
        assertFalse(PAC1Ex2.needsPackageCollection(19, 1));
        assertTrue(PAC1Ex2.needsPackageCollection(48, 2));
        assertTrue(PAC1Ex2.needsPackageCollection(20, 5));
    }

    @Test
    public void testCalculateTotalPackages() {
        assertEquals(160, PAC1Ex2.calculateTotalPackages(new int[]{32, 9, 51, 16, 48, 4}));
        assertEquals(0, PAC1Ex2.calculateTotalPackages(new int[]{0, 0, 0, 0, 0, 0}));
        assertEquals(21, PAC1Ex2.calculateTotalPackages(new int[]{1, 2, 3, 4, 5, 6}));
    }

    @Test
    public void testGetBusiestStation() {
        assertEquals(4, PAC1Ex2.getBusiestStation(new int[]{32, 9, 51, 16, 48, 4}));
        assertEquals(0, PAC1Ex2.getBusiestStation(new int[]{20, 12, 30, 16, 24, 10}));
        assertEquals(1, PAC1Ex2.getBusiestStation(new int[]{0, 24, 60, 0, 0, 0}));
        assertEquals(5, PAC1Ex2.getBusiestStation(new int[]{0, 0, 0, 0, 0, 21}));
    }

    @Test
    public void testPrintLockerReport() {
        int[] packages = {32, 9, 51, 16, 48, 4};

        PAC1Ex2.printLockerReport(packages);

        assertEquals(
                "Total packages: 160" + System.lineSeparator()
                        + "Busiest station: Old Town" + System.lineSeparator(),
                outContent.toString()
        );
        assertArrayEquals(new int[]{32, 9, 51, 16, 48, 4}, packages);
    }
}
