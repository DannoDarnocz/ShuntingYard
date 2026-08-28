package structures;

public interface IStack<T> {
    void push(T item);      // Insertar en la cima
    T pop();                // Extraer de la cima
    T peek();               // Consultar el elemento en la cima sin quitarlo
    boolean isEmpty();      // Verifica si está vacía
    int size();
}
