package com.example.demo.Login;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Login {

    @Id
    private Long id;
    private String email ;
    private String password;

    public String getEmail() {
        return email;
    }

    public Login() {
    }

    public String getPassword() {
        return password;
    }

}
