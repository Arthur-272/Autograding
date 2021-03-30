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

    /*public void addSolution(long userId, long problemId, MultipartFile file) throws Exception {
        Problems problem = problemsRepositories.findById(problemId).get();
        Users user = usersRepositories.findById(userId).get();


        byte[] data = problem.getTestCasesFile();
        String currentProjectDirectory = getCurrentProjectDirectory();
        currentProjectDirectory += "\\src\\main\\resources\\Submissions\\";
        File problemFile = new File(currentProjectDirectory + "test" + file.getOriginalFilename());
//        System.out.println(problemFile.getAbsoluteFile());
        if(!problemFile.exists())
            problemFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(problemFile);
        fos.write(data);
        fos.close();

        File userSolution = new File(currentProjectDirectory + file.getOriginalFilename());
        file.transferTo(userSolution);

        int testCasesFailed = evaluate(file.getOriginalFilename());
        int testCasesPassed = problem.getNumOfTestCases() - testCasesFailed;
        long score = testCasesPassed * 2;
        user.setScore(user.getScore() + score);
        Solutions solution = new Solutions(
                file.getBytes(),
                testCasesPassed,
                score,
                problem,
                user

        );

        File toBeDeleted = new File(currentProjectDirectory + "test" + file.getOriginalFilename());
        toBeDeleted.delete();
        toBeDeleted = new File(currentProjectDirectory + file.getOriginalFilename());
        toBeDeleted.delete();
        toBeDeleted = new File(currentProjectDirectory + "test" + file.getOriginalFilename().split("\\.")[0] + ".class");
        toBeDeleted.delete();
        toBeDeleted = new File(currentProjectDirectory + file.getOriginalFilename().split("\\.")[0] + ".class");
        toBeDeleted.delete();

//        Updating the score based on test cases passed
        usersRepositories.save(user);

//        Adding the solution to the database
        solutionsRepositories.save(solution);
    }*/

    public ResponseEntity addSolution(long userId, long problemId, MultipartFile file) throws Exception {

        Optional<Users> user = usersRepositories.findById(userId);
        if (user.isPresent()) {
            Optional<Problems> problem = problemsRepositories.findById(problemId);
            if (problem.isPresent()) {

                List<TestCases> testCases = problem.get().getTestCases();
                if (testCases == null)
                    return ResponseEntity.noContent().build();

                String currentProjectDirectory = getCurrentProjectDirectory();
                currentProjectDirectory += "\\src\\main\\resources\\Submissions\\";
                File userSolution = new File(currentProjectDirectory + file.getOriginalFilename());
                file.transferTo(userSolution);
                int totalTestCases = problem.get().getNumOfTestCases();
                int testCasesPassed = evaluate(testCases, userSolution, currentProjectDirectory);
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

    public int evaluate(List<TestCases> testCases, File file, String currentProjectDirectory) throws Exception {
        int count = 0;
        String cmd = "cmd /c javac " + file.getAbsolutePath();
        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor();
        for (TestCases testCase : testCases) {
            String input = testCase.getInput();
            String output = testCase.getOutput();

            cmd = "cmd /c cd " + currentProjectDirectory;
            cmd += " && java " + file.getName().split("\\.")[0] + " " + input;
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
        }

        file.delete();
        file = new File(currentProjectDirectory + file.getName().split("\\.")[0] + ".class");
        file.delete();
        return count;
    }

    public String getCurrentProjectDirectory() throws Exception {
        String cmd = "cmd /c cd";
        Process process = Runtime.getRuntime().exec("cmd /c cd");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        String dir = "";
        while ((line = reader.readLine()) != null) {
            dir += line;
        }
        return dir;
    }

    /*public int evaluate(String fileName) throws Exception{
        int count = 0;
        String path = getCurrentProjectDirectory() + "\\src\\main\\resources\\Submissions\\";
        StringBuilder cmd = new StringBuilder();
        cmd.append("cmd /c ");
        cmd.append("javac " + path + "TestRunner.java ");
        cmd.append(path + "test"+fileName+ " ");
        cmd.append(path + fileName);

        String file = fileName.split("\\.")[0];
        cmd.append(" && cd " + path + " && java TestRunner test" + file);
//        System.out.println(cmd.toString());
        Process process = Runtime.getRuntime().exec(cmd.toString());

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        count = Integer.parseInt(reader.readLine());

        String line;
        *//*while ((line = reader.readLine()) != null) {
            System.out.println(line);
            if(line.contains("true"))
                count++;
        }*//*

        reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        reader.close();
        return count;
    }*/

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

    public ResponseEntity updateSolution(Long userId, Long problemId, Long solutionId, MultipartFile file) throws Exception {
        Optional<Users> user = usersRepositories.findById(userId);
        if (user.isPresent()) {
            Optional<Problems> problem = problemsRepositories.findById(problemId);
            if (problem.isPresent()) {
                Optional<Solutions> previousSolution = solutionsRepositories.findById(solutionId);
                if(previousSolution.isPresent()){
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
                    int testCasesPassed = evaluate(testCases, userSolution, currentProjectDirectory);
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
                } else{
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
