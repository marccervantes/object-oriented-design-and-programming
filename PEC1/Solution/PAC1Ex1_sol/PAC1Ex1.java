// ERROR SINTÁCTICO 1: faltaba importar Scanner, por lo que IntelliJ no reconocía la clase.
import java.util.Scanner;

public class PAC1Ex1 {

    public static long nextCollatz(long n) {
        // ERROR SINTÁCTICO 2: se utilizaba "=" en lugar del operador de comparación "==".
        if (n % 2 == 0) {
            // ERROR SINTÁCTICO 3: faltaba el punto y coma al final de la sentencia return.
            return n / 2;
        }

        // ERROR LÓGICO 1: para los números impares se usaba 3 * n - 1 en vez de 3 * n + 1.
        return 3 * n + 1;
    }

    public static void printCollatzSequence(long n) {
        System.out.print("Collatz sequence: ");

        while (n > 1) {
            System.out.print(n + " -> ");
            n = nextCollatz(n);
        }

        // ERROR LÓGICO 2: println() no mostraba el último valor de la secuencia, que debe ser 1.
        System.out.println(1);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n;

        do {
            // ERROR SINTÁCTICO 4: "Sytem" estaba mal escrito; la clase correcta es "System".
            System.out.print("Enter a positive integer: ");
            n = scanner.nextLong();
        } while (n <= 0); // ERROR LÓGICO 3: con n < 0 se aceptaba el 0, que no es positivo.

        printCollatzSequence(n);
    }
}
