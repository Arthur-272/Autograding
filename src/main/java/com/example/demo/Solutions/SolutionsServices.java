package com.example.demo.Solutions;

import com.example.demo.Problems.Problems;
import com.example.demo.Problems.ProblemsRepositories;
import com.example.demo.Users.Users;
import com.example.demo.Users.UsersRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SolutionsServices {

    @Autowired
    private SolutionsRepositories solutionsRepositories;

    @Autowired
    private ProblemsRepositories problemsRepositories;

    @Autowired
    private UsersRepositories usersRepositories;

    public void addSolution(long userId, long problemId, Solutions solution) {
        Problems problem = problemsRepositories.findById(problemId).get();
        Users user = (Users) usersRepositories.findById(userId).get();

        solution.setUsers(user);
        solution.setProblems(problem);
        solutionsRepositories.save(solution);
    }

    public List<Solutions> getAllSolutions() {
        List<Solutions> list = new ArrayList<Solutions>();
        solutionsRepositories.findAll().forEach(list::add);
        return list;
    }

    public List<Solutions> getSolutionsByProblemId(Long problemId) {
        return solutionsRepositories.findAllByProblems_Id(problemId);
    }

    public List<Solutions> getSolutionsByUserId(Long userId){
        return solutionsRepositories.findAllByUsersId(userId);
    }
}
