/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsktmbook.helpers;

/**
 *
 * @author USER
 */
public class Post {
    
    private int id;
    private int userID;
    private String title;
    private String content;
    private int likes;
    private String postDate;
    
    
    public Post(){
        
    }
    
    public Post(int userID, String title, String content, int likes, String postDate){
        
        this.userID = userID;
        this.title = title;
        this.content = content;
        this.likes = likes; 
        this.postDate = postDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = Helper.registeredDateTime();
    }
    
    
}
