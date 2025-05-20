package com.amanrao.simplified_lms.controller;

import com.amanrao.simplified_lms.model.Course;
import com.amanrao.simplified_lms.model.Enrollment;
import com.amanrao.simplified_lms.model.User;
import com.amanrao.simplified_lms.security.CustomUserDetails;
import com.amanrao.simplified_lms.service.CourseService;
import com.amanrao.simplified_lms.service.EnrollmentService;
import com.amanrao.simplified_lms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails userDetails,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size,
                            Model model) {

        User student = userService.findByEmail(userDetails.getUsername());

        Page<Course> availableCourses = courseService.getAvailableCourses(student, page, size);
        Page<Enrollment> enrolledCourses = enrollmentService.getEnrollmentsForStudent(student, PageRequest.of(page, size));


        model.addAttribute("student", student);
        model.addAttribute("enrolledCourses", enrolledCourses);
        model.addAttribute("availableCourses", availableCourses.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", availableCourses.getTotalPages());

        return "student/dashboard";
    }


    @PostMapping("/enroll")
    public String enroll(@RequestParam Long courseId,
                         @AuthenticationPrincipal CustomUserDetails userDetails,
                         RedirectAttributes redirectAttributes) {
        User student = userService.findByEmail(userDetails.getUsername());
        Course course = courseService.getCourseById(courseId);

        // Check if already enrolled
        if (enrollmentService.isEnrolled(student, course)) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are already enrolled in this course.");
            return "redirect:/student/dashboard";
        }

        enrollmentService.enrollStudentInCourse(student, course);
        redirectAttributes.addFlashAttribute("successMessage", "Successfully enrolled in " + course.getTitle() + "!");
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
