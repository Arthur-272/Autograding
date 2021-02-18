package com.example.demo.Signup;

import com.example.demo.Users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SignupController {

    @Autowired
    private SignupServices signupServices;

    @RequestMapping("/view")
    public List<Users> getUsers(){
        return signupServices.getUsers();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public void add(@RequestBody Users user){
        signupServices.addUser(user);
    }

}