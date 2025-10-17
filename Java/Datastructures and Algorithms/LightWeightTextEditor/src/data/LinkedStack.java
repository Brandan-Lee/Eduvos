package data;


import data.DoublyLinkedList;


public class LinkedStack<E> implements Stack<E> {
    
    private DoublyLinkedList<E> list;
    private int maxSize;
    
    public LinkedStack() {
        this.list = new DoublyLinkedList<>();
        maxSize = Integer.MAX_VALUE;
    }
    
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

    @Override
    public void push(E e) {
        
        if (list.size() >= maxSize) {
            list.removeLast();
        }
        
        list.addFirst(e);
        
    }

    @Override
    public E top() {
        return list.first();
    }

    @Override
    public E pop() {
        return list.removeFirst();
    }
    
    @Override
    public int getMaxSize() {
        return maxSize;
    }
    
}
