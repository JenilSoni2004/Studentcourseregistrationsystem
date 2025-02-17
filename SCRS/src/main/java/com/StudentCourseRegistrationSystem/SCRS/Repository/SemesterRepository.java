package com.StudentCourseRegistrationSystem.SCRS.Repository;

import com.StudentCourseRegistrationSystem.SCRS.Entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<Semester,Long> {
}
