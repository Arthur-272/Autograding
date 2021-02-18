package com.example.demo.Users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepositories extends CrudRepository<Users, Long> {
}
