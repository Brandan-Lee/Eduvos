package data;


import data.DoublyLinkedList;


public class LinkedStack<E> implements Stack<E> {
    
    private DoublyLinkedList<E> list;
    private int maxSize;
    
    //Default constructor where I allow infinite values to be stored
    public LinkedStack() {
        this.list = new DoublyLinkedList<>();
        maxSize = Integer.MAX_VALUE;
    }
    
    //Constructor that sets up the max size of the stack
    public LinkedStack(int m) {
        this.list = new DoublyLinkedList<>();
        maxSize = m;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    //Add the element to the top of the stack if the size of the stack does not exceed the max value
    //remove the oldest element in the stack when it exceeds the max values size
    @Override
    public void push(E e) {
        
        if (size() >= maxSize) {
            list.removeLast();
        }
        
        list.addFirst(e);
        
    }
    
    //Retrieve the first element in the stack (but does not remove)
    @Override
    public E top() {
        return list.first();
    }
    
    //Retrieve the first element in the stack and removes it
    @Override
    public E pop() {
        return list.removeFirst();
    }
    
    @Override
    public int getMaxSize() {
        return maxSize;
    }
    
}
