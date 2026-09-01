package shuntingyard.algorithm;

import shuntingyard.structures.IQueue;
import shuntingyard.structures.IStack;
import shuntingyard.structures.Queue;
import shuntingyard.structures.Stack;
import shuntingyard.tokens.*;

public class ShuntingYard {

    public static double process(String expression) throws Exception {
        IQueue<Token> postfix = toPostfix(expression);
        return evaluate(postfix);
    }

    public static IQueue<Token> toPostfix(String expression) throws Exception {
        IQueue<Token> tokenList = Tokenizer.parseTokenList(expression);
        IQueue<Token> outputQueue = new Queue<>();
        IStack<Token> stack = new Stack<>();

        // ir sacando de la cola hasta que ya no haya más tokens
        while (!tokenList.isEmpty()) {
            Token currentToken = tokenList.dequeue(); // obtener el primero y quitarlo de la cola para procesarlo
            System.out.print(currentToken.toString());

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
            outputQueue.enqueue(stack.pop());
        }

        return outputQueue;
    }

    private static void handleNumber(Token currentToken, IQueue<Token> outputQueue) {
        outputQueue.enqueue(currentToken);
    }

    private static void handleOperator(Token currentToken, IStack<Token> stack, IQueue<Token> outputQueue) {
        if (stack.isEmpty()) {
            stack.push(currentToken);
            return;
        }

        Token topOperator = stack.peek();//guarda el operador de la cima para comparar
        while (topOperator.getPriority() >= currentToken.getPriority() && !stack.isEmpty()) {//recorre comparando y mientras no este vacia
            Token operator = stack.pop();//extrae operador de la cima
            outputQueue.enqueue(operator);//lo pone en la cola
            if (!stack.isEmpty()) {
                topOperator = stack.peek();//guarda el nuevo operador de la cima mientras no este vacia
            }
        }
        stack.push(currentToken);
    }

    private static void handleParenthesis(Token currentToken, IStack<Token> stack, IQueue<Token> outputQueue) {
        Parenthesis currentParenthesis = (Parenthesis) currentToken;
        // true si es parentesis abierto
        if (currentParenthesis.getData()) {
            // es parentesis abierto, simplemente se mete en la pila y ya
            stack.push(currentToken);
        } else {
            // es parentesis de cierre, sacar hasta encontrar parentesis de apertura
            while (!stack.isEmpty()) {
                Token poppedToken = stack.pop();
                if (poppedToken instanceof Parenthesis) {
                    break;
                }
                outputQueue.enqueue(poppedToken);
            }
        }
    }

    public static double evaluate(IQueue<Token> outputQueue) {
        IStack<Token> resultStack = new Stack<>();

        while (!outputQueue.isEmpty()) {
            // sacar de la cola
            Token currentToken = outputQueue.dequeue();

            // si es numero, se mete
            if (currentToken instanceof NumericalValue) {
                resultStack.push(currentToken);
            } else {
                // sacar ultimos dos numeros (asumiendo que el proceso anterior debería de estar bien hecho
                // y efectivamente deberian haber al menos dos numeros en la pila
                resultStack.push(applyOperator(currentToken, resultStack));
            }
        }

        // el resultado queda como único elemento en la pila
        NumericalValue result = (NumericalValue) resultStack.peek();
        return result.getData();
    }

    private static Token applyOperator(Token currentToken, IStack<Token> resultStack) {
        NumericalValue operand1 = (NumericalValue) resultStack.pop();
        NumericalValue operand2 = (NumericalValue) resultStack.pop();

        // se obtiene resultado de la operación
        Operator currentOperator = (Operator) currentToken;
        double result = currentOperator.operate(operand1.getData(), operand2.getData());

        // se hace nuevo token con el resultado
        return new NumericalValue(result);
    }
}