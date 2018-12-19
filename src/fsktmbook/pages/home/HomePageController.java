/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.pages.home;

import fsktmbook.FSKTMBook;
import fsktmbook.helpers.Helper;
import fsktmbook.helpers.Post;
import fsktmbook.ui.database.Database;
import fsktmbook.ui.database.models.Posts;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Matin
 */
public class HomePageController implements Initializable {
    
 
    
    
    private Label label;
    
    @FXML
    private BorderPane rootPane;
    
    
    Database database;
   
    
    @FXML
    private Button home_btn;
    @FXML
    private Button notif_btn;
    @FXML
    private Button signout_btn;
    @FXML
    private Button settings_btn;
    @FXML
    private TextArea newpost_text_box;
    @FXML
    private Button publish_btn;
    @FXML
    private Text displayed_post_user_name;
    @FXML
    private TextArea displayed_post_text_box;
    @FXML
    private Text displayed_comment1_user_name;
    @FXML
    private TextArea displayed_comment1_text_box;
    @FXML
    private TextArea news_text_box;
    @FXML
    private Pane main_pp_container;
    @FXML
    private Button search_bt;
    @FXML
    private Pane main_pp_container1;
    @FXML
    private Pane main_pp_container11;
    @FXML
    private TextArea newpost_text_box1;
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        database = Database.getInstannce();
        
    }    

    @FXML
    private void goHome(ActionEvent event) {
    }

    @FXML
    private void goSearch(ActionEvent event) {
        loadWindow("/fsktmbook/pages/search/searchPage.fxml","search");
        Stage stage =  (Stage) rootPane.getScene().getWindow();
        stage.close();
        

    }

    @FXML
    private void gonotif(ActionEvent event) {
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
    private void goSettings(ActionEvent event) {
    }

    @FXML
    private void publish_post(ActionEvent event) throws SQLException {
        String msg = newpost_text_box.getText().trim();
        
        Post post = new Post();
        
        post.setContent(msg);
        post.setLikes(0);
        post.setPostDate(Helper.getCurrentTime());
        post.setTitle(msg);
        post.setUserID(FSKTMBook.LOGGEDUSER);
        
        
        database = Database.getInstannce();
        
        
        if(validatePostData(msg)){
            Posts posts = new Posts();
            
            posts.addPost(post);
            
            Helper.openAlert("Post added ");    
        }
        else{
            Helper.openAlert("Post can not be empty OR exceed 5000 characters!");
        }
        
    }

    
    public Pane getPostsPaneCopy(){
        Pane PostsPaneCopy = null;
        try {
            PostsPaneCopy = FXMLLoader.load(getClass().getResource("/fsktmbbook/pages/home/postsTemplate.fxml")); 
        } catch (IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return PostsPaneCopy;
    }
    


    boolean validatePostData(String postContent){
        
        //checks if the post is empty or length is exceeded
        if((postContent.length() > 5000) || (postContent.isEmpty())){
            return false;
        }
        return true;
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
