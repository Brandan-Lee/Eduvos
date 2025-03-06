package com.example.inventorymanagementsystem.Frontend;

import com.example.inventorymanagementsystem.Backend.Product;
import com.example.inventorymanagementsystem.Backend.ProductManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Date;

/**
 *
 * @author Brandan-Lee James Sherbrooke
 */

//Question 1: This is the main page of the inventory management system

public class InventoryManagementSystem extends Application {
    //Global variables for all pages
    AddProductView addView = new AddProductView();

    @Override
    public void start(Stage primaryStage) {
        //Create a product manager object to display all the products added to the product array list in the backend class
        ProductManager productManager = new ProductManager();

        //GUI Layout Components and UI control initialization
        TableView<Product> tblProducts = new TableView<>();
        final Button addBtn = new Button("Add New Product");
        final Button updateBtn = new Button("Update Product");
        final Button deleteBtn = new Button("Delete Product");
        final Label purchase = new Label("Purchase");
        final HBox btnBox = new HBox(addBtn, updateBtn, deleteBtn);
        final VBox box = new VBox(purchase, tblProducts, btnBox);

        //Ensure that the table resizes itself with all the data that has been added
        tblProducts.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        //Add the top columns to the table layout component and set the cell value property so that it can be used by the backend ProductManager class
        final TableColumn<Product, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        final TableColumn<Product, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        final TableColumn<Product, Integer> colQuantity = new TableColumn<>("Quantity");
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        final TableColumn<Product, Double> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        final TableColumn<Product, Date> colCreatedAt = new TableColumn<>("Created At");
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdat"));
        final TableColumn<Product, Date> colUpdatedAt = new TableColumn<>("Updated At");
        colUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedat"));

        //Add all the columns to the Table
        tblProducts.getColumns().addAll(
                colId, colName, colQuantity, colPrice, colCreatedAt, colUpdatedAt
        );

        //set the spacing between the buttons
        btnBox.setSpacing(10);

        //Add padding to the Vertical box as well as create spacing between the table component and the horizontal boxI
        box.setPadding( new Insets(10) );
        box.setSpacing( 10 );

        //Create the Scene and update the stage. On start the inventory management system stage should be displayed
        Scene scene = new Scene(box, 600, 400);

        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Change the form with UI button controls
        addBtn.setOnAction(e -> {
            try {
                addView.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}