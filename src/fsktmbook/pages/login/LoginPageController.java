/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.pages.login;


import fsktmbook.FSKTMBook;
import fsktmbook.helpers.Helper;
import fsktmbook.ui.database.Database;
import fsktmbook.ui.database.models.Notifications;
import fsktmbook.ui.database.models.Users;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Matin
 */
public class LoginPageController implements Initializable {
    
 
    
    
    private Label label;
    @FXML
    private PasswordField password_field;
    @FXML
    private TextField username_field;
    @FXML
    private Button signin_btn;
    @FXML
    private Button signup_btn;
    @FXML
    private AnchorPane rootPane;
    
    
    Users users;
    Notifications notifications;
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        users = new Users();
        notifications = new Notifications();
        
        rootPane.setOnKeyPressed(e -> {
   
            if(e.getCode()  == KeyCode.ENTER){
                          //System.out.println("Key pressed");
                if(password_field.getText().isEmpty()){
                    password_field.requestFocus();
                    
                }else{
                    try {
                        signin(new ActionEvent());
                    } catch (SQLException ex) {
                        Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        
        String currentDate = users.getCurrentDay();
        if(currentDate == null){
            users.addCurrentDay();
        }else{
            if(!Helper.getCurrentDay().equals(currentDate)){
                try {
                    int mostActiveUser = users.getMostActiveUser();
                    notifications.addNotification(mostActiveUser, "You got a free Grab Voucher! code : " + Helper.getRandomString(), "Grub");
                } catch (SQLException ex) {
                    Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
                users.UpdateCurrentDay();//
            }
        }
    }    

    @FXML
    private void signin(ActionEvent event) throws SQLException {
        
        String username = username_field.getText();
        String password = password_field.getText();
        
        // form validation 
        boolean validate = validateLoginData(username,password);
       
        if(validate){
            // check if username and password
            
            boolean result = users.checkPassword(username, password);
            
            if(result){ // username and passwrod is current
                
                FSKTMBook.LOGGEDUSER = users.getUserID(username);
                
                loadWindow("/fsktmbook/pages/home/HomePage.fxml","Home Page");
                
                // close the login page 
                Stage stage =  (Stage) rootPane.getScene().getWindow();
                stage.close();
                return;
            }
            Helper.openAlert("Wrong username or password.");
        }
        return;
    }
    
    
    public boolean validateLoginData(String username,String password){
        
        if(username.isEmpty() || password.isEmpty()){
           Helper.openAlert("All input fields are required.");
           return false; 
        }
        if(username.length() > 255){
          Helper.openAlert("Username cannot be longer than 255 characters.");
          return false;
        }
        if(password.length() > 20){
           Helper.openAlert("Password cannot be longer than 20 characters.");
           return false;
        }
        
        return true;
    }
    
    @FXML
    private void signup(ActionEvent event) {
        // switch to register page function.....
        loadWindow("/fsktmbook/pages/register/RegisterPage.fxml","Register page");
    }
    
    // A method to switch the scene of the program to different windows....
    void loadWindow(String location,String title){
        
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
            
        } catch (IOException ex){ 
            Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
