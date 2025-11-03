package data;

/**
 * Interface for a generic stack
 * @author brand
 * @param <E> 
 */

public interface Stack<E> {
    
    int size();
    boolean isEmpty();
    void push(E e);
    E top();
    E pop();
    int getMaxSize();
    
}
