package com.StudentCourseRegistrationSystem.SCRS.Service;

import com.StudentCourseRegistrationSystem.SCRS.Entity.Student;

public interface FeedBackService {
    public void sendFeedbackRequestEmails();
    public void sendFeedbackEmail(Student student);

}
