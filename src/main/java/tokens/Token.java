package tokens;

public abstract class Token <T> {
    abstract public int getPriority();
    abstract public T getData();
}
