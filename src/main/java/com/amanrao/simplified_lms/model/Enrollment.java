package com.amanrao.simplified_lms.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many enrollments can be linked to one course
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    // Many enrollments can belong to one student
    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    private LocalDateTime enrolledAt;
}
