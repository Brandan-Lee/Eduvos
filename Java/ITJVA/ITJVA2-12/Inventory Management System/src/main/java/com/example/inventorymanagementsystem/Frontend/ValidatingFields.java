//This class was created to answer QUESTION 3 of the project

package com.example.inventorymanagementsystem.Frontend;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ValidatingFields {

    //This is a helper method which helps the validation methods to handle errors
    private boolean handleValidationError(boolean condition, String errorMessage, CustomAlerts alert, Stage parent, TextField txtInput) {

        if (condition) {
            alert.errorAlert(errorMessage, parent);
            txtInput.setText("");
            return false;
        }

        return true;

    }

    //This method uses regex to check if the text field only contains either uppercase or lowercase letter input
    public boolean isString(TextField txt) {
        return txt.getText().matches("^[a-zA-Z]+$");
    }

    //This method checks if the text field is empty or not
    public boolean isEmpty(TextField txt) {
        return txt.getText().isEmpty();
    }

    //This method uses regex to check if the text field only contains numbers
    public boolean isNumber(TextField txt) {
        return txt.getText().matches("^[0-9]+");
    }

    //This method uses regex to check if the text field only contains numbers or decimal values
    public boolean isCurrency(TextField txt) {
        return txt.getText().matches("^[0-9]+(\\.[0-9]+)?$");
    }

    //This method validates the product ID text field. Checks if it is empty and if it is only a number. Displays valid error alerts
    public boolean validateID(TextField txt, CustomAlerts alert, Stage parent) {

        return handleValidationError(isEmpty(txt), "The product ID text field is empty.", alert, parent, txt) &&
                handleValidationError(!isNumber(txt), "Product ID input can only contain numbers. Please try again.", alert, parent, txt);

    }

    //This method validates the name text field. Checks if it is empty and if it is only a string. Displays valid error alerts
    public boolean validateName(TextField txt, CustomAlerts alert, Stage parent) {

        return handleValidationError(isEmpty(txt), "The name text field is empty.", alert, parent, txt) &&
                handleValidationError(!isString(txt), "Name input can only contain letters. Please try again.", alert, parent, txt);

    }

    //This method validates the quantity text field. Checks if it is empty and if it is only an integer. Displays valid error alerts
    public boolean validateQuantity(TextField txt, CustomAlerts alert, Stage parent) {

        return handleValidationError(isEmpty(txt), "The quantity text field is empty.", alert, parent, txt) &&
                handleValidationError(!isNumber(txt), "Quantity input can only contain numbers. Please try again.", alert, parent, txt);


    }

    //This method validates the price text field. Checks if it is empty and if it is only a double. Displays valid error alerts
    public boolean validatePrice(TextField txt, CustomAlerts alert, Stage parent) {

        return handleValidationError(isEmpty(txt), "The price text field is empty.", alert, parent, txt) &&
                handleValidationError(!isCurrency(txt), "Price input can only contain numbers and a decimal point. Please try again", alert, parent, txt);


    }

}
