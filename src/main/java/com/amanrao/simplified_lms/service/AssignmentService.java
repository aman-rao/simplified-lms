package com.amanrao.simplified_lms.service;

import com.amanrao.simplified_lms.model.Assignment;
import com.amanrao.simplified_lms.model.Course;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AssignmentService {
    Assignment saveAssignment(Assignment assignment, MultipartFile file) throws IOException;
    List<Assignment> getAssignmentsByCourse(Course course);
}
