package core;

/**
 * System class for the light weight text editor
 */

import data.LinkedStack;
import data.DoublyLinkedList;
import java.util.ArrayList;


public class TextEditor {
    
    private DoublyLinkedList<String> textList;
    private LinkedStack<String> undoStack, redoStack;
    private ArrayList<String> clipBoardList;
    private final int MAX_UNDO = 5;
    
    //Default constructor of the method
    public TextEditor() {
        textList = new DoublyLinkedList<>();
        undoStack = new LinkedStack<>(MAX_UNDO);
        redoStack = new LinkedStack<>();
        clipBoardList = new ArrayList<>();
    }
    
    /**
     * Method to insert the text into the textList
     * @param text the text that needs to be inserted
     */
    public void insertText(String text) {
        String trimmedText = text.trim();
        //Check to see if the user has entered more than one word. If so, add each word as a node to the textList
        String[] words = trimmedText.split("\\s+"); //This regex accounts for one or more white spaces
        
         for (String word : words) {
            if (!word.isEmpty()) {
              textList.addLast(word);  
            }
        }
        
        //Push the text entered by the user to the top of the stack
        undoStack.push(trimmedText);
        displayAllStructures();
        
    }
    
    /**
     * Method that allows a character to be deleted from the textList
     * @return if the delete character operation was a success or not
     */
    public boolean deleteCharacter() {
        if (textList.isEmpty()) {
            return false;
        }
        
        //Retrieve the last word stored in the textList, and push only the letter to the undoStack
        String lastWord = textList.removeLast();
        
        if (lastWord.isEmpty()) {
            return false;
        }
        
        String deletedChar = lastWord.substring(lastWord.length() - 1);
        String newWord = lastWord.substring(0, lastWord.length() - 1);
        undoStack.push(deletedChar);
        
        //Add the new updated word with the deleted character back to the textList
        if (!newWord.isEmpty()) {
            textList.addLast(newWord);
        }
        
        displayAllStructures();
        return true;
    }
    
    /**
     * Method that allows the text to be copied to the textList
     * @param text the text that should be copied
     */
    public void copy(String text) {
        //Get the current text stored in the textList. Check to see if the text is stored within the textList. If so, add it to the clipboard
        String currentText = textList.toString();

        if (!currentText.contains(text)) { 
            System.out.println("Please select from the current text");
        } else {
            clipBoardList.add(text);
            System.out.println("Text copied to clipboard");
        }
    }
    
    /**
     * Method that allows the text to be pasted from the clipboard
     * @param index the index in from the clipboard that should be pasted
     * @return true / false
     */
    public boolean pasteText(int index) {
        int actualIndex = index - 1;
        
        //Ensure that the index is valid for the clipboard
        if (actualIndex < 0 || actualIndex >= clipBoardList.size()) {
            System.out.println("Invalid clipboard index");
            return false;
        }
        
        //Get the text from the clipboard. Add the text in the textList by word
        String textToPaste = clipBoardList.get(actualIndex);
        String[] words = textToPaste.split("\\s+"); //This regex accounts for one or more white spaces
        
        for (String word : words) {
            if (!word.isEmpty()) {
                textList.addLast(word);
            }
        }
        
        //Push the text entered by the user to the top of the stack and remove from the clipboard
        undoStack.push(textToPaste);
        clipBoardList.remove(actualIndex);
        displayAllStructures();
        return true;
    }
    
    /**
     * Method that retreives the elements stored in the clipboardList
     * @return 
     */
    public String getClipBoard() {
       if (clipBoardList.isEmpty()) {
           return null;
       }
       
       //Manipulate the clipboard string to return an index as well as the value stored in the clipboardList
       String clipBoard = "";
       System.out.println("ClipBoard Contents");
       for (int i = 0; i < clipBoardList.size(); i++) {
           clipBoard = i + 1 + ": " + clipBoardList.get(i) + "\n"; 
       }
       
       return clipBoard;
    }
    
