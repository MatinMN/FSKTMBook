/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.pages.search;

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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
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
 * @author Azraf
 */
public class SearchPageController implements Initializable {

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
    private BorderPane rootPane;
    @FXML
    private TextField searchBox;
    @FXML
    private VBox searchContainer;
    
    
    private Users users;
    private User user;
    @FXML
    private ImageView profileImage_leftTop;
    @FXML
    private Button profile_btn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        users = new Users();
        try {
            user = users.getUserInformation(FSKTMBook.LOGGEDUSER);
        } catch (SQLException ex) {
            Logger.getLogger(SearchPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        profileImage_leftTop.setImage(users.getUserImage(FSKTMBook.LOGGEDUSER));
        
    }

    public void displaySearchUser(String s) throws SQLException{
        ResultSet rs = users.searchUsers(s);
        
        searchContainer.getChildren().clear();
        while(rs.next()){
             User user = users.getUserInformation(rs.getInt("id"));
             Pane temp = getUserPaneCopy();
             Text username = (Text) temp.getChildren().get(0);
             ImageView userImage = (ImageView) temp.getChildren().get(1);
             username.setText(user.getFirstName());
             userImage.setImage(users.getUserImage(user.getId()));
             userImage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    
                    @Override
                    public void handle(MouseEvent event) {
                        openProfile(user.getId());
                    }
                });
             searchContainer.getChildren().add(temp);
        }
    }
    
    public Pane getUserPaneCopy(){
         
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
         loadWindow("/fsktmbook/pages/home/HomePage.fxml","home1");
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
    private void searchUser(InputMethodEvent event) {
        String search = "";
        search +=searchBox.getText();
        System.out.println("searching ...");
        try {
            displaySearchUser(search);
        } catch (SQLException ex) {
            Logger.getLogger(SearchPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    private void searchUser(KeyEvent event) {
        String search = "";
        search +=searchBox.getText();
        System.out.println("searching ..." + search);
        try {
            displaySearchUser(search);
        } catch (SQLException ex) {
            Logger.getLogger(SearchPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goProfile(ActionEvent event) {
        openProfile(FSKTMBook.LOGGEDUSER);
    }

    @FXML
    private void goNotif(ActionEvent event) {
        loadWindow("/fsktmbook/pages/notifications/NotificationsPage.fxml","Notification");
        Stage stage =  (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
}
