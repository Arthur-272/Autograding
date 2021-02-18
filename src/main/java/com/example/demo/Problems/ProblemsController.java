package com.example.demo.Problems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProblemsController {

    @Autowired
    private ProblemsServices problemsServices;

    @RequestMapping(method = RequestMethod.POST, value = "/addProblems")
    public void AddProblems(@RequestBody Problems problem){
        problemsServices.add(problem);
    }

    @RequestMapping(value = "/problems/id/{id}")
    public Optional<Problems> showProblemById(@PathVariable Long id){
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
