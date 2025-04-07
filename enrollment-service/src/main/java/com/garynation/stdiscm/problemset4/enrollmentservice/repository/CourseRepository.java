package com.garynation.stdiscm.problemset4.enrollmentservice.repository;

import com.garynation.stdiscm.problemset4.enrollmentservice.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
}
