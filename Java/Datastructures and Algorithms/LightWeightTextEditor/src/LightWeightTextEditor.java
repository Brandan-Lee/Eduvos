
import java.util.Scanner;


public class LightWeightTextEditor {
    
    private static TextEditor textEditor;
    private static Scanner scanner;
    private static boolean isRunning;

    public static void main(String[] args) {
        
        textEditor = new TextEditor();
        scanner = new Scanner(System.in);
        
        showWelcomeMessage();
        isRunning = true;
        
        do {
            
            showMenu();
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
                    pasteFromClipBoardOperation();
                    break;
                case "6":
                    undoOption();
                    break;
                case "7":
                    redoOption();
                    break;
                case "8":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid option made. Please try again");
                    
            }
            
            
        } while (isRunning);
        
        if (!isRunning) {
            System.out.println("");
            System.out.println("===============================================");
            System.out.println(" Thank you for using the Text Editor. Goodbye");
            System.out.println("===============================================");
        }
    }
    
    private static void showWelcomeMessage() {
        System.out.println("===============================================");
        System.out.println("        WELCOME TO THE TEXT EDITOR");
        System.out.println("===============================================");
        System.out.println();
    }
    
    private static void showMenu() {
        
        System.out.println("Please select an option: ");
        System.out.println("1. Insert Text");
        System.out.println("2. Delete last character");
        System.out.println("3. Copy selected text");
        System.out.println("4. Paste last item in clipboard");
        System.out.println("5. Paste selected item from clipboard:");
        System.out.println("6. Undo Last Operation");
        System.out.println("7. Redo Last Operation");
        System.out.println("8. Exit");
        System.out.print("Enter your choice (1 - 8): ");
        
    }
    
    private static void insertTextOption() {
        System.out.print("Enter text to insert: ");
        String text = scanner.nextLine();
        
        if (text.trim().isEmpty()) {
            System.out.println("No text has been entered. Please try again");
        } else {
            textEditor.insertText(text);
            System.out.println("Text inserted successfully");
            System.out.println(textEditor.getCurrentText());
            System.out.println("");
        }
    }
    
    private static void deleteOperation() {
        if (textEditor.getCurrentText().trim().isEmpty()) {
            System.out.println("Nothing can be deleted");
            System.out.println("");
        } else {
            
            if (textEditor.deleteCharacter()) {
                System.out.println("Character deleted successfully");
                System.out.println(textEditor.getCurrentText() + "\n");
            } else {
                System.out.println("There was a problem deleting the character");
                System.out.println(textEditor.getCurrentText() + "\n");
            }
        }
    }
    
    private static void copyOperation() {
        
        System.out.println(textEditor.getCurrentText());
        System.out.print("Enter text to copy: ");
        String copiedText = scanner.nextLine();
        
        if (copiedText.trim().isEmpty()) {
            System.out.println("No text has been entered. Please try again");
        } else {
            textEditor.copy(copiedText);
        }
    }
    
    private static void pasteOperation() {
        if (textEditor.pasteText()) {
            System.out.println("Successfully pasted from the clipboard");
            System.out.println(textEditor.getCurrentText() + "\n");
        } else {
            System.out.println("There was a problem with the paste operation. Please try again");
            System.out.println(textEditor.getCurrentText() + "\n");
        }
    }
    
    private static void pasteFromClipBoardOperation() {
        
        if (textEditor.showClipBoard()) {
            System.out.print("Please select the number from your clipboard: ");
            int pasteChoice = scanner.nextInt();
            
            if (textEditor.pasteText(pasteChoice)) {
                System.out.println("Successfully pasted " + pasteChoice + " from the clipboard");
                System.out.println(textEditor.getCurrentText() + "\n"); 
            } else {
                System.out.println("There was a problem with the paste operation. Please try again");
                System.out.println(textEditor.getCurrentText() + "\n");
            }
            
            scanner = new Scanner(System.in);
        } else {
            System.out.println("The clip board is empty\n");
        }
        
    }
    
    private static void undoOption() {
        
        if (textEditor.undo()) {
            System.out.println("Operation has been successfully undone");
            System.out.println(textEditor.getCurrentText() + "\n");
        } else {
            System.out.println("There was an error with the undo operation");
            System.out.println(textEditor.getCurrentText() + "\n");
        }
        
    }
    
    private static void redoOption() {
        
        if (textEditor.redo()) {
            System.out.println("Operation has been successfully redone");
            System.out.println(textEditor.getCurrentText() + "\n");
        } else {
            System.out.println("There was an error with the redo operation");
            System.out.println(textEditor.getCurrentText() + "\n");
        }
        
    }
    
}
