package com.zjgsu.wzy.campuscourseselectionsystem.repository;

import com.zjgsu.wzy.campuscourseselectionsystem.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, String> {

    // 按课程代码查询
    Optional<Course> findByCode(String code);

    // 按讲师编号查询
    @Query("SELECT c FROM Course c WHERE c.instructor.id = :instructorId")
    List<Course> findByInstructorId(@Param("instructorId") String instructorId);

    // 筛选有剩余容量的课程
    @Query("SELECT c FROM Course c WHERE c.enrolledCount < c.capacity")
    List<Course> findCoursesWithAvailableSeats();

    // 标题关键字模糊查询
    List<Course> findByTitleContaining(String keyword);

    // 检查课程代码是否存在
    boolean existsByCode(String code);
}
