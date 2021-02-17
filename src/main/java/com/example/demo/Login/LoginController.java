package com.example.demo.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    @Autowired
    private LoginServices loginServices;

    @RequestMapping(method = RequestMethod.POST, value="/login")
    public void getUserById(@RequestBody Login user){
        if(loginServices.checkUser(user)){
            System.out.println("Successful");
        }else{
            System.out.println("Unsuccessful");
        }
    }
}
