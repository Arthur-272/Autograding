package com.example.demo.Posts;

import com.example.demo.Classes.Classes;
import com.example.demo.Classes.ClassesRepositories;
import com.example.demo.Classes.ClassesServices;
import com.example.demo.Comments.Comments;
import com.example.demo.Comments.CommentsServices;
import com.example.demo.Users.Users;
import com.example.demo.Users.UsersServices;
import org.json.JSONArray;
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
    private CommentsServices commentsServices;

    @Autowired
    private ClassesServices classesServices;

    public Optional<Posts> findPostById(long id){
        return postsRepositories.findById(id);
    }

    public void createPost(long userId, long classId, Posts newPost) throws Exception{
        if(classesServices.getAllTeachersByClassId(classId).contains(usersServices.getUserById(userId))){
            Classes classes = classesServices.findById(classId);
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
            classesServices.updateClass(classes);

        } else{
            throw new Exception("Invalid user accessing the class");
        }
    }

    public void addConcerningUsers(long userId, long classId, long id, JSONArray usersIds) throws Exception{
        if(postsRepositories.findById(id).get().getClasses().getId() == classId){
            if(postsRepositories.findById(id).get().getOwner().getId() == userId){
                List<Users> userConcerned = postsRepositories.findById(id).get().getUsersConcerning();
                for(int i=0;i< usersIds.length();i++){
                    long inListUserId = usersIds.getLong(i);
                    if(!userConcerned.contains(usersServices.getUserById(inListUserId))){
                        userConcerned.add(usersServices.getUserById(inListUserId));
                    } else{
                        System.out.println("User already added in the posts");
                    }
                }
                postsRepositories.findById(id).get().setUsersConcerning(userConcerned);
            } else{
                throw new Exception("You are not permitted to do changes this post");
            }
        } else{
            throw new Exception("Post_" + id + " does not exist in this class");
        }
    }

    public void deleteById(long id) {
        List<Comments> comments = postsRepositories.findById(id).get().getComments();
        for(Comments comment : comments){
            commentsServices.deleteById(comment.getId());
        }
        postsRepositories.deleteById(id);
    }
}
