//This class was created to answer QUESTION 1 AND QUESTION 3 of the project. I'll indicate which methods are meant for which question

package com.example.inventorymanagementsystem.Frontend;

import com.example.inventorymanagementsystem.Backend.Product;
import com.example.inventorymanagementsystem.Backend.ProductManager;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

public class CustomAlerts {

    //These are the private attributes of the CustomAlerts class
    private final ProductManager productManager;
    private final ValidatingFields validation = new ValidatingFields();
    private final ArrayList<Product> products;
    private Product product;
    private final String ALERT_TYPE_INPUT = "Input";
    private final String ALERT_TYPE_ERROR = "Error";
    private final String ALERT_TYPE_WARNING = "Warning";
    private final String ALERT_TYPE_MESSAGE = "Message";


    //This is the constructor of the class
    public CustomAlerts(ProductManager productManager) {

        this.productManager = productManager;
        this.products = productManager.viewProducts();

    }

    //This creates the main GUI of the alerts
    private GridPane createGrid(TextField txtInput) {

        final GridPane grid = new GridPane();
        grid.add(txtInput, 0, 0);
        GridPane.setHgrow(txtInput, Priority.ALWAYS);
        return grid;

    }

    //This is to find the product in the ProductManager class to handle update and delete operations
    //Answers QUESTION 3
    private void findProduct(int productId) {

        product = null;

        for (Product p : products) {
            if (p.getProductId() == productId) {
                product = p;
                break;
            }
        }

    }

    //This method is used to configure the alert that is needed for the gui
    private void configureAlert(Alert alert, String title, String headerText, Stage parent, TextField txtInput) {
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        if (txtInput != null) {
            GridPane grid = createGrid(txtInput);
            alert.getDialogPane().setContent(createGrid(txtInput));
        }

        if (parent != null) {
            positionAlert(alert, parent);
        }
    }

    //This method handles the response that is thrown from the alert
    private void handleAlertResponses(Alert alert, Runnable onOk) {
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                onOk.run();
            } else if (response == ButtonType.CANCEL) {
                alert.close();
            }
        });
    }

    //This method is to configure all the alerts used during the update and delete operations
    private void showAndConfigureAlert(Alert.AlertType type, String title, String headerText, Stage parent, TextField txtInput, Runnable onOk) {

        Alert alert = new Alert(type);
        configureAlert(alert, title, headerText, parent, txtInput);
        handleAlertResponses(alert, onOk);

    }

    //This method helps to position the alert to the middle of the parent GUI
    private void positionAlert(Alert alert, Stage parent) {

        alert.initOwner(parent);
        alert.initModality(Modality.WINDOW_MODAL);

        //Set the new alerts position to the middle of the parent stage
        double centerXPosition = parent.getX() + (parent.getWidth() - alert.getWidth()) / 2;
        double centerYPosition = parent.getY() + (parent.getHeight() - alert.getHeight()) / 2;

        alert.setX(centerXPosition);
        alert.setY(centerYPosition);

    }

    //Show the alert when an error has occurred during an operation
    public void errorAlert(String message, Stage parent) {

        showAndConfigureAlert(Alert.AlertType.ERROR, ALERT_TYPE_ERROR, message, parent, null, () -> {});

    }

    //Show the alert that the product has been deleted or updated successfully
    public void productInfo(String message, Stage parent) {

        showAndConfigureAlert(Alert.AlertType.INFORMATION, ALERT_TYPE_MESSAGE, message, parent, null, () -> {});

    }

    //This method is used to validate the input of the texttfields
    private boolean validateInput(TextField txtInput, String type, Stage parent) {

        boolean isValid;

        switch (type) {
            case ("ID"):
                isValid = validation.validateID(txtInput, this, parent);
                break;
            case ("Quantity"):
                isValid = validation.validateQuantity(txtInput, this, parent);
                break;
            case ("Price"):
                isValid = validation.validatePrice(txtInput, this, parent);
                break;
            default:
                System.out.println("Validation error: " + type);
                throw new IllegalArgumentException("Invalid validation type");
        }

        return isValid;
    }

    //This method is used to prompt the user to enter input
    private String promptInput(String title, String headerText, Stage parent, String validationType, String defaultValue) {
        TextField txtInput = new TextField();

        if (defaultValue != null) {
            txtInput.setText(defaultValue);
        }

        final String[] input = {null};

        showAndConfigureAlert(Alert.AlertType.CONFIRMATION, title, headerText, parent, txtInput, () -> {
            if (validationType != null && !validateInput(txtInput, validationType, parent)) {
                return;
            }

            input[0] = txtInput.getText().trim();
        });

        return input[0];
    }

    //This method displays the product ID update alert
    public void updateProductId(Stage parent) {
        String input = promptInput(ALERT_TYPE_INPUT, "Enter product ID to update: ", parent, "ID", null);

        if (input == null) {
            return;
        }

        int productId = Integer.parseInt(input);
        findProduct(productId);

        if (product != null) {
            updateQuantity(parent);
        } else {
            errorAlert("This product ID does not exist. Please try again", parent);
        }

    }

    //This method displays the quantity update alert
    private void updateQuantity(Stage parent) {
        String input = promptInput(ALERT_TYPE_INPUT, "Enter new quantity: ", parent, "Quantity", String.valueOf(product.getQuantity()));

        if (input == null) {
            return;
        }

        //Link backend to frontend. Answers QUESTION 3
        product.setQuantity(Integer.parseInt(input));
        updatePrice(parent);

    }

    //This method displays the price update alert. Handles the updating of the product in ProductManager class
    private void updatePrice(Stage parent) {

        String input = promptInput(ALERT_TYPE_INPUT, "Enter new price: ", parent, "Price", String.valueOf(product.getPrice()));

        if (input == null) {
            return;
        }

        try {
            //Link backend to frontend. Answers QUESTION 3
            product.setPrice(Double.parseDouble(input));

            //check to see if the product has been updated
            if (productManager.updateProduct(product.getProductId(), product)) {
                productInfo("This product has been successfully updated", parent);
                product.setUpdatedAt(LocalDate.now());
            }
        } catch (RuntimeException ex) {
            System.out.println("Exception updating of product: " + ex.getMessage());
            errorAlert("There was an error. Please try again", parent);
        }

    }

    //This method shows the delete product ID alert
    public void deleteProductID(Stage parent) {

        String input = promptInput(ALERT_TYPE_INPUT, "Enter product ID to delete: ", parent, "ID", null);

        if (input == null) {
            return;
        }

        int productId = Integer.parseInt(input);
        findProduct(productId);

        //show the delete product ID alert again if the product id has not been found
        if (product != null) {
            deleteWarning(productId, parent);
        } else {
            errorAlert("This product ID does not exist. Please try again", parent);
        }

    }

    //This method displays the alert warning if the product ID has been found
    private void deleteWarning(int productId, Stage parent) {

        showAndConfigureAlert(Alert.AlertType.WARNING, ALERT_TYPE_WARNING, "Are you sure you want to delete product " + product.getName() + "?", parent, null, () -> {
            try {
                //check to see if the product has been deleted successfully
                if (productManager.deleteProduct(productId)) {
                    productInfo("This product has been deleted successfully", parent);
                }
            } catch (RuntimeException ex) {
                System.out.println("Exception deletion of product: " + ex.getMessage());
                errorAlert("There was an error. Please try again", parent);
            }
        });

    }

}
