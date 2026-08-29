package shuntingyard;

import shuntingyard.tokens.Token;
import shuntingyard.tokens.Tokenizer;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public void main() {
        while (true) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Digite la expresión: ");
            String expression = scanner.nextLine(); // Reads a full line of text

            try{
                ArrayList<Token> tokenList = Tokenizer.convertString(expression);

                System.out.println("Expresión digitada: ");
                for(Token token : tokenList){
                    System.out.print(token.toString());
                }

                System.out.println("\n"+tokenList.toString()+"\n");

                // hacer operacion con stack y queue
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
