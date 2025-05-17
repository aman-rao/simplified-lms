package com.amanrao.simplified_lms.service;

import com.amanrao.simplified_lms.model.Course;
import com.amanrao.simplified_lms.model.User;

import java.util.List;

public interface CourseService {
    Course saveCourse(Course course);
    List<Course> getCoursesByInstructor(User instructor);
    List <Course> getAllCourses();
    Course getCourseById(Long id);
}
