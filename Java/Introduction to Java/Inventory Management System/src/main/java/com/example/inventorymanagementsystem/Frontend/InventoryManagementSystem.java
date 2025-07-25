//This class was created to answer QUESTION 1 AND QUESTION 3 of the project. I'll indicate which methods are meant for which question

package com.example.inventorymanagementsystem.Frontend;

import com.example.inventorymanagementsystem.Backend.Product;
import com.example.inventorymanagementsystem.Backend.ProductManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;

public class InventoryManagementSystem extends Application {

    //Private attributes of the InventoryManagementSystem class
    private final ProductManager productManager;
    private final CustomAlerts alerts;
    private int counter = 0;

    //This is the constructor of the class
    public InventoryManagementSystem() {

        this.productManager = new ProductManager();
        this.alerts = new CustomAlerts(productManager);

    }

    //This method is used to create the main gui of the class
    @Override
    public void start(Stage primaryStage) {

        AddProductView addView = new AddProductView(productManager);
        TableView<Product> tblProducts = createTable();
        VBox main = createMainLayout(tblProducts, primaryStage, addView);

        //Create the scene for the program
        Scene scene = new Scene(main, 600, 400);
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    //This method is used to display all the products that have been added to the ProductManager class. Answers QUESTION 3
    private TableView<Product> createTable() {

        TableView<Product> tblProducts = new TableView<>();
        tblProducts.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Product, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        TableColumn<Product, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Product, Integer> colQuantity = new TableColumn<>("Quantity");
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        TableColumn<Product, Double> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<Product, LocalDate> colCreatedAt = new TableColumn<>("Created");
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        TableColumn<Product, LocalDate> colUpdatedAt = new TableColumn<>("Updated");
        colUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        //Add the column headers as well as the data in the product class
        tblProducts.getColumns().addAll(colId, colName, colQuantity, colPrice, colCreatedAt, colUpdatedAt);

        return tblProducts;

    }

    //This method creates the main layout of the main gui. Answers QUESTION 1
    private VBox createMainLayout(TableView<Product> product, Stage primaryStage, AddProductView addView) {

        //Main components of the GUI
        Button addBtn = new Button("Add New Product");
        Button updateBtn = new Button("Update Product");
        Button deleteBtn = new Button("Delete Product");
        Button viewBtn = new Button("View All Products");
        Button closeBtn = new Button("Close");
        Label lblPurchase = new Label("Purchase");
        HBox btnBox = new HBox(addBtn, updateBtn, deleteBtn, viewBtn, closeBtn);
        VBox vBox = new VBox(lblPurchase, product, btnBox);

        //Add spacing and padding to the Hbox and VBox
        btnBox.setSpacing(30);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);

        //Disable the buttons update, delete and view on startup as there has been no products added to the ProductManager class yet
        disableButtons(updateBtn, deleteBtn, viewBtn);

        //This method is called to handle all the GUI manipulation
        buttonControl(addBtn, updateBtn, deleteBtn, viewBtn, closeBtn, product, addView, primaryStage);

        return vBox;

    }

    //This method is used to disable the update, delete, and view buttons when there has been no products added to the ProductManager class yet
    private void disableButtons(Button updateBtn, Button deleteBtn, Button viewBtn) {

        if (productManager.viewProducts().isEmpty()) {
            updateBtn.setDisable(true);
            deleteBtn.setDisable(true);
            viewBtn.setDisable(true);
        }

    }

    //This method is used to enable the update, delete and view buttons after a product has been added to the ProductManager class
    private void enableButtons(Button updateBtn, Button deleteBtn, Button viewBtn) {

        updateBtn.setDisable(false);
        deleteBtn.setDisable(false);
        viewBtn.setDisable(false);

    }

    //This method controls the manipulation of the GUI. Answers QUESTION 3
    private void buttonControl(Button addBtn, Button updateBtn, Button deleteBtn, Button viewBtn, Button closeBtn, TableView<Product> products, AddProductView addView, Stage primaryStage) {

        addBtn.setOnAction(e -> addProduct(products, addView, updateBtn, deleteBtn, viewBtn, primaryStage));

        updateBtn.setOnAction(e -> {
            updateProduct(products, primaryStage);
            resetCounter(viewBtn);
        });

        deleteBtn.setOnAction(e -> {
            deleteProduct(products, primaryStage);
            resetCounter(viewBtn);
        });

        //This button links QUESTION 3 to QUESTION 1
        viewBtn.setOnAction(e -> viewProducts(products, viewBtn, primaryStage));

        closeBtn.setOnAction(e -> handleClose(primaryStage));

    }

