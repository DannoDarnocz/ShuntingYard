package shuntingyard.tokens;

public class Operator extends Token<Character> {
    private char character;

    Operator(char character) { this.character = character; }

    @Override
    public int getPriority(){
        // menos y mas tienen menor prioridad
        //cambien 2 y 3 de posicion para que * y / tomaran el 3 como mayor precencia
        return (character=='+'||character=='-'? 2:3);
    }

    public Double operate(double a, double b){
        return switch (character) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '/' -> a / b;
            case '*' -> a * b;
            default -> null;
        };
    }

    @Override
    public Character getData() { return character; }

    @Override
    public String toString() { return Character.toString(character);}
}
