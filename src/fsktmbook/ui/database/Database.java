/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.ui.database;

import fsktmbook.helpers.Post;
import fsktmbook.helpers.Helper;
import fsktmbook.helpers.User;
import fsktmbook.helpers.Voucher;
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
        setupViewsTable();
        setupGrabVouhersTable();
        setupNotification();
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
                    "occupation VARCHAR(20), \n" +
                    "imageDirectory VARCHAR(50), \n" +
                    "about VARCHAR(1000)" +
                    " )");
            }

        }catch(SQLException e){
            System.err.println(e.getMessage());
        }finally{
        }

    }
    
    
    void setupGrabVouhersTable(){
        
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
                    "userId Integer,\n" +
                    "amount Integer,\n" +
                    "type VARCHAR(30),\n" +
                    "releaseDate VARCHAR(30)" +
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
                    "userId Integer,\n" +
                    "content VARCHAR(1000),\n" +
                    "postDate VARCHAR(30)" + // when the comment was posted
                    " )");
            }

        }catch(SQLException e){
            System.err.println(e.getMessage());
        }finally{
        }

    }
    
    void setupViewsTable(){
        String TABLE_NAME = "views";

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
                    "viewerId Integer,\n" +
                    "viewDate VARCHAR(30)" + 
                    " )");
            }

        }catch(SQLException e){
            System.err.println(e.getMessage());
        }finally{
        }

    }
    
    void setupFollowersTable(){
        String TABLE_NAME = "follows";

        try {
            sql = conn.createStatement();

            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null,null,TABLE_NAME,null);

            if(tables.next()){
                System.out.println("Table " + TABLE_NAME + " already exists");
            }else{
                sql.execute("CREATE TABLE " + TABLE_NAME + "("
                    + "id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 0, INCREMENT BY 1),\n" +
                    "followerId Integer,\n" +
                    "followedId Integer,\n" +
                    "followDate VARCHAR(30)" + 
                    " )");
            }

        }catch(SQLException e){
            System.err.println(e.getMessage());
        }finally{
        }

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
    
    
    public PreparedStatement prepareStatement(String query) throws SQLException{
        return conn.prepareStatement(query);
    }
    
    public boolean addVoucher(Voucher voucher) throws SQLException{
        
        String query = "INSERT INTO posts (userId, amount, type, releaseDate) values (?,?,?,?)";
        
        PreparedStatement stmt = conn.prepareStatement(query);
        
        stmt.setInt(1, voucher.getUserId());
        stmt.setInt(2, voucher.getAmount());
        stmt.setString(3, voucher.getType());
        stmt.setString(4, voucher.getReleaseDate());
        
        boolean rs = stmt.execute();
        
        return rs;
    }
    
    public void setupNotification(){
        
        String TABLE_NAME = "notifications";

        try {
            sql = conn.createStatement();

            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null,null,TABLE_NAME,null);

            if(tables.next()){
                System.out.println("Table " + TABLE_NAME + " already exists");
            }else{
                sql.execute("CREATE TABLE " + TABLE_NAME + "("
                    + "id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 0, INCREMENT BY 1),\n" +
                    "UserId Integer,\n" +
                    "status Boolean,\n" +
                    "content VARCHAR(5000),\n" +
                    "type VARCHAR(30)" +
                    " )");
            }

        }catch(SQLException e){
            System.err.println(e.getMessage());
        }finally{
        }
        
    }
    
}
