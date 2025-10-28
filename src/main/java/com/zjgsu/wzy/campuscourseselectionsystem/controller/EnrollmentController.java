package com.zjgsu.wzy.campuscourseselectionsystem.controller;


import com.zjgsu.wzy.campuscourseselectionsystem.model.ApiResponse;
import com.zjgsu.wzy.campuscourseselectionsystem.model.Enrollment;
import com.zjgsu.wzy.campuscourseselectionsystem.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    /**
     * 学生选课
     */

    @PostMapping
    public ApiResponse enroll(@RequestBody Map<String, String> request) {
        try {
            String studentId = request.get("studentId");
            String courseId = request.get("courseId");

            if (studentId == null || courseId == null) {
                return new ApiResponse(false, Map.of(
                        "error", "studentId 和 courseId 不能为空"
                ));
            }

            Enrollment enrollment = enrollmentService.enroll(studentId, courseId);
            return new ApiResponse(true, Map.of(
                    "ok", "选课成功",
                    "data", enrollment
            ));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ApiResponse(false, Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    /**
     * 退课
     */
    @DeleteMapping("/{id}")
    public ApiResponse dropCourse(@PathVariable String id) {
        try {
            if (enrollmentService.drop(id)) {
                return new ApiResponse(true, Map.of(
                        "ok", "退课成功"
                ));
            } else {
                return new ApiResponse(false, Map.of(
                        "error", "选课记录不存在"
                ));
            }
        } catch (IllegalStateException e) {
            return new ApiResponse(false, Map.of(
                    "error", e.getMessage()
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
        List<Enrollment> enrollments = enrollmentService.findByCourseId(courseId);
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
