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
    
    private String getNonEmptyString(String prompt) {
        String input;
        
        do {
            System.out.println(prompt);
            input = scanner.nextLine().trim();
            
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again");
            }
        } while(input.isEmpty());
        
        return input;
    }
    
    private int getPositiveInteger(String prompt) {
        int parsedInt = -1;
        
        while (parsedInt <= 0) {
            System.out.println(prompt);
            
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
                    displayInventoryOperation();
                    break;
                case "7":
                    exit();
                    break;
                default:
                    System.out.println("Invalid option made. Please try again");
            }
    }
    
    private void addBookOperation() {
        String title = getNonEmptyString("Please enter the title of the book: ");
        int copies = getPositiveInteger("Please enter the amount of copies available: ");
        
        system.addBook(title, copies);
        System.out.println("\nBook: " + title + "\n" +
                "Copies Available: " + copies + "\n"
                + "Has been successfully added to the library");
    }
    
    private void borrowBookOperation() {
        String title = getNonEmptyString("Please enter the title for the book to be borrowed: ");
        String userId = getNonEmptyString("Please enter the Users ID: ");
        String result = system.borrowBook(title, userId);
        System.out.println("\n" + result);
    }
    
    private void returnBookOperation() {
        String userId = getNonEmptyString("Please enter the User ID that is returning a book: ");
        String title = getNonEmptyString("Please enter the title of the book that they are returning: ");
        String result = system.returnBook(userId, title);
        System.out.println("\n" + result);
    }
    
    private void displayBookWaitListOperation() {
        String title = getNonEmptyString("Enter the title of the book: ");
        String result = system.displayBookWaitList(title);
        System.out.println("\nWaitlist for " + title + "\n" + result);
    }
    
    private void displayBorrowedBooksOperation() {
        System.out.println("\nDisplaying Borrowed Books: \n");
        String result = system.displayBorrowedBooks();
        System.out.println(result);
    }
    
    private void displayInventoryOperation() {
        System.out.println("\nDisplaying Library Inventory: \n");
        String result = system.displayInventory();
        System.out.println(result);
    }
    
    private void exit() {
        isRunning = false;
        System.out.println("\nThank you for using the Library System. Goodbye");
        scanner.close();
    }
    
}
