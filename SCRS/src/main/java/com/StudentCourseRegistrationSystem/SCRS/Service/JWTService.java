package com.StudentCourseRegistrationSystem.SCRS.Service;

import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;

public interface JWTService {
    String generateToken(String username);
    SecretKey getKey();
    String extractUserName(String token);
    boolean validateToken(String token, UserDetails userDetails);
}
