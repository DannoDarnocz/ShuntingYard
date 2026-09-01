//package shuntingyard;
package shuntingyard;
import java.util.Scanner;
import shuntingyard.algorithm.ShuntingYard;

public class Main {
    public void main() {
        while (true) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Digite la expresión: ");
            String expression = scanner.nextLine(); // Reads a full line of text

            try {
                System.out.println("Expresión digitada: ");
                double result = ShuntingYard.process(expression);
                System.out.println(result);
            } catch (Exception e) {
                e.printStackTrace();
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