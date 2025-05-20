
package BackEnd.JavaWithJDBC.DAL.DAO;

import BackEnd.JavaWithJDBC.DAL.DTO.PolicyDTO;
import BackEnd.JavaWithJDBC.DatabaseConnectionUtil;
import java.sql.*;
import java.util.*;

/**
 * * @author brand
 */

public class PolicyDAO {
    
    //This method will add a policy to the policy table of the database by establishing a connection to the database, executing an insert SQL query and retrieves the policy id. Valid messages will be thrown if there was an error, and the connections are closed after
    public int addPolicy(PolicyDTO policy) throws SQLException {
        
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet policyIdKey = null;
        int policyId = -1;
        String sql = "INSERT INTO policies (customer_id, policy_type, sum_insured, coverage_amount, premium_amount)"
                + " VALUES (?, ?, ?, ?, ?)";
        
        //Create the connection to the database. Use the preparedStatement to add the values to the policies table in the database
        try {
            
            con = DatabaseConnectionUtil.GetConnection();
            
            prepStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prepStmt.setInt(1, policy.getCustomerId());
            prepStmt.setString(2, policy.getPolicyType());
            prepStmt.setDouble(3, policy.getSumInsured());
            prepStmt.setDouble(4, policy.getCoverageAmount());
            prepStmt.setDouble(5, policy.getPremiumAmount());
            
            //Execute the query
            prepStmt.executeUpdate();
            
            //Find the custom generated policy id
            policyIdKey = prepStmt.getGeneratedKeys();
            
            if (policyIdKey.next()) {
                policyId = policyIdKey.getInt(1);
            }
            
            System.out.println("The policy has been added successfully " + policy.getCustomerId());
            
        } catch (SQLException e) {
            
            System.out.println("There was an error adding the policy to the database " + e.getMessage());
            throw new SQLException("Error adding a policy in PolicyDAO" + e);
            
        } finally {
            
            //close the connection to the database, the preparedStatement as well as the result set
            DatabaseConnectionUtil.CloseConnection(con, prepStmt, policyIdKey);
            
        }
        
        return policyId;
        
    }
    
    //This method will retrieve all the the policies from the policy table of the database by establishing a connection to the database, executing a select SQL query and add it to a hashmap of customers. Valid messages will be thrown if there was an error, and the connections are closed after
    public HashMap<Integer, PolicyDTO> getPolicies() throws SQLException {
        
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet result = null;
        HashMap<Integer, PolicyDTO> policies = new HashMap<>();
        String sql = "SELECT "
                + "policy_id,"
                + " customer_id,"
                + " policy_type,"
                + " sum_insured,"
                + " coverage_amount,"
                + " premium_amount"
                + " FROM policies"
                + " ORDER BY policy_id";
        
        //Create the connection to the database. Use the preparedStatement to execute the sql query and store it in the resultset. Find values of columns and add  it to the hashmap
        try {
            
            con = DatabaseConnectionUtil.GetConnection();
            prepStmt = con.prepareStatement(sql);
            result = prepStmt.executeQuery();
            
            //loop through the resultset to add the column values to the PolicyDTO and add it to a hashmap
            while(result.next()) {
                
                int policyId = result.getInt("policy_id");
                int customerId = result.getInt("customer_id");
                String policyType = result.getString("policy_type");
                double sumInsured = result.getDouble("sum_insured");
                double coverageAmount = result.getDouble("coverage_amount");
                double premiumAmount = result.getDouble("premium_amount");
                
                PolicyDTO policy = new PolicyDTO(policyId, policyType, sumInsured, coverageAmount, premiumAmount, customerId);
                policies.put(policyId, policy);
                
            }
            
            System.out.println("All policies have been retrieved successfully from the database in PolicyDAO");
            
        } catch (SQLException e) {
            
            System.out.println("There was an error retrieving a list of the policies at PolicyDAO " + e.getMessage());
            throw new SQLException("Error retrieving all policies in PolicyDAO", e);
            
        } finally {
            
            //Close all the connections
            DatabaseConnectionUtil.CloseConnection(con, prepStmt, result);
            
        }
         
        return policies;
        
    }
    
}
