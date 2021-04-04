package com.example.demo.Posts;

import com.example.demo.Classes.ClassesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostsController {

    @Autowired
    private PostsServices postsServices;

    @Autowired
    private ClassesServices classesServices;

    @PostMapping("/user/{userId}/class/{id}/newPost")
    public void addNewPost(@PathVariable long userId, @PathVariable long id, @RequestBody Posts post) throws Exception{
        postsServices.createPost(userId, id, post);
    }

}
