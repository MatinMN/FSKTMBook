/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.helpers;

import fsktmbook.FSKTMBook;
import fsktmbook.pages.home.HomePageController;
import fsktmbook.ui.database.Database;
import fsktmbook.ui.database.models.Users;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 *
 * @author USER
 */
public class ImageHandler {
    
    private ImageView profileImage;
    String imageDirectory;
    private Database database;
    
    
    public ImageHandler(){
        database = Database.getInstannce();
    }
    
    public void updateImageDiectory(String dir,int id) throws SQLException{
        String updateDirectory = "UPDATE users SET imageDirectory=? WHERE id=?";
        PreparedStatement stmt = database.prepareStatement(updateDirectory);
        stmt.setString(1, dir);
        stmt.setInt(2, id);
        stmt.executeUpdate();
            
    } 
    

    public void chooseImage() throws IOException{

        // this function is to choose an image by using FileChooser..
        File file;


        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG & JPG", "*png*","*jpg*");
        fileChooser.getExtensionFilters().add(extFilter);

        file = fileChooser.showOpenDialog(null);

        String imagePath = "";

        imagePath = file.getAbsolutePath();

        int userId = 10;
        // get the path as a String// call the method...
        String path = copyImage(imagePath, userId);
        // show the image in the imageView...
        setImageDirectory(path);

    }

    public String getImageDirectory() {
        return imageDirectory;
    }

    public void setImageDirectory(String imageDirectory) {
        this.imageDirectory = imageDirectory;
    }
    
    public String copyImage(String sourceImage, int userId) throws IOException{

        File source = new File(sourceImage);

        String imageName = imageName() + Integer.toString(userId);
        
        System.out.println(imageExists());
        if(imageExists() != null && imageExists().length() != 0 ){
            File deleteFile = new File(imageExists());
            System.out.println("I got here");
            if(deleteFile.delete()){
                System.out.println(imageExists() + " // is deleted");
            }
            else{
                System.out.println("No such file to deltet");
            }

        }

        File dest = new File("profileImages\\" + imageName +  ".png");

        //addImage(dest.toString());


        //System.out.println(dest.getAbsolutePath());
        //System.out.println(imageName + userId);

        roundedImage(source.toString(), dest.toString());


        System.out.println(dest.toString());
        return dest.toString();
        // return the new director of the user's image, in oreder to store in the database and show automatically next time....
    }
    
    
    public void roundedImage(String sourcePath, String destPath) throws IOException{

        // Get the BufferedImage object for the image file
        BufferedImage originalImg=ImageIO.read(new File(sourcePath));

        // Get the width,height of the image
        int width=originalImg.getWidth();
        int height=originalImg.getHeight();

        if(height > width){
            height = width;
        }
        else{
            width = height;
        }

        System.out.println(width + " // " + height);

        // Create a new BufferedImage object with the width,height
        // equal to that of the image file
        BufferedImage bim=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);

        // Create a Graphics2D object by using
        // createGraphics() method. This object is
        // used to perform the operation!
        Graphics2D g2=bim.createGraphics();

        // You can also use rendering hints
        // to smooth the edges or the rounded rectangle
        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);

        // This method does it all!. You can clip the
        // image into the shape you wish, play it as you like!

        g2.setBackground(Color.white);
        g2.setClip(new RoundRectangle2D.Double(0, 0, width/1, height/1, width/1, height/1));
        //g2.setClip(new RoundRectangle2D.Double(0,0,width,height,width/1,height/1));

        // Now, draw the image. The image is now
        // in the 'clipped' shape, the shape in the setClip()
        g2.drawImage(originalImg,0,0,null);

        // Dispose it, we no longer need it.
        g2.dispose();

        // Write to a new image file
        ImageIO.write(bim,"PNG",new File(destPath));


    }


    public String imageExists(){

        Users users = new Users();

        User user;
        try {
            user = users.getUserInformation(FSKTMBook.LOGGEDUSER);
            System.out.println(user.getImageDirectory());
            //updateImageDiectory(user);
            return user.getImageDirectory();

        } catch (SQLException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String imageName(){
        
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
        
    }
}
