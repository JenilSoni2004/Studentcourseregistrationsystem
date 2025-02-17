package com.StudentCourseRegistrationSystem.SCRS.Controller;

import com.StudentCourseRegistrationSystem.SCRS.Entity.login;
import com.StudentCourseRegistrationSystem.SCRS.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/JWT Login")
public class LoginController {

    @Autowired
    private UserServiceImpl userService;  // Renamed for clarity

    @PostMapping("/login")
    public String login(@RequestBody login loginRequest) {
        return userService.verify(loginRequest.getUsername(), loginRequest.getPassword());
    }
}
