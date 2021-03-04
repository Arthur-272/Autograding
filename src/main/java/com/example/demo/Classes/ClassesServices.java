package com.example.demo.Classes;

import com.example.demo.Posts.Posts;
import com.example.demo.Users.Users;
import com.example.demo.Users.UsersRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClassesServices {

    @Autowired
    private ClassesRepositories classesRepositories;

    @Autowired
    private UsersRepositories usersRepositories;

    public List<Classes> getAllClasses() {
        List<Classes> list = new ArrayList<Classes>();
        classesRepositories.findAll().forEach(list::add);
        System.out.println(list);
        return list;
    }

    public Optional<Classes> getClassById(long id) {
        return classesRepositories.findById(id);
    }

    public List<Classes> getClassesByOwnerId(long id){
        return classesRepositories.getAllByOwnerId(id);
    }

    public void addNewClass(Classes newClass, long ownerId) throws Exception{
        Users user = usersRepositories.findById(ownerId).get();
        if(!user.getRole().equals("teacher")){
            throw new Exception("Not a teacher");
        }
        else{
//            Making the current logged in user the owner of the class
            newClass.setOwnerId(ownerId);
            List<Users> teachers = new ArrayList<Users>();
            teachers.add(user);
            newClass.setTeachers(teachers);

//            Adding an empty list as students in that class
            List<Users> students = new ArrayList<Users>();
            newClass.setStudents(students);

//            Adding the class to the db
            classesRepositories.save(newClass);
        }
    }

    public void addTeachers(long userId, long id, long teacherId) throws Exception{
        Users user = usersRepositories.findById(teacherId).get();

//        Checking if the user is the same as the owner of the class
        if(classesRepositories.findById(id).get().getOwnerId() == userId) {

//            Checking if the user is adding a teacher
            if (!user.getRole().equals("teacher")) {
                throw new Exception("Not a teacher");
            } else {
                Classes classes = classesRepositories.findById(id).get();
                List<Long> teachersId = new ArrayList<>();
                classes.getTeachers().forEach(teachers -> teachersId.add(teachers.getId()));

//                Checking if the teacher the user is trying to add already exists in the class
                if (teachersId.contains(teacherId)) {
                    throw new Exception("Teacher already in class");
                } else {
                    Users newTeacher = usersRepositories.findById(teacherId).get();
                    List<Users> teachers = classes.getTeachers();
                    teachers.add(newTeacher);
                    classes.setTeachers(teachers);
                }
                classesRepositories.save(classes);
            }
        }
        else{
            throw new Exception("Invalid user accessing the class");
        }
    }

    public void addStudent(long userId, long id, long studentId) throws Exception {
        Users user = usersRepositories.findById(studentId).get();

//        Checking if the user is the same as the owner of the class
        if (classesRepositories.findById(id).get().getOwnerId() == userId) {

//            Checking if the user is adding a student
            if (user.getRole().equals("student")) {
                Classes classes = classesRepositories.findById(id).get();
                List<Long> studentsId = new ArrayList<>();
                classes.getStudents().forEach(students -> studentsId.add(students.getId()));

//                Checking if the student the user is trying to add already exists in the class
                if (studentsId.contains(studentId)) {
                    throw new Exception("Student already in class");
                } else {
                    Users newStudent= usersRepositories.findById(studentId).get();
                    List<Users> students = classes.getStudents();
                    students.add(newStudent);
                    classes.setStudents(students);
                }
                classesRepositories.save(classes);
            } else {
                throw new Exception("Not a student");
            }
        } else {
            throw new Exception("Invalid user accessing the class");
        }
    }

    public void createPost(long userId, long id, Posts post) {

    }
}
