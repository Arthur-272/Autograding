package com.example.demo.Solutions;

import com.example.demo.Problems.Problems;
import com.example.demo.Users.Users;

import javax.persistence.*;

@Entity
public class Solutions {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String solution;
    private int testCasesPassed;
    private int score;
    private String langUsed;

    @ManyToOne
    private Problems problems;

    @ManyToOne
    private Users users;

    public Solutions(long id, String solution, int testCasesPassed, int score, String langUsed, Problems problems, Users users) {
        this.id = id;
        this.solution = solution;
        this.testCasesPassed = testCasesPassed;
        this.score = score;
        this.langUsed = langUsed;
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

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public int getTestCasesPassed() {
        return testCasesPassed;
    }

    public void setTestCasesPassed(int testCasesPassed) {
        this.testCasesPassed = testCasesPassed;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getLangUsed() {
        return langUsed;
    }

    public void setLangUsed(String langUsed) {
        this.langUsed = langUsed;
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
}
