package com.amanrao.simplified_lms.repository;

import com.amanrao.simplified_lms.model.Enrollment;
import com.amanrao.simplified_lms.model.User;
import com.amanrao.simplified_lms.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStudent(User student);

    Optional<Enrollment> findByStudentAndCourse(User student, Course course);
}
