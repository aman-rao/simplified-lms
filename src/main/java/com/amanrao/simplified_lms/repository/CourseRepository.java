package com.amanrao.simplified_lms.repository;

import com.amanrao.simplified_lms.model.Course;
import com.amanrao.simplified_lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByInstructor(User instructor);
}
