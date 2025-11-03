
package model;

/**
 * A model class that represents a patient in the hospital management system
 * @author brand
 */

public class Patient implements Comparable<Patient> {
    
    private String name;
    private int priority;
    
    /**
     * Constructor for the patient model
     * @param name the name of the patient
     * @param priority the priority of the patient
     */
    public Patient(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }
    
    /**
     * Get the name of the patient
     * @return the name of the patient as a string
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get the priority of the patient
     * @return the priority of the patient
     */
    public int getPriority() {
        return priority;
    }
    
    /**
     * Get the information of the patient model
     * @return the information of the patient model as a string
     */
    @Override
    public String toString() {
        return "Patient name: " + getName() + ", Patient priority: " + getPriority();
    }
    
    /**
     * Compare the current patient to another patient. Method is needed for the max heap priority queue class
     * @param other the patient that the current patient is being compared to
     * @return an int of 1 or 0
     */
    @Override
    public int compareTo(Patient other) {
        return this.getPriority() - other.getPriority();
    }
}
