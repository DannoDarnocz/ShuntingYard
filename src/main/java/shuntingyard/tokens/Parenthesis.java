package shuntingyard.tokens;

public class Parenthesis extends Token<Boolean> {

    private Boolean isOpen;


    public Parenthesis(Boolean isOpen){this.isOpen = isOpen;}

    @Override
    public int getPriority(){
        return 1;
    }

    @Override
    public Boolean getData() {
        return isOpen;
    }

    @Override
    public String toString() { return (isOpen?"(":")");}
}
