package com.amanrao.simplified_lms.service.impl;

import com.amanrao.simplified_lms.model.Course;
import com.amanrao.simplified_lms.model.User;
import com.amanrao.simplified_lms.repository.CourseRepository;
import com.amanrao.simplified_lms.repository.EnrollmentRepository;
import com.amanrao.simplified_lms.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public List<Course> getCoursesByInstructor(User instructor) {
        return courseRepository.findByInstructor(instructor);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID: " + id));
    }

    @Override
    public Page<Course> getAvailableCourses(User student, int page, int size) {
        List<Long> enrolledIds = enrollmentRepository.findByStudent(student)
                .stream().map(e -> e.getCourse().getId()).toList();

        Pageable pageable = PageRequest.of(page, size);

        if (enrolledIds.isEmpty()) {
            return courseRepository.findAll(pageable); // no enrollments, return all courses
        } else {
            return courseRepository.findByIdNotIn(enrolledIds, pageable);
        }
    }

    @Override
    public Page<Course> searchAvailableCourses(User student, String keyword, int page, int size) {
        List<Long> enrolledIds = enrollmentRepository.findByStudent(student)
                .stream().map(e -> e.getCourse().getId()).toList();

        Pageable pageable = PageRequest.of(page, size);
        if (enrolledIds.isEmpty()) {
            return courseRepository.findByTitleContainingIgnoreCase(keyword, pageable);
        } else {
            return courseRepository.findByTitleContainingIgnoreCaseAndIdNotIn(keyword, enrolledIds, pageable);
        }
    }




}
