/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.helpers;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
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
    
    // A function that returns the current date and time in malaysia zone
    public static String getCurrentTime(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore"));
        Date currentDate = calendar.getTime();
        return currentDate.toString();
    }
    
     public static String getCurrentDay(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore"));
        int currentDate = calendar.get(Calendar.DAY_OF_YEAR);
        return currentDate+"";
    }
    
    public static String getRandomString(){
        
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
}
