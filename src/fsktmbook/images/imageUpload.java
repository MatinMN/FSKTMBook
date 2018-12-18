/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.images;

import fsktmbook.helpers.Helper;
import fsktmbook.ui.database.Database;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author admin
 */
public class imageUpload {
    
    private Database database;
    
    
    public void Follows(){
        database = Database.getInstannce();
    }
    
    /**
     *
     * @param userId
     * @param type
     * @param file_path
     * @param date
     * @param postId
     */
    public void up(int userId, Object postId, String type, String file_path) throws SQLException, FileNotFoundException{
        String query = "INSERT INTO images (userId, postId, postType, image, postDate) VALUES (?,?,?,FILE_READ(?),?";
        String path = null;
        
        PreparedStatement stmt = database.prepareStatement(query);
       
        //https://www.youtube.com/watch?v=UusZGBkV6HI
        //Note: This is just a draft. Image uploaded needs to be changed to at user signup and settings, as well as comments sections
        //The below code must be place in these pages. @GoRoXster will update the code accordingly.
        //Also Note: This draft is only for uploading. Not for retrieving.
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg", "gif", "png");
        fileChooser.addChoosableFileFilter(filter);
        int result = fileChooser.showSaveDialog(null);
        if(result==JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            path = selectedFile.getAbsolutePath();
        }
        
        InputStream is = new FileInputStream(new File(path));
        
        stmt.setInt(1, userId);
        stmt.setObject(2, postId);
        stmt.setString(3, type);
        stmt.setBlob(4, is);
        stmt.setString(4, Helper.getCurrentTime());
    }
}
