package com.StudentCourseRegistrationSystem.SCRS.Service;

import com.StudentCourseRegistrationSystem.SCRS.Entity.Professor;
import com.StudentCourseRegistrationSystem.SCRS.Repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfessorServiceImpl implements ProfessorService{
    @Autowired
    private ProfessorRepository professorrepository;
    private static final PasswordEncoder passwordencoder =new BCryptPasswordEncoder();
    @Override
    public Optional<Professor> FindProfessorById(Long id)
    {
        return professorrepository.findById(id);
    }
    @Override
    public Professor updateProfessor(Long id, Professor professorData) {
        Optional<Professor> optionalProfessor = professorrepository.findById(id);

        if (optionalProfessor.isPresent()) {
            Professor professor = optionalProfessor.get();


            if (professorData.getName() != null && !professorData.getName().isEmpty()) {
                professor.setName(professorData.getName());
            }
            if (professorData.getEmail() != null && !professorData.getEmail().isEmpty()) {
                professor.setEmail(professorData.getEmail());
            }
            if (professorData.getPassword() != null && !professorData.getPassword().isEmpty()) {
                professor.setPassword(passwordencoder.encode(professorData.getPassword()));
            }

            return professorrepository.save(professor);
        } else {
            throw new RuntimeException("Professor not found with ID: " + id);
        }
    }
}

