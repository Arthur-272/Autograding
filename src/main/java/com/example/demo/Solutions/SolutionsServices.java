package com.example.demo.Solutions;

import com.example.demo.Problems.Problems;
import com.example.demo.Problems.ProblemsRepositories;
import com.example.demo.TestCases.TestCases;
import com.example.demo.Users.Users;
import com.example.demo.Users.UsersRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@Service
public class SolutionsServices {

    @Autowired
    private SolutionsRepositories solutionsRepositories;

    @Autowired
    private ProblemsRepositories problemsRepositories;

    @Autowired
    private UsersRepositories usersRepositories;

    public ResponseEntity addSolution(long userId, long problemId, MultipartFile file, String language) throws Exception {

        Optional<Users> user = usersRepositories.findById(userId);
        if (user.isPresent()) {
            Optional<Problems> problem = problemsRepositories.findById(problemId);
            if (problem.isPresent()) {

                List<Solutions> solutions = solutionsRepositories.findAllSolutionsByUserIdAndProblemId(userId, problemId);
                int maxTestCasesPassed = 0;

                if (solutions != null) {
                    for (Solutions solution : solutions) {
                        if (solution.getTestCasesPassed() > maxTestCasesPassed)
                            maxTestCasesPassed = solution.getTestCasesPassed();
                    }
                }

                if (maxTestCasesPassed == problem.get().getNumOfTestCases()) {
                    return ResponseEntity.badRequest().build();
                }


                List<TestCases> testCases = problem.get().getTestCases();
                if (testCases == null)
                    return ResponseEntity.noContent().build();

                String currentProjectDirectory = getCurrentProjectDirectory();
                currentProjectDirectory += "\\src\\main\\resources\\Submissions\\";
                File userSolution = new File(currentProjectDirectory + file.getOriginalFilename());
                file.transferTo(userSolution);
                int totalTestCases = problem.get().getNumOfTestCases();
                int testCasesPassed = 0;
                switch (language) {
                    case "python3":
                        testCasesPassed = evaluatePython(testCases, userSolution, currentProjectDirectory, 3);
                        break;
                    case "python2":
                        testCasesPassed = evaluatePython(testCases, userSolution, currentProjectDirectory, 2);
                        break;
                    case "java":
                        testCasesPassed = evaluateJava(testCases, userSolution, currentProjectDirectory);
                        break;
                    case "C":
                        testCasesPassed = evaluateC(testCases, userSolution, currentProjectDirectory);
                        break;
                    case "CPP":
                        testCasesPassed = evaluateCPP(testCases, userSolution, currentProjectDirectory);
                        break;

                }

                /**
                 * Subtracting the score that the user scored in his previous solution and if it's user's
                 * first solution then it subtract 0, affecting nothing.
                 * */
                int testCasesFailed = totalTestCases - testCasesPassed;
                long scorePerTestCase = (long) ((problem.get().getScore()) / totalTestCases);
                long usersPreviousScore = maxTestCasesPassed * scorePerTestCase;
                long usersCurrentScore = testCasesPassed * scorePerTestCase;
                long score = 0;
                if (usersCurrentScore > usersPreviousScore)
                    score = usersCurrentScore;
                else
                    score = usersPreviousScore;

                user.get().setScore(user.get().getScore() + (score - usersPreviousScore));
                Solutions solution = new Solutions(
                        file.getBytes(),
                        testCasesPassed,
                        testCasesFailed,
                        usersCurrentScore,
                        language,
                        problem.get(),
                        user.get()
                );
                usersRepositories.save(user.get());
                solutionsRepositories.save(solution);

                return ResponseEntity.accepted().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public int compileAndRun(String cmd, String output) throws Exception{
        Process process;
        int count = 0;
        try {
            process = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String userOutput = "";
            String line;
            while ((line = reader.readLine()) != null) {
                userOutput += line;
            }

            reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            try {
//                    System.out.println(userOutput + " " + output);
                assertEquals(userOutput, output);
                count++;
            } catch (AssertionError ae) {
                System.out.println("Failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    private int evaluateC(List<TestCases> testCases, File file, String currentProjectDirectory) throws Exception{
        int count = 0;
        String cmd = "cmd /c cd " + currentProjectDirectory + " && gcc " + file.getName();
        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor();
        for (TestCases testCase : testCases) {
            String input = testCase.getInput();
            String output = testCase.getOutput();

            cmd = "cmd /c cd " + currentProjectDirectory;
            if(System.getProperty("os.name").toLowerCase().contains("windows"))
                cmd += " && a.exe " + input;
            else if(System.getProperty("os.name").toLowerCase().contains("linux"))
                cmd += " && ./a.out " + input;
            count += compileAndRun(cmd, output);
        }

        file.delete();
        if(System.getProperty("os.name").toLowerCase().contains("windows"))
            file = new File(currentProjectDirectory + "a.exe");
        else if(System.getProperty("os.name").toLowerCase().contains("linux"))
            file = new File(currentProjectDirectory + "a.out");
        file.delete();

        return count;
    }

    private int evaluateCPP(List<TestCases> testCases, File file, String currentProjectDirectory) throws Exception{
        System.out.println("In C++");
        int count = 0;
        String cmd = "cmd /c cd " + currentProjectDirectory + " && g++ -std=c++11 " + file.getName();
        System.out.println(cmd);
        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor();
        for (TestCases testCase : testCases) {
            String input = testCase.getInput();
            String output = testCase.getOutput();

            cmd = "cmd /c cd " + currentProjectDirectory;
            if(System.getProperty("os.name").toLowerCase().contains("windows"))
                cmd += " && a.exe " + input;
            else if(System.getProperty("os.name").toLowerCase().contains("linux"))
                cmd += " && ./a.out " + input;
            System.out.println(cmd);
            count += compileAndRun(cmd, output);
        }

        file.delete();
        if(System.getProperty("os.name").toLowerCase().contains("windows"))
            file = new File(currentProjectDirectory + "a.exe");
        else if(System.getProperty("os.name").toLowerCase().contains("linux"))
            file = new File(currentProjectDirectory + "a.out");
        file.delete();

        return count;
    }

    public int evaluateJava(List<TestCases> testCases, File file, String currentProjectDirectory) throws Exception {
        int count = 0;
        String cmd = "cmd /c javac " + file.getAbsolutePath();
        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor();
        for (TestCases testCase : testCases) {
            String input = testCase.getInput();
            String output = testCase.getOutput();

            cmd = "cmd /c cd " + currentProjectDirectory;
            cmd += " && java " + file.getName().split("\\.")[0] + " " + input;
            count += compileAndRun(cmd, output);
        }

        file.delete();
        file = new File(currentProjectDirectory + file.getName().split("\\.")[0] + ".class");
        file.delete();
        return count;
    }

    public int evaluatePython(List<TestCases> testCases, File file, String currentProjectDirectory, int pythonVersion) throws Exception {
        int count = 0;
        String cmd = "";
        for (TestCases testCase : testCases) {
            String input = testCase.getInput();
            String output = testCase.getOutput();
            cmd = "cmd /c cd " + currentProjectDirectory;
            if(pythonVersion == 2)
                cmd += " && python2 " + file.getName() + " " + input;
            else if(pythonVersion == 3)
                cmd += " && python " + file.getName() + " " + input;
            count += compileAndRun(cmd, output);
        }
        file.delete();
        return count;
    }

    public String getCurrentProjectDirectory() throws Exception {
        return System.getProperty("user.dir");
    }

    public List<Solutions> getAllSolutions() {
        List<Solutions> list = new ArrayList<Solutions>();
        solutionsRepositories.findAll().forEach(list::add);
        return list;
    }

    public List<Solutions> getSolutionsByProblemId(Long problemId) {
        return solutionsRepositories.findAllByProblems_Id(problemId);
    }

    public List<Solutions> getSolutionsByUserId(Long userId) {
        return solutionsRepositories.findAllByUsersId(userId);
    }

    public void deleteSolutionsByProblemId(Long problemId) {
        solutionsRepositories.deleteSolutionsByProblemId(problemId);
    }

    /**
     * We might not need this, instead we can use the addSolution itself.
     */
    /*public ResponseEntity updateSolution(Long userId, Long problemId, Long solutionId, MultipartFile file) throws Exception {
        Optional<Users> user = usersRepositories.findById(userId);
        if (user.isPresent()) {
            Optional<Problems> problem = problemsRepositories.findById(problemId);
            if (problem.isPresent()) {
                Optional<Solutions> previousSolution = solutionsRepositories.findById(solutionId);
                if (previousSolution.isPresent()) {
                    long previousScore = previousSolution.get().getScore();
                    user.get().setScore((user.get().getScore() - previousScore));

                    List<TestCases> testCases = problem.get().getTestCases();
                    if (testCases == null)
                        return ResponseEntity.noContent().build();

                    String currentProjectDirectory = getCurrentProjectDirectory();
                    currentProjectDirectory += "\\src\\main\\resources\\Submissions\\";
                    File userSolution = new File(currentProjectDirectory + file.getOriginalFilename());
                    file.transferTo(userSolution);
                    int totalTestCases = problem.get().getNumOfTestCases();
                    int testCasesPassed = evaluateJava(testCases, userSolution, currentProjectDirectory);
                    int testCasesFailed = totalTestCases - testCasesPassed;
                    long score = (long) (((problem.get().getScore()) / totalTestCases) * testCasesPassed);
                    user.get().setScore(user.get().getScore() + score);
                    Solutions solution = new Solutions(
                            file.getBytes(),
                            testCasesPassed,
                            testCasesFailed,
                            score,
                            problem.get(),
                            user.get()
                    );
                    solution.setId(solutionId);
                    usersRepositories.save(user.get());
                    solutionsRepositories.save(solution);

                    return ResponseEntity.accepted().build();
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/
}
