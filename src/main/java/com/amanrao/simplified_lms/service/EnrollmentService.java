package com.amanrao.simplified_lms.service;

import com.amanrao.simplified_lms.model.Course;
import com.amanrao.simplified_lms.model.Enrollment;
import com.amanrao.simplified_lms.model.User;

import java.util.List;

public interface EnrollmentService {
    void enrollStudentInCourse(User student, Course course);
    List<Enrollment> getEnrollmentsForStudent(User student);
    boolean isEnrolled(User student, Course course);
}
