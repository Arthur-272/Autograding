package com.example.demo.Solutions;

import com.example.demo.Problems.Problems;
import com.example.demo.Users.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.IOException;

@Entity
public class Solutions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Users users;
    private int testCasesPassed;
    private int testCasesFailed;
    private long score;

    @ManyToOne
    @JsonIgnore
    private Problems problems;

    @Lob
    @JsonIgnore
    private byte[] solution;


    public Solutions(byte[] solution, int testCasesPassed, int testCasesFailed,long score, Problems problems, Users users) throws IOException {
        this.solution = solution;
        this.testCasesPassed = testCasesPassed;
        this.testCasesFailed = testCasesFailed;
        this.score = score;
        this.problems = problems;
        this.users = users;
    }

    public Solutions() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getSolution() {
        return solution;
    }

    public void setSolution(byte[] solution) {
        this.solution = solution;
    }

    public int getTestCasesPassed() {
        return testCasesPassed;
    }

    public void setTestCasesPassed(int testCasesPassed) {
        this.testCasesPassed = testCasesPassed;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public Problems getProblems() {
        return problems;
    }

    public void setProblems(Problems problems) {
        this.problems = problems;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public int getTestCasesFailed() {
        return testCasesFailed;
    }

    public void setTestCasesFailed(int testCasesFailed) {
        this.testCasesFailed = testCasesFailed;
    }
}
