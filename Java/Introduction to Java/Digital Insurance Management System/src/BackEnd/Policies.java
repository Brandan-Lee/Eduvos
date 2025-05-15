
package BackEnd;

/**
 * @author Brandan-Lee James Sherbrooke eduv4902376
 */

public class Policies {
    
    //these are the private attributes of the policies class
    private int policyId;
    private String policyType;
    private double coverageAmount, premiumAmount;
    //foreign key to the customer table in the database
    private int customerId;
    
    //This is the constructor for the class
    public Policies(int policyId, String policyType, double coverageAmount, double premiumAmount, int customerId) {
        
        this.policyId = policyId;
        this.policyType = policyType;
        this.coverageAmount = coverageAmount;
        this.premiumAmount = premiumAmount;
        this.customerId = customerId;
        
    }
    
    //public getter and setter methods for the private attributes
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
    
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
}
