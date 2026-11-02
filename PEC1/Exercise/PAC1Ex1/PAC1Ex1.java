import java.util.Scanner;

public class PAC1Ex1 {

    public static long nextCollatz(long n) {
        if (n % 2 == 0) { // para comparar es ==
            return n / 2;
        }

        return 3 * n + 1; // El enunciado dice que es + 1, no - 1
    }

    public static void printCollatzSequence(long n) {
        System.out.print("Seqüència de Collatz: ");

        while (n > 1) {
            System.out.print(n + " -> ");
            n = nextCollatz(n);
        }

        System.out.println(1); // Despues del bucle, se ejecuta este println, por lo que el ultimo numero siempre sera 1
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n;

        do {
            System.out.print("Introduïu un enter positiu: ");
            n = scanner.nextLong();
        } while (n < 0);

        printCollatzSequence(n);
    }
}
