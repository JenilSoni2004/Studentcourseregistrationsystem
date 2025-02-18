package com.StudentCourseRegistrationSystem.SCRS.Service;

import com.StudentCourseRegistrationSystem.SCRS.DTO.CourseDTO;
import com.StudentCourseRegistrationSystem.SCRS.DTO.CoursePreferenceDTO;
import com.StudentCourseRegistrationSystem.SCRS.DTO.StudentDTO;
import com.StudentCourseRegistrationSystem.SCRS.Entity.*;
import com.StudentCourseRegistrationSystem.SCRS.Repository.*;
import com.StudentCourseRegistrationSystem.SCRS.Security.AdminConfig;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements UserDetailsService,AdminService{

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private ProfessorRepository professorrepository;
    @Autowired
    private SemesterRepository semesterRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Value("${spring.mail.username}")
    private String sender;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private AdminConfig adminConfig;


    private static final PasswordEncoder passwordencoder =new BCryptPasswordEncoder();


    @PostConstruct
    public void initAdminUser() {
        System.out.println("Initializing admin user...");
        if (adminRepository.findByUsername("admin") == null) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword(passwordencoder.encode("admin123"));
            adminRepository.save(admin);
            System.out.println("Admin user created.");
        } else {
            System.out.println("Admin user already exists.");
        }
    }





    @Override
    public void CreateProfessor(Professor professor)
    {
        professor.setPassword(passwordencoder.encode(professor.getPassword()));
        professorrepository.save(professor);
    }
    @Override
    public void CreateSemester(Semester semester)
    {
        semesterRepository.save(semester);
    }

    //Get Data
    @Override
    public Optional<Semester> Findsemesterbyid(Long id)
    {
        return semesterRepository.findById(id);
    }

    @Override
    public void allocate() {

            List<Student> students = studentRepository.findAllByOrderBySubmissionTimeAsc();

            for (int round = 0; round < 3; round++) {
                for (Student student : students) {
                    List<Course> preferredCourses = student.getPreferredCourses();
                    if (preferredCourses == null) continue;

                    for (Course course : preferredCourses) {
                        if (course.getRemainingseat() == 0 || student.getAssignedCourses().contains(course)) {
                            continue;
                        }

                        student.getAssignedCourses().add(course);
                        course.setRemainingseat(course.getRemainingseat() - 1);
                        courseRepository.save(course);
                        break;
                    }
                }
            }
        }




    @Override
    public boolean deletestudentbyid(Long studentId) {
        if (studentRepository.existsById(studentId)) {
            studentRepository.deleteById(studentId);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteProfessorById(Long id) {
        if (professorrepository.existsById(id))
        {
            professorrepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean deleteSemesterById(Long id) {
        if (semesterRepository.existsById(id)) {
            semesterRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public Course createCourse(CourseDTO courseDTO) {
        Professor professor = professorrepository.findById(courseDTO.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor not found with ID: " + courseDTO.getProfessorId()));
        Semester semester=semesterRepository.findById(courseDTO.getSemesterId()).orElseThrow(() -> new RuntimeException("Semseter not found with ID: " + courseDTO.getSemesterId()));
        Course course = new Course();
        course.setCoursename(courseDTO.getCourseName());
        course.setCapacity(courseDTO.getCapacity());
        course.setRemainingseat(courseDTO.getCapacity());
        course.setCoursecredit(courseDTO.getCourseCredit());
        course.setProfessor(professor);
        course.setSemester(semester);

        return courseRepository.save(course);
    }
    @Override
    public void CreateStudent(StudentDTO student) {


        // Check if the username already exists
        if (studentRepository.existsByUsername(student.getUsername())) {
            throw new IllegalArgumentException("Username already exists! Please choose a different username.");
        }
        Student student1=new Student();
        student1.setName(student.getName());
        student1.setUsername(student.getUsername());

        String email = student.getEmail();
        if (email == null || !email.matches("^[a-zA-Z0-9._%+-]+@daiict\\.ac\\.in$")) {
            throw new IllegalArgumentException("Invalid email! It must be a valid DAIICT email address.");
        }
        else
        {
            student1.setEmail(student.getEmail());
        }
        student1.setPassword(passwordencoder.encode(student.getPassword()));
        studentRepository.save(student1);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Optional<Admin> admin = adminRepository.findByUsername(username);
        Optional<Student> student = studentRepository.findByUsername(username);
        Optional<Professor> professor = professorrepository.findByEmail(username);



        if (admin.isPresent()) {

            return User.builder()
                    .username(admin.get().getUsername())
                    .password(admin.get().getPassword())
                    .roles("ADMIN")
                    .build();
        }
        else if (student.isPresent()) {

            return User.builder()
                    .username(student.get().getUsername())
                    .password(student.get().getPassword())
                    .roles("STUDENT")
                    .build();
        }
        else if (professor.isPresent()) {  // <-- Fix potential null issue

            return User.builder()
                    .username(professor.get().getEmail())
                    .password(professor.get().getPassword())
                    .roles("PROFESSOR")
                    .build();
        }


        throw new UsernameNotFoundException("User not found with username " + username);
    }



    @Override
    public void sendEmailToAllStudents(String subject, String messageBody) {
        List<Student> students = studentRepository.findAll();

        for (Student student : students) {
            sendEmail(student.getEmail(), subject, messageBody);
        }
    }
    @Override
    public void sendEmail(String recipient, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender); // Change this to your email
        mailMessage.setTo(recipient);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
    public void sendCourseRegistrationEmailToAll() {
        String subject = "Course Registration Deadline Reminder!";
        String message = """
                Dear Student,
                
                This is a reminder that course registration is now open.
                Please complete your course selection before the deadline.
                
                🚨 **Deadline: Today by 4:00 PM** 🚨
                
                Visit the Student Course Registration Portal and submit your choices before the deadline.
                
                Best Regards,  
                DAIICT Admin
                """;

        sendEmailToAllStudents(subject, message);
    }
    @Override
    public void sendCourseAllocationEmails() {
        // Fetch all students who have been allocated at least one course
        List<Student> allocatedStudents = studentRepository.findAll()
                .stream()
                .filter(student -> !student.getAssignedCourses().isEmpty())
                .toList();

        for (Student student : allocatedStudents) {
            sendEmailToStudent(student);
        }
    }

    @Override
    public void sendEmailToStudent(Student student) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(student.getEmail());
            helper.setSubject("Course Allocation Confirmation");

            String courseList = student.getAssignedCourses().stream()
                    .map(Course::getCoursename)
                    .collect(Collectors.joining("</li><li>"));

            String content = "<p>Dear " + student.getName() + ",</p>"
                    + "<p>You have been successfully allocated the following courses:</p>"
                    + "<ul><li>" + courseList + "</li></ul>"
                    + "<p>Best Regards,<br>Student Course Registration System</p>";

            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle properly in production
        }
    }




}