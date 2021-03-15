package com.example.demo.Posts;

import com.example.demo.Classes.Classes;
import com.example.demo.Classes.ClassesRepositories;
import com.example.demo.Comments.Comments;
import com.example.demo.Users.Users;
import com.example.demo.Users.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostsServices {

    @Autowired
    PostsRepositories postsRepositories;

    @Autowired
    private UsersServices usersServices;

    @Autowired
    private ClassesRepositories classesRepositories;

    public Optional<Posts> findPostById(long id){
        return postsRepositories.findById(id);
    }

    public boolean isOwner(long ownerId, long classId){
        return classesRepositories.findById(classId).get().getOwnerId() == ownerId;
    }

    public void createPost(long userId, long classId, Posts newPost) throws Exception{
        if(isOwner(userId, classId)){
            Classes classes = classesRepositories.findById(classId).get();
            List<Posts> postsInClass = classes.getPosts();

//            Setting the current date to the new Post.
            newPost.setDateAdded(new Date());

//            Setting the list of empty comments on the new post
            List<Comments> comments = new ArrayList<Comments>();
            newPost.setComments(comments);


//            Setting the users concerned to the new Post.
            List<Users> userConcerned = new ArrayList<>();
            newPost.setUsersConcerning(userConcerned);

//            Setting the current user as the owner of the post.
            Users user = usersServices.getUserById(userId);
            newPost.setOwner(user);

//            Adding the new post in the db
            postsRepositories.save(newPost);

//            Adding the post to the list of post already in the class
            postsInClass.add(newPost);
            classes.setPosts(postsInClass);

//            Saving changes to the db
            classesRepositories.save(classes);

        } else{
            throw new Exception("Invalid user accessing the class");
        }
    }
}
