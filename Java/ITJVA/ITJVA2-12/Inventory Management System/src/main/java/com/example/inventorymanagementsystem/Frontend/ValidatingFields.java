//This class was created to answer QUESTION 3 of the project

package com.example.inventorymanagementsystem.Frontend;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ValidatingFields {

    //This method uses regex to check if the text field only contains either uppercase or lowercase letter input
    public boolean isString(TextField txt) {
        return txt.getText().matches("^[a-zA-Z]+$");
    }

    //This method checks if the text field is empty or not
    public boolean isNotEmpty(TextField txt) {
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

        if (isNotEmpty(txt)) {
            alert.errorAlert("The product ID text field is empty.", parent);
            return false;
        } else if (!isNumber(txt)) {
            alert.errorAlert("Product ID input can only contain numbers. Please try again.", parent);
            txt.setText("");
            return false;
        }

        return true;
    }

    //This method validates the name text field. Checks if it is empty and if it is only a string. Displays valid error alerts
    public boolean validateName(TextField txt, CustomAlerts alert, Stage parent) {

        if (isNotEmpty(txt)) {
            alert.errorAlert("The name text field is empty.", parent);
            return false;
        } else if (!isString(txt)) {
            alert.errorAlert("Name input can only contain letters. Please try again.", parent);
            txt.setText("");
            return false;
        }

        return true;
    }

    //This method validates the quantity text field. Checks if it is empty and if it is only an integer. Displays valid error alerts
    public boolean validateNumber(TextField txt, CustomAlerts alert, Stage parent) {

        if (isNotEmpty(txt)) {
            alert.errorAlert("The quantity text field is empty.", parent);
            return false;
        } else if (!isNumber(txt)) {
            alert.errorAlert("Quantity input can only contain numbers. Please try again.", parent);
            txt.setText("");
            return false;
        }

        return true;
    }

    //This method validates the price text field. Checks if it is empty and if it is only a double. Displays valid error alerts
    public boolean validatePrice(TextField txt, CustomAlerts alert, Stage parent) {

        if (isNotEmpty(txt)) {
            alert.errorAlert("The price text field is empty.", parent);
            return false;
        } else if (!isCurrency(txt)) {
            alert.errorAlert("Price input can only contain numbers and a decimal point. Please try again", parent);
            txt.setText("");
            return false;
        }

        return true;
    }

}
