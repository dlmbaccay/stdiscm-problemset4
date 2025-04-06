package com.garynation.course_service.repository;

import com.garynation.course_service.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    // You can add custom queries here, if needed
}