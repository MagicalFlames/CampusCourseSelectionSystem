package com.zjgsu.wzy.campuscourseselectionsystem.repository;

import com.zjgsu.wzy.campuscourseselectionsystem.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, String> {

}
