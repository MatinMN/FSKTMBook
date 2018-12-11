/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.ui.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        setupBookTable();
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
    
    void setupBookTable(){
        String TABLE_NAME = "users";
        
        try {
            sql = conn.createStatement();
            
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null,null,TABLE_NAME,null);
            
            if(tables.next()){
                System.out.println("Table " + TABLE_NAME + " already exists");
            }else{
                sql.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "  id varchar(200) primary key,\n"
                        + "  username varchar(200),\n"
                        + "  name varchar(200),\n"
                        + "  lastname varchar(200),\n"
                        + "  about varchar(255),\n "
                        + "  password varchar(255),\n"
                        + "  registerDate Date"
                        + " )");
            }
            
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }finally{
        }
    
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

    


}
