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

    private final ProductManager productManager;
    private final ValidatingFields validation = new ValidatingFields();
    private final ArrayList<Product> products;
    private Product product;

    public CustomAlerts(ProductManager productManager) {
        this.productManager = productManager;
        this.products = productManager.viewProducts();
    }

    private GridPane createGrid(TextField txtInput) {
        final GridPane grid = new GridPane();
        grid.add(txtInput, 0, 0);
        GridPane.setHgrow(txtInput, Priority.ALWAYS);
        return grid;
    }

    private Product findProduct(int productId) {
        for (Product p : products) {
            if (p.getProductId() == productId) {
                product = p;
            } else {
                product = null;
            }
        }

        return product;
    }

    public void showAndConfigureAlert(Alert alert, String title, String headerText, TextField txtInput) {
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.getDialogPane().setContent(createGrid(txtInput));
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
    }

    private void showConfirmationAlert(String title, String header, TextField txtInput, Runnable onOk) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        showAndConfigureAlert(alert, title, header, txtInput);

        alert.showAndWait().ifPresent(response -> {
            onOk.run();
        });
    }

    public void showProductIdConfAlert() {
        TextField txtInput = new TextField();
        showConfirmationAlert("Input", "Enter Product ID to update: ", txtInput, () -> {
            validation.validateID(txtInput, this);

            int productId = Integer.parseInt(txtInput.getText());
            findProduct(productId);

            if (product != null) {
                showQuantityConfAlert();
            } else {
                errorAlert("This product ID does not exist. Please try again");
                showProductIdConfAlert();
            }
        });
    }

    public void showQuantityConfAlert() {
        TextField txtInput = new TextField();
        txtInput.setText(Integer.toString(product.getQuantity()));
        showConfirmationAlert("Input", "Enter New Quantity", txtInput, () -> {
            validation.validateNumber(txtInput, this);

            product.setQuantity(Integer.parseInt(txtInput.getText()));
            showPriceConfAlert();
        });
    }

    public void showPriceConfAlert() {
        TextField txtInput = new TextField();
        txtInput.setText(Double.toString(product.getPrice()));
        showConfirmationAlert("Input", "Enter new price: ", txtInput, () -> {
            try {
                validation.validatePrice(txtInput, this);

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

    public void showProductDeleteConfAlert() {
        TextField txtInput = new TextField();
        showConfirmationAlert("Input", "Enter product ID to delete: ", txtInput, () -> {
            validation.validateID(txtInput, this);
            int productId = Integer.parseInt(txtInput.getText());

            findProduct(productId);

            if (product != null) {
                showDeleteAlertWarning(productId);
            } else {
                errorAlert("This product ID does not exist. Please try again");
                showProductDeleteConfAlert();
            }
        });
    }

    public void showDeleteAlertWarning(int productId) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Are you sure you want to delete this product? ");
        alert.setTitle("Warning");

        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    if (productManager.deleteProduct(productId)) {
                        showProductDeleteInfoAlert();
                    }
                } catch (RuntimeException ex) {
                    errorAlert("There was an error. Please try again");
                }
            } else {
                alert.close();
            }
        });
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
                }
            });
    }
}
