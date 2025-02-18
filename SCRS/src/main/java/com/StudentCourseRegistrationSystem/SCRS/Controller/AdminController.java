package com.StudentCourseRegistrationSystem.SCRS.Controller;

import com.StudentCourseRegistrationSystem.SCRS.DTO.CourseDTO;
import com.StudentCourseRegistrationSystem.SCRS.DTO.StudentDTO;
import com.StudentCourseRegistrationSystem.SCRS.Entity.*;
import com.StudentCourseRegistrationSystem.SCRS.Service.AdminServiceImpl;
import com.StudentCourseRegistrationSystem.SCRS.Service.FeedBackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Admin")
@Tag(name = "Admin apis",description = "create delete update")
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;
    @Autowired
    private FeedBackService feedBackService;


    //--------------------------- CRUD FOR STUDENTS-------------------------------------------------------------


    @PostMapping("/Create-Student")
    public ResponseEntity<?> createStudent(@RequestBody StudentDTO student,@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {


            adminService.CreateStudent(student);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Student Created Successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Unable to create student"+e.getMessage());
        }

    }

    @DeleteMapping("/Delete-student/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id,@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        boolean isDeleted = adminService.deletestudentbyid(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Student deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: Student not found.");
        }
    }


//-----------------------CRUD FOR STUDENTS ENDED ------------------------------------------------------

    //------------------------CRUD FOR PROFESSOR STARTED-----------------------------


    @PostMapping("/Create-Professor")
    public ResponseEntity<?> createProfessor(@RequestBody Professor professor,@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            adminService.CreateProfessor(professor);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Professor created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create professor");
        }
    }

    @DeleteMapping("/Delete-professor/{id}")
    public ResponseEntity<?> deleteProfessor(@PathVariable Long id,@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        boolean isDeleted = adminService.deleteProfessorById(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Professor deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(" Professor not found with ID: ");
        }
    }


    //---------------------CRUD FOR PROFESSOR ENDED--------------------------------------

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-course")
    public ResponseEntity<?> createCourse(@RequestBody CourseDTO courseDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        System.out.println("Received Token: " + token); // Debugging

        try {
            Course createdCourse = adminService.createCourse(courseDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Course is successfully created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create course");
        }
    }


    //--------------------CURD FOR SEMESTER-------------------------

    @PostMapping("/create-semester")

    public ResponseEntity<?> createsemester(@RequestBody Semester semester,@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            adminService.CreateSemester(semester);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Semester is successfully created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create semester");
        }
    }

    @DeleteMapping("/Delete-semester/{id}")
    public ResponseEntity<?> deleteSemester(@PathVariable Long id,@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        boolean isDeleted = adminService.deleteSemesterById(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Semester deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(" Semester not found with ID: " + id);
        }
    }

    @GetMapping("/Find-semester-by-id/{id}")
    public ResponseEntity<?> findSemesterById(@PathVariable Long id,@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Optional<Semester> semester = adminService.Findsemesterbyid(id);

        if (semester.isPresent()) {
            return ResponseEntity.ok(semester.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Semester not found with ID: " + id);
        }
    }


    @PostMapping("/Allocate-Course")
    public ResponseEntity<String> allocateCourses(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        adminService.allocate();
        return ResponseEntity.ok("Course allocation completed successfully!");
    }

    @PostMapping("/send-course-registration")
    public ResponseEntity<String> sendPredefinedCourseEmail(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        adminService.sendCourseRegistrationEmailToAll();
        return ResponseEntity.ok("Predefined course registration email sent to all students!");
    }

    @PostMapping("/Announcement")
    public ResponseEntity<String> sendEmails(@RequestParam String subject, @RequestParam String message,@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        adminService.sendEmailToAllStudents(subject, message);
        return ResponseEntity.ok("Emails sent to all students!");
    }

    @GetMapping("/send-course-Allocation-Mail")
    public ResponseEntity<String> sendEmails(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        adminService.sendCourseAllocationEmails();
        return ResponseEntity.ok("Emails sent to all allocated students!");
    }

    @PostMapping("/send-Feedback-mails")
    public ResponseEntity<String> sendFeedbackEmails(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            feedBackService.sendFeedbackRequestEmails();
            return ResponseEntity.ok("Feedback emails sent successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending emails: " + e.getMessage());
        }
    }
}

