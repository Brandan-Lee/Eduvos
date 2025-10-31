package ui;

import core.LibrarySystem;
import java.util.Scanner;

public class LibrarySystemUI {
    
    private static LibrarySystem system;
    private static Scanner scanner;
    private static boolean isRunning;
    
    public LibrarySystemUI() {
        this.system = new LibrarySystem();
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        isRunning = true;
        showWelcomeMessage();
        
        do {
            showMenu();
            processUserInput();
        } while (isRunning);
    }
     
    private void showWelcomeMessage() {
        System.out.println("===============================================");
        System.out.println("  WELCOME TO THE LIBRARY SYSTEM");
        System.out.println("===============================================");
    }
    
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
                    displayInventory();
                    break;
                case "7":
                    exit();
                    break;
                default:
                    System.out.println("Invalid option made. Please try again");
            }
    }
    
    private void addBookOperation() {
        System.out.print("Please enter the title of the book: ");
        String title = scanner.nextLine();
        
        if (title.trim().isEmpty()) { 
            System.out.println("The title may not be empty");
            return;
        }
        
        System.out.print("Please enter the amount of copies available: ");
        String copies = scanner.nextLine();
        
        int parsedCopies;
        try {
            parsedCopies = Integer.parseInt(copies);
            
            if (parsedCopies <= 0) {
                System.out.println("Invalid copy amount input. Please enter a number greater than 0");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount input. Please enter a whole number");
            return;
        }
        
        system.addBook(title, parsedCopies);
        System.out.println("\nBook: " + title + "\n" +
                "Copies Available: " + parsedCopies + "\n"
                + "Has been successfully added to the library");
    }
    
    private void borrowBookOperation() {
        System.out.print("Please enter the title for the book to be borrowed: ");
        String title = scanner.nextLine();
        
        if (title.trim().isEmpty()) { 
            System.out.println("The title may not be empty");
            return;
        }
        
        System.out.print("Please enter the Users ID: ");
        String userId = scanner.nextLine();
        
        if (userId.trim().isEmpty()) { 
            System.out.println("The Users ID may not be empty");
            return;
        }
        
        String result = system.borrowBook(title, userId);
        System.out.println("\n" + result);
    }
    
    private void returnBookOperation() {
        System.out.print("Please enter the User ID that is returning a book: ");
        String userId = scanner.nextLine();
        
        if (userId.trim().isEmpty()) { 
            System.out.println("The Users ID may not be empty");
            return;
        }
        
        System.out.print("Please enter the title of the book that they are returning: ");
        String title = scanner.nextLine();
        
        if (title.trim().isEmpty()) { 
            System.out.println("The title may not be empty");
            return;
        }
        
        String result = system.returnBook(userId, title);
        System.out.println("\n" + result);
    }
    
    private void displayBookWaitListOperation() {
        System.out.print("Enter the title of the book: ");
        String title = scanner.nextLine();
        
        if (title.trim().isEmpty()) {
            System.out.println("The title may not be empty");
            return;
        }
        
        String result = system.displayBookWaitList(title);
        if (result != null) {
            System.out.println("\nWaitlist for " + title);
            System.out.println(result);
        } 
    }
    
    private void displayBorrowedBooksOperation() {
        System.out.println("\nDisplaying Borrowed Books: \n");
        String result = system.displayBorrowedBooks();
        if (result != null) {
            System.out.println(result);
        }
    }
    
    private void displayInventory() {
        System.out.println("\nDisplaying Library Inventory: \n");
        String result = system.displayInventory();
        if (result != null) {
            System.out.println(result);
        }
    }
    
    private void exit() {
        isRunning = false;
        System.out.println("\nThank you for using the Library System. Goodbye");
        scanner.close();
    }
    
}
