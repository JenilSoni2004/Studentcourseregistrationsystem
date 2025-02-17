package com.StudentCourseRegistrationSystem.SCRS.Service;

import com.StudentCourseRegistrationSystem.SCRS.DTO.CourseDTO;
import com.StudentCourseRegistrationSystem.SCRS.DTO.CoursePreferenceDTO;
import com.StudentCourseRegistrationSystem.SCRS.DTO.StudentDTO;
import com.StudentCourseRegistrationSystem.SCRS.Entity.Course;
import com.StudentCourseRegistrationSystem.SCRS.Entity.Professor;
import com.StudentCourseRegistrationSystem.SCRS.Entity.Semester;
import com.StudentCourseRegistrationSystem.SCRS.Entity.Student;

import java.util.Optional;

public interface AdminService {
    public Course createCourse(CourseDTO courseDTO);
    public void CreateStudent(StudentDTO student);
    public void CreateProfessor(Professor professor);
    public void CreateSemester(Semester semester);
    public boolean deletestudentbyid(Long studentId);
    public boolean deleteProfessorById(Long id);
    public boolean deleteSemesterById(Long id);
    public Optional<Semester> Findsemesterbyid(Long id);
    public void allocate();
    public void sendEmailToAllStudents(String subject, String messageBody);
    public void sendEmail(String recipient, String subject, String message);
    public void sendCourseRegistrationEmailToAll();
    public void sendCourseAllocationEmails();
    public void sendEmailToStudent(Student student);


}
