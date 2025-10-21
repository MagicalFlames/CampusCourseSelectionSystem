package com.zjgsu.wzy.campuscourseselectionsystem.controller;


import com.zjgsu.wzy.campuscourseselectionsystem.model.Enrollment;
import com.zjgsu.wzy.campuscourseselectionsystem.repository.EnrollmentRepository;
import com.zjgsu.wzy.campuscourseselectionsystem.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    private EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> dropCourse(@PathVariable String id) {
        if(enrollmentService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        List<Enrollment> enrollments = enrollmentService.findAll();
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<Optional<Enrollment>> getEnrollmentsByCourse(@PathVariable String courseId) {
        Optional<Enrollment> enrollments = enrollmentService.findByCourseId(courseId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Enrollment>> getEnrollmentsByStudent(@PathVariable String studentId) {
        List<Enrollment> enrollments = enrollmentService.findByStudentId(studentId);
        return ResponseEntity.ok(enrollments);
    }
}
