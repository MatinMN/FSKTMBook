/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.pages.home;

import fsktmbook.FSKTMBook;
import fsktmbook.helpers.Helper;
import fsktmbook.helpers.Post;
import fsktmbook.helpers.User;
import fsktmbook.ui.database.Database;
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
    private Button settings_btn;
    @FXML
    private TextArea newpost_text_box;
    @FXML
    private Button publish_btn;
    @FXML
    private TextArea news_text_box;
    @FXML
    private Button search_bt;
    @FXML
    private VBox postsContainer;
    @FXML
    private VBox profile_image;
    @FXML
    private Button upload_image_button;
    @FXML
    private ImageView profileImage;
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
            database = Database.getInstannce();
            
            Posts posts = new Posts();
            Users users = new Users();
        try {
            ResultSet rs = posts.getPosts();
            
            while(rs.next()){
                User user = users.getUserInformation(rs.getInt("userId"));
                GridPane post = (GridPane) getPostsPaneCopy();
                VBox vBox = (VBox) post.getChildren().get(0);
                Pane pane = (Pane) vBox.getChildren().get(0);

                Text usernameText = (Text) pane.getChildren().get(0);
                TextArea postContent = (TextArea) pane.getChildren().get(1);
                
                usernameText.setText(user.getFirstName());
                postContent.setText(rs.getString("content"));
                
                postsContainer.getChildren().add(post);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
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
    }

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
            
            Helper.openAlert("Post added ");    
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

    @FXML
    private void goSettings(ActionEvent event) {
    }
    
    @FXML
    private void uploadImage(ActionEvent event) throws IOException {
        
        chooseImage();
    }
    
    void chooseImage() throws IOException{
        
        // this function is to choose an image by using FileChooser..
        File file; 
        
        
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG & JPG", "*png*","*jpg*");
        fileChooser.getExtensionFilters().add(extFilter);
        
        file = fileChooser.showOpenDialog(null);
        
        String imagePath = "";
        
        imagePath = file.getAbsolutePath();
        
        int userId = 10;
        // get the path as a String// call the method...
        String path = copyImage(imagePath, userId);
        // show the image in the imageView...
        uploadImage(path);

    }
    
    String copyImage(String sourceImage, int userId) throws IOException{
        
        File source = new File(sourceImage);
        
        String imageName = randomString() + Integer.toString(userId);
        
        
        if(imageExists() != null && imageExists().length() != 0 ){
            File deleteFile = new File(imageExists());
            System.out.println("I got here");
            if(deleteFile.delete()){
                System.out.println(imageExists() + " // is deleted");
            }
            else{
                System.out.println("No such file to deltet");
            }
            
        }
        
        File dest = new File("profileImages\\" + imageName +  ".png");
        
        //addImage(dest.toString());
        
        
        
        //System.out.println(dest.getAbsolutePath());
        //System.out.println(imageName + userId);
        
        roundedImage(source.toString(), dest.toString());
        
        
        System.out.println(dest.toString());
        return dest.toString();
        // return the new director of the user's image, in oreder to store in the database and show automatically next time....
        
        
    }
    
    void uploadImage(String imagePath) throws IOException{
        // such a function accepts a string to, which is the path to an image in order to show in the profile....
        File file = new File(imagePath);
        if(file.exists()){
            Image image = new Image(file.toURI().toString());
            profileImage.setImage(image);
        }
        else{
            System.out.println("Image is not found in the database!!");
        }
        
    }
    
    void roundedImage(String sourcePath, String destPath) throws IOException{
        
        // Get the BufferedImage object for the image file
        BufferedImage originalImg=ImageIO.read(new File(sourcePath));
        
        // Get the width,height of the image
        int width=originalImg.getWidth();
        int height=originalImg.getHeight();
        
        if(height > width){
            height = width;
        }
        else{
            width = height;
        }
        
        System.out.println(width + " // " + height);
        
        // Create a new BufferedImage object with the width,height
        // equal to that of the image file
        BufferedImage bim=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        
        // Create a Graphics2D object by using
        // createGraphics() method. This object is 
        // used to perform the operation!
        Graphics2D g2=bim.createGraphics();
        
        // You can also use rendering hints
        // to smooth the edges or the rounded rectangle
        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);
        
        // This method does it all!. You can clip the
        // image into the shape you wish, play it as you like!
        
        g2.setBackground(Color.white);
        g2.setClip(new RoundRectangle2D.Double(0, 0, width/1, height/1, width/1, height/1));
        //g2.setClip(new RoundRectangle2D.Double(0,0,width,height,width/1,height/1));
        
        // Now, draw the image. The image is now
        // in the 'clipped' shape, the shape in the setClip()
        g2.drawImage(originalImg,0,0,null);
        
        // Dispose it, we no longer need it.
        g2.dispose();
        
        // Write to a new image file
        ImageIO.write(bim,"PNG",new File(destPath));
        
        
    }
     
    String randomString(){
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
    
    public String imageExists(){
        
        Users users = new Users();
        
        User user;
        try {
            user = users.getUserInformation(FSKTMBook.LOGGEDUSER);
            System.out.println(user.getImageDirectory());
            return user.getImageDirectory();

        } catch (SQLException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
}
