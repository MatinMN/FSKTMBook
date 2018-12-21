/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.pages.search;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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
    private Pane main_pp_container;
    @FXML
    private Pane main_pp_container1;
    @FXML
    private Button settings_btn;
    @FXML
    private Button signout_btn;
    @FXML
    private BorderPane rootPane;
    @FXML
    private ImageView search_image1;
    @FXML
    private ImageView search_image2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void goHome(ActionEvent event) {
         loadWindow("/fsktmbook/pages/home/HomePage.fxml","home1");
        Stage stage =  (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void goSearch(ActionEvent event) {
    }

    @FXML
    private void gonotif(ActionEvent event) {
    }

    @FXML
    private void goSettings(ActionEvent event) {
         loadWindow("/fsktmbook/pages/settings/SettingsPage.fxml","");
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
            stage.setScene(new Scene(parent));
            stage.show();
            
        } catch (IOException ex){ 
            ex.printStackTrace();
            //Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
