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
    private TextField newpass_input;

    @FXML
    private TextField reNewpass_input;

    @FXML
    private TextField lastname_input;

    @FXML
    private TextArea aboutme_input;

    @FXML
    private Button save_btn;

    @FXML
    private Button cancel_btn;
    @FXML
    private TextField oldpass_input;



    @Override
    public void initialize(URL url, ResourceBundle rb) {

            database = Database.getInstannce();

            posts = new Posts();
            users = new Users();
            comments = new Comments();
           
    }

    @FXML
    private void goHome(ActionEvent event) {
    }

    @FXML
    private void goSearch(ActionEvent event) {
    }

    @FXML
    private void goNotif(ActionEvent event) {
    }

    @FXML
    private void goSettings(ActionEvent event) {
    }

    @FXML
    private void goSignOut(ActionEvent event) {
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



}
