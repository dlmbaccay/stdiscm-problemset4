package com.garynation.grade_service.repository;

import com.garynation.grade_service.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    // You can add custom queries here if needed
}
