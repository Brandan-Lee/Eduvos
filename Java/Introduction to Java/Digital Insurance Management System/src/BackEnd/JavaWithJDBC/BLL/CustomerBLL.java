
package BackEnd.JavaWithJDBC.BLL;

import BackEnd.JavaWithJDBC.DAL.DAO.CustomerDAO;
import BackEnd.JavaWithJDBC.DAL.DTO.CustomerDTO;
import java.util.*;

/**
 * @author brand
 */

public class CustomerBLL {
    
    //private attributes of the class. The CustomerBLL needs to communicate with the DAO in order to add the customer to the database
    private CustomerDAO customerDAO;
    
    //Constructor for the class
    public CustomerBLL(CustomerDAO customerDAO) {    
        this.customerDAO = customerDAO;
    }
    
    public int addCustomer(CustomerDTO customer) {
        return customerDAO.addCustomer(customer);
    }
    
    public HashMap<Integer, CustomerDTO> getCustomers() {
        return customerDAO.getCustomers();
    }
    
    public CustomerDTO findCustomerByNationalId(int customerNationalId) {
        
        HashMap<Integer, CustomerDTO> customers = getCustomers();
        
        for (CustomerDTO customer : customers.values()) {
            
            if (customer.getCustomerNationalId() == customerNationalId) {
                return customer;
            }
            
        }
        
        return null;
        
    }
    
}
