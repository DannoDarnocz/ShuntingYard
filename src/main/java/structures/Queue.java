package structures;
import java.util.ArrayList;


public class Queue<T> implements IQueue<T> {
    private ArrayList<T> queueItems = new ArrayList<>();

    @Override
    public void enqueue(T item) {
        queueItems.add(item);

    }

    @Override
    public T dequeue() {
        return queueItems.removeFirst();

    }

    @Override
    public T peek() {
        return queueItems.getFirst();

    }

    @Override
    public boolean isEmpty() {
        return queueItems.isEmpty();

    }

    @Override
    public int size() {
        return queueItems.size();

    }
}
