
package BackEnd.JavaWithJDBC.DAL.DTO;

/**
 * @author brand
 */

public class PolicyDTO {
    
    //private attributes of the policy table in the database. Foreign key will be added to create the relationship between the customers table and the policies table
    private int policyId;
    private String policyType;
    private double sumInsured, coverageAmount, premiumAmount;
    
    //Foreign key
    private int customerId;
    
    //Constructor for the class
    public PolicyDTO(int policyId, String policyType, double sumInsured, double coverageAmount, double premiumAmount, int customerId) {
        
        this.policyId = policyId;
        this.policyType = policyType;
        this.sumInsured = sumInsured;
        this.coverageAmount = coverageAmount;
        this.premiumAmount = premiumAmount;
        this.customerId = customerId;
        
    }
    
    //public getter and setter methods for the class
    public int getPolicyId() {
        return policyId;
    }
    
    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }
    
    public String getPolicyType() {
        return policyType;
    }
    
    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }
    
    public double getSumInsured() {
        return sumInsured;
    }
    
    public void setSumInsured(double sumInsured) {
        this.sumInsured = sumInsured;
    }
    
    public double getCoverageAmount() {
        return coverageAmount;
    }
    
    public void setCoverageAmount(double coverageAmount) {
        this.coverageAmount = coverageAmount;
    }
    
    public double getPremiumAmount() {
        return premiumAmount;
    }
    
    public void setPremiumAmount(double premiumAmount) {
        this.premiumAmount = premiumAmount;
    }
    
    //public getter and setter for the foreign key that links PolicyDTO with CustomerDTO
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
}
