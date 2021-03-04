package com.example.demo.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {
    @Autowired
    private LoginServices loginServices;

    @RequestMapping(method = RequestMethod.POST, value="/login")
    public ResponseEntity.BodyBuilder getUserById(@RequestBody Login user, HttpServletRequest request){
        if(loginServices.checkUser(user)){
            System.out.println("Success");
            return ResponseEntity.status(200);
        }else{
            System.out.println("Fail");
            return ResponseEntity.status(404);
        }
    }
}
