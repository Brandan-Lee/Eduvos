
package FrontEnd.JavaFX.Controllers;

import BackEnd.JavaWithJDBC.BLL.ReportBLL;
import BackEnd.JavaWithJDBC.DAL.DAO.ReportDAO;
import BackEnd.JavaWithJDBC.DAL.DTO.PolicyDisplayDTO;
import FrontEnd.JavaFX.CustomAlerts;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PolicyViewController implements Initializable {

    @FXML
    private Label lblTitle;
    @FXML
    private TableView<PolicyDisplayDTO> tblPolicy;
    @FXML
    private TableColumn<PolicyDisplayDTO, Integer> customerNationalId;
    @FXML
    private TableColumn<PolicyDisplayDTO, String> customerName;
    @FXML
    private TableColumn<PolicyDisplayDTO, String> customerSurname;
    @FXML
    private TableColumn<PolicyDisplayDTO, String> customerAddress;
    @FXML
    private TableColumn<PolicyDisplayDTO, Integer> customerAge;
    @FXML
    private TableColumn<PolicyDisplayDTO, String> policyType;
    @FXML
    private TableColumn<PolicyDisplayDTO, Double> sumInsured;
    @FXML
    private TableColumn<PolicyDisplayDTO, Double> coverageAmount;
    @FXML
    private TableColumn<PolicyDisplayDTO, Double> premiumAmount;
    @FXML
    private HBox btnBox;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnRefresh;
    
    private ReportDAO reportDAO;
    private ReportBLL reportBLL;
    private CustomAlerts alert;
    private Stage stage;
    private Scene scene;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        String cssPath = getClass().getResource("/FrontEnd/JavaFX/style.css").toExternalForm();
        alert = new CustomAlerts(cssPath);
        reportDAO = new ReportDAO();
        reportBLL = new ReportBLL(reportDAO);
        
        //set the designated values for each column in the table
        customerNationalId.setCellValueFactory(new PropertyValueFactory<>("customerNationalId"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerSurname.setCellValueFactory(new PropertyValueFactory<>("customerSurname"));
        customerAddress.setCellValueFactory(new PropertyValueFactory("customerAddress"));
        customerAge.setCellValueFactory(new PropertyValueFactory("customerAge"));
        policyType.setCellValueFactory(new PropertyValueFactory<>("policyType"));
        sumInsured.setCellValueFactory(new PropertyValueFactory("sumInsured"));
        coverageAmount.setCellValueFactory(new PropertyValueFactory("coverageAmount"));
        premiumAmount.setCellValueFactory(new PropertyValueFactory<>("premiumAmount"));
        
    }    
    
    //Load all of the data into the table
    @FXML
    public  void loadData(ActionEvent event) throws SQLException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        
        try {
            ObservableList<PolicyDisplayDTO> list = reportBLL.getAllPolicies();
            tblPolicy.setItems(list);
            
            if (list.isEmpty()) {
                alert.infoAlert("Information", "No policies have been found in the database", stage, null);
            }
            
        } catch (SQLException e) {
            System.out.println("Error retrieving all policies. " + e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    
    //Go back to the main page of the application
    @FXML
    private void close(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("/FrontEnd/JavaFX/Views/DIMS.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }
    
}
