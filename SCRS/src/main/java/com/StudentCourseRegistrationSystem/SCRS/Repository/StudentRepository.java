package com.StudentCourseRegistrationSystem.SCRS.Repository;

import com.StudentCourseRegistrationSystem.SCRS.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findByUsername(String username);
    List<Student> findAll();
    List<Student> findAllByOrderBySubmissionTimeAsc();
    boolean existsByUsername(String username);
}
