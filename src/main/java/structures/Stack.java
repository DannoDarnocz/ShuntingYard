//hola
package structures;
import java.util.ArrayList;

public class Stack<T> implements IStack<T> {
    private ArrayList<T> stackItems = new ArrayList<>();

    @Override
   public void push(T item){
         stackItems.add(item);

    }
    @Override
   public T pop(){
       return stackItems.remove(this.size()-1);

    }
    @Override
   public T peek(){
       return stackItems.get(this.size()-1);

    }
    @Override
   public boolean isEmpty(){
       return stackItems.isEmpty();

    }
    @Override
  public int size(){
       return stackItems.size();

    }
}
