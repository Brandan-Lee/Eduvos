//This class was created to answer QUESTION 1 and QUESTION 3

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

    //Private attributes of the AddProductView class
    private final ProductManager productManager;
    private final ValidatingFields validation = new ValidatingFields();
    private Product product;
    private int count = 1;

    //Constructor of the class
    public AddProductView(ProductManager productManager) {
        this.productManager = productManager;
    }

    //This method displays the main GUI of the class
    @Override
    public void start(Stage primaryStage) throws IOException {

        GridPane grid = createMainLayout(primaryStage);

        //Create the scene for the program
        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setTitle("Add Product");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    //This method displays the main layout of the GUI
    private GridPane createMainLayout(Stage primaryStage) {

        CustomAlerts alert = new CustomAlerts(productManager);

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

        //This method is called to handle all the GUI manipulation
        buttonControl(addBtn, closeBtn, txtName, txtQuantity, txtPrice, alert, primaryStage);

        return grid;
    }

    //This method adds the product to the ProductManager class. Answers QUESTION 3
    private void addProduct(TextField txtName, TextField txtQuantity, TextField txtPrice, CustomAlerts alert, Stage parent) {

        try {
            if (!validation.validateName(txtName, alert, parent) || !validation.validateNumber(txtQuantity, alert, parent) || !validation.validatePrice(txtPrice, alert, parent)) {
                return;
            }

            //Get the product details
            int productId = count;
            String name = txtName.getText();
            int quantity = Integer.parseInt(txtQuantity.getText());
            double price = Double.parseDouble(txtPrice.getText());
            LocalDate created = LocalDate.now();
            LocalDate updated = null;

            //Add the product to the Product class. Still add it if there is no products. Answers QUESTION 3
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

            //Add the product to the ProductManager class. Answers QUESTION 3
            if (productManager.addProduct(product)) {
                alert.showProductInfoAlert("The product has been successfully added", parent);
                clearAddFields(txtName, txtQuantity, txtPrice);
                count++;
            }
        } catch (RuntimeException ex) {
            alert.errorAlert("There was a problem adding the product. Please check your configuration", parent);
        }

    }

    //This method clears all the fields in the QUI once a product has been added successfully
    private void clearAddFields(TextField txtQuantity, TextField txtPrice, TextField txtName) {
        
        txtName.setText("");
        txtQuantity.setText("");
        txtPrice.setText("");
        
    }

    //This method adds the product to the ProductManager class. Answers Question 3
    private void buttonControl(Button addBtn, Button closeBtn, TextField txtName, TextField txtQuantity, TextField txtPrice, CustomAlerts alert, Stage primaryStage) {

        addBtn.setOnAction(e -> addProduct(txtName, txtQuantity, txtPrice, alert, primaryStage));

        closeBtn.setOnAction(e -> primaryStage.close());

    }

}
