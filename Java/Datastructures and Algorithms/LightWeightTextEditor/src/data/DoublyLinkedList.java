package data;


public class DoublyLinkedList<E> {
    
    // ---------Nested Node class---------
    private static class Node<E> {
        
        private E element;
        private Node<E> prev, next;
        
        public Node(E e, Node<E> p, Node<E> n) {
            element = e;
            prev = p;
            next = n;
        }

        public E getElement() {
            return element;
        }

        public void setElement(E e) {
            element = e;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public void setPrev(Node<E> p) {
            prev = p;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> n) {
            next = n;
        }
        
    }
    //---------End nested Node class
    
    private Node<E> header, trailer;
    private int size;
        
    public DoublyLinkedList() {
        header = new Node<>(null, null, null);
        trailer = new Node<>(null, header, null);
        header.setNext(trailer);
    }
    
    public int size() {
        return size;
    }
    
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }
    
    //Returns the first element of the node without removing it
    public E first() {
        if (isEmpty()) {
            return null;
        } else {
            return header.getNext().getElement();
        }
    }
    
    //helper method to add an element to the linked list inbetweeen the given nodes
    private void addBetween(E e, Node<E> prev, Node<E> next) {
        Node<E> newest = new Node<>(e, prev, next);
        prev.setNext(newest);
        next.setPrev(newest);
        size++;
    }
    
    //Add the element to the front of the list
    public void addFirst(E e) {
       addBetween(e, header, header.getNext());
    }
    
    //Add the element to the end of the list
    public void addLast(E e) {
        addBetween(e, trailer.getPrev(), trailer);
    }
    
    //helper method to remove the given node from the list and to return the element
    private E remove(Node<E> node) {
        Node<E> prev = node.getPrev();
        Node<E> next = node.getNext();
        prev.setNext(next);
        next.setPrev(prev);
        size--;
        return node.getElement();
    }
    
    //Removes and returns the first element of the list
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        } else {
            return remove(header.getNext());
        }
    }
    
    //Removes and returns the last element of the list
    public E removeLast() {
        if (isEmpty()) {
            return null;
        } else {
            return remove(trailer.getPrev());
        }
    }
    
    //Method that helps to display the list as a string.
    public String listToString() {
        String result = "";
        Node<E> current = header.getNext();
        boolean first = true;
        
        //Ensure that after the first word has been added, a space should be made between the words in the list
        while (current != trailer) {
            if (!first) {
                result = result + " ";
            }
            
            result = result + current.getElement();
            current = current.getNext();
            first = false;
        }
        
        return result;
    }
    
}
