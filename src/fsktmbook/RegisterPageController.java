/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook;

import fsktmbook.helpers.Helper;
import fsktmbook.helpers.User;
import fsktmbook.ui.database.Database;
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
    
    Database database;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        database = Database.getInstannce();
    }    

    // This method will take all the inputs from the text fields and insert it in the database....
    @FXML
    private void signup(ActionEvent event) {
         String firstName = firstname_field.getText();
         String password = password_field.getText();
         String rePassword = repassword_field.getText();
         String matricNumber =  matric_field.getText();
         String lastName = lastname_field.getText();
         
         // validate all the inputs
         boolean checkInput = validateSignUpData(firstName, password, rePassword, matricNumber, lastName);
         
         if(checkInput){
             User user = new User();
             user.setFirstName(firstName);
             user.setLastName(lastName);
             user.setPassword(password);
             user.setMatricNumber(matricNumber);
             user.setRegisteredDate(Helper.registeredDateTime());
             
             database.addUser(user);
             Helper.openAlert("User registered");
             return;
         }
         return;
    }
    
    
    // A function to validate all the inputs
    public boolean validateSignUpData(String firstName, String password, String rePassword, String matricNumber, String lastName){
        
        if(firstName.isEmpty() || password.isEmpty() || rePassword.isEmpty() || matricNumber.isEmpty() || lastName.isEmpty()){
           Helper.openAlert("All input fields are required.");
           return false; 
        }
        // check if user exist, putting the statement here will save running time for program....
        if(database.doesUserExist(firstName)){
            Helper.openAlert("User Exists Already!!!");
            return false;
        }
        if(firstName.length() > 255 || firstName.length() < 6){
          Helper.openAlert("Username cannot be longer than 255 characters or less than 6 characters.");
          return false;
        }
        if(password.length() > 20 || password.length() < 8){
           Helper.openAlert("Password cannot be longer than 20 characters.");
           return false;
        }
        if(rePassword.length() > 20 || password.length() < 8){
            Helper.openAlert("Both passwords must be the same length");
            return false;
        }
        if(!(rePassword.equals(password))){
            Helper.openAlert("Password doesn't match");
            return false;
        }
        if(matricNumber.length() > 20){
           Helper.openAlert("Matric number cannot be longer than 20 characters.");
           return false;
        }
        if(lastName.length() > 255 || lastName.length() < 6){
           Helper.openAlert("Last name cannot be longer than 20 characters or less than 6 characters.");
           return false;
        }
        return true;
    }
   
    
}
