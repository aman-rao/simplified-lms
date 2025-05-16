package com.amanrao.simplified_lms.repository;

import com.amanrao.simplified_lms.model.Assignment;
import com.amanrao.simplified_lms.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByCourse(Course course);
}
