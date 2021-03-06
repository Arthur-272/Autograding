package com.example.demo.Classes;

import com.example.demo.Comments.Comments;
import com.example.demo.Comments.CommentsServices;
import com.example.demo.Posts.Posts;
import com.example.demo.Posts.PostsServices;
import com.example.demo.Users.Users;
import com.example.demo.Users.UsersRepositories;
import com.example.demo.Users.UsersServices;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClassesServices {

    @Autowired
    private ClassesRepositories classesRepositories;

    @Autowired
    private UsersRepositories usersRepositories;

    @Autowired
    private UsersServices usersServices;

    @Autowired
    private PostsServices postsServices;

    @Autowired
    private CommentsServices commentsServices;

    public List<Classes> getAllClasses() {
        List<Classes> list = new ArrayList<Classes>();
        classesRepositories.findAll().forEach(list::add);
        System.out.println(list);
        return list;
    }

    public Optional<Classes> getClassById(long id) {
        return classesRepositories.findById(id);
    }

    public boolean isOwner(long ownerId, long classId){
        return classesRepositories.findById(classId).get().getOwnerId() == ownerId;
    }


    public List<Classes> getClassesByOwnerId(long id){
        return classesRepositories.getAllByOwnerId(id);
    }

    public void addNewClass(Classes newClass, long ownerId) throws Exception{
        if (usersServices.isTeacher(ownerId)) {
//            Making the current logged in user the owner of the class
            Users user = usersRepositories.findById(ownerId).get();
            newClass.setOwnerId(ownerId);
            List<Users> teachers = new ArrayList<Users>();
            teachers.add(user);
            newClass.setTeachers(teachers);

//            Adding an empty list as students in that class
            List<Users> students = new ArrayList<Users>();
            newClass.setStudents(students);

//            Adding an empty list of posts/announcements in that class
            List<com.example.demo.Posts.Posts> posts = new ArrayList<com.example.demo.Posts.Posts>();
            newClass.setPosts(posts);

//            Adding the class to the db
            classesRepositories.save(newClass);
        } else {
            throw new Exception("Not a teacher");
        }
    }

    public void addTeachers(long userId, long classId, JSONArray teacherIds) throws Exception{

        if(teacherIds != null) {

            for(int i=0;i<teacherIds.length();i++) {

                long teacherId = teacherIds.getInt(i);

//        Checking if the user is the same as the owner of the class
                if (isOwner(userId, classId)) {
//            Checking if the user is adding a teacher

                    if (usersServices.isTeacher(teacherId)) {
                        Classes classes = classesRepositories.findById(classId).get();
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
                    } else {
                        throw new Exception("Not a teacher");
                    }
                } else {
                    throw new Exception("Invalid user accessing the class");
                }
            }
        } else{
            throw new Exception("No ids passed");
        }
    }

    public void addStudent(long userId, long classId, JSONArray studentIds) throws Exception {

        if(studentIds != null) {

            for (int i = 0; i < studentIds.length(); i++) {
                long studentId = studentIds.getInt(i);

                Users user = usersRepositories.findById(studentId).get();

//        Checking if the user is the same as the owner of the class
                if (classesRepositories.findById(classId).get().getOwnerId() == userId) {

//            Checking if the user is adding a student
                    if (user.getRole().equals("student")) {
                        Classes classes = classesRepositories.findById(classId).get();
                        List<Long> studentsId = new ArrayList<>();
                        classes.getStudents().forEach(students -> studentsId.add(students.getId()));

//                Checking if the student the user is trying to add already exists in the class
                        if (studentsId.contains(studentId)) {
                            throw new Exception("Student already in class");
                        } else {
                            Users newStudent = usersRepositories.findById(studentId).get();
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
        } else{
            throw new Exception("No ids passed");
        }
    }
}
