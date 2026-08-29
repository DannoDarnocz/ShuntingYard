package shuntingyard.tokens;

public class NumericalValue extends Token<Long> {
    private long number;


    public NumericalValue(long number) {
        this.number = number;
    };

    @Override
    public int getPriority(){
        return 3;
    }

    @Override
    public Long getData(){
        return number;
    }

    @Override
    public String toString() { return Long.toString(number);}
}
