//package shuntingyard;
package shuntingyard;
import java.util.Scanner;
import shuntingyard.algorithm.ShuntingYard;

public class Main {
    public static void main(String[] args) {
        while (true) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("\nDigite la expresión: ");

            try {
                String expression = scanner.nextLine(); // leer la linea
                System.out.println("\n");
                double result = ShuntingYard.process(expression);
                 System.out.println("\nRESULTADO: ");
                System.out.println(result);

            } catch (Exception e) {
                System.out.println("Expresión inválida: " + e.getMessage());
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