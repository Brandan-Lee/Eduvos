
package data;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SinglyLinkedList<E> implements Iterable<E> {
    
    private Node<E> head, tail;
    private int size;
    
    public SinglyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    public int size() {
        return size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public E first() {
        if (isEmpty()) {
            return null;
        }
        
        return head.getElement();
    }
    
    public E last() {
        if (isEmpty()) {
            return null;
        }
        
        return tail.getElement();
    }
    
    public void addFirst(E element) {
        head = new Node<>(element, head);
        
        if (isEmpty()) {
            tail = head;
        }
        
        size++;
    }
    
    public void addLast(E element) {
        Node<E> newest = new Node(element, null);
        
        if (isEmpty()) {
            head = newest;
        } else {
            tail.setNext(newest);
        }
        
        tail = newest;
        size++;
    }
    
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        
        E answer = head.getElement();
        head = head.getNext();
        size--;
        
        if (isEmpty()) {
            tail = null;
        }
        
        return answer;
    }
    
    public boolean remove(Object obj) {
        if (isEmpty()) {
            return false;
        }
        
        if (head.getElement().equals(obj)) {
            removeFirst();
            return true;
        }
        
        Node<E> current = head;
        while (current.getNext() != null) {
            if (current.getElement().equals(obj)) {
                if (current.getNext() == tail) {
                    tail = current;
                }
                
                current.setNext(current.getNext().getNext());
                size--;
                return true;
            }
            current = current.getNext();
        }
        
        return false;
    }

    @Override
    public Iterator<E> iterator() {
       return new Iterator<E>() {
           private Node<E> current = head;
           
           @Override
           public boolean hasNext() {
               return current != null;
           }

           @Override
           public E next() {
               if (!hasNext()) {
                   throw new NoSuchElementException();
               }
               
               E element = current.getElement();
               current = current.getNext();
               return element;
           }
       };
    }
    
    public boolean contains(E element) {
        Node<E> current = head;
        
        while (current != null) {
            if (current.getElement().equals(element)) {
                return true;
            }
            
            current = current.getNext();
        }
        
        return false;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<E> current = head;
        int count = 1;
        
        while (current != null) {
            sb.append(count + ". " + current.getElement() + "\n");
            count++;
            current = current.getNext();
        }
        
        return sb.toString();
    }
    
    
    private static class Node<E> {
        private E element;
        private Node<E> next;
        
        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
        
        public E getElement() {
            return element;
        }
        
        public void setElement(E element) {
            this.element = element;
        }
        
        public Node<E> getNext() {
            return next;
        }
        
        public void setNext(Node<E> next) {
            this.next = next;
        }
    }
    
}
