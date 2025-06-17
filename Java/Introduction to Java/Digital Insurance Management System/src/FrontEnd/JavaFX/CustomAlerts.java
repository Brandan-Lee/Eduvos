
package FrontEnd.JavaFX;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class CustomAlerts {
    
    private final String ALERT_TYPE_ERROR = "Error";
    private final String ALERT_TYPE_MESSAGE = "Success";    
    private final String ALERT_TYPE_INFORMATION = "Information";     
    private final String ALERT_TYPE_SUCCESS = "Success";

    private String pathToStyleSheet;
    
    public CustomAlerts(String pathToStyleSheet) {
        this.pathToStyleSheet = pathToStyleSheet;
    }
    
    //Set the styling of the alert and its position
    private void applyAlertStylingAndPosition(Alert alert, Stage parent) {
        
        DialogPane pane = alert.getDialogPane();
        
        //Apply custome css
        if (pathToStyleSheet != null && !pathToStyleSheet.isEmpty()) {
            
            pane.getStylesheets().add(pathToStyleSheet);
            pane.getStyleClass().add("custom-alert-dialog");
            
        }
        
        //Set the alert to the middle of the parent stage
        if (parent != null) {
            
            alert.initOwner(parent);
            alert.initModality(Modality.APPLICATION_MODAL);
            
        }
        
    }
    
    //Show the alert and tell it how it should handle your alert response
    private ButtonType showAndHandleAlert(Alert.AlertType type, String title, String headerText, String message, Stage parent, ButtonType[] buttonTypes) {
        
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.getButtonTypes().setAll(buttonTypes);
        
        applyAlertStylingAndPosition(alert, parent);
        
        return alert.showAndWait().orElse(null);
        
    }
    
    //Configuration of the error alert
    public void errorAlert(String message, Stage parent) {
        showAndHandleAlert(Alert.AlertType.ERROR, ALERT_TYPE_ERROR, null, message, parent, new ButtonType[]{ButtonType.OK});
    }
    
    //Configuration of the infoAlert
    public void infoAlert(String title, String message, Stage parent, Runnable callBack) {
        
        ButtonType result = showAndHandleAlert(Alert.AlertType.INFORMATION, ALERT_TYPE_INFORMATION, null, message, parent, new ButtonType[]{ButtonType.OK});
        
        if (result == ButtonType.OK && callBack != null) {
            callBack.run();
        }
        
    }
    
    //configuration of the success alert
    public void successAlert(String title, String message, Stage parent, Runnable callBack) {
        
        ButtonType result = showAndHandleAlert(Alert.AlertType.INFORMATION, ALERT_TYPE_INFORMATION, null, message, parent, new ButtonType[]{ButtonType.OK});
        
        if (result == ButtonType.OK && callBack != null) {
            callBack.run();
        }
        
    }
    
}
