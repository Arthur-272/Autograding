package com.example.demo.Login;

import com.example.demo.Users.Users;
import com.example.demo.Users.UsersRepositories;
import com.example.demo.Users.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class LoginServices {

    @Autowired
    private UsersServices usersServices;

    public void login(Login credentials) throws Exception{
        if(usersServices.checkIfUserExists(credentials.getId())){
            Users userFetchedFromDB = usersServices.getUserById(credentials.getId());
            if(userFetchedFromDB.getPassword().equals(credentials.getPassword())){
                userFetchedFromDB.setLastLoggedInDate(new Date());
                usersServices.save(userFetchedFromDB);
            } else{
                throw new Exception("Invalid Credentials");
            }
        } else{
            throw new Exception("User does not exits");
        }
    }
}
