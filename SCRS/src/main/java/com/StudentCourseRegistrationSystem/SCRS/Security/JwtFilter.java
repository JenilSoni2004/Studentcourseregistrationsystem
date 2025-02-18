package com.StudentCourseRegistrationSystem.SCRS.Security;

import com.StudentCourseRegistrationSystem.SCRS.Service.AdminServiceImpl;
import com.StudentCourseRegistrationSystem.SCRS.Service.JWTServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = Logger.getLogger(JwtFilter.class.getName());

    @Autowired
    private JWTServiceImpl jwtService;

    @Autowired
    private AdminServiceImpl userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");
            String token = null;
            String username = null;

            // Check if the Authorization header is present and starts with "Bearer "
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7); // Extract token after "Bearer "
                username = jwtService.extractUserName(token); // Extract username from token
            }

            // Validate the token and authenticate the user if it's valid
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Load user details using AdminService
                UserDetails userDetails = userService.loadUserByUsername(username);

                if (jwtService.validateToken(token, userDetails)) {
                    // Create authentication token
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    // Attach details from the request to the authentication token
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the authentication in the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    throw new SecurityException("Invalid JWT Token");
                }
            }

            // Proceed with the filter chain (continue processing the request)
            filterChain.doFilter(request, response);

        } catch (SecurityException e) {
            LOGGER.log(Level.WARNING, "Authentication error: {0}", e.getMessage());
            handleError(response, "Invalid or expired token.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error in JWT filter", e);
            handleError(response, "An unexpected error occurred.");
        }
    }

    private void handleError(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Unauthorized");
        errorResponse.put("message", message);
        errorResponse.put("status", HttpServletResponse.SC_UNAUTHORIZED);

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
