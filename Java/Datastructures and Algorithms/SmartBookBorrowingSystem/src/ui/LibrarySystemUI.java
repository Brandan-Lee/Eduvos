package ui;

/**
 * UI class for the Library System
 * @author brand
 */

import core.LibrarySystem;
import java.util.Scanner;

public class LibrarySystemUI {
    
    //the library system
    private static LibrarySystem system;
    //Java API that will be used to receive input from the user
    private static Scanner scanner;
    //helper method to check if the system is still running or not
    private static boolean isRunning;
    
    /**
     * Constructor for the library systems UI. Creates a new library system and scanner object
     */
    public LibrarySystemUI() {
        this.system = new LibrarySystem();
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Start the library Systems UI
     */
    public void start() {
        isRunning = true;
        showWelcomeMessage();
        
        do {
            showMenu();
            processUserInput();
        } while (isRunning);
    }
    
    /**
     * A helper method to get a non-empty string from the user
     * @param prompt the type of string that we want from the user
     * @return the non-empty string
     */
    private String getNonEmptyString(String prompt) {
        String input;
        
        //keep looping until the user enters a non empty string
        do {
            System.out.println(prompt);
            input = scanner.nextLine().trim();
            
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again");
            }
        } while(input.isEmpty());
        
        return input;
    }
    
    /**
     * A helper method that ensures that a positive integer is received from the user
     * @param prompt the type of integer that we want from the user
     * @return the positive integer
     */
    private int getPositiveInteger(String prompt) {
        int parsedInt = -1;
        
        //keep looping until the user enters a postive integer
        while (parsedInt <= 0) {
            System.out.println(prompt);
            
            //Ensure that parsing the string to an integer is successfull and doesn't break the system
            try {
               String input = scanner.nextLine().trim();
               parsedInt = Integer.parseInt(input);
               
               if (parsedInt <= 0) {
                   System.out.println("Invalid input. Please enter a whole number");
               }
            }catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number");
            }
        }
        
        return parsedInt;
    }
    
    /**
     * Greets the user with a welcome message when starting the UI
     */
    private void showWelcomeMessage() {
        System.out.println("===============================================");
        System.out.println("  WELCOME TO THE LIBRARY SYSTEM");
        System.out.println("===============================================");
    }
    
    /**
     * Displays the menu to the user
     */
    private void showMenu() {
        System.out.println("\nMenu:");
        System.out.println("Please select an option: ");
        System.out.println("1. Add book to Library");
        System.out.println("2. Borrow Book: ");
        System.out.println("3. Return Book: ");
        System.out.println("4. Display Book WaitList");
        System.out.println("5. Display all borrowed books: ");
        System.out.println("6. Display full inventory status: ");       
        System.out.println("7. Exit: ");
        System.out.print("Enter your choice (1 - 7): ");
    }
    
    /**
     * A helper method to process the positive integers entered by the user
     */
    private void processUserInput() {
        String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    addBookOperation();
                    break;
                case "2":
                    borrowBookOperation();
                    break;
                case "3":
                    returnBookOperation();
                    break;
                case "4":
                    displayBookWaitListOperation();
                    break;
                case "5":
                    displayBorrowedBooksOperation();
                    break;
                case "6":
                    displayInventoryOperation();
                    break;
                case "7":
                    exit();
                    break;
                default:
                    System.out.println("Invalid option made. Please try again");
            }
    }
    
    /**
     * Allows a book to be added to the library systems inventory
     */
    private void addBookOperation() {
        String title = getNonEmptyString("Please enter the title of the book: ");
        int copies = getPositiveInteger("Please enter the amount of copies available: ");
        
        system.addBook(title, copies);
        System.out.println("\nBook: " + title + "\n" +
                "Copies Available: " + copies + "\n"
                + "Has been successfully added to the library");
    }
    
    /**
     *Allow a user to borrow a book from the library. Output is a predefined string calculated in the library systems class
     */
    private void borrowBookOperation() {
        String title = getNonEmptyString("Please enter the title for the book to be borrowed: ");
        String userId = getNonEmptyString("Please enter the Users ID: ");
        String result = system.borrowBook(title, userId);
        System.out.println("\n" + result);
    }
    
    /**
     * Allows a user to return a book to the library. Output is a predefined string calculated in the library systems class
     */
    private void returnBookOperation() {
        String userId = getNonEmptyString("Please enter the User ID that is returning a book: ");
        String title = getNonEmptyString("Please enter the title of the book that they are returning: ");
        String result = system.returnBook(userId, title);
        System.out.println("\n" + result);
    }
    
    /**
     * Display the wait list for a certain book in the library
     */
    private void displayBookWaitListOperation() {
        String title = getNonEmptyString("Enter the title of the book: ");
        String result = system.displayBookWaitList(title);
        System.out.println("\nWaitlist for " + title + "\n" + result);
    }
    
    /**
     * Display all the books that the library has borrowed out
     */
    private void displayBorrowedBooksOperation() {
        System.out.println("\nDisplaying Borrowed Books: \n");
        String result = system.displayBorrowedBooks();
        System.out.println(result);
    }
    
    /**
     * Display the libraries inventory
     */
    private void displayInventoryOperation() {
        System.out.println("\nDisplaying Library Inventory: \n");
        String result = system.displayInventory();
        System.out.println(result);
    }
    
    /**
     * Exit the system
     */
    private void exit() {
        isRunning = false;
        System.out.println("\nThank you for using the Library System. Goodbye");
        scanner.close();
    }
    
}
