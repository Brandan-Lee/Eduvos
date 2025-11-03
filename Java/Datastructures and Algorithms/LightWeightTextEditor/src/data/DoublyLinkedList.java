package data;

/**
 * a generic data structure for the doubly linked list
 * @author brand
 * @param <E> 
 */

public class DoublyLinkedList<E> {
    
    // ---------Nested Node class---------
    private static class Node<E> {
        
        private E element;
        private Node<E> prev, next;
        
        /**
         * Constructor for the nested node class
         * @param e the element that needs to be added to the doubly linked list
         * @param p the link to the previous node in the doubly linked list
         * @param n the link to the next node in the doubly linked list
         */
        public Node(E element, Node<E> prev, Node<E> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
        
        /**
         * Get the element of the node
         * @return the element of the node
         */
        public E getElement() {
            return element;
        }
        
        /**
         * Set the element of the node
         * @param element the updated element of the node
         */
        public void setElement(E element) {
            this.element = element;
        }
        
        /**
         * get the prev linked node in the doubly linked list
         * @return the prev linked node in the doubly linked list
         */
        public Node<E> getPrev() {
            return prev;
        }
        
        /**
         * set the prev linked node in the doubly linked list
         * @param p the updated prev linked node
         */
        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }
        
        /**
         * Get the next linked node in the doubly linked list
         * @return the next linked node
         */
        public Node<E> getNext() {
            return next;
        }
        
        /**
         * set the next linked node in the doubly linked list
         * @param n 
         */
        public void setNext(Node<E> next) {
            this.next = next;
        }
    }
    //---------End nested Node class
    
    private Node<E> header, trailer;
    private int size;
    
    /**
     * Constructor for the doubly linked list
     * header is null
     * trailer is null and the previous linked node is the header
     * the headers next node is the trailer
     */
    public DoublyLinkedList() {
        header = new Node<>(null, null, null);
        trailer = new Node<>(null, header, null);
        header.setNext(trailer);
    }
    
    /**
     * Get the size of the doubly linked list
     * @return the size of the doubly linked list as an integer
     */
    public int size() {
        return size;
    }
    
    /**
     * Find out if the doubly linked list is empty or not
     * @return true / false;
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Returns the first element of the node without removing it
     * @return the element after the header sentinental node
     */
    public E first() {
        if (isEmpty()) {
            return null;
        }
        
        return header.getNext().getElement();
    }
    
    /**
     * helper method to add an element to the linked list inbetweeen the given nodes
     * @param element the element that needs to be inserted
     * @param prev the prev node link
     * @param next the next node link
     */
    private void addBetween(E element, Node<E> prev, Node<E> next) {
        Node<E> newest = new Node<>(element, prev, next);
        prev.setNext(newest);
        next.setPrev(newest);
        size++;
    }
    
    /**
     * Add the element to the front of the list
     * @param e  the element that needs to be added to the front of the doubly linked list
     */
    public void addFirst(E element) {
       addBetween(element, header, header.getNext());
    }
    
    /**
     * Add the element to the end of the list
     * @param element the element that needs to be added to the end of the doubly linked list
     */
    public void addLast(E element) {
        addBetween(element, trailer.getPrev(), trailer);
    }
    
    /**
     * helper method to remove the given node from the double linked list and to return the element
     * @param node the node that needs to be remove from the doubly linked list
     * @return the element of the node that has been removed
     */
    private E remove(Node<E> node) {
        Node<E> prev = node.getPrev();
        Node<E> next = node.getNext();
        prev.setNext(next);
        next.setPrev(prev);
        size--;
        return node.getElement();
    }
    
    /**
     * Removes and returns the first element of the list
     * @return the element of the first node in the doubly linked list
     */
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        } 
        
        return remove(header.getNext());
    }
    
    /**
     * Removes and returns the last element of the list
     * @return the element of the node 
     */
    public E removeLast() {
        if (isEmpty()) {
            return null;
        } 
        
        return remove(trailer.getPrev());
    }
    
    /**
     * Display the doubly linked list
     * @return the doubly linked list as a string
     */
    @Override
    public String toString() {
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
