package com.example.demo.Posts;

import com.example.demo.Classes.ClassesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostsController {

    @Autowired
    private PostsServices postsServices;

    @Autowired
    private ClassesServices classesServices;

    @PostMapping("/user/{userId}/class/{id}/newPost")
    public void addNewPost(@PathVariable long userId, @PathVariable long id, @RequestBody com.example.demo.Posts.Posts post) throws Exception{
        postsServices.createPost(userId, id, post);
    }
}
