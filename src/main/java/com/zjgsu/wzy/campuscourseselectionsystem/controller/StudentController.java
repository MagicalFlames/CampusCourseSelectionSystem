package com.zjgsu.wzy.campuscourseselectionsystem.controller;


import com.zjgsu.wzy.campuscourseselectionsystem.model.Student;
import com.zjgsu.wzy.campuscourseselectionsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private StudentService studentService;
    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Void> createStudent(@RequestBody Student student) {
        studentService.create(student);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(studentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable String id) {
        Optional<Student> studentOpt = studentService.findById(id);
        return studentOpt.map(student -> new ResponseEntity<>(student, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable String id, @RequestBody Student updatedStudent) {
        updatedStudent.setId(id);
        studentService.update(updatedStudent);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
        studentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
