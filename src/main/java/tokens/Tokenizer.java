package tokens;


import java.util.ArrayList;

public class Tokenizer {
    private static final String validOperators = "+-*/";

    public static ArrayList<Token> convertString(String str){
        ArrayList<Token> tokenizedList = new ArrayList<Token>();
        // convertir a un array de caracteres
        char[] chars = str.toCharArray();

        for(int i=0;i<chars.length;i++) {
            char currentChar = chars[i];
            int constructedNumber = 0;
            boolean isNegative = false;

            //todo arreglar signos

            // si el actual es un menos y el anterior era tambien un operador (o no habia nada), tomar el menos como el signo
            if(currentChar=='-'&& tokenizedList.isEmpty()){
                isNegative=true;
                continue;
            }


            if(Character.isDigit(currentChar)){
                // es numero pero hay que ver si hay más adelante tambien
                while(true){
                    currentChar = chars[i];
                    // multiplicar por diez "corre" el numero a la izquierda y sumando el nuevo hace que quede bien al
                    // final
                    constructedNumber = constructedNumber * 10 + Character.getNumericValue(currentChar);
                    i++;
                    if(i==chars.length||!Character.isDigit(chars[i])) break;
                } // si el siguiente tambien es digito

                // agregar todo el numero
                Number number = new Number(constructedNumber);
                tokenizedList.add(number);

                if(i>=chars.length) break; // si ya se llegó al final entonces no seguir con el bucle
                i--; // restarle uno porque ese se sabe que no es número pero no se sabe qué es (se le vuelve a incrementar
                // 1 cuando vuelve a empezar el bucle)
                continue; // empezar el bucle de nuevo
            }

            if(currentChar=='('||currentChar==')'){
                // es parentesis (true para cuando es abierto)
                Parenthesis parenthesis = new Parenthesis(currentChar=='(');
                tokenizedList.add(parenthesis);
            }
            else if(validOperators.contains(Character.toString(currentChar))){
                // es alguna operación válida
                // ver si antes habia un operador. solo se vale si el actual es un negativo
                Operator operator = new Operator(currentChar);
                tokenizedList.add(operator);
            } else {
                // no calza con ninguna
                throw new RuntimeException("El caracter " + currentChar + " no es un token válido.");
            }
        }

        return tokenizedList;
    }
}
