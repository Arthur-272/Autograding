package com.example.demo.Comments;

import com.example.demo.Posts.Posts;
import com.example.demo.Users.Users;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String comment;
    private Date dateAdded;

    @ManyToOne
    private Users user;

    public Comments() {
    }

    public Comments(long id, String comment, Users user, Date dateAdded) {
        this.id = id;
        this.comment = comment;
        this.dateAdded = dateAdded;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
