package com.example.demo.Posts;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepositories extends CrudRepository<Posts, Long> {

    @Query(value = "select *from Posts where Posts.classes_id=:classId and Posts.id in (select posts_users_concerning.posts_id from posts_users_concerning where posts_users_concerning.users_concerning_id=:userId)", nativeQuery = true)
    List<Posts> findAllPostsByUserIdAndClassId(long userId, long classId);
}
