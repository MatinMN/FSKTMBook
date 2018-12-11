/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.helpers;

import javafx.scene.control.Alert;

/**
 *
 * @author Matin
 */
public class Helper {
    
    
    
    public static void openAlert(String msg){
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setHeaderText(null);
           alert.setContentText(msg);
           alert.showAndWait();
    }
}
