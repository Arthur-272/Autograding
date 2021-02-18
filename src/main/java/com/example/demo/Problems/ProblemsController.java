package com.example.demo.Problems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProblemsController {

    @Autowired
    private ProblemsServices problemsServices;

    @RequestMapping(method = RequestMethod.POST, value = "/AddProblems")
    public void AddProblems(@RequestBody Problems problem){
        problemsServices.add(problem);
    }

    @RequestMapping(value = "/Problems/{id}")
    public Optional<Problems> showProblem(@PathVariable Long id){
        return problemsServices.getProblemById(id);
    }

    @RequestMapping(value = "/Problems")
    public List<Problems> showProblems(){
        return problemsServices.getAllProblems();
    }

}
