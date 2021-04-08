package com.example.demo.Posts;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepositories extends CrudRepository<Posts, Long> {

    @Query(value = "select *from Posts where Posts.classes_id=:classId", nativeQuery = true)
    List<Posts> findPostsByClassId(long classId);
}
