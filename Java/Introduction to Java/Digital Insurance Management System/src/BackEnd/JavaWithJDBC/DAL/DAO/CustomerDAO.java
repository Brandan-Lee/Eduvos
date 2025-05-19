
package BackEnd.JavaWithJDBC.DAL.DAO;

import BackEnd.JavaWithJDBC.DAL.DTO.CustomerDTO;
import BackEnd.JavaWithJDBC.DatabaseConnectionUtil;
import java.sql.*;
import java.util.*;

/**
 * @author brand
 */

public class CustomerDAO {
    
    //This method will be used to add a customer to the customer table in the database
    public int addCustomer(CustomerDTO customer) {
        
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet customerIdKey = null;
        int customerId = -1;
        String sql = "INSERT INTO customers (customer_name, customer_age, customer_national_id, customer_surname, customer_address) VALUES (?, ?, ?, ?, ?)";
        
        //Create the connection to the database. Use the preparedStatement to add the values to the customer class in the database
        try {
            
            con = DatabaseConnectionUtil.GetConnection();
            
            prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, customer.getCustomerName());
            prepStmt.setInt(2, customer.getCustomerAge());
            prepStmt.setInt(3, customer.getCustomerNationalId());
            prepStmt.setString(4, customer.getCustomerSurname());
            prepStmt.setString(5, customer.getCustomerAddress());
            
            //Execute the query
            prepStmt.executeUpdate();
            
            customerIdKey = prepStmt.getGeneratedKeys();
            
            if (customerIdKey.next()) {
                customerId = customerIdKey.getInt(1);
            }
            
            System.out.println("The customer has been added successfully " + customer.getCustomerName());
            
        } catch(SQLException e) {
            System.out.println("There was an error adding the customer to the database " + e.getMessage());
        } finally {
            
            //Close the connection and the prepared statement
            DatabaseConnectionUtil.CloseConnection(con);
            
            //check to see if their is still a statement. if so, close it.
            if (prepStmt != null) {
                try {
                    prepStmt.close();
                } catch (SQLException e) {
                    System.out.println("There was an error closing the statement in add customer method in the CustomerDAO " + e.getMessage());
                }
            }
        }
        
        return customerId;
    }
    
    //This method will be used to retrieve all the customers that has been added to the customer table in the database
    public HashMap<Integer, CustomerDTO> getCustomers() {
        
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet result = null;
        HashMap<Integer, CustomerDTO> customers = new HashMap<>();
        String sql = "SELECT customer_Id, customer_national_id, customer_name, customer_surname, customer_address, customer_age from customers";
        
        //Create the connection to the database. Use the preparedStatement to execute the sql and use the resultset to add it to the list of customers
        try {
            
            con = DatabaseConnectionUtil.GetConnection();
            prepStmt = con.prepareStatement(sql);
            result = prepStmt.executeQuery();
            
            while (result.next()) {
                int customerId = result.getInt("customer_id");
                int customerNationalId = result.getInt("customer_national_id");
                String customerName = result.getString("customer_name");
                String customerSurname = result.getString("customerSurname");
                String customerAddress = result.getString("customer_address");
                int customerAge = result.getInt("customer_age");
                
                //Add the Customer to the list of customers
                CustomerDTO customer = new CustomerDTO(customerId, customerAge, customerNationalId, customerName, customerSurname, customerAddress);
                customers.put(customerId, customer);
            }
            
        } catch (SQLException e) {
            System.out.println("There was an error retrieving a list of the customers " + e.getMessage());
        } finally {
            
            //Close the connection to the database, make sure the preparedstatement is empty and the resultset
            DatabaseConnectionUtil.CloseConnection(con);
            
            if (prepStmt != null) {
                try {
                    prepStmt.close();
                } catch (SQLException e) {
                    System.out.println("There was an error closing the prepared statement in the getCustomers method in CustomerDAO " + e.getMessage());
                }
            }
            
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    System.out.println("There was an error closing the result set in the getCustomers method in CustomerDAO " + e.getMessage());
                }
            }
            
        }
        
        return customers;
        
    }
    
}
