package shuntingyard.tokens;

public class Operator extends Token<Character> {
    private char character;

    Operator(char character) { this.character = character; }

    @Override
    public int getPriority(){
        // menos y mas tienen menor prioridad
        return (character=='+'||character=='-'? 3:2);
    }

    @Override
    public Character getData() { return character; }

    @Override
    public String toString() { return Character.toString(character);}
}
