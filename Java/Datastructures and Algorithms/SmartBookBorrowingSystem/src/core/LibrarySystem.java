
package core;

import data.HashMap;
import data.SinglyLinkedList;
import model.BookInformation;

public class LibrarySystem {
    
    private HashMap<String, BookInformation> library;
    private HashMap<String, SinglyLinkedList<String>> booksBorrowed;
    
    public LibrarySystem() {
        this.library = new HashMap<>();
        this.booksBorrowed = new HashMap<>();
    }
    
    private String getNormalizedKey(String title) {
        return title.trim().toLowerCase();
    }
    
    private BookInformation getBookDetails(String title) {
        String normalizedKey = getNormalizedKey(title);
        return library.get(normalizedKey);
    }
    
    private void recordBorrowing(String userId, String title) {
        SinglyLinkedList<String> userList = booksBorrowed.get(userId);
        
        if (userList == null) {
            userList = new SinglyLinkedList<>();
            booksBorrowed.put(userId, userList);
        }
        
        userList.addLast(title);
    }
    
    public void addBook(String key, int copies) {
        if (key.trim().isEmpty() || copies <= 0) {
            return;
        }
        
        String normalizedKey = getNormalizedKey(key);
        library.put(normalizedKey, new BookInformation(copies));
    }

    public String borrowBook(String title, String userId) {
        BookInformation details = getBookDetails(title);
        
        if (details == null) {
            return "Error: Book " + title + " is not in the library";
        }
        
        if (details.getAvailableCopies() > 0) {
            details.decrementAvailableCopies();
            recordBorrowing(userId, title);
            return "Book: " + title + " has been successfully borrowed to: " + userId + "\n" +
                "Title: " + title + " has " + details.getAvailableCopies() + " extra available";
        } else {
            details.getWaitList().enQueue(userId);
            return "Waitlist: " + title + " has no available copies. " + userId + " added to the waitlist";
        }
    }
    
    

    public String returnBook(String userId, String title) {
        BookInformation details = getBookDetails(title);
        
        if (details == null) {
            return "Book " + title + " is not in the library inventory";
        }
        
        SinglyLinkedList<String> userBorrowedList = booksBorrowed.get(userId);
        
        if (userBorrowedList == null || !userBorrowedList.contains(title)) {
            return "User ID: " + userId + " did not borrow the book: " + title + ". Check their borrowing record";
        }
        
        userBorrowedList.remove(title);
        
        if (details.getWaitList().isEmpty()) {
            details.incrementAvailableCopies();
            return "Return: " + title + " returned by " + userId + ". Copies on shelf: " + details.getAvailableCopies();
        } else {
            String nextUserId = details.getWaitList().dequeue();
            recordBorrowing(nextUserId, title);
            return "Transfer: " + title + " returned by " + userId + " and immediately assigned to " + nextUserId;
        }
        
    }

    public String displayBookWaitList(String title) {
        BookInformation details = getBookDetails(title);
        
        if (details == null) {
            return "Book " + title + " is not in the library inventory";
        }
        
        if (details.getWaitList().isEmpty()) {
            return "There is no waitlist for " + title;
        }
        
        return details.getWaitList().toString();
    }
    
    public String displayBorrowedBooks() {
        if (booksBorrowed.isEmpty()) {
            return "No books have been borrowed to any user";
        }
        
        return booksBorrowed.toString();
    }
    
    public String displayInventory() {
        if (library.isEmpty()) {
            return "The libray is empty";
        }
        
        return library.toString();
    }
    
}
