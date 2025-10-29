
package core;

import data.MaxHeapPriorityQueue;
import model.Patient;

public class HospitalManagementSystem {
    
    private MaxHeapPriorityQueue<Patient> patients;
    
    public HospitalManagementSystem() {
        this.patients = new MaxHeapPriorityQueue<>();
    }
    
    private boolean isEmtptyAndWarn(String operationName) {
        if (patients.isEmpty()) {
            return true;
        }
        
        return false;
    }
    
    public void addPatient(Patient patient) {
        if (patient == null) {
            return;
        }
        
        patients.add(patient);
    }
    
    public Patient nextPatient() {
        
        if (isEmtptyAndWarn("get the next patient")) {
            return null;
        }
        
        return patients.poll();
        
    }
    
    public String viewPatientQueue() {
        
        if (isEmtptyAndWarn("view the patient queue")) {
            return null;
        }
        
        return patients.printHeap();
        
    }
    
    public Patient viewNextPatient() {
        
        if (isEmtptyAndWarn("view the next patient")) {
            return null;
        }
        
        return patients.peek();
    }
    
    public int getQueueLength() {
        
        if (isEmtptyAndWarn("get the queue length")) {
            return 0;
        }
        
        return patients.size();
        
    }
    
}
