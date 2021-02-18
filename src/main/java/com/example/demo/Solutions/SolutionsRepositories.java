package com.example.demo.Solutions;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SolutionsRepositories extends CrudRepository <Solutions , Long>{

    List<Solutions> findAllByProblems_Id(long problemId);

    List<Solutions> findAllByUsersId(long userId);
}
