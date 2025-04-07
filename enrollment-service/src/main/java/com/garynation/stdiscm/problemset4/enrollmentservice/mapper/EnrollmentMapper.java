package com.garynation.stdiscm.problemset4.enrollmentservice.mapper;

import com.garynation.stdiscm.problemset4.enrollmentservice.dto.EnrollmentDto;
import com.garynation.stdiscm.problemset4.enrollmentservice.entity.Enrollment;

public class EnrollmentMapper {

    public static EnrollmentDto mapToEnrollmentDto(Enrollment enrollment) {
        return new EnrollmentDto(
                enrollment.getId(),
                enrollment.getUserId(),
                enrollment.getCourseId()
        );
    }

    public static Enrollment mapToEnrollment(EnrollmentDto enrollmentDto) {
        return new Enrollment(
                enrollmentDto.getId(),
                enrollmentDto.getUserId(),
                enrollmentDto.getCourseId()
        );
    }
}
