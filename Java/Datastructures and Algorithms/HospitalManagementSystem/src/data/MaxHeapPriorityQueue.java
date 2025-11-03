
package data;

/**
 * Generic data structure class of a max heap priority queue
 */

import java.util.ArrayList;
import java.util.Collections;

public class MaxHeapPriorityQueue<T extends Comparable<T>> {
    
    //Store the heap
    private ArrayList<T> heap;
    
    /**
     * Constructor that creates a new arraylist for the heap
     */
    public MaxHeapPriorityQueue() {
        this.heap = new ArrayList<>();
    }
    
    /**
     * Get the parent index of the heap
     * @param index the currentIndex of the heap
     * @return the index of the parent
     */
    private int parent(int index) {
        return (index - 1) / 2;
    }
    
    /**
     * Get the left child index of the heap
     * @param index the current index of the heap
     * @return the index of the left child
     */
    private int leftChild(int index) {
        return 2 * index + 1;
    }
    
    /**
     * Get the right child index of the heap
     * @param index the current index of the heap
     * @return the index of the right child
     */
    private int rightChild(int index) {
        return 2 * index + 2;
    }
    
    /**
     * helper method to swap elements in the heap around
     * @param i element should be swapped with
     * @param j element to swap
     */
    private void swap(int i, int j) {
        Collections.swap(heap, i, j);
    }
    
    /**
     * Get the size of the heap
     * @return the size as an int
     */
    public int size() {
        return heap.size();
    }
    
    /**
     * Find out if the heap is empty or not
     * @return true/false
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }
    
    /**
     * Return but do not remove the root element of the heap
     * @return the element at the root
     */
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        
        return heap.get(0);
    }
    
    /**
     * Add an element to the heap at the bottom of the heap
     * @param value the value that should be added to the heap
     */
    public void add(T value) {
        heap.add(value);
        bubbleUp(heap.size() - 1);
    }
    
    /**
     * Remove the root of the heap
     * @return the element at the root of the node
     */
    public T poll() {
        if (heap.isEmpty()) {
            return null;
        }
        
        //get the root of the heap
        T highestPriorityItem = heap.get(0);
        
        //check to see if the only element in the heap is the root and return it
        if (heap.size() == 1) {
            heap.remove(0);
            return highestPriorityItem;
        }
        
        //get the last item in the heap, set it to the root, and then bubble down
        T lastItem = heap.remove(heap.size() - 1);
        heap.set(0, lastItem);
        bubbleDown(0);
        
        return highestPriorityItem;
    }
    
    /**
     * Get the heap as a string
     * @return the heap as a string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < heap.size(); i++) {
            sb.append((i + 1) + ": " + heap.get(i).toString() + "\n");
        }
        
        return sb.toString();
    }
    
    /**
     * helper method that checks and compares the current index and uses the swap helper method
     * @param currentIndex currentIndex of the heap
     */
    private void bubbleUp(int currentIndex) {
        while (currentIndex > 0 && heap.get(currentIndex).compareTo(heap.get(parent(currentIndex))) > 0) {
            swap(currentIndex, parent(currentIndex));
            currentIndex = parent(currentIndex);
        }
    }
    
    /**
     * helper method that ensures the root node is the largest
     * @param currentIndex the currentIndex of the heap
     */
    private void bubbleDown(int currentIndex) {
        while (true) {
            int left = leftChild(currentIndex);
            int right = rightChild(currentIndex);
            int currentLargest = currentIndex;

            //check the left child
            if (left < heap.size() && heap.get(left).compareTo(heap.get(currentLargest)) > 0) {
               currentLargest = left;
            }
            
            //check the right child
            if (right < heap.size() && heap.get(right).compareTo(heap.get(currentLargest)) > 0) {
                currentLargest = right;
            }
            
            if (currentLargest == currentIndex) {
                break;
            }
            
            swap(currentIndex, currentLargest);
            currentIndex = currentLargest;
        }
    }
    
}
