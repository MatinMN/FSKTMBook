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
public class Comments {
    private Database database;
    
    
    public Comments(){
        database = Database.getInstannce();
    }
    
    public boolean postComment(int postId,int userId,String comment) throws SQLException{
        String query = "INSERT INTO comments (postId,userId,content,postDate) VALUES (?,?,?,?)";
        PreparedStatement stmt = database.prepareStatement(query);
        stmt.setInt(1, postId);
        stmt.setInt(2, userId);
        stmt.setString(3,comment);
        stmt.setString(4,Helper.getCurrentTime());
        return stmt.execute();
    }

    public boolean deleteComment(int commentId) throws SQLException{
        String query = "DELETE FROM comments WHERE id = ?";
        PreparedStatement stmt = database.prepareStatement(query);
        stmt.setInt(1, commentId);
        return stmt.execute();
    }
    
    
    public ResultSet getComments(int postId) throws SQLException{

        String query = "SELECT * FROM comments WHERE postId = ? ORDER BY id DESC fetch first 3 rows only";
        
        PreparedStatement stmt = database.prepareStatement(query);
        
        stmt.setInt(1, postId);
        
        ResultSet rs = stmt.executeQuery();

        return rs;
    }
}
