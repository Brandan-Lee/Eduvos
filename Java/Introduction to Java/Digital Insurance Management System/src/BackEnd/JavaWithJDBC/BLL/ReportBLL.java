
package BackEnd.JavaWithJDBC.BLL;

import BackEnd.JavaWithJDBC.DAL.DAO.ReportDAO;
import BackEnd.JavaWithJDBC.DAL.DTO.CustomerWithPoliciesDTO;
import java.util.*;
import java.sql.*;

/**
 * @author brand
 */

public class ReportBLL {
    
    private ReportDAO reportDAO;
    
    public ReportBLL(ReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }
    
    public HashMap<Integer, CustomerWithPoliciesDTO> generateCustomersWithPoliciesReport(CustomerWithPoliciesDTO customer) throws SQLException {
        
        HashMap<Integer, CustomerWithPoliciesDTO> report = reportDAO.generateCustomersWithPoliciesReport(customer);
        
        if (report == null) {
            
            System.out.println("There was an error generating a report from ReportBLL");
            return new HashMap<>();
            
        } else if (report.isEmpty()) {
            
            System.out.println("A report couldn't be generated from ReportBLL");
            return report;
            
        } else {
            
            System.out.println("A report has been successfully generated in ReportBLL");
            return report;
            
        }
        
    }
}
