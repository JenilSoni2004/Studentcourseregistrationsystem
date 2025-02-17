package com.StudentCourseRegistrationSystem.SCRS.Controller;

import com.StudentCourseRegistrationSystem.SCRS.Entity.Professor;
import com.StudentCourseRegistrationSystem.SCRS.Service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/Professor")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;
    @GetMapping("/Find-professor-by-id/{id}")
    public ResponseEntity<?> findProfessorById(@PathVariable Long id,@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Optional<Professor> professor = professorService.FindProfessorById(id);
        if (professor.isPresent()) {
            return ResponseEntity.ok(professor);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Professor not found with ID: ");
        }
    }
    @PutMapping("/Update-professor/{id}")
    public ResponseEntity<?> updateProfessor(@PathVariable Long id, @RequestBody Professor professorData,@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            Professor updatedProfessor = professorService.updateProfessor(id, professorData);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Professor updated successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Unable to update");
        }
    }
}
