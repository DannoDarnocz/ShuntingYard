package tokens;

public class Operator extends Token<Character> {
    private char character;

    Operator(char character) { this.character = character; }

    @Override
    public int getPriority(){
        return 2;
    }

    @Override
    public Character getData() { return character; }
}
