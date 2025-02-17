package com.StudentCourseRegistrationSystem.SCRS.Service;

import com.StudentCourseRegistrationSystem.SCRS.Entity.Admin;
import com.StudentCourseRegistrationSystem.SCRS.Entity.Professor;
import com.StudentCourseRegistrationSystem.SCRS.Entity.Student;
import com.StudentCourseRegistrationSystem.SCRS.Repository.AdminRepository;
import com.StudentCourseRegistrationSystem.SCRS.Repository.ProfessorRepository;
import com.StudentCourseRegistrationSystem.SCRS.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserServiceImpl implements UserService{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTServiceImpl jwtService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Override
    public String verify(String username, String password) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        if (auth.isAuthenticated()) {

            Optional<Admin> admin = adminRepository.findByUsername(username);
            if (admin .isPresent()) {
                return jwtService
                        .generateToken(admin.get().getUsername());
            }

            Optional<Student> student = studentRepository.findByUsername(username);
            if (student.isPresent()) {
                return jwtService.generateToken(student.get().getUsername());
            }

            Optional<Professor> professor = professorRepository.findByEmail(username);
            if (professor.isPresent()) {
                return jwtService.generateToken(professor.get().getEmail());
            }
        }
        return "Failed";
    }

}