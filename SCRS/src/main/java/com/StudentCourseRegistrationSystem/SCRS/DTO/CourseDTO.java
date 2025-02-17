package com.StudentCourseRegistrationSystem.SCRS.DTO;

public class CourseDTO {
    private String courseName;
    private int capacity;
    private int remainingseat;
    private int courseCredit;
    private Long professorId;
    private Long SemesterId;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRemainingseat() {
        return remainingseat;
    }

    public void setRemainingseat(int remainingseat) {
        this.remainingseat = remainingseat;
    }

    public int getCourseCredit() {
        return courseCredit;
    }

    public Long getSemesterId() {
        return SemesterId;
    }

    public void setSemesterId(Long semesterId) {
        SemesterId = semesterId;
    }

    public void setCourseCredit(int courseCredit) {
        this.courseCredit = courseCredit;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }
}
