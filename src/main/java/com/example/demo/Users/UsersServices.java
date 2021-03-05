package com.example.demo.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServices {
    @Autowired
    private UsersRepositories usersRepositories;

    public void updateUser(Users user){
        usersRepositories.save(user);
    }

    public boolean isTeacher(long id){
        return usersRepositories.findById(id).get().getRole().equals("teacher");
    }

    public boolean isStudent(long id){
        return usersRepositories.findById(id).get().getRole().equals("student");
    }
}
