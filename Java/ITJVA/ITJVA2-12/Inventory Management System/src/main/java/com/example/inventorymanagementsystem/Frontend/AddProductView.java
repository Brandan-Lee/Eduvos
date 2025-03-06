package com.example.inventorymanagementsystem.Frontend;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class AddProductView extends Application {
    @Override
    public void start(Stage primaryStage) {
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
        GridPane grid = new GridPane(20, 20);

        //Add the spacing to the button box
        btnBox.setSpacing(30);

        //Add the labels and the textFields to the grid pane
        grid.add(lblName, 0, 0);
        grid.add(txtName, 1, 0);
        grid.add(lblQuantity, 0,1);
        grid.add(txtQuantity, 1,1);
        grid.add(lblPrice, 0, 2);
        grid.add(txtPrice, 1,2);
        btnBox.setPadding(new Insets(0,10,0,0));
        grid.add(btnBox,1, 3);

        //Add padding to the grid
        grid.setPadding(new Insets(10));

        //Create the scene for the program
        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setTitle("Add Product");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Button events
        closeBtn.setOnAction(e ->
                primaryStage.close());
    }
}
