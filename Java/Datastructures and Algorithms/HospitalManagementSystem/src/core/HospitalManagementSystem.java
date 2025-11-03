
package core;

import data.MaxHeapPriorityQueue;
import model.Patient;

public class HospitalManagementSystem {
    
    //Store the patients as a max heap priority queue
    private MaxHeapPriorityQueue<Patient> patients;
    
    /**
     * Constructor of the system. create a new instance of the maxheap priority queue for patients
     */
    public HospitalManagementSystem() {
        this.patients = new MaxHeapPriorityQueue<>();
    }
    
    /**
     * helper method that checks if the paient is empty and warn the user
     * @param operationName the operation that needs to be performed
     * @return true/false
     */
    private boolean isEmtptyAndWarn(String operationName) {
        if (patients.isEmpty()) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Add a patient to the patients max heap priority queue
     * @param patient the patient that needs to be added
     */
    public void addPatient(Patient patient) {
        if (patient == null) {
            return;
        }
        
        patients.add(patient);
    }
    
    /**
     * Get the next patient in the max heap priority queue
     * @return the patient at the root of the max heap priority queue
     */
    public Patient nextPatient() {
        if (isEmtptyAndWarn("get the next patient")) {
            return null;
        }
        
        return patients.poll();
    }
    
    /**
     * View the patients queue
     */
    public void viewPatientQueue() {
        if (isEmtptyAndWarn("view the patient queue")) {
            System.out.println("There is no queue. Please add a patient");
        }
        
        System.out.println(patients.toString());
    }
    
    /**
     * View the next patient, but do not remove them from the queue
     * @return the patient at the root of the max heap priority queue
     */
    public Patient viewNextPatient() {
        if (isEmtptyAndWarn("view the next patient")) {
            return null;
        }
        
        return patients.peek();
    }
    
    /**
     * Get the total amount of patients in the max heap priority queue
     * @return the amount of patients for the max heap priority queue
     */
    public int getQueueLength() {
        if (isEmtptyAndWarn("get the queue length")) {
            return 0;
        }
        
        return patients.size();
    }
}
