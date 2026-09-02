//package shuntingyard;
package shuntingyard;
import java.util.Scanner;
import shuntingyard.algorithm.ShuntingYard;

public class Main {
    public void main() {
        while (true) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("\nDigite la expresión: ");
            String expression = scanner.nextLine(); // Reads a full line of text

            try {
                System.out.println("\n");
                double result = ShuntingYard.process(expression);
                 System.out.println("\nRESULTADO: ");
                System.out.println(result);

            } catch (Exception e) {
                System.out.println("Expresión inválida");
            }

            // esperar para que no se despapaye la consola
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}