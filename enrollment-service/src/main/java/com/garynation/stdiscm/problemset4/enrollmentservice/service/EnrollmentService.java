package com.garynation.stdiscm.problemset4.enrollmentservice.service;

import com.garynation.stdiscm.problemset4.enrollmentservice.dto.CourseDto;
import com.garynation.stdiscm.problemset4.enrollmentservice.dto.EnrollmentDto;

import java.util.List;
import java.util.UUID;

public interface EnrollmentService {
    EnrollmentDto addEnrollment(EnrollmentDto enrollmentDto);
    EnrollmentDto deleteEnrollment(UUID facultyId, UUID enrollmentId);
    EnrollmentDto getEnrollmentById(UUID enrollmentId);
    List<EnrollmentDto> getAllEnrollments();
    List<EnrollmentDto> getEnrollmentsByCourseId(UUID courseId);
    List<EnrollmentDto> getEnrollmentsByUserId(UUID userId);
}
