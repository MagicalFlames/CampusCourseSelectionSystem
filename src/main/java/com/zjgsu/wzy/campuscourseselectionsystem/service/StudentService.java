package com.zjgsu.wzy.campuscourseselectionsystem.service;

import com.zjgsu.wzy.campuscourseselectionsystem.model.Student;
import com.zjgsu.wzy.campuscourseselectionsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void create(Student student) {
        studentRepository.save(student);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> findById(String studentId) {
        return studentRepository.findById(studentId);
    }

    public void update(Student updatedStudent) {
        studentRepository.save(updatedStudent);
    }

    // 删除学生
    public boolean delete(String studentId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isEmpty()) {
            return false;
        }
        studentRepository.delete(studentOpt.get());
        return true;
    }
}
