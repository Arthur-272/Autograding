package com.example.demo.Problems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProblemsServices {

    @Autowired
    private ProblemsRepositories problemsRepositories;

    public void add(Problems problem) {
        problemsRepositories.save(problem);
    }

    public Optional<Problems> getProblemById(Long id) {
        return problemsRepositories.findById(id);
    }

    public List<Problems> getAllProblems() {
        List<Problems> list = new ArrayList<>();
        problemsRepositories.findAll().forEach(list::add);
        return list;
    }

    public List<Problems> getProblemByCategory(String problemCategory) {
        List<Problems> list = new ArrayList<>();
        problemsRepositories.findByCategoryIgnoreCase(problemCategory).forEach(list::add);
        return list;
    }

    public List<Problems> getProblemByTitle(String problemTitle){
        List<Problems> list = new ArrayList<>();
        problemsRepositories.findByTitleIgnoreCase(problemTitle).forEach(list::add);
        return list;
    }

    public List<Problems> getProblemByDifficulty(String problemDifficulty){
        List<Problems> list = new ArrayList<>();
        problemsRepositories.findByDifficultyIgnoreCase(problemDifficulty).forEach(list::add);
        return list;
    }




    /*FIXME: Need to provide unique problem id that was generated while adding the problem. But the question is how to get that id, cause no user is gonna note down each and every problem id.
    Also you don't require a user add a problem, we can add that constraint if it helps.*/
    public void updateProblem(Problems problem){
        problemsRepositories.save(problem);
    }
}
