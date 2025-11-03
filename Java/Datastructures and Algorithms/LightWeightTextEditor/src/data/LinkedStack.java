package data;

/**
 * A generic data structure of a linked stack
 */

import data.DoublyLinkedList;

public class LinkedStack<E> implements Stack<E> {
    
    private DoublyLinkedList<E> list;
    private int maxSize;
    
    /**
     * Default constructor to allow infinite values to be stored
     * List is being initialized as a new instance of doubly linked list
     * max size can be infinite value
     */
    public LinkedStack() {
        this.list = new DoublyLinkedList<>();
        maxSize = Integer.MAX_VALUE;
    }
    
    /**
     * Constructor that sets up the max size of the stack
     * List is being initialized as a new instance of doubly linked list
     * @param maxSize the maxSize of the stack
     */
    public LinkedStack(int maxSize) {
        this.list = new DoublyLinkedList<>();
        this.maxSize = maxSize;
    }
    
    /**
     * Get the size of the list
     * @return the size of the list as an int
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * Find out if the list is empty or not
     * @return true / false
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    /**
     * Add the element to the top of the stack if the size of the stack does not exceed the max value
     * remove the oldest element in the stack when it exceeds the max values size
     * @param element the element that needs to be added 
     */
    @Override
    public void push(E element) {
        if (size() >= maxSize) {
            list.removeLast();
        }
        
        list.addFirst(element);
    }
    
    /**
     * Retrieve the first element in the stack (but does not remove)
     * @return the first element in the stack
     */
    @Override
    public E top() {
        return list.first();
    }
    
    /**
     * Retrieve the first element in the stack and removes it
     * @return the first element in the stack
     */
    @Override
    public E pop() {
        return list.removeFirst();
    }
    
    /**
     * Retrieve the maxSize that the stack may be
     * @return the max size as an int
     */
    @Override
    public int getMaxSize() {
        return maxSize;
    }
}
