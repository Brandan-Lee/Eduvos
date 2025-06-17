package FrontEnd.JavaFX.Controllers;

import BackEnd.JavaWithJDBC.BLL.CustomerBLL;
import BackEnd.JavaWithJDBC.BLL.PolicyBLL;
import BackEnd.JavaWithJDBC.BLL.ReportBLL;
import BackEnd.JavaWithJDBC.DAL.DAO.CustomerDAO;
import BackEnd.JavaWithJDBC.DAL.DAO.PolicyDAO;
import BackEnd.JavaWithJDBC.DAL.DAO.ReportDAO;
import BackEnd.JavaWithJDBC.DAL.DTO.CustomerDTO;
import BackEnd.JavaWithJDBC.DAL.DTO.PolicyDTO;
import FrontEnd.JavaFX.CustomAlerts;
import GeneralUtilities.EnumSets;
import GeneralUtilities.Validation;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DIMSController implements Initializable {
    
    @FXML
    private GridPane gridMain;
    @FXML
    private Label lblHeading;
    @FXML
    private Label lblCustomerId;
    @FXML
    private TextField txtCustomerId;
    @FXML
    private Label lblCustomerName;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private Label lblSurname;
    @FXML
    private TextField txtCustomerSurname;
    @FXML
    private Label lbllCustomerAddress;
    @FXML
    private TextField txtCustomerAddress;
    @FXML
    private Label lblCustomerAge;
    @FXML
    private TextField txtCustomerAge;
    @FXML
    private Label lblPolicy;
    @FXML
    private ComboBox<String> cmbPolicyType;
    @FXML
    private Label lblSumInsured;
    @FXML
    private ComboBox<Double> cmbSumInsured;
    @FXML
    private Label lblCoverageAmount;
    @FXML
    private TextField txtCoverageAmount;
    @FXML
    private HBox calculateHbox;
    @FXML
    private Button btnCalculatePremium;
    @FXML
    private Label lblPremium;
    @FXML
    private TextField txtPremium;
    @FXML
    private HBox submitHBox;
    @FXML
    private Button btnSubmit;
    @FXML
    private HBox viewPoliciesAndCloseHbox;
    @FXML
    private Button btnViewPolicies;
    @FXML
    private Button btnClose;
    
    private Validation validate;
    private CustomAlerts alert;
    private CustomerDAO customerDAO;
    private CustomerBLL customerBLL;
    private PolicyDAO policyDAO;
    private PolicyBLL policyBLL;
    private ReportBLL reportBLL;
    private ReportDAO reportDAO;
    private Stage stage;
    private Scene scene;
    
    @Override
    public void initialize(URL location, ResourceBundle rs) {
        
        customerDAO = new CustomerDAO();        
        policyDAO = new PolicyDAO();
        reportDAO = new ReportDAO();
        customerBLL = new CustomerBLL(customerDAO);
        policyBLL = new PolicyBLL(policyDAO);
        reportBLL = new ReportBLL(reportDAO);
        validate = new Validation();
        String cssPath = getClass().getResource("/FrontEnd/JavaFX/style.css").toExternalForm();
        alert = new CustomAlerts(cssPath);
        fillComboBoxes();
        
    }

    //This method is used to fill all the comboboxes when the application is launched
    private void fillComboBoxes() {
        
        cmbPolicyType.getItems().clear();
        cmbSumInsured.getItems().clear();
        
        for (EnumSets.PolicyType value : EnumSets.PolicyType.values()) {
            cmbPolicyType.getItems().add(value.name());
        }
        
        for (EnumSets.SumInsured value : EnumSets.SumInsured.values()) {
            cmbSumInsured.getItems().add((double) value.getValue());
        }
        
        if (!cmbPolicyType.getItems().isEmpty()) {
            cmbPolicyType.getSelectionModel().selectFirst();
        }
        
        if (!cmbSumInsured.getItems().isEmpty()) {
            cmbSumInsured.getSelectionModel().selectFirst();
        }
        
    }

    //close the Application
    @FXML
    public void closeApplication(ActionEvent event) throws IOException {
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        
    }

    //Calculate the policy premium
    @FXML
    public void calculatePremium(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        //check to see if text field is empty, else calculate the premium amount
        if (!validate.validateCoverageAmount(txtCoverageAmount, alert, stage)) {
            return;
        } else {
            
            double premium = policyBLL.calculatePolicyPremium(cmbPolicyType.getValue(), Double.parseDouble(txtCoverageAmount.getText()));
            txtPremium.setText(Double.toString(premium));
            
        }
        
    }
    
    //Submit the policy to the  backend
    @FXML
    public void submitPolicy(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addPolicy(stage);
    }
    
    private void addPolicy(Stage parent) {
        try {
            
            //check to see if the system is ready or not
            if (customerBLL == null || policyBLL == null || validate == null || alert == null) {
                System.err.println("BLLS/Utilitities haven't been initialized");
                alert.errorAlert("The Service functionality isn't ready yet.", parent);
            }
            
            //Validate user input first
        if (!validate.validateCustomerId(txtCustomerId, alert, parent) || !validate.validateCustomerName(txtCustomerName, alert, parent) || !validate.validateCustomerName(txtCustomerSurname, alert, parent) || !validate.validateCustomerAddress(txtCustomerAddress, alert, parent) || !validate.validateCustomerAge(txtCustomerAge, alert, parent)) {
            return;
        } else {
            
            //----------Start by adding the customer-----------
            int customerId;
            //Check to see if the customer already exists or not
            CustomerDTO existingCustomer = customerBLL.findCustomerByNationalId(txtCustomerId.getText());
            
            //the customer already exists
            if (existingCustomer != null) {
                
                customerId = existingCustomer.getCustomerId();
                alert.infoAlert("Information", "This customer already exists in the system. Adding another policy to them", parent, null);
                
            } else {
                
                //The customer doesn't already exist, so we create a new one
                CustomerDTO customer = new CustomerDTO(
                        0,
                        Integer.parseInt(txtCustomerAge.getText()),
                        txtCustomerId.getText(),
                        txtCustomerName.getText(),
                        txtCustomerSurname.getText(),
                        txtCustomerAddress.getText()
                );
                
                //check to see if the customer was added or not
                customerId = customerBLL.addCustomer(customer);
                
                if (customerId == -1) {
                    
                    alert.errorAlert("There was a problem adding the customer. Please validate your input", parent);
                    return;
                    
                }
                
            }
            
            //----------Adding the policy-----------
            //Validate user input first
            if (!validate.validatePolicyType(cmbPolicyType, alert, parent) || !validate.validateSumInsured(cmbSumInsured, alert, parent) || !validate.validateCoverageAmount(txtCoverageAmount, alert, parent) || !validate.validatePremium(txtPremium, alert, parent)) {
                return;
            } else {
                
                PolicyDTO policy = new PolicyDTO(
                    0,
                    cmbPolicyType.getValue(),
                    cmbSumInsured.getValue(),
                    Double.parseDouble(txtCoverageAmount.getText()),
                    Double.parseDouble(txtPremium.getText()),
                    customerId
                );
            
                //Check to see if the policy was added or not
                int generatedPolicyId = policyBLL.addPolicy(policy);
            
                if (generatedPolicyId == -1) {
                    alert.errorAlert("There was a problem adding the policy. Please try again", parent);
                }
                
            }
            
            //Notify the user that the addition of the policy was a success and clear all inputs
            alert.successAlert("Submission Successull!", "The policy was added successfully", parent, () -> {
               clearInput();
            });
            
        }
            
            
        } catch (SQLException e) {
            
            System.err.println("Error adding a customer: " + e.getMessage());
            alert.errorAlert("There was a problem with adding a customer and policy to the database", parent);
            e.printStackTrace();
            
        }
        
    }
    
    //This method will be used to clear all user input
    private void clearInput() {
        
        txtCustomerId.clear();
        txtCustomerName.clear();
        txtCustomerSurname.clear();
        txtCustomerAddress.clear();
        txtCustomerAge.clear();
        cmbPolicyType.getSelectionModel().selectFirst();
        cmbSumInsured.getSelectionModel().selectFirst();
        txtCoverageAmount.clear();
        txtPremium.clear();
        
    }
    
    
    //Change the scene to the view Policies scene
    @FXML
    public void viewPolicies(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("/FrontEnd/JavaFX/Views/PolicyView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }
    
}
