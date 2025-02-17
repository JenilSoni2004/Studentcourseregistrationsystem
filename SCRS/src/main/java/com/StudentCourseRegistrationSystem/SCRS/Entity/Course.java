package com.StudentCourseRegistrationSystem.SCRS.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseid;
    @Column(nullable = false)
    private String coursename;
    private int capacity;
    private int remainingseat;
    @Column(nullable = false)
    private int coursecredit;
    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;









    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
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

    public int getCoursecredit() {
        return coursecredit;
    }

    public void setCoursecredit(int coursecredit) {
        this.coursecredit = coursecredit;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }


}
