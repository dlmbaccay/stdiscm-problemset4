package com.garynation.enrollment_service.controller;

import com.garynation.enrollment_service.entity.Enrollment;
import com.garynation.enrollment_service.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }

    @PostMapping
    public Enrollment enrollStudentInCourse(@RequestParam Long studentId, @RequestParam Long courseId) {
        return enrollmentService.enrollStudentInCourse(studentId, courseId);
    }

    @DeleteMapping("/{id}")
    public void cancelEnrollment(@PathVariable Long id) {
        enrollmentService.cancelEnrollment(id);
    }
}