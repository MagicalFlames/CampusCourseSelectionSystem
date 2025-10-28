package com.zjgsu.wzy.campuscourseselectionsystem.service;


import com.zjgsu.wzy.campuscourseselectionsystem.model.Course;
import com.zjgsu.wzy.campuscourseselectionsystem.repository.CourseRepository;
import com.zjgsu.wzy.campuscourseselectionsystem.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Course> findById(String id) {
        return courseRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Course> findAll(){
        return courseRepository.findAll();
    }

    public Course add(Course course){
        // 检查课程代码是否已存在
        if (courseRepository.existsByCode(course.getCode())) {
            throw new IllegalArgumentException("课程代码已存在: " + course.getCode());
        }
        return courseRepository.save(course);
    }

    public boolean delete(String id){
        Optional<Course> course = courseRepository.findById(id);
        if(course.isEmpty()){
            return false;
        }

        // 检查是否有活跃的选课记录
        long activeEnrollments = enrollmentRepository.countActiveByCourseId(id);
        if (activeEnrollments > 0) {
            throw new IllegalStateException("无法删除课程，存在 " + activeEnrollments + " 个活跃选课记录");
        }

        courseRepository.delete(course.get());
        return true;
    }

    public Course update(String id, Course courseDetails){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("课程不存在: " + id));

        // 如果修改了课程代码，检查新代码是否已被使用
        if (!course.getCode().equals(courseDetails.getCode()) &&
            courseRepository.existsByCode(courseDetails.getCode())) {
            throw new IllegalArgumentException("课程代码已存在: " + courseDetails.getCode());
        }

        course.setCode(courseDetails.getCode());
        course.setTitle(courseDetails.getTitle());
        course.setInstructor(courseDetails.getInstructor());
        course.setScheduleSlot(courseDetails.getScheduleSlot());
        course.setCapacity(courseDetails.getCapacity());

        return courseRepository.save(course);
    }
}
