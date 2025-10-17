
package ui;

import core.TextEditor;
import java.util.Scanner;

public class TextEditorUI {
    
    private static TextEditor editor;
    private static Scanner scanner;
    private static boolean isRunning;
    
    public TextEditorUI() {
        this.editor = new TextEditor();
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
        System.out.println("        WELCOME TO THE TEXT EDITOR");
        System.out.println("===============================================");
    }
    
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
    
    private void insertTextOption() {
        
        System.out.print("Enter text to insert: ");
        String text = scanner.nextLine();
        editor.insertText(text);
        System.out.println("Text inserted: " + editor.getCurrentText());
        
    }
    
    private void deleteOperation() {
        
        if (editor.deleteCharacter()) {
            System.out.println("Character deleted: " + editor.getCurrentText());
        } else {
            System.out.println("Nothing to delete");
        }
        
    }
    
    private static void copyOperation() {
        
        System.out.println("Current text: " + editor.getCurrentText());
        System.out.print("Enter text to copy: ");
        String copiedText = scanner.nextLine();
        
        if (copiedText.trim().isEmpty()) {
            System.out.println("No text has been entered. Please try again");
        } else {
            editor.copy(copiedText);
        }
        
    }
    
    private static void pasteOperation() {
        
        String clipboard = editor.getClipBoard();
        
        if (clipboard == null) {
            System.out.println("Clipboard is empty: ");
            return;
        }
        
        System.out.println(clipboard);
        System.out.print("Please select the number from your clipboard: ");
        int index = scanner.nextInt();
        
        if (editor.pasteText(index)) {
            System.out.println("Text Pasted: " + editor.getCurrentText());
        } else {
            System.out.println("There was a problem with the paste operation. Please try again");
        }
        
        scanner = new Scanner(System.in);
        
    }
    
    private void showClipboard() {
        
        String clipBoard = editor.getClipBoard();
        
        if (clipBoard == null) {
            System.out.println("Clipboard is empty: ");
            return;
        }
        
        System.out.println(clipBoard);
        
    }
    
    private static void undoOption() {
        
        if (editor.undo()) {
            System.out.println("Undo successful. Current text: " + editor.getCurrentText());
        } else {
            System.out.println("There was an error with the undo operation");
        }
        
    }
    
    private static void redoOption() {
        
        if (editor.redo()) {
            System.out.println("Redo successful. Current text: " + editor.getCurrentText());
        } else {
            System.out.println("There was an error with the redo operation");
        }
        
    }
    
    private void exit() {
        isRunning = false;
        System.out.println("\nThank you for using the text editor. Goodbye");
        scanner.close();
    }
    
}
