package shuntingyard.structures;

public interface IDeque<T> {
    void enqueueFront(T item);    // Insertar por el frente
    void enqueueRear(T item);     // Insertar por el final
    T dequeueFront();             // Extraer por el frente
    T dequeueRear();              // Extraer por el final
    T peekFront();                // Revisar por el frente sin quitarlo
    T peekRear();                 // Revisar por el final sin quitarlo
    boolean isEmpty();            // Revisar si esta vacio
    int size();                   // cantidad de elementos

}
