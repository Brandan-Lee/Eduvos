
package BackEnd.JavaWithJDBC.DAL.DAO;

import BackEnd.JavaWithJDBC.DAL.DTO.PolicyDTO;
import BackEnd.JavaWithJDBC.DatabaseConnectionUtil;
import java.sql.*;
import java.util.*;

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
    
}
