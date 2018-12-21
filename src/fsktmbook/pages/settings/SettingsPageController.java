/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.pages.settings;

import fsktmbook.FSKTMBook;
import fsktmbook.helpers.Helper;
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


    Database database;
    Posts posts;
    Users users;
    Comments comments;


	
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
    private ImageView profileImage_center;

    @FXML
    private Button upload_btn;

    @FXML
    private Button deleteImage_btn;

    @FXML
    private TextField firstname_input;

    @FXML
    private TextField matric_input;

    @FXML
    private PasswordField newpass_input;

    @FXML
    private PasswordField reNewpass_input;

    @FXML
    private TextField lastname_input;

    @FXML
    private TextArea aboutme_input;

    @FXML
    private Button save_btn;

    @FXML
    private Button cancel_btn;
    @FXML
    private PasswordField oldpass_input;



    @Override
    public void initialize(URL url, ResourceBundle rb) {

            database = Database.getInstannce();

            posts = new Posts();
            users = new Users();
            comments = new Comments();
           
    }

    @FXML
    private void goHome(ActionEvent event) {
        loadWindow("/fsktmbook/pages/home/HomePage.fxml","home1");
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
    private void doUpload(ActionEvent event) {
    }

    @FXML
    private void doDeleteImage(ActionEvent event) {
    }

    @FXML
    private void doSave(ActionEvent event) {
    }

    @FXML
    private void doCancel(ActionEvent event) {
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



}
