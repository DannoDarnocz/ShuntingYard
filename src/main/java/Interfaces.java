public class Interfaces {
    // Interfaz para una Pila (LIFO) public interface IPila<T>
    {
        void Push(T item);      // Insertar en la cima     T Pop();                // Extraer de la cima
        T Peek();               // Consultar el elemento en la cima sin quitarlo     bool IsEmpty();         // Verifica si está vacía     int Count { get; }      // Cantidad de elementos
    }

    // Interfaz para una Cola (FIFO) public interface ICola<T>
    {
        void Encolar(T item);   // Insertar al final     T Desencolar();         // Extraer al frente
        T Peek();               // Consultar el frente sin quitarlo     bool IsEmpty();         // Verifica si está vacía     int Count { get; }      // Cantidad de elementos
    }

    // Interfaz para una Bicola (doble extremo) public interface IBicola<T>
    {
        void EncolarFrente(T item);   // Insertar por el frente     void EncolarFinal(T item);    // Insertar por el final     T DesencolarFrente();         // Extraer por el frente
        T DesencolarFinal();          // Extraer por el final
        T PeekFrente();               // Consultar el frente sin quitar
        T PeekFinal();                // Consultar el final sin quitar     bool IsEmpty();               // Verifica si está vacía     int Count { get; }            // Cantidad de elementos }

    }
