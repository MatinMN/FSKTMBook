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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        posts = new Posts();
        users = new Users();
        comments = new Comments();
        
        profileImage_center.setImage(users.getUserImage(FSKTMBook.LOGGEDUSER));
        coverImage.setImage(users.getUserImage(FSKTMBook.LOGGEDUSER));
        
        
        displayPosts();
    }
    

    public void displayPosts(){
        int postCount = 0;
        loadMoreBtn.setDisable(true);
        try {
            ResultSet rs = posts.getPosts(offset,postsNumber+1);
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
            ex.printStackTrace();
            //Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
     
    @FXML
    private void goProfile(ActionEvent event) {
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
    private void loadMore(ActionEvent event) {
        offset+= postsNumber;
        displayPosts();
    }
    
    
    


}
