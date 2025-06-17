
package FrontEnd.JavaFX;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DigitalInsuranceManagementSystem extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
       
        
        Parent root = FXMLLoader.load(getClass().getResource("Views/DIMS.fxml"));
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Digital Insurance Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
