package com.amanrao.simplified_lms.service.impl;

import com.amanrao.simplified_lms.model.Course;
import com.amanrao.simplified_lms.model.Enrollment;
import com.amanrao.simplified_lms.model.User;
import com.amanrao.simplified_lms.repository.EnrollmentRepository;
import com.amanrao.simplified_lms.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    @Override
    public void enrollStudentInCourse(User student, Course course) {
        if (!isEnrolled(student, course)) {
            Enrollment enrollment = Enrollment.builder()
                    .student(student)
                    .course(course)
                    .enrolledAt(LocalDateTime.now())
                    .build();
            enrollmentRepository.save(enrollment);
        }
    }

//    @Override
//    public List<Enrollment> getEnrollmentsForStudent(User student) {
//        return enrollmentRepository.findByStudent(student);
//    }

    @Override
    public boolean isEnrolled(User student, Course course) {
        return enrollmentRepository.findByStudentAndCourse(student, course).isPresent();
    }

    @Override
    public Page<Enrollment> getEnrollmentsForStudent(User student, Pageable pageable) {
        return enrollmentRepository.findByStudent(student, pageable);
    }

    @Override
    public List<Enrollment> getEnrollmentsForCourse(Course course) {
        return enrollmentRepository.findByCourse(course);
    }


}
