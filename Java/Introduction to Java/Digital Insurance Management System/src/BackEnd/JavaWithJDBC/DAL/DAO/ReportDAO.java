
package BackEnd.JavaWithJDBC.DAL.DAO;

import BackEnd.JavaWithJDBC.DAL.DTO.CustomerWithPoliciesDTO;
import BackEnd.JavaWithJDBC.DAL.DTO.CustomerDTO;
import BackEnd.JavaWithJDBC.DAL.DTO.PolicyDTO;
import BackEnd.JavaWithJDBC.DatabaseConnectionUtil;
import java.util.*;
import java.sql.*;

/**
 * @author brand
 */

public class ReportDAO {
    
    public HashMap<Integer, CustomerWithPoliciesDTO> generateCustomersWithPoliciesReport(CustomerWithPoliciesDTO customer) throws SQLException {
        
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet result = null;
        HashMap<Integer, CustomerWithPoliciesDTO> customerReports = new HashMap<>();
        String sql = "SELECT"
                + " c.customer_id,"
                + " c.customer_national_id,"
                + " c.customer_name,"
                + " c.customer_surname,"
                + " c.customer_address,"
                + " c.customer_age,"
                + " p.policy_type,"
                + " p.sum_insured,"
                + " p.coverage_amount,"
                + " p.premiumAmount"
                + " FROM customers c"
                + " INNER JOIN policies p ON c.customer_id = p.customer_id"
                + " ORDER BY c.customer_id";
        
        try {
            
            con = DatabaseConnectionUtil.GetConnection();
            prepStmt = con.prepareStatement(sql);
            result = prepStmt.executeQuery();
            
            while (result.next()) {
                int customerId = result.getInt("customer_id");
                
                //Check to see if the customer ids report has already been handled if not, add the customer
                CustomerWithPoliciesDTO report = customerReports.get(customerId);
                
                if (report == null) {
                    CustomerDTO customerDTO = new CustomerDTO(
                        customerId,
                        result.getInt("customer_age"),
                        result.getString("customer_national_id"),
                        result.getString("customer_name"),
                        result.getString("customer_surname"),
                        result.getString("customer_address")
                    );
                    
                    report.setCustomer(customerDTO);
                    customerReports.put(customerId, report);
                }
                
                //Add the policies if they exist for the current customer
                if (result.getInt("policy_id") != 0) {
                    PolicyDTO policyDTO = new PolicyDTO(
                            result.getInt("policy_id"),
                            result.getString("policy_type"),
                            result.getDouble("sum_insured"),
                            result.getDouble("coverage_amount"),
                            result.getDouble("premium_amount"),
                            result.getInt("customer_id")
                    );
                    
                    report.addPolicy(policyDTO);
                }
                
                System.out.println("The Customers with Policies report has been successfully generated");
                
            }
            
        } catch (SQLException e) {
            
            System.out.println("There was an error generating a report of the customers with policies " + e.getMessage());
            throw new SQLException("Error generating report of customers with policies in ReportDAO ", e);
            
        } finally {
            DatabaseConnectionUtil.CloseConnection(con, prepStmt, result);
        }
        
        return customerReports;
        
    }
    
}
