/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.pages.home;

import fsktmbook.FSKTMBook;
import fsktmbook.helpers.Helper;
import fsktmbook.helpers.ImageHandler;
import fsktmbook.helpers.Post;
import fsktmbook.helpers.User;
import fsktmbook.pages.profile.ProfilePageController;
import fsktmbook.ui.database.Database;
import fsktmbook.ui.database.models.Comments;
import fsktmbook.ui.database.models.Follows;
import fsktmbook.ui.database.models.Notifications;
import fsktmbook.ui.database.models.Posts;
import fsktmbook.ui.database.models.Users;
import fsktmbook.ui.database.models.Views;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
    Posts posts;
    Users users;
    Follows follows;
    Comments comments;

    @FXML
    private Button home_btn;
    @FXML
    private Button notif_btn;
    @FXML
    private Button settings_btn;
    @FXML
    private TextArea newpost_text_box;
    @FXML
    private Button publish_btn;
    @FXML
    private Button search_bt;
    @FXML
    private VBox postsContainer;

    @FXML
    private VBox profile_image;
    @FXML
    private ImageView profileImage;

    @FXML
    private ScrollPane scroll_container;



    private int offset;
    private int postsNumber = 3;
    
    @FXML
    private Button loadMoreBtn;

    private User user;
    @FXML
    private Button signout_btn;
    @FXML
    private Button buynow_btn1;
    @FXML
    private Button buynow_btn2;

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

            database = Database.getInstannce();
            offset = 0;
            posts = new Posts();
            users = new Users();
            comments = new Comments();
            follows = new Follows();
                   
        try {
            user = users.getUserInformation(FSKTMBook.LOGGEDUSER);
        } catch (SQLException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            profileImage.setImage(users.getUserImage(FSKTMBook.LOGGEDUSER));

            displayPosts();
    }


    public void clearPosts(){
        postsContainer.getChildren().clear();
        offset = 0;
    }

    public void displayPosts(){
        int postCount = 0;
        
        loadMoreBtn.setDisable(true);
        try {
            ResultSet rs = posts.getPosts(offset,postsNumber+1);
            
            while(rs.next()){
                postCount++;
                System.out.println("Here");
                if(postCount > postsNumber){
                    loadMoreBtn.setDisable(false);
                    return;
                }
                
                System.out.println("Over here");
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
                            if(follows.DoFollowBack(FSKTMBook.LOGGEDUSER, user.getId())){
                                String commentContent = commentInput.getText();
                                comments.postComment(postId, FSKTMBook.LOGGEDUSER, commentContent);
                                displayComments(postId,(VBox)commentsBox.getChildren().get(0));
                                //System.out.println(commentContent);
                                commentInput.setText("");
                            }else{
                                Helper.openAlert("You can only comment on this post if " + user.getFirstName() +" and you follow each other");
                            }

                        } catch (SQLException ex) {
                            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                
                postUserImage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    
                    @Override
                    public void handle(MouseEvent event) {
                        openProfile(user.getId());
                            System.out.println(user.getId());
                    }
                });
                System.out.println("tsest");
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
        loadWindow("/fsktmbook/pages/notifications/NotificationsPage.fxml","Notifications");
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
            clearPosts();
            displayPosts();
            newpost_text_box.setText("");
            users.getPostsNumber(FSKTMBook.LOGGEDUSER);
            
           
        }
        else{
            Helper.openAlert("Post can not be empty OR exceed 5000 characters!");
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
            stage.getIcons().add(new Image("/fsktmbook/images/1.png"));
            stage.setScene(new Scene(parent));
            stage.show();

        } catch (IOException ex){
            ex.printStackTrace();
            //Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goSettings(ActionEvent event) {
        loadWindow("/fsktmbook/pages/settings/SettingsPage.fxml","Settings");
        Stage stage =  (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    public void viewImage() throws IOException, SQLException{
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
                profileImage.setImage(image);
                handler.updateImageDiectory(imagePath, FSKTMBook.LOGGEDUSER);
            }
            else{
                System.out.println("Image is not found in the database!!");
            }
        }

    }

    @FXML
    private void loadMore(ActionEvent event) {
        offset+= postsNumber;
        System.out.println("here" + offset);
        displayPosts();
    }

    @FXML
    private void goProfile(MouseEvent event) throws IOException {
        
        openProfile(FSKTMBook.LOGGEDUSER);
    }

    public void openProfile(int userId){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fsktmbook/pages/profile/ProfilePage.fxml"));
            Parent parent;
        try {
            parent = loader.load();
        
            
            ProfilePageController controller = (ProfilePageController) loader.getController();
            
            controller.getData(userId);
            
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Profile Page");
            stage.getIcons().add(new Image("/fsktmbook/images/1.png"));
            stage.setScene(new Scene(parent));
            stage.show();

            //add view
            Views views = new Views();
            if (FSKTMBook.LOGGEDUSER != userId){
                views.addView(FSKTMBook.LOGGEDUSER, userId);
                Notifications not = new Notifications();
                
                not.addNotification(userId,user.getFirstName() + " viewed your profile" ,"View");
            }
            
            Stage currentStage =  (Stage) rootPane.getScene().getWindow();
            currentStage.close();
            
        } catch (IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goAd1(ActionEvent event) {
        
        try {
            java.awt.Desktop.getDesktop().browse(new URI("https://www.mcdonalds.com.my/?gclid=Cj0KCQiAgf3gBRDtARIsABgdL3lPdW1_Pr51NmDeXzYHK3_au7mLpjUdtArFbZ7Ed40UWsDj4eJeJd0aAs3qEALw_wcB"));
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goAd2(ActionEvent event) {
        
        try {
            java.awt.Desktop.getDesktop().browse(new URI("https://www.lazada.com.my/?exlaz=d_1:mm_150050845_51350203_2010350203::11:124182683!12032563043!%2Blazada!b!aud-279437839081:kwd-31300663887!c&utm_source=google&utm_medium=sem_brand&utm_content=%2Blazada_b&utm_campaign=124182683&utm_term=12032563043_c_aud-279437839081:kwd-31300663887&adjust_tracker=t2v78t_5n20iw&adjust_campaign=124182683&adjust_adgroup=12032563043_c_aud-279437839081:kwd-31300663887&adjust_creative=%2Blazada_b&tracker_limit=1000000&gclid=Cj0KCQiAgf3gBRDtARIsABgdL3kJEIFJXMpHvsXJ9rBQblwCUVSEh4qJmS8zsGuV-UGuQ1z8QLRKofMaAsTKEALw_wcB"));
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void loadMore(MouseEvent event) {
        offset+= postsNumber;
        System.out.println("here" + offset);
        displayPosts();
    }
    
}
