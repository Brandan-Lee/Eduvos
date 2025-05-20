
package BackEnd.JavaWithJDBC.BLL;

import BackEnd.JavaWithJDBC.DAL.DAO.CustomerDAO;
import BackEnd.JavaWithJDBC.DAL.DAO.PolicyDAO;
import BackEnd.JavaWithJDBC.DAL.DTO.PolicyDTO;
import java.sql.SQLException;
import java.util.*;
import GeneralUtilities.EnumSets;
import GeneralUtilities.EnumSets.PolicyPremium;

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
    
    //This method is used to communicate with the database through the PolicyDAO to add a policy. Once a policy has been created, the method checks if it was a success with the newPolicyId variable
    public int addPolicy(PolicyDTO policy) throws SQLException {
        
        int newPolicyId = policyDAO.addPolicy(policy);
        
        if (newPolicyId != -1) {
            
            System.out.println("Policy has been added successfully at PolicyBLL. The new policy ID is: " + newPolicyId);
            return newPolicyId;
            
        } else {
            System.out.println("There was a problem adding the policy in PolicyBLL");
        }
        
        return newPolicyId;
        
    }
    
    ////This method is used to communicate with the database through the PolicyDAO to get all the policies. Once all policies has been received, the method stores all the policies in a hashmap
    public HashMap<Integer, PolicyDTO> getPolicies() throws SQLException {
        
        HashMap<Integer, PolicyDTO> allPolicies = policyDAO.getPolicies();
        
        if (allPolicies == null) {
            
            System.out.println("There was an error retrieving all the policies in the PolicyBLL");
            return new HashMap<>();
            
        } else if (allPolicies.isEmpty()) {
            
            System.out.println("No policies has been found in the database");
            return allPolicies;
            
        } else {
            
            System.out.println("All policies have been retrieved successfully from the database in PoliciesBLL");
            return allPolicies;
            
        }
        
    }
    
    //This method is used to calculate the premium amount on the frontend by utilising the PolicyPremium enum, comparing the policy type, retrieving the premiumAmount, and doing the necessary math calculations
    public double calculatePolicyPremium(String policyType, double coverageAmount) {
        
        PolicyPremium chosenPremium = EnumSets.PolicyPremium.fromPolicyType(policyType);
        
        if (chosenPremium != null) {
            
            double premiumRate = chosenPremium.getPremiumRate();
            double calculatePremiumAmount = coverageAmount * premiumRate;
            return calculatePremiumAmount;
            
        }
        
        return 0.0;
        
    }
    
}
