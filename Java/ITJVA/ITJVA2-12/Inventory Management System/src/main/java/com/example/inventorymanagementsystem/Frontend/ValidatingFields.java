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
        return txt.getText().matches("^[0-9]+\\.[0-9]+$");
    }
}