    /**
     * Method that allows the last operation performed to be undone
     * @return true/false
     */
    public boolean undo() {
       if (undoStack.isEmpty()) {
           return false;
       }
       
       //Get the first element from the top of the stack
       String element = undoStack.pop();
       
       //check to see if the element is a word or a letter
       //Delete operation in undo stack
       if (element.length() == 1) {
           String lastWord = "";
           
           if (!textList.isEmpty()) {
               lastWord = textList.removeLast();
           }
           
           textList.addLast(lastWord + element);
       
        //This is the insert/paste operation logic
       } else if (!element.isEmpty()) {
           String[] words = element.split("\\s+");
           
           for (int i = 0; i < words.length; i++) {
               textList.removeLast();
           }
       } else {
           return false;
       }
       
       //Add the element that was undone to the top of the redoStack
       redoStack.push(element);
       displayAllStructures();
       return true;
    }
    
    /**
     * Method that allows the last operation undone to be redone
     * @return true/false
     */
    public boolean redo() {
        if (redoStack.isEmpty()) {
            return false;
        }
        
        //Get the first element from the top of the stack
        String elementToRedo = redoStack.pop();
        
        //check to see if the element is a word or a letter
        //Delete operation in redo stack
        if (elementToRedo.length() == 1) {
            if (!textList.isEmpty()) {
                String lastWord = textList.removeLast();
                String newWord = lastWord.substring(0, lastWord.length() - 1);
                
                if (!newWord.isEmpty()) {
                    textList.addLast(newWord);
                }
            }
            
        } else if (!elementToRedo.isEmpty()) {
            //This is the insert paste operation logic
            String[] words = elementToRedo.split("\\s+");
            
            for (String word : words) {
                if (!word.isEmpty()) {
                    textList.addLast(word);
                }
            }
            
        } else {
            return false;
        }
        
        //Add the element that was redone to the top of the redoStack
        undoStack.push(elementToRedo);
        displayAllStructures();
        return true;
    }
    
    
    //Method that returns the textList as a String
    public String getCurrentText() {
        return textList.toString();
    }
    
    //-------From here, these methods were used to help display the functionality of the datastructures
    
    //helper method to display the contents of the undo Stack
    private void displayUndoStack() {
        System.out.println("=== UNDO STACK ===");
        if (undoStack.isEmpty()) {
            System.out.println("Undo stack is empty");
       } else {
            System.out.println("Undo stack contents (top to bottom):");
            displayStackContents(undoStack);
        }
        System.out.println();
    }
//
//    // helper method to display the contents of the redo stack
    private void displayRedoStack() {
        System.out.println("=== REDO STACK ===");
        if (redoStack.isEmpty()) {
            System.out.println("Redo stack is empty");
        } else {
            System.out.println("Redo stack contents (top to bottom):");
            displayStackContents(redoStack);
        }
        System.out.println();
    }
//    
//    //helper method to display the contents of the stack given in the parameter
    private void displayStackContents(LinkedStack<String> stack) {
        if (stack.isEmpty()) {
            System.out.println("Stack is empty");
            return;
        }

        // Create a temporary stack to preserve the original order
        LinkedStack<String> tempStack = new LinkedStack<>();
        int count = 1;

        System.out.println("Stack (most recent first):");
        while (!stack.isEmpty()) {
            String element = stack.pop();
            System.out.println(count++ + ": " + element);
           tempStack.push(element);
        }

        // Restore the original stack
        while (!tempStack.isEmpty()) {
            stack.push(tempStack.pop());
        }
    }
//
//    // helper method to display the contents of the text list (doubly linked list)
    private void displayTextList() {
        System.out.println("=== TEXT LIST (DoublyLinkedList) ===");
        if (textList.isEmpty()) {
            System.out.println("Text list is empty");
       } else {
            System.out.println("Text list contents (first to last):");
            displayListContents();
        }
        System.out.println();
    }
//
//    // Helper method to display list contents
    private void displayListContents() {
        if (textList.isEmpty()) {
            System.out.println("List is empty");
            return;
        }

//        // Create a temporary list to preserve original list
        DoublyLinkedList<String> tempList = new DoublyLinkedList<>();
        int position = 1;
//
//        // Remove all elements and display them
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

//    // Method that helps to display the datastructures after each operation
    public void displayAllStructures() {
        System.out.println("=====================================");
        displayTextList();
        displayUndoStack();
        displayRedoStack();
        System.out.println("=====================================");
    }
//    
}
