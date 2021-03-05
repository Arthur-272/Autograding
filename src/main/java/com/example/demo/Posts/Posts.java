package com.example.demo.Posts;

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

    public Posts() {

    }

    public Posts(long id, String title, String description, Date dateAdded, List<Users> usersConcerning) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateAdded = dateAdded;
        this.usersConcerning = usersConcerning;
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
}
