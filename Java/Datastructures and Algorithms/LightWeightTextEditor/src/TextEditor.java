
import java.util.ArrayList;


public class TextEditor {
    
    private DoublyLinkedList<String> textList;
    private LinkedStack<String> undoStack, redoStack;
    private ArrayList<String> clipBoardList;
    
    public TextEditor() {
        textList = new DoublyLinkedList<>();
        undoStack = new LinkedStack<>(5);
        redoStack = new LinkedStack<>();
        clipBoardList = new ArrayList<>();
    }
    
    //for this method, i want the user to be able to enter text and store it inside the doublyLinkedList. Ex: hello world
    public void insertText(String text) {
        if (!text.isEmpty()) {
            
            String[] words = text.split(" ");
            
            for (String word : words) {
                textList.addLast(word);
            }
            
            //Store the operation
            undoStack.push("Insert: " + text);
            
            while(!redoStack.isEmpty()) {
                redoStack.pop();
            }
            
            displayAllStructures();
        } 
    }
    
    //for this method, i want to check if there is text. If there is i want to delete the last character in the node of the doublylinked list. Ex: hello worl
    public boolean deleteCharacter() {
        if (!canPerformOperation("delete")) {
            System.out.println("You have not added any text yet");
            return false;
        }
        
        String lastWord = textList.removeLast();
        
        if (lastWord.length() > 1) {
            String newWord = lastWord.substring(0, lastWord.length() - 1);
            textList.addLast(newWord);
            undoStack.push("Delete: " + lastWord.charAt(lastWord.length() - 1));
        } else {
            undoStack.push("Delete: " + lastWord);
        }
        
        displayAllStructures();
        return true;
    }
    
    // For this method, the latest operation is popped from the undo stack and pushed to the redo stack. Ex: hello
    public boolean undo() {
        
       if (canPerformOperation("undo")) {
           String lastOperation = undoStack.pop();
           
           //last action was insert
           if (lastOperation.startsWith("Insert: ")) {
               String insertedText = lastOperation.substring(8);
               String[] words = insertedText.split(" ");
               
               for (String word : words) {
                   if (!word.isEmpty()) {
                       textList.removeLast();
                   }
               }
               
               System.out.println("Undid insert operation");
           //last action was delete
           } else if (lastOperation.startsWith("Delete: ")) {
               String deletedChar = lastOperation.substring(8);
               
               //check to see if the user has emptied the current textList
               if (textList.isEmpty()) {
                   textList.addLast(deletedChar);
               } else {
                   String lastWord = textList.removeLast();
                   textList.addLast(lastWord + deletedChar);
               }
               
               System.out.println("Undid delete operation");
           //last action was paste
           } else if (lastOperation.startsWith("Paste: ")) {
               String lastPaste = lastOperation.substring(7);
               String[] words = lastOperation.split(" ");
               
               for (String word : words) {
                   if (!word.isEmpty()) {
                       textList.removeLast();
                   }
               }
               
               System.out.println("Undid paste operation");
           }
           
           redoStack.push(lastOperation);
           displayAllStructures();
           return true;
       } else {
           return false;
       }
    }
    
    //for this method, the latest operation is popped from the redo stack and pushed to the undo stack. Ex: hello world
    public boolean redo() {
        if (canPerformOperation("redo")) {
            String actionToRedo = redoStack.pop();
            
            //Action to be redone is insert
            if (actionToRedo.startsWith("Insert: ")) {
                String redoInsert = actionToRedo.substring(8);
                String[] words = redoInsert.split(" ");
                
                for (String word : words) {
                    if (!word.isEmpty()) {
                        textList.addLast(word);
                    }
                }
                
                System.out.println("Redid insert operation");
            //Action to be redone is delete
            } else if (actionToRedo.startsWith("Delete: ")) {
                String redoDelete = actionToRedo.substring(8);
                
                if (!textList.isEmpty()) {
                    String lastWord = textList.removeLast();
                    
                    if (lastWord.length() > 1) {
                        textList.addLast(lastWord.substring(0, lastWord.length() - 1));
                    }
                } else {
                    textList.addLast(redoDelete);
                }
                
                System.out.println("Redid delete operation");
            //Action to be redone is paste
            } else if (actionToRedo.startsWith("Paste: ")) {
                String redoPaste = actionToRedo.substring(7);
                String[] words = redoPaste.split(" ");
                
                for (String word : words) {
                    if (!word.isEmpty()) {
                        textList.addLast(word);
                    }
                }
                
                System.out.println("Redid paste operation");
            }
            
            undoStack.push(actionToRedo);
            return true;
            
        } else {
            return false;
        }
    }
    
    //for this method, i want the user to enter the text that they would like to copy. from there i want to push the text to the clipboard arraylist: text = hello clipboard gets element of hello
    public void copy(String text) {
        if (canPerformOperation("copy")) {
            
            String currentText = textList.listToString();
            
            if (currentText.contains(text)) {
                clipBoardList.add(text);
                System.out.println("Copied text to clipBoard\n");  
            } else {
                System.out.println("Please select from the current text\n");
                return;
            }
            
        } else {
            System.out.println("No text has been selected");
        }
    }
    
