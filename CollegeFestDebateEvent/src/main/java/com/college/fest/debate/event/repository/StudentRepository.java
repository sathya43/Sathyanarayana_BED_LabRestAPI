package com.college.fest.debate.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.college.fest.debate.event.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

}
