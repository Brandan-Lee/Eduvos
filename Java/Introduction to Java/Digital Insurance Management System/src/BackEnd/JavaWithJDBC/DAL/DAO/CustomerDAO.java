
package BackEnd.JavaWithJDBC.DAL.DAO;

import BackEnd.JavaWithJDBC.DAL.DTO.CustomerDTO;
import BackEnd.JavaWithJDBC.DatabaseConnectionUtil;
import java.sql.*;
import java.util.*;

public class CustomerDAO {
    
    //This method will be used to add a customer to the customer table in the database by establishing a connection to the database, executing an insert SQL query, retrieving the customer id. Valid messages will be thrown if there was an error, and the connections are closed after
    public int addCustomer(CustomerDTO customer) throws SQLException {
        
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet customerIdKey = null;
        int customerId = -1;
        String sql = "INSERT INTO customers (customer_name, customer_age, customer_national_id, customer_surname, customer_address)"
                + " VALUES (?, ?, ?, ?, ?)";
        
        //Create the connection to the database. Use the preparedStatement to add the values to the customers table in the database
        try {
            
            con = DatabaseConnectionUtil.GetConnection();
            
            prepStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prepStmt.setString(1, customer.getCustomerName());
            prepStmt.setInt(2, customer.getCustomerAge());
            prepStmt.setString(3, customer.getCustomerNationalId());
            prepStmt.setString(4, customer.getCustomerSurname());
            prepStmt.setString(5, customer.getCustomerAddress());
            
            //Execute the query
            prepStmt.executeUpdate();
            
            //find the custom generated policy id key
            customerIdKey = prepStmt.getGeneratedKeys();
            
            if (customerIdKey.next()) {
                customerId = customerIdKey.getInt(1);
            }
            
            System.out.println("The customer has been added successfully " + customer.getCustomerName());
            
        } catch(SQLException e) {
            
            System.out.println("There was an error adding the customer to the database " + e.getMessage());
            throw new SQLException("Error adding a customer in CustomerDAO ", e);
            
        } finally {
            
            //Close the connection to the database, make sure the preparedstatement is empty and the resultset
            DatabaseConnectionUtil.CloseConnection(con, prepStmt, customerIdKey);
            
        }
        
        return customerId;
        
    }
    
    //This method will be used to find a customer that matches a national id in the customer table in the database by establishing a connection to the database, executing an select SQL query, and creating a new customerDTO object if found. Valid messages will be thrown if there was an error, and the connections are closed after
    public CustomerDTO findCustomerByNationalId(String customerNationalId) throws SQLException {
        
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet result = null;
        CustomerDTO foundCustomer = null;
        String sql = "SELECT"
                + " customer_id,"
                + " customer_national_id,"
                + " customer_name,"
                + " customer_surname,"
                + " customer_address,"
                + " customer_age"
                + " FROM customers"
                + " WHERE customer_national_id = ?";
        //Create the connection to the database. Use the preparedStatement to execute the sql and find the customer mathing the national id. Create a new customer if the national id has been found
        try {
            
            con = DatabaseConnectionUtil.GetConnection();
            prepStmt = con.prepareStatement(sql);
            prepStmt.setString(1, customerNationalId);
            result = prepStmt.executeQuery();
            
            if (result.next()) {
                int customerId = result.getInt("customer_id");
                String customerNId = result.getString("customer_national_id");
                String customerName = result.getString("customer_name");
                String customerSurname = result.getString("customer_surname");
                String customerAddress = result.getString("customer_address");
                int customerAge = result.getInt("customer_age");
                
                foundCustomer = new CustomerDTO(customerId, customerAge, customerNId, customerName, customerSurname, customerAddress);
                System.out.println("A customer with a national id has been found ");
            }
            
        } catch (SQLException e) {
            
            System.out.println("There was an error finding a customer with a mathching national ID " + e.getMessage());
            throw new SQLException("Error finding a customer with mathing national ID ", e);
            
        } finally {
            
            //Close the connection to the database, make sure the preparedstatement is empty and the resultset
            DatabaseConnectionUtil.CloseConnection(con, prepStmt, result);
            
        }
        
        return foundCustomer;
        
    }
    
}
