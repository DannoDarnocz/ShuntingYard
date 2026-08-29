package structures;
import java.util.ArrayList;


public class Deque<T> implements IDeque<T> {
    private ArrayList<T> dequeItems = new ArrayList<>();

    @Override
    public void enqueueFront(T item) {
        dequeItems.addFirst(item);

    }

    @Override
    public void enqueueRear(T item) {
        dequeItems.add(item);

    }

    @Override
    public T dequeueFront() {
        return dequeItems.removeFirst();
    }

    @Override
    public T dequeueRear() {
        return dequeItems.remove(this.size()-1);
    }

    @Override
    public T peekFront() {
        return dequeItems.getFirst();
    }

    @Override
    public T peekRear() {
        return dequeItems.get(this.size()-1);
    }

    @Override
    public boolean isEmpty() {
        return dequeItems.isEmpty();
    }

    @Override
    public int size() {
        return dequeItems.size();
    }
}

