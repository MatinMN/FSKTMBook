/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.ui.database;

import fsktmbook.helpers.Post;
import fsktmbook.helpers.Helper;
import fsktmbook.helpers.User;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

//import javax.swing.JOptionPane;

/**
 *
 * @author Matin
 */
public class Database {
    
    private static Database handler = null;

    private static final String DB_URL = "jdbc:derby:database;create=true";
    private static Connection conn = null;
    private static Statement sql = null;
    
    
    public Database(){
        createConnection();
        setupPostsTable();
        setupUsersTable();
        printUsersTable();
    }
    
    public static Database getInstannce(){
    
        if(handler ==null){
            handler = new Database();
        }
        return handler;
    }
    
     private static void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(DB_URL);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cant load database", "Database Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
     

    public boolean execAction(String qu) {
        try {
            sql = conn.createStatement();
            sql.execute(qu);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        } finally {
        }
    }
    
    void setupUsersTable(){
        String TABLE_NAME = "users";
        
        try {
            sql = conn.createStatement();
            
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null,null,TABLE_NAME,null);
            
            if(tables.next()){
                System.out.println("Table " + TABLE_NAME + " already exists");
            }else{
                sql.execute("CREATE TABLE " + TABLE_NAME + "("
                    + "id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 0, INCREMENT BY 1),\n" +
                    "username VARCHAR(255),\n" +
                    "lastname VARCHAR(255),\n" +
                    "password VARCHAR(255),\n" +
                    "matricNumber VARCHAR(10),\n" +
                    "registeredDate VARCHAR(30),\n" +
                    "about VARCHAR(255)" +
                    " )");
            }
            
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }finally{
        }
    
    }
    
    // creates the posts table *only run it once 
    void setupPostsTable(){
        String TABLE_NAME = "posts";
        
        try {
            sql = conn.createStatement();
            
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null,null,TABLE_NAME,null);
            
            if(tables.next()){
                System.out.println("Table " + TABLE_NAME + " already exists");
            }else{
                sql.execute("CREATE TABLE " + TABLE_NAME + "("
                    + "id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 0, INCREMENT BY 1),\n" +
                    "userId Integer,\n" +
                    "title VARCHAR(255),\n" +
                    "content TEXT,\n" + // 255 but it's 200 like twitter 
                    "likes Integer,\n" +
                    "postDate VARCHAR(30)" + // date the post was posted (LOL)
                    " )");
            }
            
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }finally{
        }
    
    }
    
    public boolean checkPassword(String userName,String password){
        String query = "SELECT username,password FROM users WHERE username='"+userName+"'";
        ResultSet rs = this.execQuery(query);
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
    
    public int getUserID(String userName){
        String query = "SELECT id FROM users WHERE username =' " + userName +"'";
        ResultSet rs = this.execQuery(query);
        try {
            while(rs.next()){
                return rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public boolean printUsersTable(String userName){
        String query = "SELECT id,username,password FROM users WHERE username='"+userName+"'";
        ResultSet rs = this.execQuery(query);
        String msg = "";
        try{
            while(rs.next()){
                 msg+="|"+rs.getInt("id")+"|"+rs.getString("username")+"|"+rs.getString("name")+"|"+rs.getString("lastname")+"|"+rs.getString("password")+"|";
                 System.out.println(msg);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }
    
    public boolean printUsersTable(){
        String query = "SELECT id,username,lastname,password,matricNumber,registeredDate FROM users";
        ResultSet rs = this.execQuery(query);
        String msg = "";
        try{
            while(rs.next()){
                  msg=rs.getInt("id")+"|"+rs.getString("username")+"|"+rs.getString("lastname")+"|"+rs.getString("password")+"|"+rs.getString("matricNumber")+"|"+rs.getString("registeredDate");
                 System.out.println(msg);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }
    
    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            sql = conn.createStatement();
            result = sql.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        } finally {
        }
        
        return result;
    }
    
    public boolean doesUserExist(String userName){
        String query = "SELECT username,password FROM users WHERE username='"+userName+"'";
        ResultSet rs = this.execQuery(query);
        try {
            while(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean addUser(User user){
        
        // check if user already excist
        /*if(doesUserExist(user.getFirstName())){
            Helper.openAlert("User Exists Already!!");
            return false;
        }*/
        String query = "INSERT INTO users (username,lastname,password,matricNumber,registeredDate) values ('"
                + user.getFirstName()+"',"
                + "'" + user.getLastName()+"',"
                + "'" + user.getPassword()+"',"
                + "'" + user.getMatricNumber()+"',"
                + "'" + user.getRegisteredDate()+"'"
                + ")";
        //System.out.println(user.getFirstName() + "/" + user.getLastName());
        boolean result = execAction(query);
 
        return result;
    }
    
    public boolean addPost(Post post){
        
        String query = "INSERT INTO posts(userId, title, content, likes, postDate) values ('"
                + post.getUserID() + "',"
                + "'" + post.getTitle() + "',"
                + "'" + post.getContent() + "',"
                + "'" + post.getLikes() + "',"
                + "'" + post.getPostDate() + "'"
                + ")";
        
        boolean result = execAction(query);
        return result;
    }
    
    public boolean deletePost(Post post){
        String query = "DELETE FROM posts WHERE id  = '" + post.getId() +"'";
        
        boolean result = execAction(query);
        return result;
    }
    
    public ResultSet searchPost(String searchStatement){
        
        String query = "SELECT FROM posts WHERE title LIKE '%searchStatement%' AND content LIKE '%searchStatement%'";
        
        ResultSet rs = this.execQuery(query);
        
        return rs;
    }
    
    
}