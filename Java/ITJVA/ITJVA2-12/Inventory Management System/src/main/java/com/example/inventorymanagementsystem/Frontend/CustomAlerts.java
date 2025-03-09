//This class was created to answer QUESTION 1 AND QUESTION 3 of the project. I'll indicate which methods are meant for which question

package com.example.inventorymanagementsystem.Frontend;

import com.example.inventorymanagementsystem.Backend.Product;
import com.example.inventorymanagementsystem.Backend.ProductManager;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import java.time.LocalDate;
import java.util.ArrayList;

public class CustomAlerts {

    //These are the private attributes of the CustomAlerts class
    private final ProductManager productManager;
    private final ValidatingFields validation = new ValidatingFields();
    private final ArrayList<Product> products;
    private Product product;

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

        for (Product p : products) {
            if (p.getProductId() == productId) {
                product = p;
            } else {
                product = null;
            }
        }

    }

    //This method is to configure all the alerts used during the update and delete operations
    public void showAndConfigureAlert(Alert alert, String title, String headerText, TextField txtInput) {

        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.getDialogPane().setContent(createGrid(txtInput));
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

    }

    //This method shows the confirmation alerts when updating and deleting products from the ProductManager class
    private void showConfirmationAlert(String title, String header, TextField txtInput, Runnable onOk) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        showAndConfigureAlert(alert, title, header, txtInput);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                onOk.run();
            } else if (response == ButtonType.CANCEL) {
                alert.close();
            }
        });

    }

    //This method displays the product ID update alert
    public void showProductIdConfAlert() {

        TextField txtInput = new TextField();
        showConfirmationAlert("Input", "Enter Product ID to update: ", txtInput, () -> {

            //Validate the text fields input for numbers only and to see if it is empty.
            //Answers QUESTION 3
            validation.validateID(txtInput, this);

            int productId = Integer.parseInt(txtInput.getText());
            findProduct(productId);

            //show the update product ID alert again if the product id has not been found
            if (product != null) {
                showQuantityConfAlert();
            } else {
                errorAlert("This product ID does not exist. Please try again");
                showProductIdConfAlert();
            }
        });

    }

    //This method displays the quantity update alert
    public void showQuantityConfAlert() {

        TextField txtInput = new TextField();
        txtInput.setText(Integer.toString(product.getQuantity()));
        showConfirmationAlert("Input", "Enter New Quantity", txtInput, () -> {

            //Validate the text fields input for numbers only and to see if it is empty.
            //Answers QUESTION 3
            validation.validateNumber(txtInput, this);

            //Link backend to frontend. Answers QUESTION 3
            product.setQuantity(Integer.parseInt(txtInput.getText()));

            showPriceConfAlert();
        });

    }

    //This method displays the price update alert. Handles the updating of the product in ProductManager class
    public void showPriceConfAlert() {

        TextField txtInput = new TextField();
        txtInput.setText(Double.toString(product.getPrice()));
        showConfirmationAlert("Input", "Enter new price: ", txtInput, () -> {

            try {

                //Validate the text fields input for price only and to see if it is empty.
                //Answers QUESTION 3
                validation.validatePrice(txtInput, this);

                //Link backend to frontend. Answers QUESTION 3
                product.setPrice(Double.parseDouble(txtInput.getText()));

                if (productManager.updateProduct(product.getProductId(), product)) {
                    showProductInfoAlert("This product has been successfully updated");
                    product.setUpdatedAt(LocalDate.now());
                }
            } catch (RuntimeException ex) {
                errorAlert("There was an error. Please try again");
            }
        });

    }

    //This method shows the delete product ID alert
    public void showProductDeleteConfAlert() {

        TextField txtInput = new TextField();
        showConfirmationAlert("Input", "Enter product ID to delete: ", txtInput, () -> {

            //Validate the text fields input for numbers only and to see if it is empty.
            //Answers QUESTION 3
            validation.validateID(txtInput, this);
            int productId = Integer.parseInt(txtInput.getText());

            findProduct(productId);

            //show the delete product ID alert again if the product id has not been found
            if (product != null) {
                showDeleteAlertWarning(productId);
            } else {
                errorAlert("This product ID does not exist. Please try again");
                showProductDeleteConfAlert();
            }
        });

    }

    //This method displays the alert warning if the product ID has been found
    public void showDeleteAlertWarning(int productId) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Are you sure you want to delete this product? ");
        alert.setTitle("Warning");

        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        alert.showAndWait().ifPresent(response -> {

            if (response == ButtonType.OK) {

                try {

                    if (productManager.deleteProduct(productId)) {
                        showProductInfoAlert("This product has been deleted successfully");
                    }

                } catch (RuntimeException ex) {
                    errorAlert("There was an error. Please try again");
                }

            } else {
                alert.close();
            }
        });

    }

    //Show the alert that the product has been deleted or updated successfully
    public void showProductInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(message);
        alert.setTitle("Message");

        alert.getButtonTypes().setAll(ButtonType.OK);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                alert.close();
            }
        });
    }

    //Show the alert when an error has occurred during an operation
    public void errorAlert(String message) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(message);
            alert.setTitle("Error");

            alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

            alert.showAndWait().ifPresent(response -> {

                if (response == ButtonType.OK) {
                    alert.close();
                }

            });

    }
}
