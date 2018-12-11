/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author Matin
 */
public class RegisterPageController implements Initializable {
    
 
    
    
    private Label label;
    @FXML
    private TextField firstname_field;
    @FXML
    private TextField password_field;
    @FXML
    private TextField matric_field;
    @FXML
    private TextField lastname_field;
    @FXML
    private TextField repassword_field;
    @FXML
    private Button signup_btn;
    
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void signup(ActionEvent event) {
    }

   
    
}
