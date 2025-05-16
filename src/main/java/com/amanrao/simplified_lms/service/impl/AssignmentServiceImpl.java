package com.amanrao.simplified_lms.service.impl;

import com.amanrao.simplified_lms.model.Assignment;
import com.amanrao.simplified_lms.model.Course;
import com.amanrao.simplified_lms.repository.AssignmentRepository;
import com.amanrao.simplified_lms.service.AssignmentService;
import com.amanrao.simplified_lms.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final FileStorageService fileStorageService;

    @Override
    public Assignment saveAssignment(Assignment assignment, MultipartFile file) throws IOException {
        String filename = fileStorageService.storeFile(file);
        assignment.setFilePath(filename);
        return assignmentRepository.save(assignment);
    }

    @Override
    public List<Assignment> getAssignmentsByCourse(Course course) {
        return assignmentRepository.findByCourse(course);
    }
}
