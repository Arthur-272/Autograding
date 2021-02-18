package com.example.demo.Problems;

import com.example.demo.Problems.Problems;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemsRepositories extends CrudRepository <Problems, Long> {
    List<Problems> findByTitleIgnoreCase(String title);

    List<Problems> findByCategoryIgnoreCase(String category);

    List<Problems> findByDifficultyIgnoreCase(String difficulty);

}
