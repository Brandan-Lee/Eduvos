package com.example.inventorymanagementsystem.Frontend;

import com.example.inventorymanagementsystem.Backend.Product;
import com.example.inventorymanagementsystem.Backend.ProductManager;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.time.LocalDate;

public class CustomAlerts {

    private static InventoryManagementSystem ims = new InventoryManagementSystem();
    private static final ProductManager productManager = new ProductManager();
    private static final ValidatingFields validation = new ValidatingFields();
    private static int productId = 0;
    private static int quantity = 0;
    private static double price = 0.0;
    private static Product product;

    public static InventoryManagementSystem getIms() {
        return ims;
    }

    public static void setIms(InventoryManagementSystem ims) {
        CustomAlerts.ims = ims;
    }

    private GridPane createGrid(TextField txtInput) {
        final GridPane grid = new GridPane();
        grid.add(txtInput, 0, 0);
        GridPane.setHgrow(txtInput, Priority.ALWAYS);
        return grid;
    }

    public void showAndConfigureAlert(Alert alert, String title, String headerText, TextField txtInput) {
        alert.setTitle(title);
        alert.setHeaderText(headerText);

        alert.getDialogPane().setContent(createGrid(txtInput));

        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
    }

    public void showProductIdConfAlert() {
        TextField txtInput = new TextField();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        showAndConfigureAlert(alert, "Input", "Enter Product ID to Update: ", txtInput);

        try {
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    if (validation.isNotEmpty(txtInput)) {
                        errorAlert("The Product ID field is empty");
                    } else if (!validation.isNumber(txtInput)) {
                        errorAlert("The Product ID field can only contain numbers");
                        txtInput.setText("");
                    } else {
                        productId = Integer.parseInt(txtInput.getText());
                        showQuantityConfAlert();
                    }
                } else {
                    alert.close();
                }
            });
        } catch (RuntimeException ex) {
            errorAlert("There was an error. Please try again");
        }

    }

    public void showQuantityConfAlert() {
        TextField txtInput = new TextField();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        showAndConfigureAlert(alert, "Input", "Enter New Quantity: ", txtInput);

        try {
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    if (!validation.isNotEmpty(txtInput)) {
                        errorAlert("The Quantity field is empty");
                    } else if (!validation.isNumber(txtInput)) {
                        errorAlert("The Quantity field can only contain numbers");
                        txtInput.setText("");
                    } else {
                        quantity = Integer.parseInt(txtInput.getText());
                        showPriceConfAlert();
                    }
                } else {
                    alert.close();
                }
            });
        } catch (RuntimeException ex) {
            errorAlert("There was an error. Please try again");
        }

    }

    public void showPriceConfAlert() {
        TextField txtInput = new TextField();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        showAndConfigureAlert(alert, "Input", "Enter new Price: ", txtInput);

        try {
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    if (!validation.isNotEmpty(txtInput)) {
                        errorAlert("The Price field is empty");
                    } else if (!validation.isCurrency(txtInput)) {
                        errorAlert("The Price field can only contain numbers and a decimal point");
                        txtInput.setText("");
                    } else {
                        price = Double.parseDouble(txtInput.getText());

                        product.setProductId(productId);
                        product.setQuantity(quantity);
                        product.setPrice(price);
                        product.setUpdatedAt(LocalDate.now());

                        productManager.updateProduct(productId, product);
                    }
                } else {
                    alert.close();
                }
            });
        } catch (RuntimeException ex) {
            errorAlert("There was an error. Please try again");
        }

    }

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

    public void showProductDeleteConfAlert() {
        TextField txtInput = new TextField();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        showAndConfigureAlert(alert, "Input", "Enter Product ID to Delete: ", txtInput);

        try {
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    if (!validation.isNotEmpty(txtInput)) {
                        errorAlert("The Product ID field is empty");
                    } else if (!validation.isNumber(txtInput)) {
                        errorAlert("The Product ID field can only contain numbers");
                        txtInput.setText("");
                    } else {
                        productId = Integer.parseInt(txtInput.getText());
                        showDeleteAlertWarning();
                    }
                } else {
                    alert.close();
                }
            });
        } catch (RuntimeException ex) {
            errorAlert("There was an error. Please try again");
        }

    }

    public void showDeleteAlertWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Are you sure you want to delete this product? ");
        alert.setTitle("Warning");

        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    productManager.deleteProduct(productId);
                } catch (RuntimeException ex) {
                    errorAlert("There was an error. Please try again");
                }
            } else {
                alert.close();
            }
        });
    }

    public void showProductDeleteInfoAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Product deleted successfully! ");
        alert.setTitle("Message");

        alert.getButtonTypes().setAll(ButtonType.OK);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                alert.close();
            }
        });
    }

    public void errorAlert(String message) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(message);
            alert.setTitle("Error");

            alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    alert.close();
                } else {
                    System.out.println("Operation canceled.");
                }
            });
    }
}
