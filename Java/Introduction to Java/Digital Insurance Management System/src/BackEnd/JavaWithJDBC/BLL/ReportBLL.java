
package BackEnd.JavaWithJDBC.BLL;

import BackEnd.JavaWithJDBC.DAL.DAO.ReportDAO;
import BackEnd.JavaWithJDBC.DAL.DTO.PolicyDisplayDTO;
import java.sql.*;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author brand
 */

public class ReportBLL {
    
    private ReportDAO reportDAO;
    
    public ReportBLL(ReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }
    
    //Communicate with reportDAO to get all the policies from the table
    public ObservableList<PolicyDisplayDTO> getAllPolicies() throws SQLException {
        
        ArrayList<PolicyDisplayDTO> policies = reportDAO.getAllPolicies();
        
        ObservableList<PolicyDisplayDTO> list = FXCollections.observableArrayList(policies);
        
        //check to see if the display list is empty or not
        if (list.isEmpty()) {
            System.out.println("No policies have been found in report dao");
        }
        
        return list;
        
    }
    
}
