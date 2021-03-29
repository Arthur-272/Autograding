package com.example.demo.Solutions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class SolutionsController {

    @Autowired
    private SolutionsServices solutionsServices;

    @RequestMapping(value = "user/{userId}/problems/{problemId}/addSolution")
    public void addSolution(@RequestBody MultipartFile solution, @PathVariable long userId, @PathVariable long problemId) throws Exception {
        solutionsServices.addSolution(userId, problemId, solution);
    }

    @RequestMapping(value="/solutions/view")
    public List<Solutions> getAllSolutions(){
        return solutionsServices.getAllSolutions();
    }

    @RequestMapping(value = "/problem/{problemId}/solutions")
    public List<Solutions> getSolutionsByProblemId(@PathVariable Long problemId){
        return solutionsServices.getSolutionsByProblemId(problemId);
    }

    @RequestMapping(value = "/user/{userId}/Solutions")
    public List<Solutions> getSolutionsByUserId(@PathVariable Long userId){
        return solutionsServices.getSolutionsByUserId(userId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/{userId}/problems/{problemId}/updateSolution")
    public void updateSolution(@PathVariable long userId, @PathVariable long problemId, @RequestBody Solutions solution){
//        solutionsServices.updateSolution(solution);
    }
}
