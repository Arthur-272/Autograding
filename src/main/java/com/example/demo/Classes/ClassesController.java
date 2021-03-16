package com.example.demo.Classes;

import com.example.demo.Test.Model;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    public void addTeachers(@PathVariable long userId, @PathVariable long id, @RequestBody String string)throws Exception{
        classesServices.addTeachers(userId, id, new JSONObject(string).getJSONArray("ids"));
    }

    @PostMapping("/user/{userId}/class/{id}/addStudents")
    public void addStudents(@PathVariable long userId, @PathVariable long id,@RequestBody String string) throws Exception{
        classesServices.addStudent(userId, id, new JSONObject(string).getJSONArray("ids"));
    }

    @DeleteMapping("/user/{userId}/class/{id}/deleteStudents")
    public void deleteStudents(@PathVariable long userId, @PathVariable long id, @RequestBody String string) throws Exception{
        classesServices.deleteStudent(userId, id, new JSONObject(string).getJSONArray("ids"));
    }

    @DeleteMapping("/user/{userId}/class/{id}/deleteTeachers")
    public void deleteTeachers(@PathVariable long userId, @PathVariable long id, @RequestBody String string) throws Exception{
        classesServices.deleteTeacher(userId, id, new JSONObject(string).getJSONArray("ids"));
    }

    @DeleteMapping("/user/{userId}/class/{id}/deleteClass")
    public void deleteClass(@PathVariable long userId, @PathVariable long id) throws Exception{
        classesServices.deleteClass(userId, id);
    }
}