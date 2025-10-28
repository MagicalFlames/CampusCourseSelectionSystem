package com.zjgsu.wzy.campuscourseselectionsystem.controller;

import com.zjgsu.wzy.campuscourseselectionsystem.model.ApiResponse;
import com.zjgsu.wzy.campuscourseselectionsystem.model.Course;
import com.zjgsu.wzy.campuscourseselectionsystem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ApiResponse getAllCourses() {
        List<Course> courses =courseService.findAll();
        return new ApiResponse(true, Map.of(
                "ok","课程发挥成功",
                "data", courses
        ));
    }

    @GetMapping("/{courseId}")
    public ApiResponse getCourseById(@PathVariable String courseId) {
        Optional<Course> course = courseService.findById(courseId);
        if(course.isPresent()) {
            return new ApiResponse(true, Map.of(
                    "ok","查询成功",
                    "data", course
            ));
        }
        else{
            return new ApiResponse(false, Map.of(
                    "error","查询失败",
                    "data", course
            ));
        }
    }

    @PostMapping
    public ApiResponse addCourse(@RequestBody Course course) {
        try {
            Course savedCourse = courseService.add(course);
            return new ApiResponse(true, Map.of(
                    "ok", "课程添加成功",
                    "data", savedCourse
            ));
        } catch (IllegalArgumentException e) {
            return new ApiResponse(false, Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    @PutMapping("/{courseId}")
    public ApiResponse updateCourse(@PathVariable String courseId, @RequestBody Course updatedCourse) {
        try {
            Course course = courseService.update(courseId, updatedCourse);
            return new ApiResponse(true, Map.of(
                    "ok", "课程更新成功",
                    "data", course
            ));
        } catch (IllegalArgumentException e) {
            return new ApiResponse(false, Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/{courseId}")
    public ApiResponse deleteCourse(@PathVariable String courseId) {
        try {
            if (courseService.delete(courseId)) {
                return new ApiResponse(true, Map.of(
                        "ok", "课程删除成功"
                ));
            } else {
                return new ApiResponse(false, Map.of(
                        "error", "课程不存在"
                ));
            }
        } catch (IllegalStateException e) {
            return new ApiResponse(false, Map.of(
                    "error", e.getMessage()
            ));
        }
    }
}
