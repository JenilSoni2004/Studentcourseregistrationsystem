package com.StudentCourseRegistrationSystem.SCRS.Service;

import com.StudentCourseRegistrationSystem.SCRS.DTO.CourseDTO;
import com.StudentCourseRegistrationSystem.SCRS.DTO.CoursePreferenceDTO;
import com.StudentCourseRegistrationSystem.SCRS.DTO.StudentDTO;
import com.StudentCourseRegistrationSystem.SCRS.Entity.Course;
import com.StudentCourseRegistrationSystem.SCRS.Entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    public Student updateStudent(Long id, StudentDTO studentDTO);
    public Optional<Student> Findstudentbyid(Long id);
    public void saveCoursePreference(CoursePreferenceDTO preferenceDTO);
    public List<Object> getAllCourses();

}
