
package FrontEnd.JavaFX;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * @author Brandan-Lee James Sherbrooke eduv4902376
 */

public class DigitalnsuranceManagementSystem extends Application {
    
    private static final int SPACING = 20;
    private static final double TEXFIELD_WIDTH = 80;
    
    @Override
    public void start(Stage primaryStage) {
        
        GridPane grid = createMainLayout(primaryStage);
        
        Scene scene = new Scene(grid);
        
        primaryStage.setTitle("Digital Insurance Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    private GridPane createMainLayout(Stage primaryStage) {
        
        //GUI Layout Components and UI control initialization
        final Label LBLHEADING = new Label("Insurance Management System");
        //Gridpane elements
        final Label LBLID = new Label("ID Number: ");
        TextField txtId = new TextField();
        final Label LBLNAME = new Label("Name");
        TextField txtName = new TextField();
        final Label LBLSURNAME = new Label("Surname");
        TextField txtSurname = new TextField();
        final Label LBLADDRESS = new Label("Address");
        TextArea txtAddress = new TextArea();
        final Label LBLAGE = new Label("Age");
        TextField txtAge = new TextField();
        final Label LBLPOLICYTYPE = new Label("Policy Type");
        ComboBox cmbPolicyType = new ComboBox();
        final Label LBLSUMINSURED = new Label("Sum Insured");
        ComboBox cmbSumInsured = new ComboBox();
        final Label LBLCOVERAGEAMOUNT = new Label("Coverage Amount");
        TextField txtCoverageAmount = new TextField();
        final Button BTNCALCULATEPREMIUM = new Button("Calculate Premium");
        final Label LBLPREMIUM = new Label("Premium");
        final TextField TXTPREMIUM = new TextField();
        final Button BTNSUBMIT = new Button("Submit");
        final Button BTNVIEWPOLICIES = new Button("View Policies");
        final Button BTNCLOSE = new Button("CLOSE");
        
        
        GridPane grid = createGrid(LBLHEADING, LBLID, txtId, LBLNAME, txtName, LBLSURNAME, txtSurname, LBLADDRESS, txtAddress, LBLAGE, txtAge, LBLPOLICYTYPE, cmbPolicyType, LBLSUMINSURED, cmbSumInsured, LBLCOVERAGEAMOUNT, txtCoverageAmount, BTNCALCULATEPREMIUM, LBLPREMIUM, TXTPREMIUM, BTNSUBMIT, BTNVIEWPOLICIES, BTNCLOSE);
        
        buttonControl(BTNCALCULATEPREMIUM, BTNSUBMIT, BTNVIEWPOLICIES, BTNCLOSE, primaryStage);
        
        return grid;
        
    }
    
    private GridPane createGrid(Label lblHeading, Label lblId, TextField txtId, Label lblName, TextField txtName, Label lblSurname, TextField txtSurname, Label lblAddress, TextArea txtAddress, Label lblAge, TextField txtAge, Label lblPolicyType, ComboBox cmbPolicyType, Label lblSumInsured, ComboBox cmbSumInsured, Label lblCoverageAmount, TextField txtCoverageAmount, Button btnCalculatePremium, Label lblPremium, TextField txtPremium, Button btnSubmit, Button btnViewPolicies, Button btnClose) {
        
        GridPane grid = new GridPane();
        
        //Add padding to the grid and set Spacing
        grid.setPadding(new Insets(10));
        grid.setVgap(SPACING);
        grid.setHgap(SPACING);
        
        //Add the labels and the textfields to the Gridpane
        grid.add(lblHeading, 0, 0);
        grid.add(lblId, 0, 1);
        grid.add(txtId, 1, 1);
        grid.add(lblName, 0, 2);
        grid.add(txtName, 1, 2);
        grid.add(lblSurname, 0, 3);
        grid.add(txtSurname, 1, 3);
        grid.add(lblAddress, 0, 4);
        grid.add(txtAddress, 1, 4);
        grid.add(lblAge, 0, 5);
        grid.add(txtAge, 1, 5);
        grid.add(lblPolicyType, 0, 6);
        grid.add(cmbPolicyType, 1, 6);
        grid.add(lblSumInsured, 0, 7);
        grid.add(cmbSumInsured, 1, 7);
        grid.add(lblCoverageAmount, 0, 8);
        grid.add(txtCoverageAmount, 1, 8);
        grid.add(btnCalculatePremium, 1, 9);
        grid.add(lblPremium, 0, 10);
        grid.add(txtPremium, 1, 10);
        grid.add(btnSubmit, 1, 11);
        grid.add(btnViewPolicies, 0, 12);
        grid.add(btnClose, 2, 12);
        
        return grid;
        
    }
    
    private void buttonControl(Button btnCalculatePremium, Button btnSubmit, Button btnViewPolicies, Button btnClose, Stage primaryStage) {
        
        btnCalculatePremium.setOnAction(e -> {
            System.out.println("Button calculate premium works");
        });
        
        btnSubmit.setOnAction(e -> {
            System.out.println("Button submit works");
        });
        
        btnViewPolicies.setOnAction(e -> {
            System.out.println("Button view policies works");
        });
        
        btnClose.setOnAction(e -> {
            handleClose(primaryStage);
        });
        
    }
    
    private void handleClose(Stage primaryStage) {
        primaryStage.close();
    }
    
}
