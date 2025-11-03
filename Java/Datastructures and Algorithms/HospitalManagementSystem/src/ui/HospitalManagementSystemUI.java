
package ui;

/**
 * The main UI class for the Hospital Management system
 */

import core.HospitalManagementSystem;
import java.util.Scanner;
import model.Patient;

public class HospitalManagementSystemUI {
    
    //an instance of the hospital management system
    private static HospitalManagementSystem system;
    //Java api to receive user input
    private static Scanner scanner;
    //method that monitors if the program is running or not
    private static boolean isRunning;
    
    /**
     * Constructor that creates a new object of the Hospital Management System and the scanner
     */
    public HospitalManagementSystemUI() {
        this.system = new HospitalManagementSystem();
        this.scanner = new Scanner(System.in);
    }
    
     /**
     * Start the Hospital Management systems UI
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
     * Greets the user with a welcome message when starting the UI
     */
     private void showWelcomeMessage() {
        System.out.println("===============================================");
        System.out.println("  WELCOME TO THE HOSPITAL MANAGEMENT SYSTEM");
        System.out.println("===============================================");
    }
     
     /**
     * Displays the menu to the user
     */
    private void showMenu() {
        System.out.println("\nMenu:");
        System.out.println("Please select an option: ");
        System.out.println("1. Insert Patient");
        System.out.println("2. Get Next Patient");
        System.out.println("3. Show Patient Queue");
        System.out.println("4. View Next Patient");
        System.out.println("5. Queue length");
        System.out.println("6. Exit");
        System.out.print("Enter your choice (1 - 6): ");
    }
    
    /**
     * A helper method that displays a result or error of an objects operation
     * @param <T> the datatype
     * @param result the result of the data type
     * @param successMessage the success message if the operation was a success or not
     * @param operationName the name of the operation to be performed
     */
    private <T> void displayResultOrError(T result, String successMessage, String operationName) {
        if (result == null) {
            System.out.println("Operation failed: The queue is likely empty or an error occurred during " + operationName + ".");
        } else {
            System.out.println(successMessage + result.toString());
        }
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
     * A helper method to process the positive integers entered by the user
     */
    private void processUserInput() {
        String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    insertPatientOperation();
                    break;
                case "2":
                    nextPatientOperation();
                    break;
                case "3":
                    viewPatientQueueOperation();
                    break;
                case "4":
                    viewNextPatientOperation();
                    break;
                case "5":
                    getQueueLengthOperation();
                    break;
                case "6":
                    exit();
                    break;
                default:
                    System.out.println("Invalid option made. Please try again");
            }
    }
    
    /**
     * A helper method that gets input about the user
     * @return a new patient object containing the name and priority of the patient
     */
    private Patient getPatientInput() {
        String name = getNonEmptyString("Please enter the patients name: ");
        int priority = getPositiveInteger("Please enter the priority of the patient (0 - 10): ");
        
        //ensure that the priority of the patient is between 0 and 10
        if (priority < 0 || priority > 10) {
            System.out.println("Priority must be between 0 and 10");
            return null;
        }
        
        return new Patient(name, priority);
    }
    
    /**
     * Allows the user to insert a patient into the system
     */
    private void insertPatientOperation() {
        Patient patient = getPatientInput();
        
        //Ensure that the patient has been created and add the patient to the system
        if (patient != null) {
            system.addPatient(patient);
            System.out.println("\n" + patient.toString());
            System.out.println("Has Been Added Successfully!");
        }
    }
    
    /**
     * Allows the user to get the next patient in the max heap priority queue
     */
    private void nextPatientOperation() {
        Patient patient = system.nextPatient();
        displayResultOrError(patient, "The next patient to see the doctor is: \n", "next patient retrieval");
    }
    
    /**
     * Display all the patients that is in the max heap priority queue
     */
    private void viewPatientQueueOperation() {
        System.out.println("The current queue of patients are: ");
        system.viewPatientQueue();
    }
    
    /**
     * Allows the user to see who is the next patient without removing the patient from the max priority queue
     */
    private void viewNextPatientOperation() {
        Patient patient = system.viewNextPatient();
        displayResultOrError(patient, "The next patient is: \n", "view of next patient");
    }
    
    /**
     * Get the length of the queue
     */
    private void getQueueLengthOperation() {
        int length = system.getQueueLength();
        
        if (length == 0) {
            System.out.println("The current length of the queue is: 0");
            return;
        }
        
        System.out.println("The current length of the queue is: " + length);
    }
    
    
    private void exit() {
        isRunning = false;
        System.out.println("\nThank you for using the Hospital Management System. Goodbye");
        scanner.close();
    }
}
