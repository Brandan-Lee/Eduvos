
package BackEnd;

/**
 * @author Brandan-Lee James Sherbrooke eduv4902376
 */

public class Customers {
    
    //These are the private attributes of the Customers class
    private int customerId, customerAge;
    private String customerName;
    
    //Create the constructor for the class to store the customers details
    public Customers(int customerd, int customerAge, String customerName) {
        
        this.customerId = customerId;
        this.customerAge = customerAge;
        this.customerName = customerName;
        
    }
    
    //public getter and setter methods in order to interact with the classes private attributes
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public int getCustomerAge() {
        return customerAge;
    }
    
    public void setCustomerAge(int customerAge) {
        this.customerAge = customerAge;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
}
