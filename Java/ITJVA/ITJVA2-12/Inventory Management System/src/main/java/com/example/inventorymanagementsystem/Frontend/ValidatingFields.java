package com.example.inventorymanagementsystem.Frontend;

import javafx.scene.control.TextField;

public class ValidatingFields {

    public boolean isString(TextField txt) {
        return txt.getText().matches("^[a-zA-Z]+$");
    }

    public boolean isNotEmpty(TextField txt) {
        return txt.getText().isEmpty();
    }

    public boolean isNumber(TextField txt) {
        return txt.getText().matches("^[0-9]+");
    }

    public boolean isCurrency(TextField txt) {
        return txt.getText().matches("^[0-9]+(\\.[0-9]+)?$");
    }

    public void validateID(TextField txt, CustomAlerts alert) {
        if (isNotEmpty(txt)) {
            alert.errorAlert("The product ID text field is empty.");
        } else if (!isNumber(txt)) {
            alert.errorAlert("Product ID input can only contain numbers. Please try again.");
            txt.setText("");
        }
    }

    public void validateName(TextField txt, CustomAlerts alert) {
        if (isNotEmpty(txt)) {
            alert.errorAlert("The name text field is empty.");
        } else if (!isString(txt)) {
            alert.errorAlert("Name input can only contain letters. Please try again.");
            txt.setText("");
        }
    }

    public void validateNumber(TextField txt, CustomAlerts alert) {
        if (isNotEmpty(txt)) {
            alert.errorAlert("The quantity text field is empty.");
        } else if (!isNumber(txt)) {
            alert.errorAlert("Quantity input can only contain numbers. Please try again.");
            txt.setText("");
        }
    }

    public void validatePrice(TextField txt, CustomAlerts alert) {
        if (isNotEmpty(txt)) {
            alert.errorAlert("The price text field is empty.");
        } else if (!isCurrency(txt)) {
            alert.errorAlert("Price input can only contain numbers and a decimal point. Please try again");
            txt.setText("");
        }
    }
}
