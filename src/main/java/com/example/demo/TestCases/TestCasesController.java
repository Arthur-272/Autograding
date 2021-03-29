package com.example.demo.TestCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestCasesController {

    @Autowired
    private TestCasesServices testCasesServices;

    @PostMapping("/user/{userId}/problem/{problemId}/addTestCases")
    public ResponseEntity addTestCases(@RequestBody List<TestCases> testCases,
                                       @PathVariable Long userId,
                                       @PathVariable Long problemId) throws  Exception{
        return testCasesServices.addTestCases(userId, problemId, testCases);
    }
}
