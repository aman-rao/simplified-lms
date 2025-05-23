package com.amanrao.simplified_lms.repository;

import com.amanrao.simplified_lms.model.Course;
import com.amanrao.simplified_lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByInstructor(User instructor);
    Page<Course> findByIdNotIn(List<Long> ids, Pageable pageable);
    Page<Course> findByTitleContainingIgnoreCaseAndIdNotIn(String keyword, List<Long> ids, Pageable pageable);
    Page<Course> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

}
