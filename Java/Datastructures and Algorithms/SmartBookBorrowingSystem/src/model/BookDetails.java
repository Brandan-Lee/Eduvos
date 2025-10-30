
package model;

import data.LinkedQueue;

public class BookDetails {
    
    private int totalCopies, availableCopies;
    private LinkedQueue<String> waitList;
    
    public BookDetails(int totalCopies) {
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        this.waitList = new LinkedQueue<>();
    }
    
    public int getAvailableCopies() {
        return availableCopies;
    }
    
    public void decrementAvailableCopies() {
        if (availableCopies > 0) {
            availableCopies--;
        }
    }
    
    public void incrementAvailableCopies() {
        if (availableCopies < totalCopies) {
            availableCopies++;
        }
    }
    
    public LinkedQueue<String> getWaitList() {
        return waitList;
    }
    
    @Override
    public String toString() {
        return "Available: " + availableCopies
                + "Total Copies: " + totalCopies
                + "Waitlist Size: " + waitList.size();
    }
    
    
    
}
