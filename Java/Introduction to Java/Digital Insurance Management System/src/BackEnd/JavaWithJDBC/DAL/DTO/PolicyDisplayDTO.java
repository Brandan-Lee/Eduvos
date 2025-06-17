
package BackEnd.JavaWithJDBC.DAL.DTO;

import java.util.*;

/**
 * @author brand
 */

public class PolicyDisplayDTO {
   
    //private attributes that will link the two tables
    private int customerAge;
    private String customerName, customerSurname, customerAddress, customerNationalId, policyType;
    private double sumInsured, coverageAmount, premiumAmount;
    
    public PolicyDisplayDTO(int customerAge, String customerNationalId, String customerName, String customerSurname, String customerAddress, String policyType, double sumInsured, double coverageAmount, double premiumAmount) {
        
        this.customerAge = customerAge;
        this.customerNationalId = customerNationalId;
        this.customerName = customerName;
        this.customerSurname = customerSurname;
        this.customerAddress = customerAddress;
        this.policyType = policyType;
        this.sumInsured = sumInsured;
        this.coverageAmount = coverageAmount;
        this.premiumAmount = premiumAmount;
        
    }

    public int getCustomerAge() {
        return customerAge;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerSurname() {
        return customerSurname;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getCustomerNationalId() {
        return customerNationalId;
    }

    public String getPolicyType() {
        return policyType;
    }

    public double getSumInsured() {
        return sumInsured;
    }

    public double getCoverageAmount() {
        return coverageAmount;
    }

    public double getPremiumAmount() {
        return premiumAmount;
    }
    
}
