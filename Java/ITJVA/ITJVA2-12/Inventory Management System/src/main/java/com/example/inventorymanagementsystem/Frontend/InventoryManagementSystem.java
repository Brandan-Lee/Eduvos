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
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class InventoryManagementSystem extends Application {

    private ProductManager productManager;
    private CustomAlerts alerts;

    public InventoryManagementSystem() {
        this.productManager = new ProductManager();
        this.alerts = new CustomAlerts(productManager);
    }

    @Override
    public void start(Stage primaryStage) {
        AddProductView addView = new AddProductView(productManager);
        TableView<Product> tblProducts = createTable();
        VBox main = createMainLayout(tblProducts, primaryStage, addView);
        Scene scene = new Scene(main, 600, 400);
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

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

        tblProducts.getColumns().addAll(colId, colName, colQuantity, colPrice, colCreatedAt, colUpdatedAt);

        return tblProducts;
    }

    private VBox createMainLayout(TableView<Product> product, Stage primaryStage, AddProductView addView) {
        Button addBtn = new Button("Add New Product");
        Button updateBtn = new Button("Update Product");
        Button deleteBtn = new Button("Delete Product");
        Button viewBtn = new Button("View All Products");
        Button closeBtn = new Button("Close");
        Label lblPurchase = new Label("Purchase");
        HBox btnBox = new HBox(addBtn, updateBtn, deleteBtn, viewBtn, closeBtn);
        VBox vBox = new VBox(lblPurchase, product, btnBox);

        btnBox.setSpacing(30);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);

        disableButtons(updateBtn, deleteBtn, viewBtn);
        buttonControl(addBtn, updateBtn, deleteBtn, viewBtn, closeBtn, product, addView, primaryStage);

        return vBox;
    }
    
    private void disableButtons(Button updateBtn, Button deleteBtn, Button viewBtn) {
        if (productManager.viewProducts().isEmpty()) {
            updateBtn.setDisable(true);
            deleteBtn.setDisable(true);
            viewBtn.setDisable(true);
        }
    }

    private void enableButtons(Button updateBtn, Button deleteBtn, Button viewBtn) {
        updateBtn.setDisable(false);
        deleteBtn.setDisable(false);
        viewBtn.setDisable(false);
    }

    private void buttonControl(Button addBtn, Button updateBtn, Button deleteBtn, Button viewBtn, Button closeBtn, TableView<Product> products, AddProductView addView, Stage primaryStage) {
        addBtn.setOnAction(e -> {
            addProduct(products, addView, updateBtn, deleteBtn, viewBtn);
            enableButtons(updateBtn, deleteBtn, viewBtn);
        });

        updateBtn.setOnAction(e -> {
            updateProduct(products);
        });

        deleteBtn.setOnAction(e -> {
            deleteProduct(products);
        });

        viewBtn.setOnAction(e -> {
            viewProducts(products, viewBtn);
        });

        closeBtn.setOnAction(e -> {
            handleClose(primaryStage);
        });
    }

    private void addProduct(TableView<Product> products, AddProductView addView, Button updateBtn, Button deleteBtn, Button viewBtn) {
        try {
            Stage addStage = new Stage();
            addView.start(addStage);
            addStage.setOnHiding(event -> {
                refreshTable(products, productManager);
            });

            enableButtons(updateBtn, deleteBtn, viewBtn);
        } catch (IOException ex) {
            alerts.errorAlert("There was a problem loading the Add Product view. Please check your configuration.");
        }

    }

    private void updateProduct(TableView<Product> products) {
        try {
            alerts.showProductIdConfAlert();
            refreshTable(products, productManager);
        } catch (RuntimeException ex) {
            alerts.errorAlert("There was an error loading the Update Product view. Please check your configuration.");
        }
    }

    private void deleteProduct(TableView<Product> products) {
        try {
            alerts.showProductDeleteConfAlert();
            refreshTable(products, productManager);
        } catch (RuntimeException ex) {
            alerts.errorAlert("There was an error loading the Delete Product view. Please check your configuration.");
        }
    }

    private void viewProducts(TableView<Product> products, Button viewBtn) {
        int count = 0;

        try {
            refreshTable(products, productManager);
            count++;
        } catch (RuntimeException ex) {
            alerts.errorAlert("There was an error updating the table. Please check your configuration.");
        }

        if (count == 1) {
            viewBtn.setDisable(true);
        }
    }

    private void handleClose(Stage primaryStage) {
        primaryStage.close();
    }

    private void refreshTable(TableView<Product> table, ProductManager productManager) {
        table.getItems().clear();
        table.getItems().addAll(productManager.viewProducts());
    }
}
