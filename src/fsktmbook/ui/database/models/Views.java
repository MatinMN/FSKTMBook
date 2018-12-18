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
public class Views {
    
    private Database database;
    
    public Views(){
        database = Database.getInstannce();
    }
    
    public boolean addView(int userId,int profileId) throws SQLException{
        
        String query = "INSERT INTO views (userId,viewerId,viewDate) VALUES (?,?,?)";
        
        boolean result;
        
        PreparedStatement ps = database.prepareStatement(query);

        ps.setInt(1, profileId);
        ps.setInt(2,userId);
        ps.setString(3,Helper.getCurrentTime());
        
        result = ps.execute();
        return result;
    }
    
    // get id of the last person viewing the profile given
    public int getLastView(int profileId) throws SQLException{
        
        String query = "SELECT id FROM users WHERE id = ? ORDER BY id DESC fetch first 1 rows only";
        
        PreparedStatement ps = database.prepareStatement(query);
        
        ps.setInt(1, profileId);
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
            return rs.getInt("id");
        }
        
        return -1; 
    }
    
}
