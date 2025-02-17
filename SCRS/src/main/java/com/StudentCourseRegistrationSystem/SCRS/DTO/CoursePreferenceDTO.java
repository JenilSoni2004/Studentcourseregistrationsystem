package com.StudentCourseRegistrationSystem.SCRS.DTO;

import java.util.List;

public class CoursePreferenceDTO {
    private Long studentId;
    private List<Integer> preferredCourseIds;

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public List<Integer> getPreferredCourseIds() { return preferredCourseIds; }
    public void setPreferredCourseIds(List<Integer> preferredCourseIds) { this.preferredCourseIds = preferredCourseIds; }
}
