package com.example.demo.Signup;

import com.example.demo.Users.Users;
import com.example.demo.Users.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SignupController {

    @Autowired
    private UsersServices usersServices;

    @RequestMapping("/view")
    public List<Users> getUsers(){
        return usersServices.getUsers();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public void add(@RequestBody Users user) throws Exception{
        usersServices.addUser(user);
    }

    @GetMapping("/user/{userId}")
    public Users getUserDetails(@PathVariable long userId){
        return usersServices.getUserById(userId);
    }
}