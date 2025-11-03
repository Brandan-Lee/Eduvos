
package data;

/**
 * A generic data structure of a singly linked list
 * @author brand
 * @param <E> the datatype of the singly linked list
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SinglyLinkedList<E> implements Iterable<E> {
    
    //the head and the tail of the singly linked list is stored as a nested node
    private Node<E> head, tail;
    //the size of the singly linked list
    private int size;
    
    /**
     * Constructor of the singly linked list
     */
    public SinglyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    /**
     * Get the size of the singly linked list
     * @return the size as an int
     */
    public int size() {
        return size;
    }
    
    /**
     * Find out if the singly linked list is empty or not
     * @return true/false
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Get the first node in the singly linked list if it is not empty
     * @return the first node as an element
     */
    public E first() {
        if (isEmpty()) {
            return null;
        }
        
        return head.getElement();
    }
    
    /**
     * Get the last node in the singly linked list if it is not empty
     * @return the last node as an element
     */
    public E last() {
        if (isEmpty()) {
            return null;
        }
        
        return tail.getElement();
    }
    
    /**
     * Add a node to the singly linked list
     * @param element the element that needs to be added to the singly linked list
     */
    public void addFirst(E element) {
        //Create a new head node
        head = new Node<>(element, head);
        
        //if the singly linked list is empty, the tail node is the same as the head node
        if (isEmpty()) {
            tail = head;
        }
        
        //increase the size of the singly linked list
        size++;
    }
    
    /**
     * Add a node to the end of the singly linked list
     * @param element the element that needs to be added to the singly linked list
     */
    public void addLast(E element) {
        //create the new node
        Node<E> newest = new Node(element, null);
        
        //check to see if the singly linked list is empty or not, if yes, the new node becomes the head, else make the new node the tail and update the size of the singly linked list
        if (isEmpty()) {
            head = newest;
        } else {
            tail.setNext(newest);
        }
        
        tail = newest;
        size++;
    }
    
    /**
     * Remove the first node element from the singly linked list
     * @return the first node as an element
     */
    public E removeFirst() {
        //Return a null element if the singly linked list is empty
        if (isEmpty()) {
            return null;
        }
        
        //Retrieve the head node, update the head node to the next element in the singly linked list and decrease the size
        E answer = head.getElement();
        head = head.getNext();
        size--;
        
        //set the tail to null if the singly linked list is empty
        if (isEmpty()) {
            tail = null;
        }
        
        return answer;
    }
    
    /**
     * A helper method to find the previous node
     * @param element the node that is search for
     * @return the node that is before the element
     */
    private Node<E> findPrevious(E element) {
        //check to see if the sinlgy linked list is empty and if the head element is the searched for object and return null
        if (isEmpty() ||  head.getElement().equals(element)) {
            return null;
        }
        
        //Set the current node as the head, loop through the elements until you have finished looping through the whole singly linked list. If the node is found, return the previous node
        Node<E> current = head;
        while (current.getNext() != null) {
            if (current.getNext().getElement().equals(element)) {
                return current;
            }
            
            current = current.getNext();
        }
        
        return null;
    }
    
    /**
     * Remove the node from the singly linked list
     * @param obj the node that needs to be removed from the singly linked list
     * @return true/false
     */
    public boolean remove(Object obj) {
        //check to see if the singly linked list is empty or not
        if (isEmpty()) {
            return false;
        }
        
        E element = (E) obj;
        
        //check to see if the head node is the element that needs to be removed
        if (head.getElement().equals(element)) {
            removeFirst();
            return true;
        }
        
        //get the previous node
        Node<E> prev = findPrevious(element);
        if (prev == null) {
            return false;
        }
        
        //get the node just before the obj node that needs to be removed, and set it as the tail
        Node<E> nodeToRemove = prev.getNext();
        prev.setNext(nodeToRemove.getNext());
        if (nodeToRemove == tail) {
            tail = prev;
        }
        
        size--;
        return true;
    }
    
    /**
     * Find out if the singly linked list contains a certain node or not
     * @param element the element that is searched for
     * @return true/false
     */
    public boolean contains(E element) {
        //get the head node
        Node<E> current = head;
        
        //loop through the singly linked list till the end. first check the head, if its not the head, look at the next node in the singly linked list
        while (current != null) {
            if (current.getElement().equals(element)) {
                return true;
            }
            
            current = current.getNext();
        }
        
        return false;
    }
    
    /**
     * A helper method to iterate over the singly linked list
     * @return an iterator for the singly linked list
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head;
            
            /**
             * check to see if the singly linked list does not just contain a head node
             */
            @Override
            public boolean hasNext() {
                return current != null;
            }
            
            /**
             * get the next node element
             */
            @Override
            public E next() {
                //use the hasnext method to check if the singly linked list only contains a head node
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                
                //set the element to the head node. The element will then change to be the next element after the head node, and that is the node element that will be returned
                E element = current.getElement();
                current = current.getNext();
                return element;
           }
       };
    }
    
    /**
     * Display the singly linked list and all its nodes as a string
     * @return a string of the singly linked list
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        //set the current node to the head
        Node<E> current = head;
        //helper method to keep track of how many node elements are in the singly linked list
        int count = 1;
        
        //loop through the singly linked list till the end. add the count variable as well as the current element to the singly linked list. After that, increase count and get the next element in the singly linked list
        while (current != null) {
            sb.append(count + ". " + current.getElement() + "\n");
            count++;
            current = current.getNext();
        }
        
        return sb.toString();
    }
    
    /**
     * Nested class to store all the elements in the singly linked list as a Node
     * @param <E> generic element
     * @param <Node<E>> link to the next element in the singly linked list
     */
    private static class Node<E> {
        private E element;
        private Node<E> next;
        
        /**
         * Constructor for the nested node class
         * @param element the element that is being created
         * @param next the next element link
         */
        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
        
        /**
         * Get the element of the node
         * @return the element as a generic value
         */
        public E getElement() {
            return element;
        }
        
        /**
         * Update the private element 
         * @param element element that the private element should be updated to
         */
        public void setElement(E element) {
            this.element = element;
        }
        
        /**
         * Get the linked next node
         * @return the linked next node
         */
        public Node<E> getNext() {
            return next;
        }
        
        /**
         * Update the next linked node
         * @param next linked node that the private next node should be updated to
         */
        public void setNext(Node<E> next) {
            this.next = next;
        }
    }
}