package com.garynation.stdiscm.problemset4.enrollmentservice.repository;

import com.garynation.stdiscm.problemset4.enrollmentservice.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    List<Enrollment> findByCourseId(UUID courseId);
    List<Enrollment> findByUserId(UUID userId);
}
