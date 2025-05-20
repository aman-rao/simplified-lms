package com.amanrao.simplified_lms.service;

import com.amanrao.simplified_lms.model.Course;
import com.amanrao.simplified_lms.model.User;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


public interface CourseService {
    Course saveCourse(Course course);
    List<Course> getCoursesByInstructor(User instructor);
    List <Course> getAllCourses();
    Course getCourseById(Long id);
    Page<Course> getAvailableCourses(User student, int page, int size);
    Page<Course> searchAvailableCourses(User student, String keyword, int page, int size);

}
