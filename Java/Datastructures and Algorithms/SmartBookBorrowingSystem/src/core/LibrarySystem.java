
package core;

/**
 * System class for the library system
 * @author brand
 */

import data.HashMap;
import data.SinglyLinkedList;
import model.BookInformation;

public class LibrarySystem {
    
    //Hashmap stores the String (title of the book as the key), and a custom model class (details of that book) for the inventory of the library
    private HashMap<String, BookInformation> library;
    //Hashmap that stores the userId (String) and the keys of the library system in the singly linked list to show the books that that user has borrowed
    private HashMap<String, SinglyLinkedList<String>> booksBorrowed;
    
    /**
     * Constructor for the library system. Creates new and empty hashmaps for library and booksborrowed hashmaps
     */
    public LibrarySystem() {
        this.library = new HashMap<>();
        this.booksBorrowed = new HashMap<>();
    }
    
    /**
     * Helper method to make the key generic, meaning lowercase, which allows insensitive case handling
     * @param title the title of the book
     * @return the title of the book but as lower case
     */
    private String getNormalizedKey(String title) {
        return title.trim().toLowerCase();
    }
    
    /**
     * A helper method to get the books details that is associated with the title (key) in the library hashmap
     * @param title the title of the book
     * @return the information associated with that book
     */
    private BookInformation getBookDetails(String title) {
        String normalizedKey = getNormalizedKey(title);
        return library.get(normalizedKey);
    }
    
    /**
     * Helper method to record the borrowing of a books process
     * @param userId the user that will be borrowing the book
     * @param title the title of the book that the user is borrowing
     */
    private void recordBorrowing(String userId, String title) {
        SinglyLinkedList<String> userList = booksBorrowed.get(userId);
        
        if (userList == null) {
            userList = new SinglyLinkedList<>();
            booksBorrowed.put(userId, userList);
        }
        
        userList.addLast(title);
    }
    
    /**
     * Add the book to the library, but ensure that the input is valid before adding to the library hashmap
     * @param key the title of the book
     * @param copies the amount of copies associated with that book
     */
    public void addBook(String key, int copies) {
        if (key.trim().isEmpty() || copies <= 0) {
            return;
        }
        
        String normalizedKey = getNormalizedKey(key);
        library.put(normalizedKey, new BookInformation(copies));
    }
    
    /**
     * Allow the user to borrow a book from the library
     * @param title the title of the book
     * @param userId the users id thats borowing the book
     * @return the outcome of the book borrowing process as a string
     */
    public String borrowBook(String title, String userId) {
        BookInformation details = getBookDetails(title);
        
        //check to see if there are any book title storied in the libraries inventory
        if (details == null) {
            return "Error: Book " + title + " is not in the library";
        }
        
        //the book is availiable to borrow
        if (details.getAvailableCopies() > 0) {
            details.decrementAvailableCopies();
            recordBorrowing(userId, title);
            return "Book: " + title + " has been successfully borrowed to: " + userId + "\n" +
                "Title: " + title + " has " + details.getAvailableCopies() + " extra available";
        } else {
            //book is not available, add the user to the books waitlist
            details.getWaitList().enQueue(userId);
            return "Waitlist: " + title + " has no available copies. " + userId + " added to the waitlist";
        }
    }
    
    /**
     * Allow a user to return a book to the library
     * @param userId the user that has borrowed the book
     * @param title the title of the book that was borrowed
     * @return the outcome of the return book process as a string
     */
    public String returnBook(String userId, String title) {
        BookInformation details = getBookDetails(title);
        
        //check to see if there are any book title stored in the libraries inventory
        if (details == null) {
            return "Book " + title + " is not in the library inventory";
        }
        
        //Get the books that the user has borrowed
        SinglyLinkedList<String> userBorrowedList = booksBorrowed.get(userId);
        //the title of the book is not associated with that user
        if (userBorrowedList == null || !userBorrowedList.contains(title)) {
            return "User ID: " + userId + " did not borrow the book: " + title + ". Check their borrowing record";
        }
        
        //the book title is removed from the users borrow list
        userBorrowedList.remove(title);
        
        //check to see if there is a waitlist for that book, if not, return it to inventory, and update the amount of available copies
        if (details.getWaitList().isEmpty()) {
            details.incrementAvailableCopies();
            return "Return: " + title + " returned by " + userId + ". Copies on shelf: " + details.getAvailableCopies();
        } else {
            //there is a waitlist for the book. assign the book to the first person in the waitlist queue
            String nextUserId = details.getWaitList().dequeue();
            recordBorrowing(nextUserId, title);
            return "Transfer: " + title + " returned by " + userId + " and immediately assigned to " + nextUserId;
        }
    }
    
    /**
     * Display the wait list for the book
     * @param title the title of the book whose wait list you want to retrieve
     * @return the wait list for that specific book as a string
     */
    public String displayBookWaitList(String title) {
        BookInformation details = getBookDetails(title);
        
        //check to see if there are any book title storied in the libraries inventory
        if (details == null) {
            return "Book " + title + " is not in the library inventory";
        }
        
        //check to see if there is a wait list for that book
        if (details.getWaitList().isEmpty()) {
            return "There is no waitlist for " + title;
        }
        
        //there is a wait list for the book, display it to the user
        return details.getWaitList().toString();
    }
    
    /**
     * Display the books that the library has borrowed
     * @return the books that the library has borrowed as a string
     */
    public String displayBorrowedBooks() {
        //check to see if a book has been borrowed from the library
        if (booksBorrowed.isEmpty()) {
            return "No books have been borrowed to any user";
        }
        
        //books has been borrowed, display the hashmap
        return booksBorrowed.toString();
    }
    
    /**
     * Display the books in the library
     * @return the inventory of the library
     */
    public String displayInventory() {
        //check to see if books has been added to the library
        if (library.isEmpty()) {
            return "The library is empty";
        }
        
        //books have been added, display the hashmap.
        return library.toString();
    }
    
}
