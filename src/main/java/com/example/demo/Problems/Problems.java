package com.example.demo.Problems;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Problems {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String statement;
    private String title;
    private String solution;
    private double score;
    private int numOfTestCases;
    private String testCasesFile;
    private String category;
    private String difficulty;
//    private Date problemDate;

    public Problems() {}
    
    public Problems(long id, String statement, String title, String solution, double score, int numOfTestCases, String testCasesFile, String category, String difficulty) {
        this.id = id;
        this.statement = statement;
        this.title = title;
        this.solution = solution;
        this.score = score;
        this.numOfTestCases = numOfTestCases;
        this.testCasesFile = testCasesFile;
        this.category = category;
        this.difficulty = difficulty;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String problemStatement) {
        this.statement = problemStatement;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String problemTitle) {
        this.title = problemTitle;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String problemSolution) {
        this.solution = problemSolution;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double problemScore) {
        this.score = problemScore;
    }

    public int getNumOfTestCases() {
        return numOfTestCases;
    }

    public void setNumOfTestCases(int numOfTestCases) {
        this.numOfTestCases = numOfTestCases;
    }

    public String getTestCasesFile() {
        return testCasesFile;
    }

    public void setTestCasesFile(String testCasesFile) {
        this.testCasesFile = testCasesFile;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String problemCategory) {
        this.category = problemCategory;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String problemDifficulty) {
        this.difficulty = problemDifficulty;
    }
}
