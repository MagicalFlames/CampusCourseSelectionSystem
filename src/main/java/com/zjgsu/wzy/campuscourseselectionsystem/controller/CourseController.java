package com.zjgsu.wzy.campuscourseselectionsystem.controller;

import com.zjgsu.wzy.campuscourseselectionsystem.model.ApiResponse;
import com.zjgsu.wzy.campuscourseselectionsystem.model.Course;
import com.zjgsu.wzy.campuscourseselectionsystem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        courseService.add(course);
        return new ApiResponse(true,Map.of(
               "ok","课程添加成功"
        ));
    }

    @PutMapping("/{courseId}")
    public ApiResponse updateCourse(@PathVariable String courseId, @RequestBody Course updatedCourse) {
        updatedCourse.setId(courseId);
        courseService.update(updatedCourse);
        return new ApiResponse(true,Map.of(
                "ok","课程更新成功"
        ));
    }

    @DeleteMapping("/{courseId}")
    public ApiResponse deleteCourse(@PathVariable String courseId) {
        courseService.delete(courseId);
        return new ApiResponse(true,Map.of(
                "ok","课程删除成功"
        ));
    }
}
