module com.example.inventorymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.example.inventorymanagementsystem.Backend;
    exports com.example.inventorymanagementsystem.Frontend to javafx.graphics;


}