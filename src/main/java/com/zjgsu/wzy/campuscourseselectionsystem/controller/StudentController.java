package com.zjgsu.wzy.campuscourseselectionsystem.controller;


import com.zjgsu.wzy.campuscourseselectionsystem.model.ApiResponse;
import com.zjgsu.wzy.campuscourseselectionsystem.model.Student;
import com.zjgsu.wzy.campuscourseselectionsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    public ApiResponse createStudent(@RequestBody Student student) {
        studentService.create(student);
        return new ApiResponse(true, Map.of(
                "ok","创建成功"
        ));
    }

    @GetMapping
    public ApiResponse getAllStudents() {
        return new ApiResponse(true,Map.of(
                "ok","获取成功",
                "data",studentService.findAll()
        ));
    }

    @GetMapping("/{id}")
    public ApiResponse getStudentById(@PathVariable String id) {
        Optional<Student> studentOpt = studentService.findById(id);
        return studentOpt.map(student -> new ApiResponse(true, Map.of(
                "ok", "获取成功",
                "data", student
        ))).orElseGet(() -> new ApiResponse(false, Map.of(
                "error", "学生不存在"
        )));
    }

    @PutMapping("/{id}")
    public ApiResponse updateStudent(@PathVariable String id, @RequestBody Student updatedStudent) {
        updatedStudent.setId(id);
        studentService.update(updatedStudent);
        return new ApiResponse(true, Map.of(
                "ok","学生信息更新成功"
        ));
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteStudent(@PathVariable String id) {
        studentService.delete(id);
        return new ApiResponse(true, Map.of(
                "ok","学生删除成功"
        ));
    }
}
