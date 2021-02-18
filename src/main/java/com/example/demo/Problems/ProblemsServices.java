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
        List<Problems> list = new ArrayList<Problems>();
        problemsRepositories.findAll().forEach(list::add);
        return list;
    }
}
