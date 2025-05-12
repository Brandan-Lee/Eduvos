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
    private static final int SPACING = 20;

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
        //Add the spacing to the button box
        btnBox.setSpacing(SPACING);

        GridPane grid = createGrid(txtName, lblName, txtQuantity, lblQuantity, txtPrice, lblPrice, btnBox);

        //This method is called to handle all the GUI manipulation
        buttonControl(addBtn, closeBtn, txtName, txtQuantity, txtPrice, alert, primaryStage);

        return grid;
    }

    //This method creates the grid needed for the GUI
    private GridPane createGrid(TextField txtName, Label lblName, TextField txtQuantity, Label lblQuantity, TextField txtPrice, Label lblPrice, HBox btnBox) {
        GridPane grid = new GridPane();

        //Add padding to the grid and spacing
        grid.setPadding(new Insets(10));
        grid.setVgap(SPACING);
        grid.setHgap(SPACING);

        //Add the labels and the textFields to the grid pane
        grid.add(lblName, 0, 0);
        grid.add(txtName, 1, 0);
        grid.add(lblQuantity, 0,1);
        grid.add(txtQuantity, 1,1);
        grid.add(lblPrice, 0, 2);
        grid.add(txtPrice, 1,2);
        btnBox.setPadding(new Insets(0,10,0,0));
        grid.add(btnBox,1, 3);

        return grid;
    }

    //This method adds the product to the ProductManager class. Answers QUESTION 3
    private void addProduct(TextField txtName, TextField txtQuantity, TextField txtPrice, CustomAlerts alert, Stage parent) {

        try {
            if (!validation.validateName(txtName, alert, parent) ||
                    !validation.validateQuantity(txtQuantity, alert, parent) ||
                    !validation.validatePrice(txtPrice, alert, parent)) {
                return;
            }

            //Get the product details
            int productID = productManager.viewProducts().size() + 1;

            //Add the product to the Product class. Still add it if there is no products. Answers QUESTION 3
            product = new Product(
                    productID,
                    txtName.getText(),
                    Integer.parseInt(txtQuantity.getText()),
                    Double.parseDouble(txtPrice.getText()),
                    LocalDate.now(),
                    null
            );

            //Add the product to the ProductManager class and show feedback. Answers QUESTION 3
            if (productManager.addProduct(product)) {
                alert.productInfo("The product has been successfully added", parent);
                clearAddFields(txtName, txtQuantity, txtPrice);
                productID++;
            }
        } catch (RuntimeException ex) {
            System.out.println("Error adding product " + ex.getMessage());
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
