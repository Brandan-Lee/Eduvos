
package data;

import java.util.ArrayList;
import java.util.Collections;
import model.Patient;

public class MaxHeapPriorityQueue<T extends Comparable<T>> {
    
    private ArrayList<T> heap;
    
    public MaxHeapPriorityQueue() {
        this.heap = new ArrayList<>();
    }
    
    private int parent(int index) {
        return (index - 1) / 2;
    }
    
    private int leftChild(int index) {
        return 2 * index + 1;
    }
        
    private int rightChild(int index) {
        return 2 * index + 2;
    }
    
    private void swap(int i, int j) {
        Collections.swap(heap, i, j);
    }
    
    public int size() {
        return heap.size();
    }
    
    public boolean isEmpty() {
        return heap.isEmpty();
    }
    
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        
        return heap.get(0);
    }
    
    public void add(T value) {
        heap.add(value);
        bubbleUp(heap.size() - 1);
    }
    
    public T poll() {
        if (heap.isEmpty()) {
            return null;
        }
        
        T highestPriorityItem = heap.get(0);
        
        if (heap.size() == 1) {
            heap.remove(0);
            return highestPriorityItem;
        }
        
        T lastItem = heap.remove(heap.size() - 1);
        heap.set(0, lastItem);
        bubbleDown(0);
        
        return highestPriorityItem;
    }
    
    public String printHeap() {
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < heap.size(); i++) {
            sb.append((i + 1) + ": " + heap.get(i).toString() + "\n");
        }
        
        return sb.toString();
    }
    
    private void bubbleUp(int currentIndex) {
        while (currentIndex > 0 && heap.get(currentIndex).compareTo(heap.get(parent(currentIndex))) > 0) {
            swap(currentIndex, parent(currentIndex));
            currentIndex = parent(currentIndex);
        }
    }
    
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
