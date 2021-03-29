package com.example.demo.TestCases;

import com.example.demo.Problems.Problems;
import com.example.demo.Problems.ProblemsRepositories;
import com.example.demo.Problems.ProblemsServices;
import com.example.demo.Users.Users;
import com.example.demo.Users.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestCasesServices {

    @Autowired
    private UsersServices usersServices;

    @Autowired
    private TestCasesRepository testCasesRepository;

    @Autowired
    private ProblemsServices problemsServices;

    @Autowired
    private ProblemsRepositories problemsRepositories;

    public ResponseEntity addTestCases(Long userId, Long problemId, List<TestCases> testCases) throws  Exception{
        Users user = usersServices.getUserById(userId);
        if(user != null){
            Problems problem = problemsServices.getProblemById(problemId);
            if(problem != null){
                if(problem.getAuthor().equals(user)){
                    List<TestCases> list = problem.getTestCases();

                    if(testCases.size() != problem.getNumOfTestCases())
                        return ResponseEntity.badRequest().build();

                    for(TestCases testCase : testCases){
                        testCase.setProblem(problem);
                        testCasesRepository.save(testCase);
                    }
                    list.addAll(testCases);
                    problem.setTestCases(list);

                    problemsRepositories.save(problem);
                    return ResponseEntity.accepted().build();
                } else{
                    return ResponseEntity.badRequest().build();
                }
            } else{
                return ResponseEntity.notFound().build();
            }
        } else{
            return ResponseEntity.notFound().build();
        }
    }
}
