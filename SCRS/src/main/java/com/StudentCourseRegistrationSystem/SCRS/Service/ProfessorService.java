package com.StudentCourseRegistrationSystem.SCRS.Service;

import com.StudentCourseRegistrationSystem.SCRS.Entity.Professor;

import java.util.Optional;

public interface ProfessorService {
    public Optional<Professor> FindProfessorById(Long id);
    public Professor updateProfessor(Long id, Professor professorData);
}
