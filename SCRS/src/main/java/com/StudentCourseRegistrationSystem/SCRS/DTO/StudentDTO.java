package com.StudentCourseRegistrationSystem.SCRS.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;

public class StudentDTO {

    @Column( nullable = false)
    private String Name;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String Password;
    @Email
    private String email;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
