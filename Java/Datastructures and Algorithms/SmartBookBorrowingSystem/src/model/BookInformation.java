
package model;

/**
 * Model class that stores information about a book
 * @author brand
 */

import data.LinkedQueue;

public class BookInformation {
    
    //the total and available copies of the book
    private int totalCopies, availableCopies;
    //the waitlist of users for the book that uses the self-made linked queue data structure
    private LinkedQueue<String> waitList;
    
    /**
     * The constructor for the books information
     * @param totalCopies the total copies that the library has of said book
     */
    public BookInformation(int totalCopies) {
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        this.waitList = new LinkedQueue<>();
    }
    
    /**
     * Get the amount of available copies for the book
     * @return an int for the amount of available copies
     */
    public int getAvailableCopies() {
        return availableCopies;
    }
    
    /**
     * Reduce the amount of available copies if available copies for the book is available
     */
    public void decrementAvailableCopies() {
        if (availableCopies > 0) {
            availableCopies--;
        }
    }
    
    /**
     * Add available copies available for borrow for the book if the available copies does not exceed the total copies amount for the book
     */
    public void incrementAvailableCopies() {
        if (availableCopies < totalCopies) {
            availableCopies++;
        }
    }
    
    /**
     * Get the wait list of users that is waiting to borrow the book from the library
     * @return a linked queue of the users that want to borrow the book
     */
    public LinkedQueue<String> getWaitList() {
        return waitList;
    }
    
    /**
     * Display the model class's information
     * @return a string that stores the amount of available copies, the total copies and the size of the wait list for the book
     */
    @Override
    public String toString() {
        return "Available: " + availableCopies
                + " Total Copies: " + totalCopies
                + " Waitlist Size: " + waitList.size();
    }
    
}
