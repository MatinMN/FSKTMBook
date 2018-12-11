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
public class User {
    private String userName;
    private String name;
    private String lastName;
    private String password;
    private String about;
    private String matricNumber;
    private String registeredDate;
    
    
    public User(String userName, String name, String lastName, String password, String about, String matricNumber, String registeredDate){
        this.userName = userName;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.about = about;
        this.matricNumber = matricNumber;
        this.registeredDate = registeredDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getMatricNumber() {
        return matricNumber;
    }

    public void setMatricNumber(String matricNumber) {
        this.matricNumber = matricNumber;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }
    
    
    
}
