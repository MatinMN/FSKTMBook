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
public class Voucher {
    
    int id;
    int userId;
    String releaseDate;
    String type;
    int amount;
    
    
    
    public Voucher(){
        
    }
    
    public Voucher(int userId, String releaseDate, String type, int amount){
        this.userId = userId;
        this.releaseDate = releaseDate;
        this.type = type;
        this.amount = amount;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
