package com.example.demo.Login;

import com.example.demo.Users.Users;
import org.springframework.data.repository.CrudRepository;

public interface LoginRepositories extends CrudRepository<Users, Integer> {

}
