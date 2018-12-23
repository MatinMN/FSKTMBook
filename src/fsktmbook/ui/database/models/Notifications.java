/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.ui.database.models;

import fsktmbook.helpers.Helper;
import fsktmbook.ui.database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Matin
 */
public class Notifications {
    
    private Database database;
    
    public Notifications(){
        database = Database.getInstannce();
    }
    
    public boolean addNotification(int userId , String content,String type) throws SQLException{

        String query = "INSERT INTO notifications (userId,content,type,status) VALUES (?,?,?,false)";   
                
        PreparedStatement stmt = database.prepareStatement(query);
        
        stmt.setInt(1, userId);
        stmt.setString(2, content);
        stmt.setString(3,type);
        
        //stmt.setString(4, Helper.getCurrentTime()); need to change notifications table 
        
        boolean res = stmt.execute();
        
        return res; 
    }
    
    
    public ResultSet getNotifications(int userId) throws SQLException{
       
        String query = "SELECT * FROM notifications WHERE userId = ? ORDER BY id DESC fetch first 10 rows only"; // 10 last notifications only
        
        PreparedStatement stmt = database.prepareStatement(query);
        
        stmt.setInt(1, userId);
        
        ResultSet rs = stmt.executeQuery();

        return rs;
    }
    
}
