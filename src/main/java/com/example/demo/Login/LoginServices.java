package com.example.demo.Login;

import com.example.demo.Users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServices {

    @Autowired
    private LoginRepositories loginRepositories;

    public boolean checkUser(Login user) {
        Integer id = user.getId();
        String password = user.getPassword();
        Optional<Users> users =  loginRepositories.findById(id);
        return users.get().getPassword().equals(password);
    }
}
