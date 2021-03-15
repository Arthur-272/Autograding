package com.example.demo.Solutions;

import com.example.demo.Problems.Problems;
import com.example.demo.Problems.ProblemsRepositories;
import com.example.demo.Users.Users;
import com.example.demo.Users.UsersRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public void addSolution(long userId, long problemId, MultipartFile file) throws IOException {
        Problems problem = problemsRepositories.findById(problemId).get();
        Users user = usersRepositories.findById(userId).get();

        /*
        * TODO: JUNIT
        *  */

        int testCasesPassed = 0;
        long score = 0;
        user.setScore(user.getScore() + score);
        Solutions solution = new Solutions(
                file.getBytes(),
                testCasesPassed,
                score,
                problem,
                user

        );

//        Updating the score based on test cases passed
        usersRepositories.save(user);

//        Adding the solution to the database
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

    /**
     * TODO: Think about how to get that particular solution id so that we can update that exact solution.
     * One way is to make that id meaningful rather than just numbers like, 98 (user id) + 1 (problem id) = 981
     * */
    /*public void updateSolution(Solutions solution){
        Solutions previousSolution;
        long previousScore = previousSolution.getScore();

//        Subtracting the previous solution's score from the user and updating it in Solution table
        Users user = solution.getUsers();
        System.out.println(previousScore);
        user.setScore((user.getScore() - previousScore));
        solution.setUsers(user);

        solutionsRepositories.save(solution);
    }*/

}
