package com.example.demo.Posts;

import com.example.demo.Classes.Classes;
import com.example.demo.Users.Users;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.List;

@Entity
public class Posts {
    @Id
    private long id;
    private String title;
    private String description;
    //    private Date dateAdded;

    @ManyToOne
    private Classes classes;

    @ManyToMany
    private List<Users> usersConcerning;

    public Posts() {

    }

    public Posts(long id, String title, String description, Classes classes, List<Users> usersConcerning) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.classes = classes;
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

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public List<Users> getUsersConcerning() {
        return usersConcerning;
    }

    public void setUsersConcerning(List<Users> usersConcerning) {
        this.usersConcerning = usersConcerning;
    }
}
