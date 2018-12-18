/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.ui.database.models;

import fsktmbook.helpers.Helper;
import fsktmbook.helpers.Post;
import fsktmbook.ui.database.Database;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Matin
 */
public class Follows {
    
    private Database database;
    
    
    public void Follows(){
        database = Database.getInstannce();
    }
    
    
        
    public boolean follow(int followedId , int followerId) throws SQLException{

        String query = "INSERT INTO follows (followerId,followedId,followDate) VALUES (?,?,?)";   
                
        PreparedStatement stmt = database.prepareStatement(query);
        
        stmt.setInt(1, followerId);
        stmt.setInt(2, followedId);
        stmt.setString(3, Helper.getCurrentTime());
        
        boolean res = stmt.execute();
        
        return res;
        
    }
    
    
    
    
}
