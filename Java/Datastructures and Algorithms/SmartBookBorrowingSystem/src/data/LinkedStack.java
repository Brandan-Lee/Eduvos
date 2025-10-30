package data;


public class LinkedStack<E> {
   
    private SinglyLinkedList<E> list;
    
    public LinkedStack() {
        this.list = new SinglyLinkedList<>();
    }
    
    public int size() {
        return list.size();
    }
    
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    public void push(E element) {
        list.addFirst(element);
    }
    
    public E top() {
        return list.first();
    }
    
    public E pop() {
        return list.removeFirst();
    }
    
}
