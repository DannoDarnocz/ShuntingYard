package shuntingyard.algorithm;

import shuntingyard.structures.IQueue;
import shuntingyard.structures.IStack;
import shuntingyard.structures.Queue;
import shuntingyard.structures.Stack;
import shuntingyard.tokens.*;

public class ShuntingYard {

    public static double process(String expression) throws Exception {
        IQueue<Token> postfix = toPostfix(expression);
        // mostrar
        System.out.println("\nEXPRESIÓN POSTFIJA: ");
        System.out.println(postfix.toString()+"\n");

        System.out.println("\nEVALUACION EXPRESION");
        return evaluate(postfix);
    }

    public static IQueue<Token> toPostfix(String expression) throws Exception {
        IQueue<Token> tokenList = Tokenizer.parseTokenList(expression);
        IQueue<Token> outputQueue = new Queue<>();
        IStack<Token> stack = new Stack<>();


        System.out.println("CONVIRTIENDO A POSTFIJA");
        // ir sacando de la cola hasta que ya no haya más tokens
        while (!tokenList.isEmpty()) {
            System.out.println("----");
            Token currentToken = tokenList.dequeue(); // obtener el primero y quitarlo de la cola para procesarlo
           // System.out.print(currentToken.toString());
            System.out.println("Se extrajo "+currentToken+" de la cola de entrada");


            // si es número (operando) agregar a la cola de salida
            if (currentToken instanceof NumericalValue) {
                handleNumber(currentToken, outputQueue);
            } else if (currentToken instanceof Operator) {
                handleOperator(currentToken, stack, outputQueue);
            } else if (currentToken instanceof Parenthesis) {
                handleParenthesis(currentToken, stack, outputQueue);
            }
        }

        // vaciar lo que quede en la pila al final
        while (!stack.isEmpty()) {
            System.out.println("----");
            Token poppedToken = stack.pop();
            System.out.println("Se extrajo "+poppedToken+" de la pila");
            outputQueue.enqueue(poppedToken);
            System.out.println("Se inserto "+poppedToken+" en la cola de salida");        }

        return outputQueue;
    }

//cola(de salida)== expresion postfija
    private static void handleNumber(Token currentToken, IQueue<Token> outputQueue) {
        outputQueue.enqueue(currentToken);
        System.out.println("Se inserto el numero "+currentToken+" en la cola de salida");//cola
    }

    private static void handleOperator(Token currentToken, IStack<Token> stack, IQueue<Token> outputQueue) {
        if (stack.isEmpty()) {
            stack.push(currentToken);
            System.out.println("Se inserto el operador "+currentToken+" en la pila");


            return;
        }

        Token topOperator = stack.peek();//guarda el operador de la cima para comparar
        while (topOperator.getPriority() >= currentToken.getPriority() && !stack.isEmpty()) {//recorre comparando y mientras no este vacia
            Token operator = stack.pop();//extrae operador
            System.out.println("Se extrajo el operador "+operator+" de la pila");
            outputQueue.enqueue(operator);// pone el operador en la cola
            System.out.println("Se inserto el operador "+operator+" en la cola de salida");//cola


            if (!stack.isEmpty()) {
                topOperator = stack.peek();//guarda el nuevo operador de la cima mientras no este vacia
            }
        }
        stack.push(currentToken);
        System.out.println("Se inserto el operador "+currentToken+" en la pila");
    }

    private static void handleParenthesis(Token currentToken, IStack<Token> stack, IQueue<Token> outputQueue) {
        Parenthesis currentParenthesis = (Parenthesis) currentToken;
        // true si es parentesis abierto
        if (currentParenthesis.getData()) {
            // es parentesis abierto, simplemente se mete en la pila y ya
            stack.push(currentToken);
            System.out.println("Se inserto el parentesis "+currentParenthesis+" en la pila");
        } else {
            // es parentesis de cierre, sacar hasta encontrar parentesis de apertura
            while (!stack.isEmpty()) {
                Token poppedToken = stack.pop();
                System.out.println("Se extrajo "+poppedToken+" de la pila");

                if (poppedToken instanceof Parenthesis) {
                    break;
                }
                outputQueue.enqueue(poppedToken);
                System.out.println("Se inserto el operador"+poppedToken+" en la cola da salida");
            }
        }
    }

    public static double evaluate(IQueue<Token> outputQueue) {
        IStack<Token> resultStack = new Stack<>();

        while (!outputQueue.isEmpty()) {
            System.out.println("----");
            // sacar de la cola
            Token currentToken = outputQueue.dequeue();
            System.out.println("Se extrajo "+currentToken+" de la cola de salida");

            // si es numero, se mete
            if (currentToken instanceof NumericalValue) {
                resultStack.push(currentToken);
                System.out.println("Se inserto el numero "+currentToken+" en la pila de evaluacion");
            } else {
                // sacar ultimos dos numeros (asumiendo que el proceso anterior debería de estar bien hecho
                // y efectivamente deberian haber al menos dos numeros en la pila
                Token resultToken = applyOperator(currentToken,resultStack);
                resultStack.push(resultToken);
                System.out.println("Se inserto el resultado "+resultToken+" en la pila de evaluacion");
            }
        }

        // el resultado queda como único elemento en la pila
        NumericalValue result = (NumericalValue) resultStack.peek();
        return result.getData();
    }

    private static Token applyOperator(Token currentToken, IStack<Token> resultStack) {
        NumericalValue operand2 = (NumericalValue) resultStack.pop();
        System.out.println("Se extrajo el numero "+operand2+" de la pila de evaluacion");
        NumericalValue operand1 = (NumericalValue) resultStack.pop();
        System.out.println("Se extrajo el numero "+operand1+" de la pila de evaluacion");


        // se obtiene resultado de la operación
        Operator currentOperator = (Operator) currentToken;
        double result = currentOperator.operate(operand1.getData(), operand2.getData());

        // se hace nuevo token con el resultado
        return new NumericalValue(result);
    }
}