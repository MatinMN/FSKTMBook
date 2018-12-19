/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.ui.database.models;

import fsktmbook.FSKTMBook;
import fsktmbook.helpers.Post;
import fsktmbook.ui.database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Matin
 */
public class Posts {
    private Database database;
    
    public Posts(){
        database = Database.getInstannce();
    }
    
    
    
    
    public boolean addPost(Post post) throws SQLException{

        String query = "INSERT INTO posts (userId, title, content, likes, postDate) VALUES (?,?,?,?,?)";   
                
        PreparedStatement stmt = database.prepareStatement(query);
        
        stmt.setInt(1, post.getUserID());
        stmt.setString(2, post.getTitle());
        stmt.setString(3, post.getContent());
        stmt.setInt(4, post.getLikes());
        stmt.setString(5, post.getPostDate());
        
        boolean res = stmt.execute();
        
        return res;
        
    }

    public boolean deletePost(Post post) throws SQLException{
        String query = "DELETE FROM posts WHERE id  = ? ";
        
        PreparedStatement stmt = database.prepareStatement(query);
        
        stmt.setInt(1, post.getId());
        
        boolean result = stmt.execute();
        
        return result;
    }


    public ResultSet searchPost(String searchStatement) throws SQLException{

        String query = "SELECT * FROM posts WHERE title LIKE '%?%' AND content LIKE '%?%'";
        
        PreparedStatement stmt = database.prepareStatement(query);
        
        stmt.setString(1, searchStatement);
        stmt.setString(2, searchStatement);
        
        ResultSet rs = stmt.executeQuery();

        return rs;
    }
    
    public ResultSet getPosts() throws SQLException{

        String query = "SELECT * FROM posts WHERE id != ?";
        
        PreparedStatement stmt = database.prepareStatement(query);
        
        stmt.setInt(1, FSKTMBook.LOGGEDUSER);
        
        ResultSet rs = stmt.executeQuery();

        return rs;
    }
}
