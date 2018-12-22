/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.pages.profile;

import fsktmbook.FSKTMBook;
import fsktmbook.helpers.Helper;
import fsktmbook.helpers.Post;
import fsktmbook.helpers.User;
import fsktmbook.pages.home.HomePageController;
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
public class ProfilePageController implements Initializable {




    private Label label;

    @FXML
    private BorderPane rootPane;

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
    private Button profile_btn;
    @FXML
    private ImageView coverImage;
    @FXML
    private Text username_center;
    @FXML
    private Text occupation_center;
    @FXML
    private Text numFollowers;
    @FXML
    private Text numFollowing;
    @FXML
    private VBox recentVisitorsContainer;
    @FXML
    private VBox postsContainer;

    
     private int offset;
    private int postsNumber = 3;
    
    @FXML
    private Button loadMoreBtn;
    
   private Users users;
   private Posts posts;
   private Comments comments;
   private User user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    

    public void displayPosts(){
        int postCount = 0;
        loadMoreBtn.setDisable(true);
        try {
            ResultSet rs = posts.getPostsFromUser(offset,postsNumber+1,user.getId());
            while(rs.next()){
                postCount++;
                if(postCount > postsNumber){
                    loadMoreBtn.setDisable(false);
                    return;
                }
                User user = users.getUserInformation(rs.getInt("userId"));
                GridPane post = (GridPane) getPostsPaneCopy();
                int postId = rs.getInt("id");
                VBox vBox = (VBox) post.getChildren().get(0);
                Pane pane = (Pane) vBox.getChildren().get(0);
                Pane commentsPane = (Pane) vBox.getChildren().get(1);
                VBox commentsBox = (VBox) commentsPane.getChildren().get(0);
                Pane addCommentPane = (Pane) commentsBox.getChildren().get(1);

                Text usernameText = (Text) pane.getChildren().get(0);
                TextArea postContent = (TextArea) pane.getChildren().get(1);
                ImageView postUserImage = (ImageView) pane.getChildren().get(2);

                Button addCommentButton = (Button) addCommentPane.getChildren().get(1);
                TextArea commentInput = (TextArea) addCommentPane.getChildren().get(0);


                addCommentButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            // add a new comment
                            String commentContent = commentInput.getText();
                            comments.postComment(postId, FSKTMBook.LOGGEDUSER, commentContent);
                            displayComments(postId,(VBox)commentsBox.getChildren().get(0));
                            //System.out.println(commentContent);
                            commentInput.setText("");

                        } catch (SQLException ex) {
                            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });


                usernameText.setText(user.getFirstName());
                postContent.setText(rs.getString("content"));
                postUserImage.setImage(users.getUserImage(user.getId()));
                postsContainer.getChildren().add(post);

                displayComments(postId,(VBox)commentsBox.getChildren().get(0));
            }
        } catch (SQLException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void displayComments(int postId,VBox commentsBox) throws SQLException{
        ResultSet rs = comments.getComments(postId);
        User user;
        commentsBox.getChildren().clear();
        while(rs.next()){

             user  = users.getUserInformation(rs.getInt("userId"));
             Pane post = (Pane) getCommentPaneCopy();
             Text username = (Text) post.getChildren().get(0);
             TextArea content = (TextArea) post.getChildren().get(1);
             ImageView userImage = (ImageView) post.getChildren().get(2);
             username.setText(user.getFirstName());
             content.setText(rs.getString("content"));
             userImage.setImage(users.getUserImage(user.getId()));
             commentsBox.getChildren().add(post);
        }
    }
    
     void loadWindow(String location,String title){

        try {
            Parent parent = FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();

        } catch (IOException ex){
            
        }
    }
    
      public Pane getPostsPaneCopy(){
          
        Pane PostsPaneCopy = null;
        
        try {
            PostsPaneCopy = FXMLLoader.load(getClass().getResource("/fsktmbook/helpers/PostsTemplate.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return PostsPaneCopy;
    }

    public Pane getCommentPaneCopy(){
        Pane PostsPaneCopy = null;
        try {
            PostsPaneCopy = FXMLLoader.load(getClass().getResource("/fsktmbook/helpers/CommentTemplate.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return PostsPaneCopy;
    }
     
    
    public void getData(int userId){
        
        posts = new Posts();
        users = new Users();
        comments = new Comments();
        
        try {
            user = users.getUserInformation(userId);
        } catch (SQLException ex) {
            Logger.getLogger(ProfilePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        profileImage_center.setImage(users.getUserImage(user.getId()));
        coverImage.setImage(users.getUserImage(user.getId()));
        username_center.setText(user.getUserName());
        occupation_center.setText(user.getOccupation());
        profileImage_leftTop.setImage(users.getUserImage(FSKTMBook.LOGGEDUSER));
        displayPosts();
    }
    
    @FXML
    private void goProfile(ActionEvent event) {
        openProfile(FSKTMBook.LOGGEDUSER);
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
         loadWindow("/fsktmbook/pages/settings/SettingsPage.fxml","Settings");
        Stage stage =  (Stage) rootPane.getScene().getWindow();
        stage.close();
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
    private void loadMore(ActionEvent event) {
        offset+= postsNumber;
        displayPosts();
    }
    
    public void openProfile(int userId){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fsktmbook/pages/profile/ProfilePage.fxml"));
            Parent parent;
        try {
            parent = loader.load();
        
            
            ProfilePageController controller = (ProfilePageController) loader.getController();
            
            controller.getData(userId);
            
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Edit book");
            stage.setScene(new Scene(parent));
            stage.show();

            Stage currentStage =  (Stage) rootPane.getScene().getWindow();
            //currentStage.close();
        } catch (IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    


}
