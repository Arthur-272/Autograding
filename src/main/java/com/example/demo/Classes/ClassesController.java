package com.example.demo.Classes;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.JSONParserTokenManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ClassesController {

    @Autowired
    ClassesServices classesServices;

    @GetMapping("/classes")
    public List<Classes> showAllClasses(){
        return classesServices.getAllClasses();
    }

    @PostMapping(value =  "/user/{id}/createClass")
    public void createNewClass(@RequestBody Classes newClass, @PathVariable long id)throws Exception{
        classesServices.addNewClass(newClass, id);
    }

    @GetMapping("/user/{id}/classes")
    public List<Classes> showAllClassesById(@PathVariable long id){
        return classesServices.getClassesByOwnerId(id);
    }

    @PostMapping("/user/{userId}/class/{id}/addTeachers")
    public void addTeachers(@PathVariable long userId, @PathVariable long id, @PathVariable String jsonPassed)throws Exception{
        classesServices.addTeachers(userId, id, new JSONObject(jsonPassed).getJSONArray("ids"));
    }

//    For this method the request body should be of type plain text
    @PostMapping("/user/{userId}/class/{id}/addStudents")
    public void addStudents(@PathVariable long userId, @PathVariable long id, @RequestBody String jsonPassed) throws Exception{
        classesServices.addStudent(userId, id, new JSONObject(jsonPassed).getJSONArray("ids"));
    }
}
