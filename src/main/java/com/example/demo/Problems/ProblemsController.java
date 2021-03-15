package com.example.demo.Problems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
public class ProblemsController {

    @Autowired
    private ProblemsServices problemsServices;

    @RequestMapping(method = RequestMethod.POST, value = "/user/{userId}/addProblems")
    public void addProblems(@PathVariable long userId, @ModelAttribute ProblemsDTO problem) throws Exception{
        problemsServices.addProblem(userId, problem);
    }

    @RequestMapping(value = "/problems/id/{id}")
    public Problems showProblemById(@PathVariable Long id) throws Exception{
        return problemsServices.getProblemById(id);
    }

    @RequestMapping(value = "/problems")
    public List<Problems> showAllProblems(){
        return problemsServices.getAllProblems();
    }

    @RequestMapping(value = "/problems/category/{problemCategory}")
    public List<Problems> showProblemsByCategory(@PathVariable String problemCategory){
        return problemsServices.getProblemByCategory(problemCategory);
    }

    @RequestMapping(value = "/problems/difficulty/{problemDifficulty}")
    public List<Problems> showProblemsByDifficulty(@PathVariable String problemDifficulty){
        return problemsServices.getProblemByDifficulty(problemDifficulty);
    }

    @RequestMapping(value = "/problems/{problemTitle}")
    public List<Problems> showProblemsByTitle(@PathVariable String problemTitle){
        return problemsServices.getProblemByTitle(problemTitle);
    }

}
