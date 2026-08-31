package shuntingyard.tokens;

public class NumericalValue extends Token<Double> {
    private double number;


    public NumericalValue(double number) {
        this.number = number;
    };

    @Override
    public int getPriority(){
        return 3;
    }

    @Override
    public Double getData(){
        return number;
    }

    @Override
    public String toString() { return Double.toString(number);}
}
