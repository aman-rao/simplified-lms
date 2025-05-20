package com.amanrao.simplified_lms.service;

import com.amanrao.simplified_lms.model.Course;
import com.amanrao.simplified_lms.model.Enrollment;
import com.amanrao.simplified_lms.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EnrollmentService {
    void enrollStudentInCourse(User student, Course course);
    boolean isEnrolled(User student, Course course);
    Page<Enrollment> getEnrollmentsForStudent(User student, Pageable pageable);
    List<Enrollment> getEnrollmentsForCourse(Course course);


}
