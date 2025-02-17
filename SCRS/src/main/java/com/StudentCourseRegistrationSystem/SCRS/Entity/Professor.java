package com.StudentCourseRegistrationSystem.SCRS.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Professor {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ProfessorId;
    @Column(nullable = false)
    private String name;
    @Column(unique = true,nullable = false)
    @Email(message = "Invalid email format")
    private String email;
    @Column(nullable = false)
    private String password;


    @OneToMany(mappedBy = "professor",cascade = CascadeType.ALL)
    private List<Course> courses=new ArrayList<>();















    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
