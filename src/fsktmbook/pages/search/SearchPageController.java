/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.pages.search;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Azraf
 */
public class SearchPageController implements Initializable {

    @FXML
    private TextField search_bar;
    @FXML
    private Button search;
    @FXML
    private Button home_btn;
    @FXML
    private Button search_btn;
    @FXML
    private Button notif_btn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void searchBar(ActionEvent event) {
    }

    @FXML
    private void doSearch(ActionEvent event) {
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
    
}
