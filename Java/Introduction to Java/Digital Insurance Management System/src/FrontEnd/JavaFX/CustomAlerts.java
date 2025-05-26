
package FrontEnd.JavaFX;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class CustomAlerts {
    
    private final String ALERT_TYPE_ERROR = "Error";
    private final String ALERT_TYPE_MESSAGE = "Success";
    
    private void configureAlert(Alert alert, String title, String headerText, Stage parent, TextField txtInput) {
        
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.getButtonTypes().setAll(ButtonType.OK);
        
        if (txtInput != null) {
            alert.getDialogPane();
        }
        
        if (parent != null) {
            positionAlert(alert, parent);
        }
        
    }
    
    //This method handles the response that is thrown from the alert
    private void handleAlertResponses(Alert alert, Runnable onOk) {
        
        alert.showAndWait().ifPresent(response -> {
        
            if (response == ButtonType.OK) {
                alert.close();
            }
            
        });
        
    }
    
    //This method is to configure all the alerts during the frontend operations
    private void showAndConfigureAlert(Alert.AlertType type, String title, String headerText, Stage parent, TextField txtInput, Runnable onOk) {
        
        Alert alert = new Alert(type);
        configureAlert(alert, title, headerText, parent, txtInput);
        handleAlertResponses(alert, onOk);
        
    }
    
    public void errorAlert(String message, Stage parent) {
        showAndConfigureAlert(Alert.AlertType.ERROR, ALERT_TYPE_ERROR, message, parent, null, () -> {});
    }
    
    //This method helps to position the alert to the middle of the parent GUI
    private void positionAlert(Alert alert, Stage parent) {
        
        alert.initOwner(parent);
        alert.initModality(Modality.WINDOW_MODAL);
        
        //Set the new alerts position to the middle of the parent stage
        double centerXPosition = parent.getX() + (parent.getWidth() - alert.getWidth()) / 2;
        double centerYPosition = parent.getY() + (parent.getHeight() -alert.getHeight()) / 2;
        
        alert.setX(centerXPosition);
        alert.setY(centerYPosition);
        
    }
    
}
