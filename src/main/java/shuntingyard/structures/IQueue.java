package shuntingyard.structures;

public interface IQueue<T> {
    void enqueue(T item);     // Insertar al final
    T dequeue();              // Extraer del frente
    T peek();                 // Consultar el frente sin quitarlo
    boolean isEmpty();        // Verifica si está vacía
    int size();               // Cantidad de elementos
    String toString();

}
