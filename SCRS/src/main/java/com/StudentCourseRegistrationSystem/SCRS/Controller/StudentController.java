package com.StudentCourseRegistrationSystem.SCRS.Controller;

import com.StudentCourseRegistrationSystem.SCRS.DTO.CoursePreferenceDTO;
import com.StudentCourseRegistrationSystem.SCRS.DTO.FeedBackDTO;
import com.StudentCourseRegistrationSystem.SCRS.DTO.StudentDTO;
import com.StudentCourseRegistrationSystem.SCRS.Entity.Course;
import com.StudentCourseRegistrationSystem.SCRS.Entity.FeedBack;
import com.StudentCourseRegistrationSystem.SCRS.Entity.Professor;
import com.StudentCourseRegistrationSystem.SCRS.Entity.Student;
import com.StudentCourseRegistrationSystem.SCRS.Repository.CourseRepository;
import com.StudentCourseRegistrationSystem.SCRS.Repository.FeedBackRepository;
import com.StudentCourseRegistrationSystem.SCRS.Repository.ProfessorRepository;
import com.StudentCourseRegistrationSystem.SCRS.Repository.StudentRepository;
import com.StudentCourseRegistrationSystem.SCRS.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private FeedBackRepository feedbackRepository;
    @PutMapping("/Update-student/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO,@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            Student updatedStudent = studentService.updateStudent(id, studentDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(" Student updated successfully!");
        } catch (RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(" Student not found with ID: " + id);
        }
    }
    @GetMapping("/Find-student-by-id/{id}")
    public ResponseEntity<?> findbysid(@PathVariable Long id,@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Optional<Student> student = studentService.Findstudentbyid(id);

        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student not found with ID: " + id);
        }
    }
    @PostMapping("/save-preferences")
    public ResponseEntity<String> saveCoursePreferences(@RequestBody CoursePreferenceDTO preferenceDTO,@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        studentService.saveCoursePreference(preferenceDTO);
        return ResponseEntity.ok(" Preferences saved successfully!");
    }
    @GetMapping
    public List<Object> getAllCourses() {
        return studentService.getAllCourses();
    }

    @GetMapping("/student/{studentId}/courses")
    public ResponseEntity<List<Course>> getStudentCourses(@PathVariable Long studentId,@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        List<Course> courses = student.getAssignedCourses();

        return ResponseEntity.ok(courses);
    }
    @PostMapping("/student/{studentId}/feedback")
    public ResponseEntity<String> getCoursesAndSubmitFeedback(@PathVariable Long studentId, @RequestBody List<FeedBackDTO> feedbackList,@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        for (FeedBackDTO feedbackDTO : feedbackList) {
            Course course = courseRepository.findById(feedbackDTO.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Course not found"));

            Professor professor = professorRepository.findById(feedbackDTO.getProfessorId())
                    .orElseThrow(() -> new RuntimeException("Professor not found"));

            FeedBack feedback = new FeedBack();
            feedback.setStudent(student);
            feedback.setCourse(course);
            feedback.setProfessor(professor);
            feedback.setRating(feedbackDTO.getRating());
            feedback.setComments(feedbackDTO.getComments());

            feedbackRepository.save(feedback);
        }

        return ResponseEntity.ok("Feedback submitted successfully!");
    }

}


