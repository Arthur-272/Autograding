package com.example.demo.Users;

import com.example.demo.Classes.Classes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;


@Entity
public class Users {

    @Id
    private long id;
    private String firstName;
    private String lastName;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String mobile;
    private long score;
    private String role;
    @JsonIgnore
    private Date registeredDate;
    private Date lastLoggedInDate;

    public Users(){

    }

    public Users(long id, String firstName, String lastName, String password, String mobile, long score, String role, Date registeredDate, Date lastLoggedInDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.mobile = mobile;
        this.score = score;
        this.role = role;
        this.registeredDate = registeredDate;
        this.lastLoggedInDate = lastLoggedInDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Date getLastLoggedInDate() {
        return lastLoggedInDate;
    }

    public void setLastLoggedInDate(Date lastLoggedInDate) {
        this.lastLoggedInDate = lastLoggedInDate;
    }
}
