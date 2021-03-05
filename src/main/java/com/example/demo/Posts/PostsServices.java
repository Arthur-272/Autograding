package com.example.demo.Posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostsServices {

    @Autowired
    PostsRepositories postsRepositories;

    public void addPost(Posts post){
        postsRepositories.save(post);
    }
}
