/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.pages.main;

import fsktmbook.FSKTMBook;
import fsktmbook.helpers.Helper;
import fsktmbook.helpers.Post;
import fsktmbook.ui.database.Database;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class MainPageController implements Initializable {

    @FXML
    private Button add_post;
    @FXML
    private Button delete_post;
    @FXML
    private Button search_post;

   Database database;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        database = Database.getInstannce();
    }    

    @FXML
    private void addPost(ActionEvent event) {

    }

    @FXML
    private void deletePost(ActionEvent event) {
    }

    @FXML
    private void searchPost(ActionEvent event) {
    }
    
}
