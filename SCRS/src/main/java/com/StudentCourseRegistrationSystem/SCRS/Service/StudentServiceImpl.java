package com.StudentCourseRegistrationSystem.SCRS.Service;

import com.StudentCourseRegistrationSystem.SCRS.DTO.CourseDTO;
import com.StudentCourseRegistrationSystem.SCRS.DTO.CoursePreferenceDTO;
import com.StudentCourseRegistrationSystem.SCRS.DTO.StudentDTO;
import com.StudentCourseRegistrationSystem.SCRS.Entity.Course;
import com.StudentCourseRegistrationSystem.SCRS.Entity.Student;
import com.StudentCourseRegistrationSystem.SCRS.Repository.CourseRepository;
import com.StudentCourseRegistrationSystem.SCRS.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.oauth2.login.OAuth2LoginSecurityMarker;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    private static final PasswordEncoder passwordencoder =new BCryptPasswordEncoder();
    @Override
    public Student updateStudent(Long id, StudentDTO studentDTO) {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();

            if (studentDTO.getName() != null && !studentDTO.getName().isEmpty()) {
                student.setName(studentDTO.getName());
            }
            if (studentDTO.getUsername() != null && !studentDTO.getUsername().isEmpty()) {
                student.setUsername(studentDTO.getUsername());
            }
            if (studentDTO.getEmail() != null && !studentDTO.getEmail().isEmpty()) {
                student.setEmail(studentDTO.getEmail());
            }
            if (studentDTO.getPassword() != null && !studentDTO.getPassword().isEmpty()) {
                student.setPassword(passwordencoder.encode(studentDTO.getPassword()));
            }

            return studentRepository.save(student);
        } else {
            throw new RuntimeException("Student not found with ID: " + id);
        }
    }
    @Override
    public Optional<Student> Findstudentbyid(Long id)
    {
        return studentRepository.findById(id);

    }
    @Override
    public List<Object> getAllCourses() {
        List<Course> courses = courseRepository.findAll();

        return courses.stream().map(course -> {
            return new Object() {
                public final String courseName = course.getCoursename();
                public final int capacity = course.getCapacity();
                public final int remainingSeats = course.getRemainingseat();
                public final int courseCredit = course.getCoursecredit();
                public final String professorName = course.getProfessor().getName();
                public final String semesterName = course.getSemester().getSemesterName();
            };
        }).collect(Collectors.toList());
    }


    @Override
    public void saveCoursePreference(CoursePreferenceDTO preferenceDTO) {
        Optional<Student> studentOptional = studentRepository.findById(preferenceDTO.getStudentId());

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setSubmissionTime(LocalDateTime.now());

            List<Course> preferredCourses = courseRepository.findAllById(preferenceDTO.getPreferredCourseIds());
            student.setPreferredCourses(preferredCourses);
            studentRepository.save(student);
        } else {
            throw new RuntimeException("Student not found with ID: " + preferenceDTO.getStudentId());
        }
    }

}
