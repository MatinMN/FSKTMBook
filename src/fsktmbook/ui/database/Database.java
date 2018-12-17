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
        setupTables();
        debugTables();
    }

    public void debugTables(){
        printUsersTable();
        printPostsTable();
    }
    public void setupTables(){
        setupPostsTable();
        setupUsersTable();
        setupCommentsTable();
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
                    "firstname VARCHAR(255),\n" +
                    "lastname VARCHAR(255),\n" +
                    "password VARCHAR(255),\n" +
                    "matricNumber VARCHAR(10),\n" +
                    "registeredDate VARCHAR(30),\n" +
                    "followers Integer,\n" +
                    "following Integer, \n" + 
                    "about VARCHAR(1000)" +
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
                    "content VARCHAR(5000),\n" + // 255 but it's 200 like twitter
                    "likes Integer,\n" +
                    "postDate VARCHAR(30)" + // date the post was posted (LOL)
                    " )");
            }

        }catch(SQLException e){
            System.err.println(e.getMessage());
        }finally{
        }

    }

    void setupCommentsTable(){
        String TABLE_NAME = "comments";

        try {
            sql = conn.createStatement();

            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null,null,TABLE_NAME,null);

            if(tables.next()){
                System.out.println("Table " + TABLE_NAME + " already exists");
            }else{
                sql.execute("CREATE TABLE " + TABLE_NAME + "("
                    + "id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 0, INCREMENT BY 1),\n" +
                    "postId Integer,\n" +
                    "content VARCHAR(1000),\n" +
                    "postDate VARCHAR(30)" + // when the comment was posted
                    " )");
            }

        }catch(SQLException e){
            System.err.println(e.getMessage());
        }finally{
        }

    }

    public boolean checkPassword(String userName,String password) throws SQLException{
        String query = "SELECT username,password FROM users WHERE username = ?" ;
        
        PreparedStatement stmt = conn.prepareStatement(query);
        
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
    
    public boolean printPostsTable(){
        String query = "SELECT * FROM posts";
        ResultSet rs = this.execQuery(query);
        String msg = "";
        try{
            while(rs.next()){
                  msg=rs.getInt("id")+"|"+rs.getString("userId")+"|"+rs.getString("content")+"|"+rs.getString("likes");
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

    public boolean doesUserExist(String userName) throws SQLException{
        String query = "SELECT username,password FROM users WHERE username = ?";
        
        PreparedStatement stmt = conn.prepareStatement(query);
        
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

    public boolean addUser(User user) throws SQLException{

        // check if user already excist
        if(doesUserExist(user.getUserName())){
            Helper.openAlert("User Exists Already!!");
            return false;
        }
        
        String query = "INSERT INTO users (username,firstname,lastname,password,matricNumber,registeredDate) values (?,?,?,?,?,?)";
        
        PreparedStatement stmt = conn.prepareStatement(query);
        
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
    
    public ResultSet searchUser(String searchStatement) throws SQLException{
        
        String query = "SELECT FROM users WHERE username LIKE '%?%' OR firstname LIKE '%?%'";
        
        PreparedStatement stmt = conn.prepareStatement(query);
        
        stmt.setString(1, searchStatement);
        stmt.setString(2, searchStatement);
        
        ResultSet rs = stmt.executeQuery();
        
        return rs;
    }
    
    public User getUserInformation(int id) throws SQLException{
        String query = "SELECT FROM users WHERE id = ?";
        
        PreparedStatement stmt = conn.prepareStatement(query);
        
        stmt.setInt(1, id);
        User user = new User();
        
        ResultSet rs = stmt.executeQuery();
        String msg="";
        try{
            while(rs.next()){
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("username"));
                user.setLastName(rs.getString("lastname"));
                user.setPassword(rs.getString("password"));
                user.setMatricNumber(rs.getString("matricNumber"));
                user.setRegisteredDate(rs.getString("registeredDate"));                
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return user;
    }
    public boolean addPost(Post post) throws SQLException{

        String query = "INSERT INTO posts (userId, title, content, likes, postDate) VALUES (?,?,?,?,?)";   
                
        PreparedStatement stmt = conn.prepareStatement(query);
        
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
        
        PreparedStatement stmt = conn.prepareStatement(query);
        
        stmt.setInt(1, post.getId());
        
        boolean result = stmt.execute();
        
        return result;
    }


    public ResultSet searchPost(String searchStatement) throws SQLException{

        String query = "SELECT FROM posts WHERE title LIKE '%?%' AND content LIKE '%?%'";
        
        PreparedStatement stmt = conn.prepareStatement(query);
        
        stmt.setString(1, searchStatement);
        stmt.setString(2, searchStatement);
        
        ResultSet rs = stmt.executeQuery();

        return rs;
    }
    
    public boolean postComment(int postId,int userId,String comment) throws SQLException{
        String query = "INSERT INTO comments (postId,userId,content,postDate) VALUES (?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, postId);
        stmt.setInt(2, userId);
        stmt.setString(3,comment);
        stmt.setString(4,Helper.getCurrentTime());
        return stmt.execute();
    }

    public boolean deleteComment(int commentId) throws SQLException{
        String query = "DELETE FROM comments WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, commentId);
        return stmt.execute();
    }

    public boolean updateUser(User user) {
        try {
            String update = "UPDATE users SET name=?, lastname=?, about=? , matricNumber = ? , password = ? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.setString(1, user.getFirstName());           
            stmt.setString(2, user.getLastName()); 
            stmt.setString(3, user.getAbout());
            stmt.setString(4, user.getMatricNumber());
            stmt.setString(5, user.getPassword());
            stmt.setInt(6, user.getId());
            int res = stmt.executeUpdate();
            return (res > 0);
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            //return false;
        }
        return false;
    }
    
    
    void setupGrabVouhers(){
        
        String TABLE_NAME = "grabVouchers";

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
                    "firstname VARCHAR(255),\n" +
                    "lastname VARCHAR(255),\n" +
                    "password VARCHAR(255),\n" +
                    "matricNumber VARCHAR(10),\n" +
                    "registeredDate VARCHAR(30),\n" +
                    "followers Integer,\n" +
                    "following Integer, \n" + 
                    "about VARCHAR(1000)" +
                    " )");
            }

        }catch(SQLException e){
            System.err.println(e.getMessage());
        }finally{
        }
        
    }
    

}
