package com.garynation.enrollment_service.repository;

import com.garynation.enrollment_service.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    // You can add custom queries here if needed
}