package com.StudentCourseRegistrationSystem.SCRS.Service;

import com.StudentCourseRegistrationSystem.SCRS.Entity.Student;
import com.StudentCourseRegistrationSystem.SCRS.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedBackServiceImpl implements FeedBackService{
    @Autowired
    private StudentRepository studentRepository;
    @Value("${spring.mail.username}") private String sender;
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void sendFeedbackRequestEmails() {
        List<Student> students = studentRepository.findAll();

        for (Student student : students) {
            sendFeedbackEmail(student);
        }
    }


    public void sendEmail(String recipient, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender); // Change this to your email
        mailMessage.setTo(recipient);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
    @Override
    public void sendFeedbackEmail(Student student) {
        String subject = "Your Feedback Matters! 📢";
        String message = """
            Dear %s,

            The semester has ended, and we value your feedback.
            Please take a moment to rate your courses and professors.

            Please go to the system and submit your review;

            Best Regards,
            Student Course Registration System
            """.formatted(student.getName());

        sendEmail(student.getEmail(), subject, message);
    }

}
