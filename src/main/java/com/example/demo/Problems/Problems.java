package com.example.demo.Problems;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Problems {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String problemStatement;
    private String problemTitle;
    private String problemSolution;
    private double problemScore;
    private int numOfTestCases;
    private String testCasesFile;
    private String problemCategory;
    private String problemDifficulty;
//    private Date problemDate;

    public Problems() {}
    
    public Problems(long id, String problemStatement, String problemTitle, String problemSolution, double problemScore, int numOfTestCases, String testCasesFile, String problemCategory, String problemDifficulty) {
        this.id = id;
        this.problemStatement = problemStatement;
        this.problemTitle = problemTitle;
        this.problemSolution = problemSolution;
        this.problemScore = problemScore;
        this.numOfTestCases = numOfTestCases;
        this.testCasesFile = testCasesFile;
        this.problemCategory = problemCategory;
        this.problemDifficulty = problemDifficulty;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProblemStatement() {
        return problemStatement;
    }

    public void setProblemStatement(String problemStatement) {
        this.problemStatement = problemStatement;
    }

    public String getProblemTitle() {
        return problemTitle;
    }

    public void setProblemTitle(String problemTitle) {
        this.problemTitle = problemTitle;
    }

    public String getProblemSolution() {
        return problemSolution;
    }

    public void setProblemSolution(String problemSolution) {
        this.problemSolution = problemSolution;
    }

    public double getProblemScore() {
        return problemScore;
    }

    public void setProblemScore(double problemScore) {
        this.problemScore = problemScore;
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

    public String getProblemCategory() {
        return problemCategory;
    }

    public void setProblemCategory(String problemCategory) {
        this.problemCategory = problemCategory;
    }

    public String getProblemDifficulty() {
        return problemDifficulty;
    }

    public void setProblemDifficulty(String problemDifficulty) {
        this.problemDifficulty = problemDifficulty;
    }
}
