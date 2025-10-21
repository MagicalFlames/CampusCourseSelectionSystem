package com.zjgsu.wzy.campuscourseselectionsystem.controller;

import com.zjgsu.wzy.campuscourseselectionsystem.model.Course;
import com.zjgsu.wzy.campuscourseselectionsystem.repository.CourseRepository;
import com.zjgsu.wzy.campuscourseselectionsystem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses =courseService.findAll();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable String courseId) {
        Optional<Course> course = courseService.findById(courseId);
        return course.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        courseService.add(course);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(@PathVariable String courseId, @RequestBody Course updatedCourse) {
        updatedCourse.setId(courseId);
        courseService.update(updatedCourse);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String courseId) {
        courseService.delete(courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
