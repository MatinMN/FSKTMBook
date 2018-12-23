/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.pages.settings;

import fsktmbook.ui.database.Database;
import fsktmbook.FSKTMBook;
import fsktmbook.helpers.Helper;
import fsktmbook.helpers.ImageHandler;
import fsktmbook.helpers.Post;
import fsktmbook.helpers.User;
import fsktmbook.ui.database.Database;
import fsktmbook.ui.database.models.Comments;
import fsktmbook.ui.database.models.Posts;
import fsktmbook.ui.database.models.Users;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;

/**
 *
 * @author Matin
 */
public class SettingsPageController implements Initializable {




    private Label label;

    @FXML
    private BorderPane rootPane;


    Users users;

    @FXML
    private ImageView profileImage_leftTop;

    @FXML
    private Button home_btn;
    
    @FXML
    private Button search_btn;

    @FXML
    private Button notif_btn;

    @FXML
    private Button settings_btn;

    @FXML
    private Button signout_btn;

    @FXML
    private TextField firstname_input;

    @FXML
    private TextField matric_input;

    @FXML
    private TextField lastname_input;

    @FXML
    private TextArea aboutme_input;

    @FXML
    private Button save_btn;

    @FXML
    private Button cancel_btn;

    
    @FXML
    private ImageView ProfileImage;
    
    @FXML
    private Button Upload_Image_Btn;
    
    @FXML
    private Button Delete_Image_Btn;
    
    @FXML
    private TextField oldPassword_input;
    
    
    @FXML
    private Button profile_btn;
    @FXML
    private TextField newPassword_input;
    
    @FXML
    private TextField renewPassword_input;

    private User user;
    private Database database;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
            
            users = new Users();
        try {
            

            user = users.getUserInformation(FSKTMBook.LOGGEDUSER);
            Image profileImg = users.getUserImage(FSKTMBook.LOGGEDUSER);
            ProfileImage.setImage(profileImg);
            profileImage_leftTop.setImage(profileImg);
            
            aboutme_input.setText(user.getAbout());
            firstname_input.setText(user.getUserName());
            lastname_input.setText(user.getLastName());
            matric_input.setText(user.getMatricNumber());
            System.out.println(user.getPassword());
        } catch (SQLException ex) {
            Logger.getLogger(SettingsPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
    }
    
    @FXML
    private void goHome(ActionEvent event) {
        loadWindow("/fsktmbook/pages/home/HomePage.fxml","Home Page");
        Stage stage =  (Stage) rootPane.getScene().getWindow();
        stage.close();   
    }

    @FXML
    private void goSearch(ActionEvent event) {
        loadWindow("/fsktmbook/pages/search/searchPage.fxml","search");
        Stage stage =  (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void goNotif(ActionEvent event) {
    }

    @FXML
    private void goSettings(ActionEvent event) {
    }

    @FXML
    private void goSignOut(ActionEvent event) {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Confirm Signout");
      alert.setHeaderText(null);
      alert.setContentText("Are you sure you want to sign out?\n\nWe have more features for you to try out...");
      ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
      ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
               
      alert.getButtonTypes().setAll(okButton, noButton);
      Optional <ButtonType> action = alert.showAndWait();
      if (action.get() == okButton) {
            loadWindow("/fsktmbook/pages/login/LoginPage.fxml","Login Page");
            // close the home page 

            Stage stage =  (Stage) rootPane.getScene().getWindow();
            stage.close();
            return;
      }
        
        
    }

    
    @FXML
    public void UploadImage(ActionEvent event) throws SQLException, IOException {
        
        ImageHandler handler = new ImageHandler();
        
        handler.chooseImage();

        String imagePath = handler.getImageDirectory();
        
        if(imagePath == null){
            //do nothing
        }
        else{
            File file = new File(imagePath);
            if(file.exists()){
                Image image = new Image(file.toURI().toString());
                ProfileImage.setImage(image);
                profileImage_leftTop.setImage(image);
                
                handler.updateImageDiectory(imagePath, FSKTMBook.LOGGEDUSER);
            }
            else{
                System.out.println("Image is not found in the database!!");
            }
        }
        
    }

    @FXML
    private void DeleteImage(ActionEvent event) throws SQLException {
        ImageHandler handler = new ImageHandler();
        handler.deleteImage();
        File file = new File("profileImages\\default.png");
        Image image = new Image(file.toURI().toString());
        profileImage_leftTop.setImage(image);
        ProfileImage.setImage(image);
        
    }

    @FXML
    private void SaveInformation(ActionEvent event) throws SQLException {
        changeUserInformation();
    }

    @FXML
    private void CancelSavingInformation(ActionEvent event) {
        loadWindow("/fsktmbook/pages/home/HomePage.fxml","Home Page");
        Stage stage =  (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    
    
    public void changeUserInformation() throws SQLException{
        
       
        if(validateInputData()){
            if(checkNewPassword()){
                // we will store the new password in the database.......
                setUserInformation(1);
                users.updateUser(user);
                //database.printUsersTable();
            }
            else{
                setUserInformation(0);
                users.updateUser(user);
                //database.printUsersTable();
            }
        }
    }
    
    public boolean checkNewPassword() throws SQLException{
        String oldPassword = oldPassword_input.getText();

        
        if(!(oldPassword.isEmpty())){
            if(newPassword_input.getText().length() < 8){
            Helper.openAlert("Password length cannot be less than 8 characters!");
                return false;
            }
            if(!((newPassword_input.getText()).equals(renewPassword_input.getText()))){
                Helper.openAlert("Both passwords must match!");
                return false;
            }
            if(!(user.getPassword().equals(oldPassword))){
                Helper.openAlert("Old password is not the same as the user's password!");
                return false;
            }
            if(((oldPassword).equals(newPassword_input.getText()))){
                Helper.openAlert("New password cannot be the same as old password!!");
                return false;
            }
        }
        else{
            
            System.out.println(user.getPassword());
            return false;
        }

        return true;
    }
    
    public boolean validateInputData() throws SQLException{
        
        if((firstname_input.getText().isEmpty()) || (lastname_input.getText().isEmpty()) || (matric_input.getText().isEmpty())){
            Helper.openAlert("First name, Last name and Matric number fields are required");
            return false;
        }
        
        if(firstname_input.getText().length() < 6){
            Helper.openAlert("First name length cannot be less than 6 characters!");
            return false;
        }
        if(lastname_input.getText().length() < 6){
            Helper.openAlert("Last name length cannot be less than 6 characters!");
            return false;
        }
        
        

        
        if((users.doesUserExist(firstname_input.getText()))){
            if((user.getUserName()).equals(firstname_input.getText())){
                return true;
            }
            else{
                Helper.openAlert("User name already Exists!!");
                return false;
            }
        }
        
        return true;
    }
    
    public User setUserInformation(int type){

        user.setFirstName(firstname_input.getText());
        user.setLastName(lastname_input.getText());
        ///user.setUserName();
        if (type == 1) user.setPassword(newPassword_input.getText());
        user.setMatricNumber(matric_input.getText());
        //user.setOccupation();
        user.setAbout(aboutme_input.getText());
        
        return user;
    }
    
    void loadWindow(String location,String title){

        try {
            Parent parent = FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();

        } catch (IOException ex){
            ex.printStackTrace();
            //Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    private void goProfile(ActionEvent event) {
    }




}
