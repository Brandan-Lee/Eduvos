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

    @Override
    public void start(Stage primaryStage) {
        productManager = new ProductManager();
        AddProductView addView = new AddProductView(productManager);
        CustomAlerts alerts = new CustomAlerts(productManager);

        TableView<Product> tblProducts = createTable();

        Button addBtn = new Button("Add New Product");
        Button updateBtn = new Button("Update Product");
        Button deleteBtn = new Button("Delete Product");
        Button viewAllBtn = new Button("View All Products");
        Button closeBtn = new Button("Close");
        Label lblPurchase = new Label("Purchase");
        HBox btnBox = new HBox(addBtn, updateBtn, deleteBtn, viewAllBtn, closeBtn);
        VBox vBox = new VBox(lblPurchase, tblProducts, btnBox);

        if (productManager.viewProducts().isEmpty()) {
            updateBtn.setDisable(true);
            deleteBtn.setDisable(true);
            viewAllBtn.setDisable(true);
        }

        btnBox.setSpacing(30);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);

        Scene scene = new Scene(vBox, 600, 400);
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(scene);
        primaryStage.show();

        addBtn.setOnAction(e -> {
            try {

                Stage addStage = new Stage();
                addView.start(addStage);
                addStage.setOnHiding(event -> {
                    refreshTable(tblProducts, productManager);
                });

                updateBtn.setDisable(false);
                deleteBtn.setDisable(false);
                viewAllBtn.setDisable(false);

            } catch (IOException ex) {
                alerts.errorAlert("There was a problem loading the Add Product view. Please check your configuration.");
            }

        });

        updateBtn.setOnAction(e -> {
            try {
                alerts.showProductIdConfAlert();
                refreshTable(tblProducts, productManager);
            } catch (RuntimeException ex) {
                alerts.errorAlert("There was an error loading the Update Product view. Please check your configuration.");
            }
        });

        deleteBtn.setOnAction(e -> {
            try {
                alerts.showProductDeleteConfAlert();
                refreshTable(tblProducts, productManager);
            } catch (RuntimeException ex) {
                alerts.errorAlert("There was an error loading the Delete Product view. Please check your configuration.");
            }
        });

        viewAllBtn.setOnAction(e -> {
            int count = 0;

            try {
                refreshTable(tblProducts, productManager);
                count++;
            } catch (RuntimeException ex) {
                alerts.errorAlert("There was an error updating the table. Please check your configuration.");
            }

            if (count == 1) {
                viewAllBtn.setDisable(true);
            }
        });

        closeBtn.setOnAction(e -> primaryStage.close());
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

    private void refreshTable(TableView<Product> table, ProductManager productManager) {
        table.getItems().clear();
        table.getItems().addAll(productManager.viewProducts());
    }
}
