
package data;

public class LinkedQueue<E> {
    
    private SinglyLinkedList<E> list;
    
    public LinkedQueue() {
        this.list = new SinglyLinkedList<>();
    }
    
    public int size() {
        return list.size();
    }
    
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    public void enQueue(E element) {
        list.addLast(element);
    }
    
    public E first() {
        return list.first();
    }
    
    public E dequeue() {
        return list.removeFirst();
    }
    
    @Override
    public String toString() {
        return list.toString();
    }
    
}
