
package model;

public class Patient implements Comparable<Patient> {
    
    private String name;
    private int priority;
    
    public Patient(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    @Override
    public String toString() {
        return "Patient name: " + getName() + ", Patient priority: " + getPriority();
    }

    @Override
    public int compareTo(Patient other) {
        return this.getPriority() - other.getPriority();
    }
    
}
