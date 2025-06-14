
package BackEnd.JavaWithJDBC.DAL.DAO;

import BackEnd.JavaWithJDBC.DAL.DTO.PolicyDisplayDTO;
import BackEnd.JavaWithJDBC.DAL.DTO.CustomerDTO;
import BackEnd.JavaWithJDBC.DAL.DTO.PolicyDTO;
import BackEnd.JavaWithJDBC.DatabaseConnectionUtil;
import java.util.*;
import java.sql.*;


public class ReportDAO {
    
    //This method will retrieve all the the policies added to the database by establishing a connection to the database, executing a select SQL query and add it to a hashmap of reports. Valid messages will be thrown if there was an error, and the connections are closed after
    public ArrayList<PolicyDisplayDTO> getAllPolicies() throws SQLException {
        
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet result = null;
        ArrayList<PolicyDisplayDTO> list = new ArrayList<>();
        String sql = "SELECT"
                + " c.customer_national_id,"
                + " c.customer_name,"
                + " c.customer_surname,"
                + " c.customer_address,"
                + " c.customer_age,"
                + " p.policy_type,"
                + " p.sum_insured,"
                + " p.coverage_amount,"
                + " p.premium_amount "
                + " FROM customers c"
                + " LEFT JOIN policies p ON c.customer_id = p.customer_id"
                + " ORDER BY c.customer_national_id ";
        
           //Create the connection to the database. Use the preparedStatement to execute the sql query and store it in the resultset. Find values of columns and add  it to the hashmap
        try {
            
            con = DatabaseConnectionUtil.GetConnection();
            prepStmt = con.prepareStatement(sql);
            result = prepStmt.executeQuery();
            
            //loop through the resultset to add the column values to the PolicyDisplayDTO and add it to a hashmap
            while (result.next()) {
                
                PolicyDisplayDTO dto = new PolicyDisplayDTO(
                        result.getInt("customer_age"),
                        result.getString("customer_national_id"),
                        result.getString("customer_name"),
                        result.getString("customer_surname"),
                        result.getString("customer_address"),
                        result.getString("policy_type"),
                        result.getDouble("sum_insured"),
                        result.getDouble("coverage_amount"),
                        result.getDouble("premium_amount")
                );
                
                //Add the policies to the list
                list.add(dto);
                
                
            }
            
            System.out.println("The policies report has been successfully generated");
            
        } catch (SQLException e) {
            
            System.out.println("There was an error generating a report of the customers with policies " + e.getMessage());
            throw new SQLException("Error generating report of customers with policies in ReportDAO ", e);
            
        } finally {
            
            //Close all the connections
            DatabaseConnectionUtil.CloseConnection(con, prepStmt, result);
            
        }
        
        return list;
        
    }
    
}
