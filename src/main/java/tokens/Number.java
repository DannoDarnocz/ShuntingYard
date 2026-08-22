package tokens;

public class Number extends Token<Long> {
    private long number;


    public Number(long number) {
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
}
