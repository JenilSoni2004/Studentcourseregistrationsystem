package com.StudentCourseRegistrationSystem.SCRS.Service;

import javax.crypto.SecretKey;

public interface JWTService {
    String generateToken(String username, String role);
    SecretKey getKey();
    String extractRole(String token);
    String extractUserName(String token);
    boolean validateToken(String token, org.springframework.security.core.userdetails.UserDetails userDetails);
}
