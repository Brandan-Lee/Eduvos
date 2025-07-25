
package GeneralUtilities;

import FrontEnd.JavaFX.CustomAlerts;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author brand
 */

public class Validation {
    
    //This is a helper method which helps the text field validation methods to handle errors
    private boolean handleTextFieldValidationError(boolean condition, String errorMessage, CustomAlerts alert, Stage parent, TextField txtInput) {
        
        if (condition) {
            
            alert.errorAlert(errorMessage, parent);
            txtInput.setText("");
            return false;
            
        }
        
        return true;
        
    }
    
    //This is a helper method which helps the combobox validation methods to handle errors
    private boolean handleComboBoxValidationError(boolean condition, String errorMessage, CustomAlerts alert, Stage parent, ComboBox cmb) {
        
        if (condition) {
            
            alert.errorAlert(errorMessage, parent);
            cmb.getSelectionModel().select(-1);
            return false;
            
        }
        
        return true;
    }
    
    //This method uses regex to check if the text field only contains either uppercase or lowercase letter input
    public boolean isString(TextField txt) {
        return txt.getText().matches("^[a-zA-Z]+$");
    }
    
    //This method checks if the text field is empty or not
    public boolean textFieldIsEmpty(TextField txt) {
         return txt.getText().isEmpty();
    }
    
    public boolean cmbIsEmpty(ComboBox cmb) {
        return cmb.getSelectionModel().isEmpty();
    }
    
    //This method uses regex to check if the tex field only constains numbers
    public boolean isNumber(TextField txt) {
        return txt.getText().matches("^[0-9]+$");
    }
    
    //This method uses regex to check if the text field only contains numbers or decimal values
    public boolean isCurrency(TextField txt) {
        return txt.getText().matches("^[0-9]+(\\.[0-9]+)?$");
    }
    
    //This method validates the customer ID text field. Check if it is empty and if it is only a number and that there aren't more than 13 characters entered. Displays valid error alerts
    public boolean validateCustomerId(TextField txt, CustomAlerts alert, Stage parent) {
        
        return handleTextFieldValidationError(textFieldIsEmpty(txt), "The customer ID text field is empty.", alert, parent, txt) && handleTextFieldValidationError(!isNumber(txt), "Customer ID input can only contain numbers. Please try again.", alert, parent, txt) && handleTextFieldValidationError(txt.getText().length() > 13, "The customer ID must be a South African ID and can't exceed the length of 13 characters. Please try again", alert, parent, txt);
        
    }
    
    //This method validates the customer surname text field. Check if it is empty and if it only contains letters. Displays valid error alerts
    public boolean validateCustomerName(TextField txt, CustomAlerts alert, Stage parent) {
        
        return handleTextFieldValidationError(textFieldIsEmpty(txt), "The customer name text field is empty.", alert, parent, txt) && handleTextFieldValidationError(!isString(txt), "The customer name text field can only contain letters. Please try again.", alert, parent, txt);
        
    }
    
    public boolean validateCustomerAddress(TextField txt, CustomAlerts alert, Stage parent) {
        return handleTextFieldValidationError(textFieldIsEmpty(txt), "The customer address text field is empty", alert, parent, txt);
    }
    
    public boolean validateCustomerAge(TextField txt, CustomAlerts alert, Stage parent) {
        
        return handleTextFieldValidationError(textFieldIsEmpty(txt), "The customer age text field is empty", alert, parent, txt) && handleTextFieldValidationError(!isNumber(txt), "The customer age text field can only contain numbers. Please try again.", alert, parent, txt);
        
    }
    
    public boolean validatePolicyType(ComboBox cmb, CustomAlerts alert, Stage parent) {
        return handleComboBoxValidationError(cmbIsEmpty(cmb), "Please select a policy type from the policy type combo box", alert, parent, cmb);
    }
    
    public boolean validateSumInsured(ComboBox cmb, CustomAlerts alert, Stage parent) {
        return handleComboBoxValidationError(cmbIsEmpty(cmb), "Please select a sum insured value from the sum insured combo box", alert, parent, cmb);
    }
    
    public boolean validateCoverageAmount(TextField txt, CustomAlerts alert, Stage parent) {
        
        return handleTextFieldValidationError(textFieldIsEmpty(txt), "The coverage amount text field is empty", alert, parent, txt) && handleTextFieldValidationError(!isCurrency(txt), "The coverage amount text field only accepts a currency. Please try again.", alert, parent, txt);
        
    }
    
    public boolean validatePremium(TextField txt, CustomAlerts alert, Stage parent) {
        
        return handleTextFieldValidationError(textFieldIsEmpty(txt), "Please ensure that you have entered a value at the coverage amount field and that you have pressed the calculate premium button", alert, parent, txt);
        
    }

    
}
