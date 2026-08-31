package shuntingyard;

import shuntingyard.structures.IQueue;
import shuntingyard.structures.IStack;
import shuntingyard.structures.Queue;
import shuntingyard.structures.Stack;
import shuntingyard.tokens.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public void main() {
        while (true) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Digite la expresión: ");
            String expression = scanner.nextLine(); // Reads a full line of text

            try{
                IQueue<Token> tokenList = Tokenizer.parseTokenList(expression);

                // para hacer el proceso va aca
                IQueue<Token> outputQueue = new Queue<>();
                IStack<Token> stack = new Stack<>();

                System.out.println("Expresión digitada: ");

                Token currentToken = null;

                // ir sacando de la cola hasta que ya no haya más tokens
                while(!tokenList.isEmpty()){
                    currentToken = tokenList.dequeue(); // obtener el primero y quitarlo de la cola para procesarlo
                    System.out.print(currentToken.toString());

                    // si es número (operando) agregar a la cola de salida
                    if(currentToken instanceof NumericalValue){
                        outputQueue.enqueue(currentToken);
                    }
                    else if(currentToken instanceof Operator){

                    }
                    else if(currentToken instanceof Parenthesis){
                        Parenthesis currentParenthesis = (Parenthesis)currentToken;
                        // true si es parentesis abierto
                        if(currentParenthesis.getData()){
                            // es parentesis abierto, simplemente se mete en la pila y ya
                            stack.push(currentToken);
                        }
                        else{
                            // es parentesis de cierre, sacar hasta encontrar parentesis de apertura
                            do{
                                Token poppedToken = stack.pop();
                                if(poppedToken instanceof Parenthesis){
                                    outputQueue.enqueue(poppedToken);
                                }
                            } while (true);
                        }
                    }
                }

                // Ingresar la cola a la pila para obtener resultado

                IStack<Token> resultStack = new Stack<>();
                NumericalValue operand1 = null;
                NumericalValue operand2 = null;

                while(!outputQueue.isEmpty()){
                    // sacar de la cola
                    currentToken = outputQueue.dequeue();

                    // si es numero, se mete
                    if(currentToken instanceof NumericalValue){
                        resultStack.push(currentToken);
                    }
                    else{
                        // sacar ultimos dos numeros (asumiendo que el proceso anterior debería de estar bien hecho
                        // y efectivamente deberian haber al menos dos numeros en la pila
                        operand1 = (NumericalValue)resultStack.pop();
                        operand2 = (NumericalValue)resultStack.pop();

                        // se obtiene resultado de la operación
                        Operator currentOperator = (Operator)currentToken;
                        double result = currentOperator.operate(operand1.getData(),operand2.getData());

                        // se hace nuevo token con el resultado
                        Token resultToken = new NumericalValue(result);

                        // se mete ese resultado en la pila
                        resultStack.push(resultToken);
                    }
                }

                // el resultado queda como único elemento en la pila
                NumericalValue result = (NumericalValue)resultStack.peek();
                System.out.println(result.getData());


                System.out.println("\n"+tokenList.toString()+"\n");

                // convertir


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