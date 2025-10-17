package core;


import data.LinkedStack;
import data.DoublyLinkedList;
import java.util.ArrayList;


public class TextEditor {
    
    private DoublyLinkedList<String> textList;
    private LinkedStack<String> undoStack, redoStack;
    private ArrayList<String> clipBoardList;
    private final int MAX_UNDO = 5;
    
    public TextEditor() {
        textList = new DoublyLinkedList<>();
        undoStack = new LinkedStack<>(5);
        redoStack = new LinkedStack<>();
        clipBoardList = new ArrayList<>();
    }
    
    //for this method, i want the user to be able to enter text and store it inside the doublyLinkedList. Ex: hello world
    public void insertText(String text) {
        
        if (text.isEmpty()) {
            return;
        }
        
        String[] words = text.split(" ");
        
         for (String word : words) {
            if (!word.isEmpty()) {
              textList.addLast(word);  
            }
        }
         
        undoStack.push(text);
        displayAllStructures();
        
    }
    
    //for this method, i want to check if there is text. If there is i want to delete the last character in the node of the doublylinked list. Ex: hello worl
    public boolean deleteCharacter() {
        
        if (textList.isEmpty()) {
            return false;
        }
        
        String lastWord = textList.removeLast();
        undoStack.push(lastWord.substring(lastWord.length() - 1));

        if (lastWord.length() > 1) {
            String newWord = lastWord.substring(0, lastWord.length() - 1);
            textList.addLast(newWord);
        }
        
        displayAllStructures();
        return true;
        
    }
    
    //for this method, i want the user to enter the text that they would like to copy. from there i want to push the text to the clipboard arraylist: text = hello clipboard gets element of hello
    public void copy(String text) {
        
        if (text.isEmpty()) {
            System.out.println("No text provided to copy");
            return;
        }
        
        String currentText = textList.listToString();

        if (!currentText.contains(text)) {
            System.out.println("Please select from the current text\n");
            return;
              
        }
        
        clipBoardList.add(text);
        System.out.println("Copied text to clipBoard\n");
        
    }
    
       //here I want the user to have freedom to paste their choice from the clipboard. It should remove the element from the clipboard: Ex hello world, world is copied, when pasted from clipboard. text becomes hello worldworld
    public boolean pasteText(int index) {
        
        int actualIndex = index - 1;
        
        if (clipBoardList.isEmpty()) {
            System.out.println("The clipboard is empty");
            return false;
        }
        
        if (actualIndex < 0 || actualIndex >= clipBoardList.size()) {
            System.out.println("Invalid clipboard index");
            return false;
        }
        
        
        String textToPaste = clipBoardList.get(actualIndex);
        String[] words = textToPaste.split(" ");
        
        for (String word : words) {
            if (!word.isEmpty()) {
                textList.addLast(word);
            }
        }
        
        System.out.println("Pasted: " + textToPaste);
        undoStack.push(textToPaste);
        displayAllStructures();
        return true;
        
    }
    
    //this method will show to the user their current clipboard
    public String getClipBoard() {
        
       if (clipBoardList.isEmpty()) {
           return null;
       }
       
       StringBuilder sb = new StringBuilder();
       System.out.println("ClipBoard Contents");
       for (int i = 0; i < clipBoardList.size(); i++) {
           sb.append(i + 1 + ": " + clipBoardList.get(i) + "\n"); 
       }
       
       return sb.toString();
       
    }
    
    // For this method, the latest operation is popped from the undo stack and pushed to the redo stack. Ex: hello
    public boolean undo() {
        
       if (undoStack.isEmpty()) {
           return false;
       }
       
       String lastElement = undoStack.pop();
       
       if (!lastElement.isEmpty() && lastElement.length() > 1) {
           String[] words = lastElement.split(" ");
           
           for (int i = 0; i < words.length; i++) {
               textList.removeLast();
           }
       } else if (lastElement.length() == 1 && !textList.isEmpty()) {
           String lastWord = textList.removeLast();
           textList.addLast(lastWord + lastElement);
       }
       
       redoStack.push(lastElement);
       displayAllStructures();
       return true;
        
    }
    
    //for this method, the latest operation is popped from the redo stack and pushed to the undo stack. Ex: hello world
    public boolean redo() {
        
        if (redoStack.isEmpty()) {
            return false;
        }
        
        String elementToRedo = redoStack.pop();
        
        if (!elementToRedo.isEmpty() && elementToRedo.length() > 1) {
            String[] words = elementToRedo.split(" ");
            
            for (String word : words) {
                textList.addLast(word);
            }
        } else if (elementToRedo.length() == 1 && !elementToRedo.isEmpty()) {
            String lastWord = textList.removeLast();
            textList.addLast(lastWord.substring(0, lastWord.length() - 1));
        }
        
        undoStack.push(elementToRedo);
        displayAllStructures();
        return true;

    }

    public String getCurrentText() {
        return textList.listToString();
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
            String state = stack.pop();
            System.out.println(count++ + ": " + state);
            tempStack.push(state);
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
    
}