    //This method adds the product to the ProductManager class based on conditions. Answers QUESTION 3
    private void addProduct(TableView<Product> products, AddProductView addView, Button updateBtn, Button deleteBtn, Button viewBtn, Stage parent) {

        try {
            //this opens the addProduct view and ensures that the inventory management system is the primary stage and the addview stage is the child
            //this is to ensure that the user can't open multiple windows at the same time
            openNewWindow("Add new Product", addView, parent, () -> {
                //Refresh the table once a product has been added successfully.
                refreshTable(products, productManager);

                //Enable the buttons once a product has been added
                if (!productManager.viewProducts().isEmpty()) {
                    enableButtons(updateBtn, deleteBtn, viewBtn);
                }
            });
        } catch (IOException ex) {
            System.out.println("Error opening add page " + ex.getMessage());
            alerts.errorAlert("There was a problem loading the Add Product view. Please check your configuration.", parent);
        }

    }

    //This method updates the product in the ProductManager class. Answers QUESTION 3
    private void updateProduct(TableView<Product> products, Stage stage) {

        try {
            alerts.updateProductId(stage);

            //Refresh the table once a product has been updated successfully.
            refreshTable(products, productManager);
        } catch (RuntimeException ex) {
            System.out.println("Error opening update view " + ex.getMessage());
            alerts.errorAlert("There was an error loading the Update Product view. Please check your configuration.", stage);
        }

    }

    //This method deletes the product in the ProductManager class. Answers QUESTION 3
    private void deleteProduct(TableView<Product> products, Stage parent) {

        try {
            alerts.deleteProductID(parent);
            refreshTable(products, productManager);
        } catch (RuntimeException ex) {
            System.out.println("Error opening delete view " + ex.getMessage());
            alerts.errorAlert("There was an error loading the Delete Product view. Please check your configuration.", parent);
        }

    }

    //This method allows the user to the view the Products that have been added to the ProductManager class. Answers QUESTION 3
    //The view products button can only be pressed once and then the button will be deactived
    private void viewProducts(TableView<Product> products, Button viewBtn, Stage parent) {

        try {
            if (counter == 0) {
                //Refresh the table to view the products that have been added to the ProductManager class
                refreshTable(products, productManager);

                //Button has been pressed, so increase the counter and disable the button
                counter++;

                viewBtn.setDisable(true);
            }
        } catch (RuntimeException ex) {
            System.out.println("Error refreshing the table " + ex.getMessage());
            alerts.errorAlert("There was an error updating the table. Please check your configuration.", parent);
        }

    }

    //This method will reset the counter for the view button
    private void resetCounter(Button viewBtn) {

        counter = 0;
        viewBtn.setDisable(false);

    }

    //This method handles the close operation of the GUI.
    private void handleClose(Stage primaryStage) {
        primaryStage.close();
    }

    //This method refreshes the table every time a product has been added, updated and deleted from the ProjectManager class. Answers QUESTION 3
    private void refreshTable(TableView<Product> table, ProductManager productManager) {

        table.getItems().clear();
        table.getItems().addAll(productManager.viewProducts());

    }

    //This method allows the user to open a new window, while keeping the new window the child stage and the ims window the parent stage
    //it ensures that the user can't open multiple windows at the same time
    private void openNewWindow(String title, AddProductView add, Stage parent, Runnable onClose) throws IOException {

        Stage newStage = new Stage();

        newStage.initModality(Modality.WINDOW_MODAL);
        newStage.initOwner(parent);
        newStage.setTitle(title);

        add.start(newStage);

        //Set the new pages position to the middle of the parent stage
        double centerXPosition = parent.getX() + (parent.getWidth() - newStage.getWidth()) / 2;
        double centerYPosition = parent.getY() + (parent.getHeight() - newStage.getHeight()) / 2;
        newStage.setX(centerXPosition);
        newStage.setY(centerYPosition);

        newStage.setOnHiding(event -> onClose.run());

        newStage.show();

    }

}
