package com.amanrao.simplified_lms.controller;

import com.amanrao.simplified_lms.model.Assignment;
import com.amanrao.simplified_lms.model.Course;
import com.amanrao.simplified_lms.model.User;
import com.amanrao.simplified_lms.service.AssignmentService;
import com.amanrao.simplified_lms.service.CourseService;
import com.amanrao.simplified_lms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/instructor")
@RequiredArgsConstructor
public class InstructorController {

    private final CourseService courseService;
    private final AssignmentService assignmentService;
    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // current user email
        User instructor = userService.findByEmail(email);

        List<Course> courses = courseService.getCoursesByInstructor(instructor);
        model.addAttribute("courses", courses);
        model.addAttribute("newCourse", new Course());
        return "instructor/dashboard";
    }


    @PostMapping("/create-course")
    public String createCourse(@ModelAttribute Course course) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User instructor = userService.findByEmail(email);

        course.setInstructor(instructor);
        courseService.saveCourse(course);

        return "redirect:/instructor/dashboard";
    }

    @GetMapping("/course/{courseId}")
    public String courseDetail(@PathVariable Long courseId, Model model) {
        Course course = new Course();
        course.setId(courseId);
        List<Assignment> assignments = assignmentService.getAssignmentsByCourse(course);
        model.addAttribute("assignments", assignments);
        model.addAttribute("courseId", courseId);
        model.addAttribute("assignment", new Assignment());
        return "instructor/course-detail";
    }

    @PostMapping("/course/{courseId}/upload-assignment")
    public String uploadAssignment(@PathVariable Long courseId,
                                   @ModelAttribute Assignment assignment,
                                   @RequestParam("file") MultipartFile file) throws IOException {
        Course course = new Course();
        course.setId(courseId);
        assignment.setCourse(course);
        assignmentService.saveAssignment(assignment, file);
        return "redirect:/instructor/course/" + courseId;
    }
}
