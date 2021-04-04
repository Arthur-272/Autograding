package com.example.demo.Login;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Login {

    @Id
    private long id;
    private String password;

    public long getId() {
        return id;
    }

    public Login() {
    }

    public String getPassword() {
        return password;
    }

}
