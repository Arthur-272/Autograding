package com.example.demo.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UsersController {
    @Autowired
    private UsersServices usersServices;

    @PostMapping("/user/{id}/updateProfile")
    public void updateProfile(@PathVariable long id, Users user) throws Exception{
        usersServices.updateUser(id, user);
    }

    @GetMapping("/leaderboard")
    public Optional<List<Users>> getLeaderboard(){
        return usersServices.getUsersByHighestScore();
    }
}
