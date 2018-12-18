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
 * @author admin
 */
public class Follow2 {
    private Database database;
    
    
    public void Follows(){
        database = Database.getInstannce();
    }
    
    public boolean follow1(int userID1 , int userID2) throws SQLException{

        String query = "SELECT * FROM follows WHERE followerID='" + userID1 + "' AND followedID='" + userID2 + "'";   
        
        
        PreparedStatement stmt = database.prepareStatement(query);
        boolean rs = stmt.execute();
        
        return rs;
    }
        
    public boolean follow2(int userID1 , int userID2) throws SQLException{

        String query = "SELECT * FROM follows WHERE followerID='" + userID1 + "' AND followedID='" + userID2 + "'";   
        String query1 = "SELECT * FROM follows WHERE followerID='" + userID2 + "' AND followedID='" + userID1 + "'";   
        
        
        PreparedStatement stmt = database.prepareStatement(query);
        boolean rs = stmt.execute();
        
        PreparedStatement stmt1 = database.prepareStatement(query1);
        boolean res = stmt1.execute();
        
        return res&&rs;
        
    }
}
