package com.example.demo.Classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/user/{userId}/class/{id}/addTeachers/{teacherId}")
    public void addTeachers(@PathVariable long userId, @PathVariable long id, @PathVariable long teacherId)throws Exception{
        classesServices.addTeachers(userId, id, teacherId);
    }

    @PostMapping("/user/{userId}/class/{id}/addStudents/{studentId}")
    public void addStudents(@PathVariable long userId, @PathVariable long id, @PathVariable long studentId) throws Exception{
        classesServices.addStudent(userId, id, studentId);
    }
}
