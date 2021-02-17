package com.example.demo.Signup;

import com.example.demo.Users.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignupRepositories extends CrudRepository <Users, String> {
}
