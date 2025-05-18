package com.amanrao.simplified_lms.controller;

import com.amanrao.simplified_lms.model.Course;
import com.amanrao.simplified_lms.model.Enrollment;
import com.amanrao.simplified_lms.model.User;
import com.amanrao.simplified_lms.security.CustomUserDetails;
import com.amanrao.simplified_lms.service.CourseService;
import com.amanrao.simplified_lms.service.EnrollmentService;
import com.amanrao.simplified_lms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User student = userService.findByEmail(userDetails.getUsername());
        List<Course> availableCourses = courseService.getAllCourses();
        List<Course> enrolledCourses = enrollmentService.getEnrollmentsForStudent(student)
                .stream()
                .map(Enrollment::getCourse)
                .toList();

        availableCourses.removeAll(enrolledCourses);

        model.addAttribute("student", student);
        model.addAttribute("availableCourses", availableCourses);
        model.addAttribute("enrolledCourses", enrolledCourses);

        return "student/dashboard";
    }

    @PostMapping("/enroll")
    public String enroll(@RequestParam Long courseId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User student = userService.findByEmail(userDetails.getUsername());
        Course course = courseService.getCourseById(courseId);
        enrollmentService.enrollStudentInCourse(student, course);
        return "redirect:/student/dashboard";
    }

    @GetMapping("/course/{courseId}")
    public String viewCourse(@PathVariable Long courseId,
                             @AuthenticationPrincipal CustomUserDetails userDetails,
                             Model model) {
        User student = userService.findByEmail(userDetails.getUsername());
        Course course = courseService.getCourseById(courseId);

        // Ensure the student is enrolled
        boolean isEnrolled = enrollmentService.isEnrolled(student, course);
        if (!isEnrolled) {
            return "redirect:/student/dashboard?error=accessDenied";
        }

        model.addAttribute("course", course);
        return "student/course_details";
    }

}
