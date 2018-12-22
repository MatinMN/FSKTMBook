/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.ui.database.models;

import fsktmbook.FSKTMBook;
import fsktmbook.helpers.Helper;
import fsktmbook.helpers.User;
import fsktmbook.pages.home.HomePageController;
import fsktmbook.ui.database.Database;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javax.swing.JOptionPane;

/**
 *
 * @author Matin
 */
public class Users {
    private Database database;
    
    
    public Users(){
        database = Database.getInstannce();
    }
    
    
    public boolean doesUserExist(String userName) throws SQLException{
        String query = "SELECT username,password FROM users WHERE username = ?";
        
        PreparedStatement stmt = database.prepareStatement(query);
        
        stmt.setString(1, userName);
        
        ResultSet rs = stmt.executeQuery();
        
        try {
            while(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public int getUserID(String userName) {
        String query = "SELECT id FROM users WHERE username = ?" ;

        try {
            PreparedStatement stmt = database.prepareStatement(query);
            stmt.setString(1, userName);
            
                    ResultSet rs = stmt.executeQuery();
        
            try{
                while(rs.next()){
                     return rs.getInt("id");
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }
    
    public boolean addUser(User user) throws SQLException{

        // check if user already excist
        if(doesUserExist(user.getUserName())){
            Helper.openAlert("User Exists Already!!");
            return false;
        }
        
        String query = "INSERT INTO users (username,firstname,lastname,password,matricNumber,registeredDate) values (?,?,?,?,?,?)";
        
        PreparedStatement stmt = database.prepareStatement(query);
        
        stmt.setString(1, user.getUserName());
        stmt.setString(2, user.getFirstName());
        stmt.setString(3, user.getLastName());
        stmt.setString(4, user.getPassword());
        stmt.setString(5, user.getMatricNumber());
        stmt.setString(6, user.getRegisteredDate());
        
        Helper.openAlert("User registered");
        
        //System.out.println(user.getFirstName() + "/" + user.getLastName());
        
        boolean res = stmt.execute();

        return res;
    }
    
    public ResultSet searchUsers(String searchStatement) throws SQLException{
        
        String query = "SELECT FROM users WHERE username LIKE '%?%' OR firstname LIKE '%?%'";
        
        PreparedStatement stmt = database.prepareStatement(query);
        
        stmt.setString(1, searchStatement);
        stmt.setString(2, searchStatement);
        
        ResultSet rs = stmt.executeQuery();
        
        return rs;
    }
    
    public User getUserInformation(int id) throws SQLException{
        String query = "SELECT * FROM users WHERE id = ?";
        
        PreparedStatement stmt = database.prepareStatement(query);
        
        stmt.setInt(1, id);
        User user = new User();
        
        ResultSet rs = stmt.executeQuery();
        
        try{
            while(rs.next()){
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("username"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setPassword(rs.getString("password"));
                user.setMatricNumber(rs.getString("matricNumber"));
                user.setRegisteredDate(rs.getString("registeredDate"));
                user.setFollowers(rs.getInt("followers"));
                user.setImageDirectory(rs.getString("imageDirectory"));
                user.setFollowing(rs.getInt("following"));
                user.setOccupation(rs.getString("occupation"));
                user.setAbout(rs.getString("about"));
                
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return user;
    }
    
    public String getUserImageDir(int id) throws SQLException{
        String query = "SELECT imageDirectory FROM users WHERE id = ?";
        
        PreparedStatement stmt = database.prepareStatement(query);
        
        stmt.setInt(1, id);
        
        ResultSet rs = stmt.executeQuery();
        
        try{
            while(rs.next()){

                return rs.getString("imageDirectory");

            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return "";
    }
    
    public boolean updateUser(User user) {
        try {
            String update = "UPDATE users SET username=?, lastname=?, about=? , matricNumber = ? , password = ? , occupation = ?  WHERE id=?";
            PreparedStatement stmt = database.prepareStatement(update);
            stmt.setString(1, user.getFirstName());           
            stmt.setString(2, user.getLastName()); 
            stmt.setString(3, user.getAbout());
            stmt.setString(4, user.getMatricNumber());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getOccupation());
            stmt.setInt(7, FSKTMBook.LOGGEDUSER);
            int res = stmt.executeUpdate();
            return (res > 0);
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            //return false;
        }
        return false;
    }
    
    
    
    
    public boolean checkPassword(String userName,String password) throws SQLException{
        String query = "SELECT username,password FROM users WHERE username = ?" ;
        
        PreparedStatement stmt = database.prepareStatement(query);

        stmt.setString(1, userName);
        
        ResultSet rs = stmt.executeQuery();
        
        try{
            while(rs.next()){
                 String pass = rs.getString("password");
                 if(pass.equals(password)){
                     return true;
                 }
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }
    
    
    public Image getUserImage(int id){
            try {
                String imagePath = getUserImageDir(id);
                
                
                File file = new File((imagePath == null) ? "" : imagePath);
                
                if(file.exists()){
                    return new Image(file.toURI().toString());
                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return new Image(new File("profileImages\\default.png").toURI().toString());
    }
}
