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
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
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
    private AnchorPane rootPane;
    
    
    Database database;
   
    
    @FXML
    private Button home_btn;
    @FXML
    private Button search_btn;
    @FXML
    private Button notif_btn;
    @FXML
    private Button signout_btn;
    @FXML
    private ImageView main_pp;
    @FXML
    private ImageView comment1_pp;
    @FXML
    private ImageView comment2_pp;
    @FXML
    private ImageView suggest1_pp;
    @FXML
    private ImageView suggest2_pp;
    @FXML
    private ImageView suggest3_pp;
    @FXML
    private ImageView suggest4_pp;
    @FXML
    private Button settings_btn;
    @FXML
    private TextArea newpost_text_box;
    @FXML
    private ImageView displayed_post_pp;
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
    private Text displayed_comment2_user_name;
    @FXML
    private TextArea displayed_comment2_text_box;
    @FXML
    private ImageView news_pic;
    @FXML
    private TextArea news_text_box;
    @FXML
    private Button news_readmore_btn;
    @FXML
    private Text suggest1_user_name;
    @FXML
    private Text suggest2_user_name;
    @FXML
    private Text suggest3_user_name;
    @FXML
    private Text suggest4_user_name;
    @FXML
    private Button suggest1_follow_btn;
    @FXML
    private Button suggest2_follow_btn;
    @FXML
    private Button suggest3_follow_btn;
    @FXML
    private Button suggest4_follow_btn;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        database = Database.getInstannce();
    }    

    @FXML
    private void goHome(ActionEvent event) {
    }

    @FXML
    private void goSearch(ActionEvent event) {
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
                //alert.showAndWait().ifPresent(type -> {
                    if (action.get() == okButton) {
                        loadWindow("/fsktmbook/pages/login/LoginPage.fxml","Login Page");
                        // close the home page 
                        Stage stage =  (Stage) rootPane.getScene().getWindow();
                        stage.close();
                        return;
                    }
                //});
                
                
        
        
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
        boolean result;  
        
        if(validatePostData(msg)){
            result = database.addPost(post);
            
            Helper.openAlert("Post added ");    
        }
        else{
            Helper.openAlert("Post can not be empty OR exceed 5000 characters!");
        }
        
    }

    @FXML
    private void goReadMoreNews(ActionEvent event) {
    }

    @FXML
    private void followSuggest1(ActionEvent event) {
    }

    @FXML
    private void followSuggest2(ActionEvent event) {
    }

    @FXML
    private void followSuggest3(ActionEvent event) {
    }

    @FXML
    private void followSuggest4(ActionEvent event) {
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
