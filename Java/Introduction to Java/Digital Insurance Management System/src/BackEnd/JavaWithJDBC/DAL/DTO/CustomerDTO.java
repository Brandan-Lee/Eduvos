
package BackEnd.JavaWithJDBC.DAL.DTO;

/**
 * @author brand
 */

public class CustomerDTO {
    
    //These are the private attributes of the customer class for the database
    private int customerId, customerAge;
    private String customerName, customerSurname, customerAddress, customerNationalId;
    
    
    //This is the constructor of the class
    public CustomerDTO(int customerId, int customerAge, String customerNationalId, String customerName, String customerSurname, String customerAddress) {
        
        this.customerId = customerId;
        this.customerAge = customerAge;
        this.customerNationalId = customerNationalId;
        this.customerName = customerName;
        this.customerSurname = customerSurname;
        this.customerAddress = customerAddress;
        
    }
    
    //public getter and setter methods in order to add a customer to the dto
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
    
    public String getCustomerNationalId() {
        return customerNationalId;
    }
    
    public void setCustomerNationalId(String customerNationalId) {
        this.customerNationalId = customerNationalId;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getCustomerSurname() {
        return customerSurname;
    }
    
    public void setCustomerSurname(String customerSurname) {
        this.customerSurname = customerSurname;
    }
    
    public String getCustomerAddress() {
        return customerAddress;
    }
    
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
    
}
