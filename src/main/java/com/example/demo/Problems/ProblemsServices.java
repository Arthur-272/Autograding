package com.example.demo.Problems;

import com.example.demo.Users.UsersRepositories;
import com.example.demo.Users.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProblemsServices {

    @Autowired
    private ProblemsRepositories problemsRepositories;

    @Autowired
    private UsersServices usersServices;

    public void addProblem(long userId, ProblemsDTO problemDTO) throws Exception{

        if(usersServices.checkIfUserExists(userId)) {

            Problems problem = new Problems(
                    problemDTO.getStatement(),
                    problemDTO.getTitle(),
                    problemDTO.getSolution().getBytes(),
                    problemDTO.getScore(),
                    problemDTO.getNumOfTestCases(),
                    problemDTO.getTestCasesFile().getBytes(),
                    problemDTO.getCategory(),
                    problemDTO.getDifficulty()
            );
            problem.setProblemDate(new Date());
            problem.setAuthor(usersServices.getUserById(userId));

            problemsRepositories.save(problem);
        }else{
            throw new Exception("User does not exists...");
        }
    }

    public Problems getProblemById(Long id) throws Exception{
        Problems problem;
        if(checkIfProblemExists(id)){
            problem = problemsRepositories.findById(id).get();
        } else{
            throw new Exception("Problems does not exists...");
        }
        return problem;
    }

    private boolean checkIfProblemExists(Long id) {
        if(problemsRepositories.findById(id).isPresent()) {
            return true;
        }
        else{
            return false;
        }
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

//    public void deleteByUserId(long userId){
//        problemsRepositories.();
//    }

    public void deleteProblemByAuthorId(String id){
        problemsRepositories.deleteProblemByAuthorId(id);
    }
}
