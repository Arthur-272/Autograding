package com.example.demo.Posts;

import com.example.demo.Classes.ClassesServices;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class PostsController {

    @Autowired
    private PostsServices postsServices;

    @Autowired
    private ClassesServices classesServices;

    @PostMapping("/user/{userId}/class/{id}/newPost")
    public void addNewPost(@PathVariable long userId, @PathVariable long id, @RequestBody Posts post) throws Exception{
        postsServices.createPost(userId, id, post);
    }

    @PostMapping("/user/{userId}/class/{classId}/post/{postId}/assignProblems")
    public ResponseEntity assignProblems(@PathVariable long userId,
                                         @PathVariable long classId,
                                         @PathVariable long postId,
                                         @RequestBody String string) throws JSONException {
        return postsServices.addProblemsToPost(userId, classId, postId, new JSONObject(string).getJSONArray("ids"));
    }

    @PostMapping("/user/{userId}/class/{classId}/post/{postId}/addUsers")
    public ResponseEntity addUsersToPost(@PathVariable long userId,
                                         @PathVariable long classId,
                                         @PathVariable long postId,
                                         @RequestBody String string) throws JSONException {

        return postsServices.addUsersToPost(userId, classId, postId, new JSONObject(string).getJSONArray("ids"));
    }

    @PostMapping("/user/{userId}/class/{classId}/post/{postId}/removeUsers")
    public ResponseEntity removeUsersFromPost(@PathVariable long userId,
                                              @PathVariable long classId,
                                              @PathVariable long postId,
                                              @RequestBody String string) throws JSONException {
        return postsServices.removeUsersFromPost(userId, classId, postId, new JSONObject(string).getJSONArray("ids"));
    }

    @GetMapping("/user/{userId}/class/{classId}/posts")
    ResponseEntity findAllPostsByUserIdAndClassId(@PathVariable long userId, @PathVariable long classId){
        return postsServices.findAllPostsByUserIdAndClassId(userId, classId);
    }
}
