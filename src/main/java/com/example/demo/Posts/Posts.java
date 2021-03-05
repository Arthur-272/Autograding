package com.example.demo.Posts;

import com.example.demo.Comments.Comments;
import com.example.demo.Users.Users;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private Date dateAdded;

    @ManyToMany
    private List<Users> usersConcerning;

    @OneToMany
    private List<Comments> comments;

    public Posts() {

    }

    public Posts(long id, String title, String description, Date dateAdded, List<Users> usersConcerning, List<Comments> comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateAdded = dateAdded;
        this.usersConcerning = usersConcerning;
        this.comments = comments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public List<Users> getUsersConcerning() {
        return usersConcerning;
    }

    public void setUsersConcerning(List<Users> usersConcerning) {
        this.usersConcerning = usersConcerning;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }
}
