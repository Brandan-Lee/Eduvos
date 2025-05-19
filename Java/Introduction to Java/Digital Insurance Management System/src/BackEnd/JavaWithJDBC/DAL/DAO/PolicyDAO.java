
package BackEnd.JavaWithJDBC.DAL.DAO;

import BackEnd.JavaWithJDBC.DAL.DTO.PolicyDTO;
import java.sql.*;

/**
 * * @author brand
 */

public class PolicyDAO {
    
    //This method will add a policy to the policy table of the database
    public void addPolicy(PolicyDTO policy) {
        
        Connection con = null;
        PreparedStatement prepStmt = null;
        String sql = "SELECT "
        
    }
    
}
