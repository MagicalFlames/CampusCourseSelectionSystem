package com.zjgsu.wzy.campuscourseselectionsystem.controller;


import com.zjgsu.wzy.campuscourseselectionsystem.model.ApiResponse;
import com.zjgsu.wzy.campuscourseselectionsystem.model.Enrollment;
import com.zjgsu.wzy.campuscourseselectionsystem.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @DeleteMapping("/{id}")
    public ApiResponse dropCourse(@PathVariable String id) {
        if(enrollmentService.delete(id)) {
            return new ApiResponse(true, Map.of(
               "ok","删除成功"
            ));
        }
        else{
            return new ApiResponse(false, Map.of(
                    "error","记录不存在"
            ));
        }
    }

    @GetMapping
    public ApiResponse getAllEnrollments() {
        List<Enrollment> enrollments = enrollmentService.findAll();
        return new ApiResponse(true, Map.of(
                "ok","获取成功",
                "data", enrollments
        ));
    }

    @GetMapping("/course/{courseId}")
    public ApiResponse getEnrollmentsByCourse(@PathVariable String courseId) {
        Optional<Enrollment> enrollments = enrollmentService.findByCourseId(courseId);
        return new ApiResponse(true, Map.of(
                "ok","获取成功",
                "data", enrollments
        ));
    }

    @GetMapping("/student/{studentId}")
    public ApiResponse getEnrollmentsByStudent(@PathVariable String studentId) {
        List<Enrollment> enrollments = enrollmentService.findByStudentId(studentId);
        return new ApiResponse(true, Map.of(
                "ok","查询成功",
                "data", enrollments
        ));
    }
}
