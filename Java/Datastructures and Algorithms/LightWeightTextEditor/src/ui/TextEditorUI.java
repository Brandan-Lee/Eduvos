
package ui;

/**
 * UI class for the lightweight text editor
 */

import core.TextEditor;
import java.util.Scanner;

public class TextEditorUI {
    
    private static TextEditor editor;
    private static Scanner scanner;
    private static boolean isRunning;
    
    /**
     * Constructor that creates new instances of the editor and the scanner
     */
    public TextEditorUI() {
        this.editor = new TextEditor();
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Start the Light Weight Text Editor UI
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
        System.out.println("        WELCOME TO THE TEXT EDITOR");
        System.out.println("===============================================");
    }
    
    /**
     * Displays the menu to the user
     */
    private void showMenu() {
        System.out.println("\nMenu:");
        System.out.println("Please select an option: ");
        System.out.println("1. Insert Text");
        System.out.println("2. Delete last character");
        System.out.println("3. Copy text");
        System.out.println("4. Paste text");
        System.out.println("5. Show Clipboard");
        System.out.println("6. Undo");
        System.out.println("7. Redo");
        System.out.println("8. Exit");
        System.out.print("Enter your choice (1 - 8): ");
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
                    insertTextOption();
                    break;
                case "2":
                    deleteOperation();
                    break;
                case "3":
                    copyOperation();
                    break;
                case "4":
                    pasteOperation();
                    break;
                case "5":
                    showClipboard();
                    break;
                case "6":
                    undoOption();
                    break;
                case "7":
                    redoOption();
                    break;
                case "8":
                    exit();
                    break;
                default:
                    System.out.println("Invalid option made. Please try again");
            }
    }
    
    /**
     * Allows the user to insert text into the text editor
     */
    private void insertTextOption() {
        String text = getNonEmptyString("Enter text to insert: ");
        editor.insertText(text);
        System.out.println("Text inserted: " + editor.getCurrentText());
    }
    
    /**
     * Delete a character from the textlist
     */
    private void deleteOperation() {
        if (editor.deleteCharacter()) {
            System.out.println("Character deleted: " + editor.getCurrentText());
        } else {
            System.out.println("Nothing to delete");
        }
    }
    
    /**
     * Have the user insert text to be copy
     */
    private void copyOperation() {
        System.out.println("Current text: " + editor.getCurrentText());
        String copiedText = getNonEmptyString("Enter text to copy: ");
        editor.copy(copiedText);
    }
    
    /**
     * Show the clipboard where the user can insert an option to paste
     */
    private void pasteOperation() {
        showClipboard();
        int index = getPositiveInteger("Please enter the number from your clipboard: ");
        
        if (editor.pasteText(index)) {
            System.out.println("Text Pasted: " + editor.getCurrentText());
        } else {
            System.out.println("There was a problem with the paste operation. Please try again");
        }
        
        scanner = new Scanner(System.in);
    }
    
    /**
     * Display the clipboard to the user
     */
    private void showClipboard() {
        String clipBoard = editor.getClipBoard();
        
        if (clipBoard == null) {
            System.out.println("Clipboard is empty: ");
            return;
        }
        
        System.out.println(clipBoard);
    }
    
    /**
     * User can undo an action that they have performed
     */
    private static void undoOption() {
        if (editor.undo()) {
            System.out.println("Undo successful. Current text: " + editor.getCurrentText());
        } else {
            System.out.println("There was an error with the undo operation");
        }
    }
    
    /**
     * User can redo an action that they have undone
     */
    private static void redoOption() {
        if (editor.redo()) {
            System.out.println("Redo successful. Current text: " + editor.getCurrentText());
        } else {
            System.out.println("There was an error with the redo operation");
        }
    }
    
    /**
     * Exit the system
     */
    private void exit() {
        isRunning = false;
        System.out.println("\nThank you for using the text editor. Goodbye");
        scanner.close();
    }
}
