package com.example.demo.Posts;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepositories extends CrudRepository<Posts, Long> {
}
