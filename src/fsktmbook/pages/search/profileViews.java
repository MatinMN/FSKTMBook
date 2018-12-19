/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.pages.search;

import fsktmbook.helpers.Helper;
import fsktmbook.ui.database.Database;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class profileViews {
    
    private Database database;
    
    
    public void profileViews(){
        database = Database.getInstannce();
    }
    
    //function needs to be called when user clicks on anouther user's profile
    public boolean views(int stalkerId , int stalkingId) throws SQLException{

        String query = "INSERT INTO views (userId,viewerId,viewDate) VALUES (?,?,?)";   
                
        PreparedStatement stmt = database.prepareStatement(query);
        
        stmt.setInt(1, stalkingId);
        stmt.setInt(2, stalkerId);
        stmt.setString(3, Helper.getCurrentTime());
        
        boolean res = stmt.execute();
        
        return res;
        
    }
}
