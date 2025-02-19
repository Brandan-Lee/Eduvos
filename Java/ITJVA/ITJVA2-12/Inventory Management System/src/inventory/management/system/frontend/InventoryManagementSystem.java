package inventory.management.system.frontend;

import inventory.management.system.backend.Product;
import inventory.management.system.backend.ProductManager;
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
    
    @Override
    public void start(Stage primaryStage) {
        //Create a product manager object for use when displaying updated table with
        ProductManager productManager = new ProductManager();

        //GUI Layout Components and UI control initialization
        TableView<Product> tblProducts = new TableView<>();
        final Button addBtn = new Button("Add New Product");
        final Button updateBtn = new Button("Update Product");
        final Button deleteBtn = new Button("Delete Product");
        final HBox btnBox = new HBox(addBtn, updateBtn, deleteBtn);
        final VBox vbox = new VBox(tblProducts, btnBox);

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

        //Add the horizontal box for the buttons and set the spacing between the buttons
        btnBox.setSpacing(10);

        //Add padding to the Vertical box as well as create spacing between the table component and the horizontal boxI
        vbox.setPadding( new Insets(10) );
        vbox.setSpacing( 10 );

        //Create the Scene and update the stage. On start the inventory management system stage should be displayed
        Scene scene = new Scene(vbox);

        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
