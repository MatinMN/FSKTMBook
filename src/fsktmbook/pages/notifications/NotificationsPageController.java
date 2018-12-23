/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.pages.notifications;

import fsktmbook.FSKTMBook;
import fsktmbook.helpers.User;
import fsktmbook.pages.home.HomePageController;
import fsktmbook.pages.profile.ProfilePageController;
import fsktmbook.ui.database.models.Notifications;
import fsktmbook.ui.database.models.Users;
import fsktmbook.ui.database.models.Views;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
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
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Matin
 */
public class NotificationsPageController implements Initializable {

    @FXML
    private BorderPane rootPane;
    @FXML
    private ImageView profileImage;
    @FXML
    private Button home_btn;
    @FXML
    private Button search_bt;
    @FXML
    private Button notif_btn;
    @FXML
    private Button settings_btn;
    @FXML
    private Button signout_btn;
    @FXML
    private VBox notificatoinsContainer;

    Users users;
    Notifications notifications;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
            users = new Users();
            notifications = new Notifications();
            profileImage.setImage(users.getUserImage(FSKTMBook.LOGGEDUSER));
            try {
                displayNotifications();
            } catch (SQLException ex) {
                Logger.getLogger(NotificationsPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }    

    
    public void displayNotifications() throws SQLException{
        ResultSet rs = notifications.getNotifications(FSKTMBook.LOGGEDUSER);
        
        User user;
        notificatoinsContainer.getChildren().clear();
        while(rs.next()){
            
             Pane temp = getNotificationPaneCopy();
             Text username = (Text) temp.getChildren().get(0);
             ImageView userImage = (ImageView) temp.getChildren().get(1);
             username.setText(rs.getString("content"));
             System.out.println(rs.getString("content"));
             //userImage.setImage(users.getUserImage(user.getId()));
             notificatoinsContainer.getChildren().add(temp);
        }
    }
    
    
     public Pane getNotificationPaneCopy(){
         
        Pane PostsPaneCopy = null;
        try {
            PostsPaneCopy = FXMLLoader.load(getClass().getResource("/fsktmbook/helpers/userTemplate.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return PostsPaneCopy;
    }
    
    @FXML
    private void goHome(ActionEvent event) {
         loadWindow("/fsktmbook/pages/home/HomePage.fxml","Home");
        Stage stage =  (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void goSearch(ActionEvent event) {
         loadWindow("/fsktmbook/pages/search/searchPage.fxml","Search");
        Stage stage =  (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void gonotif(ActionEvent event) {
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
            stage.setScene(new Scene(parent));
            stage.show();

            //add view
            Views views = new Views();
            if (FSKTMBook.LOGGEDUSER != userId){
                views.addView(FSKTMBook.LOGGEDUSER, userId);
            }
            Stage currentStage =  (Stage) rootPane.getScene().getWindow();
            currentStage.close();
        } catch (IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
