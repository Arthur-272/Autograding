package com.example.demo.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsersServices {
    @Autowired
    private UsersRepositories usersRepositories;

    public void updateUser(long id, Users user) throws Exception{
        if(checkIfUserExists(id)){
//            The following fields cannot be changed by user.
            Users userFetchedFromDB = usersRepositories.findById(id).get();
            user.setPassword(userFetchedFromDB.getPassword());
            user.setRole(userFetchedFromDB.getRole());
            user.setScore(userFetchedFromDB.getScore());
            usersRepositories.save(user);

        }
        else {
            throw new Exception("User not found!");
        }
    }

    public boolean checkIfUserExists(long id){
        Optional<Users> list = usersRepositories.findById(id);
        if(list.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean isTeacher(long id){
        return usersRepositories.findById(id).get().getRole().equals("teacher");
    }

    public boolean isStudent(long id){
        return usersRepositories.findById(id).get().getRole().equals("student");
    }

    public Users getUserById(long id){
        return usersRepositories.findById(id).get();
    }

    public void save(Users user){
        usersRepositories.save(user);
    }

    public void addUser(Users user) throws Exception{
        Optional<Users> list = usersRepositories.findByEmail(user.getEmail());
        if(list.isEmpty()){
            user.setRegisteredDate(new Date());
            usersRepositories.save(user);
        } else{
            throw new Exception("User already exists");
        }
    }

    public List<Users> getUsers(){
        List<Users> list = new ArrayList<Users>();
        usersRepositories.findAll().forEach(list::add);
        return list;
    }
}
