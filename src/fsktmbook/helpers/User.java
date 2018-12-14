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
    private String firstName;
    private String lastName;
    private String password;
    private String matricNumber;
    private String registeredDate;
    private String about;
    
    public User(){
        
    }
    
    public User(String firstName, String lastName, String password, String matricNumber, String registeredDate, String about){
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.matricNumber = matricNumber;
        this.registeredDate = registeredDate;
        this.about = about;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
