
package BackEnd.JavaWithJDBC.BLL;

import BackEnd.JavaWithJDBC.DAL.DAO.CustomerDAO;
import BackEnd.JavaWithJDBC.DAL.DAO.PolicyDAO;
import BackEnd.JavaWithJDBC.DAL.DTO.PolicyDTO;
import java.util.*;

/**
 * @author brand
 */

public class PolicyBLL {

    //private attributes of the class. The PolicyBLL needs to communicate with the policyDAO in order to add the data to the database
    private PolicyDAO policyDAO;
    
    //Constructor for the class
    public PolicyBLL(CustomerDAO customerDAO, PolicyDAO policy) {
        
        this.policyDAO = policyDAO;
        
    }
    
    //Communicate with the DAO to add a policy to the database
    public void addPolicy(PolicyDTO policy) {
        policyDAO.addPolicy(policy);
    }
    
    //Communicate with the DAO to return all the policies added to the policies table in the database
    public ArrayList<PolicyDTO> getPolicies() {
        return policyDAO.getPolicies();
    }
    
}
