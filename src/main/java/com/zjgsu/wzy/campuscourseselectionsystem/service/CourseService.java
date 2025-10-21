package com.zjgsu.wzy.campuscourseselectionsystem.service;


import com.zjgsu.wzy.campuscourseselectionsystem.model.Course;
import com.zjgsu.wzy.campuscourseselectionsystem.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private CourseRepository courseRepository;

    @Autowired
    public void CurseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Optional<Course> findById(String id) {
        return courseRepository.findById(id);
    }

    public List<Course> findAll(){
        return courseRepository.findAll();
    }

    public void add(Course course){
        courseRepository.save(course);
    }

    public boolean delete(String code){
        Optional<Course> course = courseRepository.findById(code);
        if(course.isPresent()){
            courseRepository.delete(course.get());
            return true;
        }
        else{
            return false;
        }
    }

    public void update(Course course){
        courseRepository.save(course);
    }
}
