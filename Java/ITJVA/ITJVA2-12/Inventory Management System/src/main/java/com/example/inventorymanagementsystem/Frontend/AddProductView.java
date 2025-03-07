package com.example.inventorymanagementsystem.Frontend;

import com.example.inventorymanagementsystem.Backend.Product;
import com.example.inventorymanagementsystem.Backend.ProductManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class AddProductView extends Application {

    private final ProductManager productManager;
    private Product product;
    private int count = 1;

    public AddProductView(ProductManager productManager) {
        this.productManager = productManager;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        //GUI Layout Components and UI control initialization
        final Label lblName = new Label("Name:");
        TextField txtName = new TextField();
        final Label lblQuantity = new Label("Quantity:");
        TextField txtQuantity = new TextField();
        final Label lblPrice = new Label("Price:");
        final TextField txtPrice = new TextField();
        final Button addBtn = new Button("Add");
        final Button closeBtn = new Button("Close");
        final HBox btnBox = new HBox(addBtn, closeBtn);
        GridPane grid = new GridPane();

        //Add the spacing to the button box
        btnBox.setSpacing(30);

        //Add padding to the grid
        grid.setPadding(new Insets(10));
        grid.setVgap(20);
        grid.setHgap(20);

        //Add the labels and the textFields to the grid pane
        grid.add(lblName, 0, 0);
        grid.add(txtName, 1, 0);
        grid.add(lblQuantity, 0,1);
        grid.add(txtQuantity, 1,1);
        grid.add(lblPrice, 0, 2);
        grid.add(txtPrice, 1,2);
        btnBox.setPadding(new Insets(0,10,0,0));
        grid.add(btnBox,1, 3);

        //Create the scene for the program
        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setTitle("Add Product");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Button events
        addBtn.setOnAction(e -> {
            CustomAlerts alert = new CustomAlerts();
            ValidatingFields validation = new ValidatingFields();

            try {
                int productId = count;
                String name = "";
                int quantity = 0;
                double price = 0.0;
                LocalDate created = LocalDate.now();
                LocalDate updated = null;

                if (!validation.isString(txtName)) {
                    alert.errorAlert("Name input can only contain letters. Please try again.");
                    txtName.setText("");
                } else if (validation.isNotEmpty(txtName)) {
                    alert.errorAlert("The name text field is empty.");
                } else {
                    name = txtName.getText();
                }

                if (!validation.isNumber(txtQuantity)) {
                    alert.errorAlert("Quantity input can only contain numbers. Please try again.");
                    txtQuantity.setText("");
                } else if (validation.isNotEmpty(txtQuantity)) {
                    alert.errorAlert("The quantity text field is empty.");
                } else {
                    quantity = Integer.parseInt(txtQuantity.getText());
                }

                if (!validation.isCurrency(txtPrice)) {
                    alert.errorAlert("Price input can only contain numbers and a decimal point. Please try again");
                    txtPrice.setText("");
                } else if (validation.isNotEmpty(txtPrice)) {
                    alert.errorAlert("The price text field is empty.");
                } else {
                    price = Double.parseDouble(txtPrice.getText());
                }

                if (product == null) {
                    product = new Product(productId, name, quantity, price, created, updated);
                    product.setProductId(productId);
                    product.setName(name);
                    product.setQuantity(quantity);
                    product.setPrice(price);
                    product.setCreatedAt(created);
                    product.setUpdatedAt(updated);
                } else {
                    product = new Product(productId, name, quantity, price, created, updated);
                }

                if (productManager.addProduct(product)) {
                    alert.showProductInfoAlert("The product has been successfully added");
                }

                count++;

                txtName.setText("");
                txtQuantity.setText("");
                txtPrice.setText("");

            } catch (RuntimeException ex) {
                alert.errorAlert("There was a problem adding the product. Please check your configuration");
            }
        });

        closeBtn.setOnAction(e -> {
            primaryStage.close();
        });
    }

}
