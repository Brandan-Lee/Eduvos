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
    
    public E first() {
        if (isEmpty()) {
            return null;
        } else {
            return header.getNext().getElement();
        }
    }
//    
//    public E last() {
//        if (isEmpty()) {
//            return null;
//        } else {
//            return trailer.getPrev().getElement();
//        }
//    }
    
    private void addBetween(E e, Node<E> prev, Node<E> next) {
        Node<E> newest = new Node<>(e, prev, next);
        prev.setNext(newest);
        next.setPrev(newest);
        size++;
    }
    
    public void addFirst(E e) {
       addBetween(e, header, header.getNext());
    }
    
    public void addLast(E e) {
        addBetween(e, trailer.getPrev(), trailer);
    }
    
    private E remove(Node<E> node) {
        Node<E> prev = node.getPrev();
        Node<E> next = node.getNext();
        prev.setNext(next);
        next.setPrev(prev);
        size--;
        return node.getElement();
    }
    
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        } else {
            return remove(header.getNext());
        }
    }
    
    public E removeLast() {
        if (isEmpty()) {
            return null;
        } else {
            return remove(trailer.getPrev());
        }
    }
    
    public String listToString() {
        StringBuilder result = new StringBuilder();
        Node<E> current = header.getNext();
        boolean first = true;
        
        while (current != trailer) {
            if (!first) {
                result.append(" ");
            }
            
            result.append(current.getElement());
            current = current.getNext();
            first = false;
        }
        
        return result.toString();
    }
    
    //Todo implement iterator
    
}
