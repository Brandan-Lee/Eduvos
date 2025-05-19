
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
    
    public ArrayList<PolicyDTO> getPolicies() {
        return new ArrayList<>(policies);
    }
    
    public void setPolicies(ArrayList<PolicyDTO> policies) {
        this.policies = policies; 
    }
    
}
