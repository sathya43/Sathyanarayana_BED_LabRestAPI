package com.college.fest.debate.event.service;

import com.college.fest.debate.event.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> getAllStudents();

    Optional<Student> getStudentById(Long id);

    Student saveStudent(Student student);

    void deleteStudent(Long id);
}
