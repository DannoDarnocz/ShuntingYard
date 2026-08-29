package shuntingyard.tokens;


import java.io.InvalidObjectException;
import java.lang.Number;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Tokenizer {
    private static final String validOperators = "+*/";

    public static ArrayList<Token>convertString(String str) throws Exception {
        ArrayList<Token> tokenizedList = new ArrayList<>();
        // convertir a un array de caracteres
        char[] chars = str.toCharArray();

        boolean nextNumShouldBeNegative = false; // esto lleva registro de si el siguiente número que aparezca debe ser negativo (signo de menos cuando haya un operador antes o sea al inicio)
        boolean operatorLoaded = false;
        int unclosedParenthesis = 0; // esto para llevar control de que se hayan cerrado todos los parentesis

        // recorrer cada caracter
        for (int i = 0; i < chars.length; i++) {
            char currentChar = chars[i];

            /*System.out.println(
                    "Current character: " + currentChar +
                            "\n nextNumShouldBeNegative: " + nextNumShouldBeNegative +
                            "\n operatorLoaded: " + operatorLoaded +
                            "\n unclosedParenthesis: " + unclosedParenthesis + "\n"
            );*/

            // si el actual es digito, va moviendo el cursor hasta que ya no sea digito
            if (Character.isDigit(currentChar)) {
                int x = i;

                // recorrer hasta encontrar dónde el índice ya no es digito
                while (x < chars.length && Character.isDigit(chars[x])) {
                    /*System.out.println(
                            "Current character: " + chars[x] +
                                    "\n nextNumShouldBeNegative: " + nextNumShouldBeNegative +
                                    "\n operatorLoaded: " + operatorLoaded +
                                    "\n unclosedParenthesis: " + unclosedParenthesis + "\n"
                    );*/
                    x++;
                }

                // construir numero de hacer substring de los indices
                String strNumber = str.substring(i, x); //substring es exclusivo, no incluye ultimo digito
                int number = Integer.parseInt(strNumber);

                // si tenia un signo de menos antes, pasa a ser que el numero sea negativo
                NumericalValue numericalValue = new NumericalValue(nextNumShouldBeNegative ? -(number) : number);
                tokenizedList.add(numericalValue);

                i = x; // avanzar el cursor hasta donde terminó x porque ese donde quedó ya no es dígito

                // el indice actual de x es el siguiente token, que ya sabemos que NO es digito.
                // y como vamos a iterar otra vez se le suma 1 a i, asi que se contrarresta.
                i-=1;

                // se sabe que no es operador asi que se quita
                operatorLoaded = false;

                // ya se "gastó" el signo de negativo, quitarlo si es que se usó
                nextNumShouldBeNegative = false;
            }

            // si actual es un menos se debe de tener cuidado, puede ser de signo del numero
            else if(currentChar=='-'){
                if(nextNumShouldBeNegative){
                    // si tiene indicador de "número negativo" significa que ya había un operador, luego un menos.
                    // en este caso otro menos le sigue, entonces no tiene sentido (seria como 2+--3 o --2)
                    throw new InvalidObjectException("Hay dos o más signos de resta consecutivamente. Posición: " + (i+1));
                }
                else if(tokenizedList.isEmpty()||operatorLoaded){
                    // si no hay nada antes entonces no queda de otra, es un signo de numero negativo
                    // y si el anterior era un operador entonces tambien no queda de otra mas que sea de negativo
                    nextNumShouldBeNegative = true;
                }else{
                    // si no pasa ninguna condicion es porque es un signo de resta simple
                    Operator operator = new Operator('-');
                    tokenizedList.add(operator);
                    operatorLoaded = true;
                    nextNumShouldBeNegative = false;
                }
            }

            // si el actual es paréntesis
            else if(currentChar=='('||currentChar==')'){
                // es parentesis (true para cuando es abierto)
                Parenthesis parenthesis = new Parenthesis(currentChar=='(');

                if(nextNumShouldBeNegative){
                    // tiene la bandera de cuando hay dos signos de menos seguidos, eso tiene sentido si el siguiente
                    // es un numero pero no si es parentesis: 2--(3)
                    throw new InvalidObjectException("Hay un signo de resta inválido antes de un paréntesis. Posición: " + (i--));
                }

                if(currentChar=='('){
                    // el parentesis de apertura hace que no se pueda poner un operador después
                    // (a menos que sea
                    // un menos de signo de negativo) porque no tiene sentido hacer 2*(/9+1)
                    operatorLoaded = true;
                    unclosedParenthesis++;
                }
                else{
                    // si se cierra un parentesis cuando no hay ninguno abierto entonces excepción
                    if(unclosedParenthesis==0) throw new InvalidObjectException("Se cerró un paréntesis cuando no había ninguno abierto. Posición: " + (i+1));

                    // si antes tiene un operador no tiene sentido, por ejemplo 2*(9+)
                    if(operatorLoaded) throw new InvalidObjectException("Hay un operador antes de cerrar un paréntesis, o un paréntesis de apertura inmediatamente antes. Posición: " + (i+1));
                    unclosedParenthesis--;
                }

                // agregar parentesis si no hay problemas
                tokenizedList.add(parenthesis);
            }

            // si es un operador valido (sin contar el menos porque eso se maneja antes)
            else if (validOperators.contains(Character.toString(currentChar))){
                if(operatorLoaded) throw new InvalidObjectException("Hay dos signos consecutivos de forma inválida. Posición: " + (i+1));
                if(tokenizedList.isEmpty()) throw new InvalidObjectException("La expresión empieza con un operador.");

                Operator operator = new Operator(currentChar);
                operatorLoaded = true;
                tokenizedList.add(operator);
            }
        }

        // si se dejó un paréntesis sin cerrar entonces no es valido
        if(unclosedParenthesis!=0) throw new InvalidObjectException("No se cerraron todos los paréntesis");

        // si la lista está vacía totalmente entonces no hay nada válido para agregar
        if(tokenizedList.isEmpty()) throw new InvalidObjectException("La expresión dada no es válida");

        // la lista está íntegra, sin errores
        return tokenizedList;
    }

    /*
    public static ArrayList<Token> convertString2(String str){
        ArrayList<Token> tokenizedList = new ArrayList<Token>();
        // convertir a un array de caracteres
        char[] chars = str.toCharArray();

        boolean isNegative = false;

        for(int i=0;i<chars.length;i++) {
            char currentChar = chars[i];
            int constructedNumber = 0;

            Token previous;

            // obtener el previo, sino hay ninguno es null y se maneja asi
            if(!tokenizedList.isEmpty()){
                previous = tokenizedList.getLast(); // al inicio siempre sera null porque no hay nada
            }
            else{
                previous = null;
            }

            // si el actual es un menos y el anterior era tambien un operador (o no habia nada), tomar el menos como el signo
            // si el anterior es un numero entonces se toma como un menos de operación y se maneja despues
            if(currentChar=='-' && (!(previous instanceof NumericalValue))){
                // si ya tenia asignado que el numero es negativo porque tenia otro signo de menos antes,
                // son 3 signos seguidos y eso no tiene sentido
                if(isNegative) throw new RuntimeException("Hay dos signos de operación de resta o más en sucesión");
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

                // agregar el numero
                NumericalValue number = new NumericalValue(isNegative?-(constructedNumber):constructedNumber);
                tokenizedList.add(number);

                if(i>=chars.length) break; // si ya se llegó al final entonces no seguir con el bucle
                i--; // restarle uno porque ese se sabe que no es número pero no se sabe qué es (se le vuelve a incrementar
                // 1 cuando vuelve a empezar el bucle)

                isNegative = false;
                continue; // empezar el bucle de nuevo
            }

            // no puede ser un numero entonces ya no asignarle negativo a un hipotetico numero siguiente
            isNegative = false;
            if(currentChar=='('||currentChar==')'){
                // es parentesis (true para cuando es abierto)
                Parenthesis parenthesis = new Parenthesis(currentChar=='(');
                tokenizedList.add(parenthesis);
            }
            else if(validOperators.contains(Character.toString(currentChar))){
                // es alguna operación válida
                // ver si antes habia un operador. solo se vale si el actual es un negativo
                if(previous instanceof Operator){
                    throw new RuntimeException("El operador " + currentChar + " es inválido debido a que antes hay un " + previous.getData());
                }
                Operator operator = new Operator(currentChar);
                tokenizedList.add(operator);
            } else {
                // no calza con ninguna
                throw new RuntimeException("El caracter " + currentChar + " no es un token válido.");
            }
        }

        return tokenizedList;
    }*/
}
