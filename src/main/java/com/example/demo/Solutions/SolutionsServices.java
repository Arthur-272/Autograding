package com.example.demo.Solutions;

import com.example.demo.Problems.Problems;
import com.example.demo.Problems.ProblemsRepositories;
import com.example.demo.Users.Users;
import com.example.demo.Users.UsersRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class SolutionsServices {

    @Autowired
    private SolutionsRepositories solutionsRepositories;

    @Autowired
    private ProblemsRepositories problemsRepositories;

    @Autowired
    private UsersRepositories usersRepositories;

    public void addSolution(long userId, long problemId, MultipartFile file) throws Exception {
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
    }

    public String getCurrentProjectDirectory() throws Exception{
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

    public int evaluate(String fileName) throws Exception{
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
        /*while ((line = reader.readLine()) != null) {
            System.out.println(line);
            if(line.contains("true"))
                count++;
        }*/

        reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        reader.close();
        return count;
    }

    public List<Solutions> getAllSolutions() {
        List<Solutions> list = new ArrayList<Solutions>();
        solutionsRepositories.findAll().forEach(list::add);
        return list;
    }

    public List<Solutions> getSolutionsByProblemId(Long problemId) {
        return solutionsRepositories.findAllByProblems_Id(problemId);
    }

    public List<Solutions> getSolutionsByUserId(Long userId){
        return solutionsRepositories.findAllByUsersId(userId);
    }

    /**
     * TODO: Think about how to get that particular solution id so that we can update that exact solution.
     * One way is to make that id meaningful rather than just numbers like, 98 (user id) + 1 (problem id) = 981
     * */
    /*public void updateSolution(Solutions solution){
        Solutions previousSolution;
        long previousScore = previousSolution.getScore();

//        Subtracting the previous solution's score from the user and updating it in Solution table
        Users user = solution.getUsers();
        System.out.println(previousScore);
        user.setScore((user.getScore() - previousScore));
        solution.setUsers(user);

        solutionsRepositories.save(solution);
    }*/

}