    //for this mmethod, i want the last element of the arraylist to be pushed to the textList node at the end. last element is hello. Current text gets shown as hellohello
    public boolean pasteText() {
        if (!canPerformOperation("paste")) {
            System.out.println("The clipboard is empty");
            return false;
        } else {
            String lastElement = clipBoardList.get(clipBoardList.size() - 1);
            String[] words = lastElement.split(" ");
            
            for (String word : words) {
                if (!word.isEmpty()) {
                    textList.addLast(word);
                }
            }
            
            undoStack.push("Paste: " + lastElement);
            
            while (!redoStack.isEmpty()) {
                redoStack.pop();
            }
            
            displayAllStructures();
            return true;
        }
    }
    
    //here I want the user to have freedom to paste their choice from the clipboard. It should remove the element from the clipboard: Ex hello world, world is copied, when pasted from clipboard. text becomes hello worldworld
    public boolean pasteText(int choice) {
        if (!canPerformOperation("paste")) {
            System.out.println("The clipboard is empty");
            return false;
        } else {
            if (choice > 0 && choice <= clipBoardList.size()) {
                String element = clipBoardList.get(choice - 1);
                String[] words = element.split(" ");
                
                for (String word : words) {
                    if (!word.isEmpty()) {
                        textList.addLast(word);
                    }
                }
                
                undoStack.push("Paste: " + element);
                
                while (!redoStack.isEmpty()) {
                    redoStack.pop();
                }
                
                displayAllStructures();
                return true;
            } else {
                System.out.println("The number you have entered is not on the clipboard. Please try again\n");
                return false;
            }
        }
    }
    
    //this method will show to the user their current clipboard
    public boolean showClipBoard() {
        
        if (canPerformOperation("showClipBoard")) {
            int count = 0;
        
            for (String text : clipBoardList) {
                count++;
                System.out.println(count + ". " + text);
            }
            
            return true;
        } else {
            return false;
        }
        
    }
    
    public String getCurrentText() {
        return "Current text: " + textList.listToString();
    }
    
    private boolean canPerformOperation(String operation) {
        
        switch(operation) {
            
            case "delete":
                if (textList.isEmpty()) {
                    return false;
                }
                break;
            case "copy":
                if (textList.isEmpty()) {
                    return false;
                }
                break;
            case "paste":
                if (clipBoardList.isEmpty()) {
                    return false;
                }
                break;
            case "showClipBoard":
                if (clipBoardList.isEmpty()) {
                    return false;
                }
                break;
            case "undo":
                if (undoStack.isEmpty()) {
                    return false;
                }
                break;
            case "redo":
                if (redoStack.isEmpty()) {
                    return false;
                }
                break;
        }
        
        return true;
    }
    
    // Display the contents of the undo stack
public void displayUndoStack() {
    System.out.println("=== UNDO STACK ===");
    if (undoStack.isEmpty()) {
        System.out.println("Undo stack is empty");
    } else {
        System.out.println("Undo stack contents (top to bottom):");
        displayStackContents(undoStack);
    }
    System.out.println();
}

// Display the contents of the redo stack
public void displayRedoStack() {
    System.out.println("=== REDO STACK ===");
    if (redoStack.isEmpty()) {
        System.out.println("Redo stack is empty");
    } else {
        System.out.println("Redo stack contents (top to bottom):");
        displayStackContents(redoStack);
    }
    System.out.println();
}

// Helper method to display stack contents
private void displayStackContents(LinkedStack<String> stack) {
    if (stack.isEmpty()) {
        System.out.println("Stack is empty");
        return;
    }
    
    // Create a temporary stack to preserve original stack
    LinkedStack<String> tempStack = new LinkedStack<>();
    int position = 1;
    
    // Pop all elements and display them
    while (!stack.isEmpty()) {
        String element = stack.pop();
        System.out.println(position + ". " + element);
        tempStack.push(element);
        position++;
    }
    
    // Restore the original stack
    while (!tempStack.isEmpty()) {
        stack.push(tempStack.pop());
    }
}

// Display the contents of the text list (doubly linked list)
public void displayTextList() {
    System.out.println("=== TEXT LIST (DoublyLinkedList) ===");
    if (textList.isEmpty()) {
        System.out.println("Text list is empty");
    } else {
        System.out.println("Text list contents (first to last):");
        displayListContents();
    }
    System.out.println();
}

// Helper method to display list contents
private void displayListContents() {
    if (textList.isEmpty()) {
        System.out.println("List is empty");
        return;
    }
    
    // Create a temporary list to preserve original list
    DoublyLinkedList<String> tempList = new DoublyLinkedList<>();
    int position = 1;
    
    // Remove all elements and display them
    while (!textList.isEmpty()) {
        String element = textList.removeFirst();
        System.out.println(position + ". \"" + element + "\"");
        tempList.addLast(element);
        position++;
    }
    
    // Restore the original list
    while (!tempList.isEmpty()) {
        textList.addLast(tempList.removeFirst());
    }
}

// Display everything at once
public void displayAllStructures() {
    System.out.println("=====================================");
    displayTextList();
    displayUndoStack();
    displayRedoStack();
    System.out.println("=====================================");
}

// Display current text (what user sees)
public void displayCurrentText() {
    System.out.println("=== CURRENT TEXT ===");
    System.out.println("Text: \"" + getCurrentText() + "\"");
    System.out.println();
}
    
}
