
package BackEnd.JavaWithJDBC.DAL.DTO;

import java.util.*;

/**
 * @author brand
 */

public class CustomerWithPoliciesDTO {
   
    //private attributes that will link the two tables
    private CustomerDTO customer;
    private List<PolicyDTO> policies = new ArrayList<>();
    
    //Constructor for the class
    public CustomerWithPoliciesDTO(CustomerDTO customer, List<PolicyDTO> policies) {
        
        this.customer = customer;
        this.policies = policies;
        
    }
    
    public CustomerDTO getCustomer() {
        return customer;
    }
    
    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }
    
    public List<PolicyDTO> getPolicies() {
        return new ArrayList<>(policies);
    }
    
    public void setPolicies(List<PolicyDTO> policies) {
        this.policies = policies; 
    }
    
    public void addPolicy(PolicyDTO policy) {
        if (this.policies == null) {
            this.policies = new ArrayList<>();
        }
        
        this.policies.add(policy);
    }
    
}
