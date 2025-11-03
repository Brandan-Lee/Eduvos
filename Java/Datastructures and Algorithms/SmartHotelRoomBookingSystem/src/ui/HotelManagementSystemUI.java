
package ui;

import core.HotelManagementSystem;
import java.util.Scanner;

/**
 * UI class for hotel system
 * @author brand
 */
public class HotelManagementSystemUI {
    
    //the hotel management system
    private static HotelManagementSystem system;
    //Java API that will be used to receive input from the user
    private static Scanner scanner;
    //helper method to check if the system is still running or not
    private static boolean isRunning;
    
    public HotelManagementSystemUI() {
        this.scanner = new Scanner(System.in);
    }
    
     /**
     * Start the hotel management systems UI
     */
    public void start() {
        isRunning = true;
        showWelcomeMessage();
        
        //Initialize the hotel
        int totalFloors = getPositiveInteger("Please enter the total number of floors: ");
        int totalRooms = getPositiveInteger("Please enter the total number of rooms: ");
        system = new HotelManagementSystem(totalFloors, totalRooms);
        
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
            System.out.print(prompt);
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
            System.out.print(prompt);
            
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
        System.out.println("WELCOME TO THE HOTEL MANAGEMENT SYSTEM");
        System.out.println("===============================================");
    }
    
    /**
     * Displays the menu to the user
     */
    private void showMenu() {
        System.out.println("\nMenu:");
        System.out.println("Please select an option: ");
        System.out.println("1. Check-in Guest: ");
        System.out.println("2. Check-out Guest: ");
        System.out.println("3. Display the last 10 guests that have checked out of the hotel: ");
        System.out.println("4. Generate daily report: ");
        System.out.println("5. Exit: ");
        System.out.print("Enter your choice (1 - 5): ");
    }
    
    /**
     * A helper method to process the positive integers entered by the user
     */
    private void processUserInput() {
        String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    checkInGuestOperation();
                    break;
                case "2":
                    checkOutGuestOperation();
                    break;
                case "3":
                    printCheckOutHistoryOperation();
                    break;
                case "4":
                    generateDailyReportOperation();
                    break;
                case "5":
                    exit();
                    break;
                default:
                    System.out.println("Invalid option made. Please try again");
            }
    }
    
    /**
     * Check a guest into the hotel room
     */
    private void checkInGuestOperation() {
        String guestName = getNonEmptyString("Please enter the guests name: ");
        String guestId = getNonEmptyString("Please enter the guests ID: ");
        String result = system.checkInGuest(guestName, guestId);
        System.out.println(result);
    }
    
    /**
     * Check a guest out of the hotel
     */
    private void checkOutGuestOperation() {
        int floorNumber = getPositiveInteger("Please enter the floor number: ");
        int roomNumber = getPositiveInteger("Please enter the rooms number: ");
        String result = system.checkOutGuest(floorNumber, roomNumber);
        System.out.println(result);
    }
    
    /**
     * Display the check out history of the last 10 guests to the system
     */
    private void printCheckOutHistoryOperation() {
        System.out.println("\nCheck out History of the last 10 guests");
        system.printCheckoutHistory();
    }
    
    /**
     * Display a daily report to the user
     */
    private void generateDailyReportOperation() {
        System.out.println("\nDaily Report");
        system.generateDailyReport();
    }
    
     /**
     * Exit the system
     */
    private void exit() {
        isRunning = false;
        System.out.println("\nThank you for using the Hotel Management System. Goodbye");
        scanner.close();
    }
}
