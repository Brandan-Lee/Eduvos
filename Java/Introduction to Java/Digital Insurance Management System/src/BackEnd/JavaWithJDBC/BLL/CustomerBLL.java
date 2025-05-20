
package BackEnd.JavaWithJDBC.BLL;

import BackEnd.JavaWithJDBC.DAL.DAO.CustomerDAO;
import BackEnd.JavaWithJDBC.DAL.DTO.CustomerDTO;
import java.sql.SQLException;
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
    
    //This method is used to communicate with the database through the CustomerDAO to add a customer. Once a customer has been created, the method checks if it was a success with the newCustomerId variable
    public int addCustomer(CustomerDTO customer) throws SQLException {
        
        int newCustomerId = customerDAO.addCustomer(customer);
        
        if (newCustomerId != -1) {
            
            System.out.println("Customer has been added successfully at CustomerBLL. The new customers ID is: " + newCustomerId);
            return newCustomerId;
            
        } else {
            System.out.println("There was a problem adding the customer in CustomerBLL");
        }
        
        return newCustomerId;
        
    }
    
    ////This method is used to communicate with the database through the CustomerDAO to get all the customers. Once all customers has been received, the method stores all the customers in a hashmap
    public HashMap<Integer, CustomerDTO> getCustomers() throws SQLException {
        
        HashMap<Integer, CustomerDTO> allCustomers = customerDAO.getCustomers();
        
        if (allCustomers == null) {
            
            System.out.println("There was an error retrieving all the customers from the database in CustomerBLL");
            return new HashMap<>();
            
        } else if (allCustomers.isEmpty()) {
            
            System.out.println("No customers has been found in the database");
            return allCustomers;
            
        } else {
            
            System.out.println("All customers have been retrieved successfully from the database in CustomerBLL");
            return allCustomers;
            
        }
        
    }
    
    //This method is used to communicate with the database through the CustomerDAO to find a customer that matches a specific national id. Once a customer has been found, the method checks if it was a success and adds it to a new CustomerDTO object
    public CustomerDTO findCustomerByNationalId(int customerNationalId) throws SQLException {
        
        CustomerDTO customer = customerDAO.findCustomerByNationalId(customerNationalId);
        
        if (customer != null) {
            System.out.println("A customer with a matching national ID has been found in CustomerBLL");
            return customer;
        } else {
            System.out.println("No mathching customers with the national ID has been found");
        }
        
        return customer;
        
    }
    
}
