package com.example.demo.Signup;

import com.example.demo.Users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SignupServices {
    @Autowired
    private SignupRepositories signupRepositories;

    public void addUser(Users users){
        signupRepositories.save(users);
    }

    public List<Users> getUsers(){
        List<Users> list = new ArrayList<Users>();
        signupRepositories.findAll().forEach(list::add);
        return list;
    }
}
