package com.example.demo.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {
    @Autowired
    private UsersServices usersServices;

    @PostMapping("/user/{id}/updateProfile")
    public void updateProfile(@PathVariable long id, Users user) throws Exception{
        usersServices.updateUser(id, user);
    }
}
