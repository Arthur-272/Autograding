package com.example.demo.Problems;

import com.example.demo.Problems.Problems;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemsRepositories extends CrudRepository <Problems, Long> {
}
