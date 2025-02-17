package com.StudentCourseRegistrationSystem.SCRS.Repository;

import com.StudentCourseRegistrationSystem.SCRS.Entity.Course;
import com.StudentCourseRegistrationSystem.SCRS.Entity.FeedBack;
import com.StudentCourseRegistrationSystem.SCRS.Entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedBackRepository extends JpaRepository<FeedBack,Long> {
    List<FeedBack> findByCourse(Course course);
    List<FeedBack> findByProfessor(Professor professor);
}
