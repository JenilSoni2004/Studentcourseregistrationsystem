package com.StudentCourseRegistrationSystem.SCRS.Repository;

import com.StudentCourseRegistrationSystem.SCRS.Entity.Admin;
import com.StudentCourseRegistrationSystem.SCRS.Entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor,Long> {
    Optional<Professor> findByEmail(String email);

}
