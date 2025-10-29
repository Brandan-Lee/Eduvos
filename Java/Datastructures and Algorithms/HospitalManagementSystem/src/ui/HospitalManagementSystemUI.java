
package ui;

import core.HospitalManagementSystem;
import java.util.Scanner;
import model.Patient;

public class HospitalManagementSystemUI {
    
    private static HospitalManagementSystem system;
    private static Scanner scanner;
    private static boolean isRunning;
    
    public HospitalManagementSystemUI() {
        this.system = new HospitalManagementSystem();
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
        System.out.println("  WELCOME TO THE HOSPITAL MANAGEMENT SYSTEM");
        System.out.println("===============================================");
    }
     
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
    
    private <T> void displayResultOrError(T result, String successMessage, String operationName) {
        if (result == null) {
            System.out.println("Operation failed: The queue is likely empty or an error occurred during " + operationName + ".");
        } else {
            System.out.println(successMessage + result.toString());
        }
    }
    
    private void processUserInput() {
        String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    insertPatient();
                    break;
                case "2":
                    nextPatient();
                    break;
                case "3":
                    viewPatientQueue();
                    break;
                case "4":
                    viewNextPatient();
                    break;
                case "5":
                    getQueueLength();
                    break;
                case "6":
                    exit();
                    break;
                default:
                    System.out.println("Invalid option made. Please try again");
            }
    }
    
    private Patient getPatientInput() {
        System.out.print("Please enter the patients name: ");
        String name = scanner.nextLine();
        
        if (name.trim().isEmpty() || name.matches("[a-zA-Z\\s]+")) { // [a-zA-Z\\s]+ ensures at least one character is present, and all characters are letters or spaces.
            System.out.println("The patients name can only contain letters and spaces and may not be empty");
            return null;
        }
        
        if (name.matches("\\d+")) {
            System.out.println("The name can only contain letters");
        }
        
        System.out.print("Please enter the priority of the patient (0 - 10): ");
        String priorityInput = scanner.nextLine();
        
        int parsedPriority;
        try {
            parsedPriority = Integer.parseInt(priorityInput);
            
            if (parsedPriority < 0 || parsedPriority > 10) {
                System.err.println("Priority must be between 0 and 10");
                return null;
            }
        } catch (NumberFormatException e) {
            System.err.print("Invalid priority input. Please enter a whole number.");
            return null;
        }
        
        return new Patient(name, parsedPriority);
    }
    
    private void insertPatient() {
        Patient patient = getPatientInput();
        
        if (patient != null) {
            system.addPatient(patient);
            System.out.println("\n" + patient.toString());
            System.out.println("Has Been Added Successfully!");
        }
    }
    
    private void nextPatient() {
        Patient patient = system.nextPatient();
        displayResultOrError(patient, "The next patient to see the doctor is: \n", "next patient retrieval");
    }
    
    private void viewPatientQueue() {
        String queue = system.viewPatientQueue();
        displayResultOrError(queue, "The patient queue is: \n", "queue view");
    }
    
    private void viewNextPatient() {
        Patient patient = system.viewNextPatient();
        displayResultOrError(patient, "The next patient is: \n", "view of next patient");
    }
    
    private void getQueueLength() {
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
