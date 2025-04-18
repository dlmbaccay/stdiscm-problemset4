package com.garynation.stdiscm.problemset4.enrollmentservice.service;

import java.util.List;
import java.util.UUID;

import com.garynation.stdiscm.problemset4.enrollmentservice.dto.EnrollmentDto;

public interface EnrollmentService {
    EnrollmentDto addEnrollment(EnrollmentDto enrollmentDto);
    EnrollmentDto deleteEnrollment(UUID enrollmentId);
    EnrollmentDto getEnrollmentById(UUID enrollmentId);
    List<EnrollmentDto> getAllEnrollments();
    List<EnrollmentDto> getEnrollmentsByCourseId(UUID courseId);
    List<EnrollmentDto> getEnrollmentsByUserId(UUID userId);
}
