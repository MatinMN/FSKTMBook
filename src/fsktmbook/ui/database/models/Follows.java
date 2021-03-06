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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Matin
 */
public class Follows {
    
    private Database database;
    
    
    public Follows(){
        database = Database.getInstannce();
    }
    
    
        
    public boolean follow(int followerId , int followedId) throws SQLException{

        String query = "INSERT INTO follows (followerId,followedId,followDate) VALUES (?,?,?)";   
                
        PreparedStatement stmt = database.prepareStatement(query);
        
        stmt.setInt(1, followerId);
        stmt.setInt(2, followedId);
        stmt.setString(3, Helper.getCurrentTime());
        
        boolean res = stmt.execute();
        
        
        return res;
        
    }
    
     public boolean unfollow(int followerId, int followedId) throws SQLException{
         
        String query = "DELETE FROM follows WHERE followerId = ? AND followedId = ?";
        PreparedStatement stmt = database.prepareStatement(query);
        
        stmt.setInt(1,followerId);
        stmt.setInt(2,followedId);
        
        return (stmt.execute());
    }
     
     public boolean isFollowing(int followerId,int followedId) throws SQLException{
     
         String query = "SELECT id FROM follows WHERE followerId = ? AND followedId = ?";
         
         PreparedStatement stmt = database.prepareStatement(query);
         
         stmt.setInt(1,followerId);
         stmt.setInt(2,followedId);
         
         ResultSet rs = stmt.executeQuery();
         
         while(rs.next()){
             return true;
         }
         return false;
     }
    public int getNumberFollowing(int userId){
        String query = "SELECT COUNT(*) as count FROM follows WHERE followerId = ?";
         
         PreparedStatement stmt;
        try {
            stmt = database.prepareStatement(query);
        
         
         stmt.setInt(1,userId);
         
         
         ResultSet rs = stmt.executeQuery();
         
         rs.next();
         return rs.getInt("count");
         
         } catch (SQLException ex) {
            Logger.getLogger(Follows.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
     
     public int getNumberFollowers(int userId){
        String query = "SELECT COUNT(*) as count FROM follows WHERE followedId = ?";
         
         PreparedStatement stmt;
        try {
            stmt = database.prepareStatement(query);
        
         
         stmt.setInt(1,userId);
         
         
         ResultSet rs = stmt.executeQuery();
         
         rs.next();
         return rs.getInt("count");
         
         } catch (SQLException ex) {
            Logger.getLogger(Follows.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
     public boolean DoFollowBack(int user1,int user2) throws SQLException{
         return (user1 == user2)? true : isFollowing(user1,user2) && isFollowing(user2,user1);
    }
}
