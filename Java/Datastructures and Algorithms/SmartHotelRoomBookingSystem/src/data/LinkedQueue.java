    
package data;

/**
 * Data structure class of a generic linked queue
 * @author brand
 * @param <E> datatype that will be associated with the generic linked queue
 */

public class LinkedQueue<E> {
    
    //Linked queue will store the queue as a singly linked list
    private SinglyLinkedList<E> list;
    
    /**
     * Constructor for the class. Creates a new, empty singly linked list
     */
    public LinkedQueue() {
        this.list = new SinglyLinkedList<>();
    }
    
    /**
     * Get the size of the singly linked list
     * @return the amount of elements inside the list
     */
    public int size() {
        return list.size();
    }
    
    /**
     * Find out if the list is empty or not
     * @return true/false
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    /**
     * Add the element to the back of the list
     * @param element the element that needs to be added to the list
     */
    public void enQueue(E element) {
        list.addLast(element);
    }
    
    /**
     * Get the first element in the list, and don't remove it
     * @return the element that is first in the list
     */
    public E first() {
        return list.first();
    }
    
    /**
     * Remove the first element in the list
     * @return the element that is first in the list
     */
    public E dequeue() {
        return list.removeFirst();
    }
    
    /**
     * Display the linked queue
     * @return the linked queue as a string 
     */
    @Override
    public String toString() {
        return list.toString();
    }
}